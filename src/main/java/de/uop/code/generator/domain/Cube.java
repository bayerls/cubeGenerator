package de.uop.code.generator.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian on 21.01.14.
 */
public class Cube {

    private String label;
    private String description;
    private String auth;
    private DatasetStructureDefinition datasetStructureDefinition = new DatasetStructureDefinition();
    private List<Observation> observations = new ArrayList<Observation>();

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public DatasetStructureDefinition getDatasetStructureDefinition() {
        return datasetStructureDefinition;
    }

    public void setDatasetStructureDefinition(DatasetStructureDefinition datasetStructureDefinition) {
        this.datasetStructureDefinition = datasetStructureDefinition;
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }
}
