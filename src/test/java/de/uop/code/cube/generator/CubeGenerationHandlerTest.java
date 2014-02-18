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


    /**
     * Generates a generic set of cubes. The Parameter can be adjusted using the Configuration class.
     *
     * @throws Exception
     */
    @Test
    public void testGenerate() throws Exception {
        Configuration.NR_DIMS = 20;
        Configuration.NR_MEAS = 10;
        Configuration.NR_OBS = 1;
        Configuration.OBS_OFFSET = 0;

        int cubes = 250;
        int maxRun = 5;

        // Outer loop for different auth parameter
        for (int run = 1; run <= maxRun; run++) {
            Configuration.AUTH = "" + run;

            // Inner loop for the actual cube generation
            for (int i = 0; i < cubes * run; i++) {
                CubeGenerationHandler cubeGenerationHandler = new CubeGenerationHandler();
                cubeGenerationHandler.generate();
            }
        }
    }

    /**
     * Generates cubes from Excel files.
     *
     * @throws Exception
     */
    @Test
    public void testGenerateFromExcel() throws Exception {
        File folder = new File(PATH_OUTPUT);
        File[] files = folder.listFiles();

        for (File file : files) {
            // ignore this file
            if (!file.getName().equals(".DS_Store")) {
                CubeGenerationExcelHandler cubeGenerationExcelHandler = new CubeGenerationExcelHandler();
                cubeGenerationExcelHandler.generate(file);
            }
        }
    }
}
