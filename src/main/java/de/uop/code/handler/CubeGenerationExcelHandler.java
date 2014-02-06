package de.uop.code.handler;

import com.google.common.base.Stopwatch;
import com.hp.hpl.jena.rdf.model.Model;
import de.uop.code.generator.*;
import de.uop.code.generator.domain.Cube;

import java.io.File;

/**
 * Created by Basti on 04/02/14.
 */
public class CubeGenerationExcelHandler {

    private ExcelCubeGenerator excelCubeGenerator = new ExcelCubeGenerator();
    private CubeModelGenerator cubeModelGenerator = new CubeModelGenerator();
    private ModelPersister modelPersister = new ModelPersister();

    public void generate(File file) {
        Stopwatch watch = Stopwatch.createStarted();
        String namedGraph = cubeModelGenerator.getNamedGraph();
        Cube cube = excelCubeGenerator.getCube(file);

        // excelCubeGenerator.printObservations(cube);

        Model model = cubeModelGenerator.getCubeModelWithStructure(cube, false);
        modelPersister.persist(model, namedGraph);

        System.out.println("Cube structure generated in: " + watch.elapsed(Configuration.TIME_UNIT) + Configuration.TIME_UNIT_SUFFIX);

        model = cubeModelGenerator.getObservationsWithEntities(cube);
        modelPersister.persist(model, namedGraph);

        System.out.println("Cube observations generated in: " + watch.elapsed(Configuration.TIME_UNIT) + Configuration.TIME_UNIT_SUFFIX);
        System.out.println("Done!");
    }
}
