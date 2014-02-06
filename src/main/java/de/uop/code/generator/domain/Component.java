package de.uop.code.generator.domain;

/**
 * Created by Basti on 24/01/14.
 */
public abstract class Component {

    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public abstract String getPrefixedLabel();

}
