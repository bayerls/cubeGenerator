package de.uop.code.generator.vocabulary;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class CODE {

    protected static final String PREFIX = "code";
    protected static final String URI = "http://code-research.eu/resource/";

    private static Model m = ModelFactory.createDefaultModel();

    public static String getPrefix() {
        return PREFIX;
    }

    public static String getURI() {
        return URI;
    }

    public static final Resource ENTITY = m.createResource(URI + "Entity");

    public static final String DATASET = URI + "Dataset-";
    public static final String DSD = URI + "Dsd-";
    public static final String OBS = URI + "Obs-";

    // Used with PROV
    public static final String IMPORT = URI + "Import";
    public static final String IMPORTER = URI + "Importer";


}
