package de.uop.code.generator.persist;

import de.uop.code.generator.Configuration;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class BigdataConnector {

	private final static String CONTEXT_URI = "?context-uri=";

    /**
     *
     * Call the SPARQL endpoint defined in the Configuration class to persist a RDF Dump.
     *
     * @param content The RDF Dump.
     * @param contentType The content type of the dump.
     * @param context The name of the NAMED GRAPH in the triple store. (A new one will be generated if not existing.)
     *
     * @return Return the answer of the triple store.
     */
	public String persist(String content, ContentTypeRdf contentType, String context) {
        //System.out.println(content);
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(Configuration.ENDPOINT_BIGDATA + CONTEXT_URI + context);
        httpPost.setHeader("Content-Type", contentType.getContentTypeRdf());

        try {
            httpPost.setEntity(new StringEntity(content));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpResponse response;
        String result = "";
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = IOUtils.toString(entity.getContent(), "UTF-8");
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }

		return result;
	}

}
