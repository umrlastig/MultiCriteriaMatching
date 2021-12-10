/**
 * 
 * This software is released under the licence CeCILL
 * 
 * see LICENSE.TXT
 * 
 * see <http://www.cecill.info/ http://www.cecill.info/
 * 
 * 
 * @copyright IGN
 * 
 * 
 */

package fr.ign.cogit.distance.semantique;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

import fr.ign.cogit.distance.Distance;
import fr.ign.cogit.util.ontology.WP;

/**
 * 
 * @author M-D Van Damme
 */
public class DistanceWuPalmer extends DistanceAbstractSemantique implements Distance {
	
	/** Ontologie. */
    private OntModel owlmodel = null;
    
    public DistanceWuPalmer(String uri) {
        try {
            owlmodel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
    		owlmodel.read(uri, "RDF/XML");
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }
    
    /*public void close() {
        if (owlmodel != null) {
        	owlmodel.close();
        }
    }*/
    
    @Override
    public double getDistance() {
        float d = (float)(1 - mesureSimilariteWuPalmer(this.attrNameSemRef, this.attrNameSemComp));
        //System.out.println("Distance WP " + this.attrNameSemRef.toLowerCase() + "-" + this.attrNameSemComp.toLowerCase() + " = " + d);
        return d;
    }
    
    public double mesureSimilariteWuPalmer(String uri1, String uri2) {
    	WP dwp = new WP(owlmodel);
    	return dwp.getScoreSimilarite(uri1, uri2);
    }
    
    @Override
    public String getNom() {
        return "WuPalmer";
    }
}
