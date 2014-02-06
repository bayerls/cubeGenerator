package de.uop.code.generator.vocabulary;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class VA {
    protected static final String PREFIX = "va";
    protected static final String URI = "http://code-research.eu/ontology/visual-analytics#";


    public static String getPrefix() {
        return PREFIX;
    }

    public static String getURI() {
        return URI;
    }

    private static Model m = ModelFactory.createDefaultModel();

    public static final Resource DIMENSION_NOMINAL = m.createResource(URI + "cubeDimensionNominal");
    public static final Resource MEASURE_NUMBER = m.createResource(URI + "cubeObservationNumber");


}
