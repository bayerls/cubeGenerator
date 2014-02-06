package de.uop.code.cube.generator;

import de.uop.code.generator.Configuration;
import de.uop.code.handler.CubeGenerationExcelHandler;
import de.uop.code.handler.CubeGenerationHandler;
import org.junit.Test;

import java.io.File;

/**
 * Created by Basti on 24/01/14.
 */
public class CubeGenerationHandlerTest {

    private static final String PATH_OUTPUT = "/Users/Basti/Desktop/Work/2014-02-03 Bacon Datasets/Fishing/output/";
    private static final String PATH_NO_COUNTRY = "/Users/Basti/Desktop/Work/2014-02-03 Bacon Datasets/Fishing/noCountry/";

    @Test
    public void testGenerate() throws Exception {

        Configuration.NR_DIMS = 6;
        Configuration.NR_MEAS = 2;
        Configuration.NR_OBS = 100;
        Configuration.OBS_OFFSET = 0;

        CubeGenerationHandler cubeGenerationHandler = new CubeGenerationHandler();
        cubeGenerationHandler.generate();


        Configuration.NR_DIMS = 8;
        Configuration.NR_MEAS = 2;
        Configuration.NR_OBS = 100;
        Configuration.OBS_OFFSET = 0;

        cubeGenerationHandler = new CubeGenerationHandler();
        cubeGenerationHandler.generate();

        Configuration.NR_DIMS = 10;
        Configuration.NR_MEAS = 2;
        Configuration.NR_OBS = 100;
        Configuration.OBS_OFFSET = 0;

        cubeGenerationHandler = new CubeGenerationHandler();
        cubeGenerationHandler.generate();


        Configuration.NR_DIMS = 12;
        Configuration.NR_MEAS = 2;
        Configuration.NR_OBS = 100;
        Configuration.OBS_OFFSET = 0;

        cubeGenerationHandler = new CubeGenerationHandler();
        cubeGenerationHandler.generate();
    }


    @Test
    public void testGenerateFromExcel() throws Exception {

        File folder = new File(PATH_OUTPUT);
        File[] files = folder.listFiles();

//        CubeGenerationExcelHandler cubeGenerationExcelHandler = new CubeGenerationExcelHandler();
//        cubeGenerationExcelHandler.generate(files[0]);


        for (File file : files) {
            if (!file.getName().equals(".DS_Store")) {
                CubeGenerationExcelHandler cubeGenerationExcelHandler = new CubeGenerationExcelHandler();
                cubeGenerationExcelHandler.generate(file);
            }

        }
    }

    @Test
    public void testGenerateFromExcelNoCountry() throws Exception {

        File folder = new File(PATH_NO_COUNTRY);
        File[] files = folder.listFiles();

//        CubeGenerationExcelHandler cubeGenerationExcelHandler = new CubeGenerationExcelHandler();
//        cubeGenerationExcelHandler.generate(files[0]);


        for (File file : files) {
            if (!file.getName().equals(".DS_Store")) {
                CubeGenerationExcelHandler cubeGenerationExcelHandler = new CubeGenerationExcelHandler();
                cubeGenerationExcelHandler.generate(file);
            }

        }
    }
}
