package fr.ign.cogit.util.ontology;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.SKOS;

public class OntoClasse {

	private String uri;
	private String localName;
	private String label;
	private String prefLabel;
	private List<String> altLabel;
	private String comment;
	
	private List<RDFNode> parents;
	
	
	public OntoClasse(OntClass oclass) {

		this.uri = oclass.getURI();
		this.localName = oclass.getLocalName();
		
		this.label = oclass.getLabel("fr");
		
		this.altLabel = new ArrayList<String>();
		
		this.parents = new ArrayList<RDFNode>();
		
		StmtIterator stmt = oclass.listProperties();
		while (stmt.hasNext()) {
			
			final Statement s = stmt.next();
			
			if (s.getPredicate().getLocalName().equals(SKOS.prefLabel.getLocalName()) && s.getLanguage().equals("fr")) {
				this.prefLabel = getObjectLiteral(s);
			}
			if (s.getPredicate().getLocalName().equals(SKOS.altLabel.getLocalName()) && s.getLanguage().equals("fr")) {
				this.altLabel.add(getObjectLiteral(s));
			}
			if (s.getPredicate().getLocalName().equals(RDFS.comment.getLocalName()) && s.getLanguage().equals("fr")) {
				this.comment = getObjectLiteral(s);
			}
			
			// if (s.getPredicate().getLocalName().equals("type")) {
			//	   System.out.println("    type : " + s.toString());
			// }
			
			if (s.getPredicate().equals(RDFS.subClassOf) && !s.getObject().isAnon()) {
				this.parents.add(s.getObject());
		    	// System.out.println("      parent de " + s.getSubject() + " est " + s.getObject());
		    }
			
		}
		
		// Property sousclasse = RDFS.subClassOf;
		
	    //ResIterator parents = owlmodel.listSubjectsWithProperty(sousclasse);
		// ExtendedIterator<OntClass> list = oclass.listSubClasses();
		// System.out.println(list.toList().size());
	}
	
	
	public String getUri() { return this.uri; }
	public String getLocalName() { return this.localName; }
	public String getLabel() { return this.label; }
	public String getPrefLabel() {
		if (this.prefLabel == null) {
			if (this.label != null) {
				return this.label;
			} else {
				return this.uri.split("#")[1];
			}
		} else return this.prefLabel; 
	}
	public List<String> getAltLabel() { return this.altLabel; }
	public String getComment() { return this.comment; }
	public List<RDFNode> getParents() { return this.parents; }
	
	
	private String getObjectLiteral(Statement s) {
		// System.out.println("    " + s.getObject().asLiteral().toString().split("@")[0] + "-" + s.getSubject());
		/*System.out.println("    " + s.getObject());
		Literal l = (Literal) s.getObject();
		String ss = PrintUtil.print(l).split("^\^")[0];
		System.out.println("    " + ss);
		*/
		return s.getObject().asLiteral().toString().split("@")[0];
	}
}
