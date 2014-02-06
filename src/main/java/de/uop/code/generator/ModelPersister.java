package de.uop.code.generator;

import com.hp.hpl.jena.rdf.model.Model;
import de.uop.code.generator.persist.BigdataConnector;
import de.uop.code.generator.persist.ContentTypeRdf;
import org.apache.jena.riot.Lang;

import java.io.ByteArrayOutputStream;

/**
 * Created by Sebastian on 21.01.14.
 */
public class ModelPersister {

    public String persist(Model model, String context) {
        BigdataConnector bigdataConnector = new BigdataConnector();
        String result = bigdataConnector.persist(convertModelToString(model), ContentTypeRdf.N3, context);

        System.out.println(result);

        return result;
    }

    public String convertModelToString(Model model) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        model.write(baos, Lang.N3.getName());

        return baos.toString();
    }
}
