package de.uop.code.handler;

import com.google.common.base.Stopwatch;
import com.hp.hpl.jena.rdf.model.Model;
import de.uop.code.generator.Configuration;
import de.uop.code.generator.CubeGenerator;
import de.uop.code.generator.CubeModelGenerator;
import de.uop.code.generator.ModelPersister;
import de.uop.code.generator.domain.Cube;

/**
 * Created by Basti on 24/01/14.
 */
public class CubeGenerationHandler {

    private CubeGenerator cubeGenerator = new CubeGenerator();
    private CubeModelGenerator cubeModelGenerator = new CubeModelGenerator();
    private ModelPersister modelPersister = new ModelPersister();

    public void generate() {
        Stopwatch watch = Stopwatch.createStarted();
        String namedGraph = cubeModelGenerator.getNamedGraph();
        Cube cube = cubeGenerator.generateCubeStructure();
        Model model = cubeModelGenerator.getCubeModelWithStructure(cube, true);
        modelPersister.persist(model, namedGraph);

        System.out.println("Cube structure generated in: " + watch.elapsed(Configuration.TIME_UNIT) + Configuration.TIME_UNIT_SUFFIX);
        int counter = 1;
        do {
            cube.setObservations(cubeGenerator.generateNextObservations());

            if (cube.getObservations().size() > 0) {
                model = cubeModelGenerator.getObservationsWithEntities(cube);
                modelPersister.persist(model, namedGraph);
                System.out.println("Observation batch generated in: " + watch.elapsed(Configuration.TIME_UNIT) + Configuration.TIME_UNIT_SUFFIX + "  " + counter + "/" + (Configuration.NR_OBS / Configuration.BATCH_SIZE));
                counter++;
            }
        } while (cube.getObservations().size() > 0);

        System.out.println("Done!");
    }

}
