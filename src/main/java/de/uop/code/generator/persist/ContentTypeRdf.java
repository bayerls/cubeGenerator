package de.uop.code.generator.persist;

public enum ContentTypeRdf {

	RDF_XML("application/rdf+xml"), N3("text/rdf+n3"), TURTLE("application/x-turtle");
	
	private String contentTypeRdf;
	
	private ContentTypeRdf(String contentTypeRdf) {
		this.contentTypeRdf = contentTypeRdf;
	}
	
	public String getContentTypeRdf() {
		return contentTypeRdf;
	}
}
