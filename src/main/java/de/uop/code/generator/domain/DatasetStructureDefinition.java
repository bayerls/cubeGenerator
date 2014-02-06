package de.uop.code.generator.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian on 21.01.14.
 */
public class DatasetStructureDefinition {

    private List<Dimension> dimensions = new ArrayList<Dimension>();
    private List<Measure> measures = new ArrayList<Measure>();

    public List<Dimension> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }
}
