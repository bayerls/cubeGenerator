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


    /**
     * Generates the next batch of observations for the cube. Batch size can be defined in the configuration class.
     *
     * @return Returns the list of generated observations.
     */
    public List<Observation> generateNextObservations(){
        List<Observation> observations = new ArrayList<Observation>();
        int i = 0;

        // generate observations until batch size or total number is reached.
        while (i < Configuration.BATCH_SIZE && obsCounter < Configuration.NR_OBS) {
            Observation observation = new Observation();

            // generate the value for every dimension
            for (Dimension dimension : cube.getDatasetStructureDefinition().getDimensions()) {
                observation.getValues().add(dimension.getPrefixedLabel() + Configuration.SPACER + (Configuration.OBS_OFFSET + obsCounter));
            }

            // generate the value for every measure
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

    /**
     * Generate the basic cube with metadata and the structure.
     *
     * @return The cube with structure (without observations)
     */
    public Cube generateCubeStructure() {
        cube = new Cube();
        cube.setDatasetStructureDefinition(generateDSD());
        String label = "d:" + Configuration.NR_DIMS + " m:" + Configuration.NR_MEAS + " o:" + Configuration.NR_OBS + " +" + Configuration.OBS_OFFSET;
        cube.setLabel(label);
        cube.setDescription(Configuration.DESCRIPTION);
        cube.setAuth(Configuration.AUTH);

        return cube;
    }

    /**
     * Generate the structure components.
     *
     * @return The generated structure definition.
     */
    private DatasetStructureDefinition generateDSD() {
        DatasetStructureDefinition datasetStructureDefinition = new DatasetStructureDefinition();
        datasetStructureDefinition.setDimensions(generateDimensions(Configuration.NR_DIMS));
        datasetStructureDefinition.setMeasures(generateMeasures(Configuration.NR_MEAS));

        return datasetStructureDefinition;
    }

    private List<Dimension> generateDimensions(int count) {
        List<Dimension> dimensions = new ArrayList<Dimension>(count);

        // Generate the dimensions: Set the incremented prefix counter.
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
