package de.uop.code.generator;

import java.util.concurrent.TimeUnit;

/**
 * Created by Sebastian on 21.01.14.
 */
public class Configuration {

    // Endpoints
    public static final String ENDPOINT_BIGDATA = "http://zaire.dimis.fim.uni-passau.de:8181/bigdata/sparql";
    // public static final String ENDPOINT_BIGDATA = "http://localhost:8080/bigdata/sparql";

    // Time unit for log
    public static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
    public static final String TIME_UNIT_SUFFIX = " ms";

    // Batch size for simultaneously generated observations
    // Higher -> fewer bigdata calls but bigger local jena model
    public static final int BATCH_SIZE = 100;

    // Parameter for the cube
    public static int NR_DIMS = 6;
    public static int NR_MEAS = 1;
    public static int NR_OBS = 5;
    public static int OBS_OFFSET = 50;

    // Metadata for the cube
    public final static String AUTH = "8023903";//"cubeGenerator";
    public final static String DESCRIPTION = "Generated with the CubeGenerator";
    public final static String VERSION = "codeCube/1.1";
    public final static String RELATION = "Cube was generated with the CubeGenerator";
    public final static String SOURCE = "no source available";

    // Some constants
    public final static String SPACER = "-";
    public final static String PREFIX_MEASURE = "m-";
    public final static String PREFIX_DIMENSION = "d-";

}
