package de.uop.code.generator.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian on 21.01.14.
 */
public class Observation {

    private List<String> values = new ArrayList<String>();

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
