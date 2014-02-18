package de.uop.code.converter;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to normalize excel files downloaded from http://epp.eurostat.ec.europa.eu/
 *
 * Created by Basti on 04/02/14.
 */
public class ExcelConverter {

    private static final String PATH = "/Users/Basti/Desktop/Work/2014-02-03 Bacon Datasets/Fishing/convert";
    private static final String PATH_OUTPUT = "/Users/Basti/Desktop/Work/2014-02-03 Bacon Datasets/Fishing/output/";
    private static final String[] COUNTRIES = {"Belgium", "Bulgaria", "Cyprus", "Germany", "Denmark", "Estonia", "Greece", "Spain", "Finland", "France", "Croatia", "Ireland", "Iceland", "Italy", "Lithuania", "Lativa", "Malta", "Netherlands", "Norway", "Poland", "Portugal", "Romania", "Sweden", "Slovenia", "United Kingdom"};

    /**
     * Normalize the excel files: One dimension/measure per column and add the country column.
     */
    public void convert() {
        File folder = new File(PATH);
        File[] files = folder.listFiles();

        if (files.length != COUNTRIES.length) {
            throw new IllegalStateException("files.length != COUNTRIES.length");
        }

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            createNewWorkbook(file, COUNTRIES[i]);
        }
    }

    private void createNewWorkbook(File file, String country) {
        Workbook wbInput = getWorkbook(file);

        List<String> dimensionYear = new ArrayList<String>();
        List<String> dimensionSpecies = new ArrayList<String>();
        List<String> measure = new ArrayList<String>();

        Sheet sheet = wbInput.getSheetAt(0);

        int rCounter = 0;
        Row r = sheet.getRow(rCounter);

        while (r != null) {
            int cCounter = 0;
            Cell c = r.getCell(cCounter);

            while (c != null) {
                c.setCellType(Cell.CELL_TYPE_STRING);
                String value = c.getStringCellValue();

                if (rCounter == 0 && cCounter > 0) {
                    dimensionYear.add(value);
                } else if (rCounter > 0 && cCounter == 0) {
                    dimensionSpecies.add(value);
                } else if (rCounter != 0 && cCounter != 0) {
                    measure.add(value);
                }

                cCounter++;
                c = r.getCell(cCounter);
            }

            rCounter++;
            r = sheet.getRow(rCounter);
        }


        XSSFWorkbook wbOutput = new XSSFWorkbook();
        XSSFSheet s = wbOutput.createSheet();

        int rowCounter = 0;

        Row row = s.createRow(rowCounter);
        row.createCell(0, Cell.CELL_TYPE_STRING).setCellValue("Country");
        row.createCell(1, Cell.CELL_TYPE_STRING).setCellValue("Species");
        row.createCell(2, Cell.CELL_TYPE_STRING).setCellValue("Year");
        row.createCell(3, Cell.CELL_TYPE_STRING).setCellValue("Euro");
        rowCounter++;

        int dimSpCounter = -1;

        for (int i = 0; i < measure.size(); i++) {
            int dimYCounter = i % dimensionYear.size();

            if (dimYCounter == 0) {
                dimSpCounter++;
            }

            row = s.createRow(rowCounter);
            row.createCell(0, Cell.CELL_TYPE_STRING).setCellValue(country);
            row.createCell(1, Cell.CELL_TYPE_STRING).setCellValue(dimensionSpecies.get(dimSpCounter));
            row.createCell(2, Cell.CELL_TYPE_STRING).setCellValue(dimensionYear.get(dimYCounter));
            row.createCell(3, Cell.CELL_TYPE_STRING).setCellValue(measure.get(i));
            rowCounter++;
        }

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(PATH_OUTPUT + file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            wbOutput.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Workbook getWorkbook(File filepath) {
        // load the file as a poi workbook
        InputStream inp = null;
        Workbook workbook = null;

        try {
            inp = new FileInputStream(filepath);
            workbook = WorkbookFactory.create(inp);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                inp.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return workbook;
    }

}
