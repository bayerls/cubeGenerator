package de.uop.code.generator;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import de.uop.code.generator.domain.Cube;
import de.uop.code.generator.domain.Dimension;
import de.uop.code.generator.domain.Measure;
import de.uop.code.generator.domain.Observation;
import de.uop.code.generator.vocabulary.CODE;
import de.uop.code.generator.vocabulary.PROV;
import de.uop.code.generator.vocabulary.QB;
import de.uop.code.generator.vocabulary.VA;
import org.apache.http.client.utils.URIBuilder;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;

public class CubeModelGenerator {

    private Model model = ModelFactory.createDefaultModel();
    private Resource dataset;
    private int obsCounter = 0;
    private List<Property> components = new ArrayList<Property>();
    private Map<String, String> entities = new HashMap<String, String>();

    public Model getCubeModelWithStructure(Cube cube, boolean usePrefix) {
        setNamespaces(model);
        Resource dataset = createDataset(cube);
        addProvenanceInformation(dataset, cube);
        Resource dsd = createDatasetStructureDefintion(dataset);
        addComponents(cube, dsd, usePrefix);

        return model;
    }

    private Model setNamespaces(Model model) {
        model.setNsPrefix(QB.getPrefix(), QB.getURI());
        model.setNsPrefix(CODE.getPrefix(), CODE.getURI());
        model.setNsPrefix("rdf", RDF.getURI());
        model.setNsPrefix("rdfs", RDFS.getURI());
        model.setNsPrefix(VA.getPrefix(), VA.getURI());
        model.setNsPrefix(PROV.getPrefix(), PROV.getURI());
        model.setNsPrefix("dc", "http://purl.org/dc/elements/1.1/");

        return model;
    }


    private Resource createDataset(Cube cube) {
        dataset = model.createResource(CODE.DATASET + getRandomId());
        dataset.addProperty(RDF.type, QB.DATASET)
                .addProperty(RDFS.label, cube.getLabel())
                .addProperty(RDFS.comment, cube.getDescription())
                .addProperty(DC.format, Configuration.VERSION)
                .addProperty(DC.relation, Configuration.RELATION)
                .addProperty(DC.source, Configuration.SOURCE);

        return dataset;
    }

    private void addProvenanceInformation(Resource dataset, Cube cube) {
        String id = getRandomId();
        Resource importerAgent = model.createResource(CODE.IMPORTER + "-" + id);
        importerAgent.addLiteral(RDFS.label, cube.getAuth());
        Resource importActivity = model.createResource(CODE.IMPORT + "-" + id);
        importActivity.addProperty(PROV.WAS_STARTED_BY, importerAgent);

        dataset.addProperty(PROV.WAS_GENERATED_BY, importActivity);
    }

    private Resource createDatasetStructureDefintion(Resource dataset) {
        Resource dsd = model.createResource(CODE.DSD + getRandomId());
        dataset.addProperty(QB.STRUCTURE, dsd);
        dsd.addProperty(RDF.type, QB.DSD);

        return dsd;
    }

    private void addComponents(Cube cube, Resource dsd, boolean usePrefix) {

        for (Dimension dimension : cube.getDatasetStructureDefinition().getDimensions()) {
            String label;

            if (usePrefix) {
                label = dimension.getPrefixedLabel();
            } else {
                label = dimension.getLabel();
            }

            Property p = model.createProperty(CODE.getURI() + label);
            p.addProperty(RDF.type, RDF.Property);
            p.addProperty(RDF.type, QB.DIM_PROPERTY);
            p.addProperty(RDFS.label, label);
            p.addProperty(RDFS.subPropertyOf, VA.DIMENSION_NOMINAL);
            dsd.addProperty(QB.COMPONENT, model.createResource().addProperty(QB.DIMENSION, p));
            components.add(p);
        }

        for (Measure measure : cube.getDatasetStructureDefinition().getMeasures()) {
            String label;

            if (usePrefix) {
                label = measure.getPrefixedLabel();
            } else {
                label = measure.getLabel();
            }

            Property p = model.createProperty(CODE.getURI() + label);
            p.addProperty(RDF.type, RDF.Property);
            p.addProperty(RDF.type, QB.MEASURE_PROPERTY);
            p.addProperty(RDFS.label, label);
            p.addProperty(RDFS.subPropertyOf, VA.MEASURE_NUMBER);
            dsd.addProperty(QB.COMPONENT, model.createResource().addProperty(QB.MEASURE, p));
            components.add(p);
        }
    }

    public Model getObservationsWithEntities(Cube cube) {
        Model observationModel = ModelFactory.createDefaultModel();
        setNamespaces(observationModel);

        for (Observation observation : cube.getObservations()) {
            Resource resource = observationModel.createResource(CODE.OBS + obsCounter);
            resource.addProperty(RDF.type, QB.OBSERVATION);
            resource.addProperty(QB.DATASET_PROPERTY, dataset);

            for (int i = 0; i < observation.getValues().size(); i++) {

                if (i < cube.getDatasetStructureDefinition().getDimensions().size()) {
                    String key = observation.getValues().get(i);


                    if (!entities.containsKey(key)) {
                        entities.put(key, UUID.randomUUID().toString());
                    }

                    String keyRep =  key.replaceAll(",", "");

                    URI uri = null;
                    try {
                        uri = new URI("http", "code-research.eu", "/resource/Def-" + keyRep, null);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }


                    URL url = null;
                    try {
                        url = uri.toURL();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    Resource entity = observationModel.createResource(CODE.getURI() + "Entity-" + entities.get(key));
                    entity.addLiteral(RDFS.label, observation.getValues().get(i));
                    entity.addProperty(RDFS.isDefinedBy, model.createResource(url.toString()));
                    entity.addProperty(RDF.type, CODE.ENTITY);
                    resource.addProperty(components.get(i), entity);
                } else {
                    resource.addProperty(components.get(i), observation.getValues().get(i));
                }
            }

            obsCounter++;
        }

       return observationModel;
    }


    private String getRandomId() {
        return UUID.randomUUID().toString();
    }

    public String getNamedGraph() {
        return CODE.getURI() + "cube/" + getRandomId();
    }

}
