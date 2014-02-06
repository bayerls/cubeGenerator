package de.uop.code.generator.domain;

import de.uop.code.generator.Configuration;

/**
 * Created by Sebastian on 21.01.14.
 */
public class Dimension extends Component {

    public String getPrefixedLabel() {
        return Configuration.PREFIX_DIMENSION + getLabel();
    }


}
