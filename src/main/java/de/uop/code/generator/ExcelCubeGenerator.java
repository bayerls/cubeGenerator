package de.uop.code.generator;

import de.uop.code.generator.domain.Cube;
import de.uop.code.generator.domain.Dimension;
import de.uop.code.generator.domain.Measure;
import de.uop.code.generator.domain.Observation;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Basti on 04/02/14.
 */
public class ExcelCubeGenerator {

    private static final int MEASURE_COLUMN = 3;

    public Cube getCube(File file) {
        Cube cube = new Cube();
        cube.setLabel(file.getName());
        cube.setDescription(Configuration.DESCRIPTION);
        cube.setAuth(Configuration.AUTH);
        Workbook wb = getWorkbook(file);
        Sheet sheet = wb.getSheetAt(0);

        int rCounter = 0;
        Row r = sheet.getRow(rCounter);

        while (r != null) {
            int cCounter = 0;
            Cell c = r.getCell(cCounter);
            Observation observation = new Observation();

            // Don't add an observation if the headers are parsed
            if (rCounter != 0) {
                cube.getObservations().add(observation);
            }

            while (c != null) {
                c.setCellType(Cell.CELL_TYPE_STRING);
                String value = c.getStringCellValue();

                if (rCounter == 0 && cCounter == MEASURE_COLUMN) {
                    Measure measure = new Measure();
                    measure.setLabel(value);
                    cube.getDatasetStructureDefinition().getMeasures().add(measure);
                } else if (rCounter == 0) {
                    Dimension dimension = new Dimension();
                    dimension.setLabel(value);
                    cube.getDatasetStructureDefinition().getDimensions().add(dimension);
                } else {
                    observation.getValues().add(value);
                }

                cCounter++;
                c = r.getCell(cCounter);
            }

            rCounter++;
            r = sheet.getRow(rCounter);
        }

        return cube;
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

        public void printStructure(Cube cube) {
        for (Dimension dimension : cube.getDatasetStructureDefinition().getDimensions()) {
            System.out.print(dimension.getLabel() + "  ");
        }

        for (Measure measure : cube.getDatasetStructureDefinition().getMeasures()) {
            System.out.print(measure.getLabel() + "  ");
        }

        System.out.println();
    }

        public void printObservations(Cube cube) {
        for (Observation observation : cube.getObservations()) {
            for (String value : observation.getValues()) {
                System.out.print(value + "  ");
            }
            System.out.println();
        }
    }


}
