package de.uop.code.generator;

import de.uop.code.generator.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian on 21.01.14.
 */
public class CubeGenerator {

    private long dimPrefixCounter = 0;
    private long measPrefixCounter = 0;
    private long obsCounter = 0;

    private Cube cube;
//
//
//    public void printStructure() {
//        for (Dimension dimension : cube.getDatasetStructureDefinition().getDimensions()) {
//            System.out.print(dimension.getPrefixedLabel() + "  ");
//        }
//
//        for (Measure measure : cube.getDatasetStructureDefinition().getMeasures()) {
//            System.out.print(measure.getPrefixedLabel() + "  ");
//        }
//
//        System.out.println();
//
//    }
//
//    public void printObservations() {
//        for (Observation observation : cube.getObservations()) {
//            for (String value : observation.getValues()) {
//                System.out.print(value + "  ");
//            }
//            System.out.println();
//        }
//    }

    public List<Observation> generateNextObservations(){
        List<Observation> observations = new ArrayList<Observation>();
        int i = 0;

        while (i < Configuration.BATCH_SIZE && obsCounter < Configuration.NR_OBS) {
            Observation observation = new Observation();

            for (Dimension dimension : cube.getDatasetStructureDefinition().getDimensions()) {
                observation.getValues().add(dimension.getPrefixedLabel() + Configuration.SPACER + (Configuration.OBS_OFFSET + obsCounter));
            }

            for (int j = 0; j < cube.getDatasetStructureDefinition().getMeasures().size(); j++) {
                observation.getValues().add(String.valueOf(Configuration.OBS_OFFSET + obsCounter));
            }

            observations.add(observation);

            // update counter
            i++;
            obsCounter++;
        }

        return observations;
    }

    public Cube generateCubeStructure() {
        cube = new Cube();
        cube.setDatasetStructureDefinition(generateDSD());
        String label = "d:" + Configuration.NR_DIMS + " m:" + Configuration.NR_MEAS + " o:" + Configuration.NR_OBS + " +" + Configuration.OBS_OFFSET;
        cube.setLabel(label);
        cube.setDescription(Configuration.DESCRIPTION);
        cube.setAuth(Configuration.AUTH);

        return cube;
    }

    private DatasetStructureDefinition generateDSD() {
        DatasetStructureDefinition datasetStructureDefinition = new DatasetStructureDefinition();
        datasetStructureDefinition.setDimensions(generateDimensions(Configuration.NR_DIMS));
        datasetStructureDefinition.setMeasures(generateMeasures(Configuration.NR_MEAS));

        return datasetStructureDefinition;
    }

    private List<Dimension> generateDimensions(int count) {
        List<Dimension> dimensions = new ArrayList<Dimension>(count);

        for (int i = 0; i < count; i++) {
            Dimension dimension = new Dimension();
            dimension.setLabel(String.valueOf(dimPrefixCounter));
            dimPrefixCounter++;
            dimensions.add(dimension);
        }

        return dimensions;
    }

    private List<Measure> generateMeasures(int count) {
        List<Measure> measures = new ArrayList<Measure>(count);

        for (int i = 0; i < count; i++) {
            Measure measure = new Measure();
            measure.setLabel(String.valueOf(measPrefixCounter));
            measPrefixCounter++;
            measures.add(measure);
        }

        return measures;
    }

}
