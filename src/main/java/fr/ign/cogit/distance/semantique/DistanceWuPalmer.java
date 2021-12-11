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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

import fr.ign.cogit.distance.Distance;
import fr.ign.cogit.util.ontology.OntoText;


/**
 * 
 * @author M-D Van Damme
 */
public class DistanceWuPalmer extends DistanceAbstractSemantique implements Distance {
	
	/** Ontologie. */
	// private OntModel owlmodel;
	private OntoText ontoText;
    
    public DistanceWuPalmer(String uri) {
        try {
        	OntModel owlmodel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        	owlmodel.read(uri, "RDF/XML");
        	ontoText = new OntoText(owlmodel);
        	owlmodel.close();
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }
    
    @Override
    public double getDistance() {
        float d = (float)(1 - mesureSimilariteWuPalmer(this.attrNameSemRef, this.attrNameSemComp));
        // System.out.println("Distance WP " + this.attrNameSemRef.toLowerCase() 
        //          + "-" + this.attrNameSemComp.toLowerCase() + " = " + d);
        return d;
    }
    
    public double mesureSimilariteWuPalmer(String uri1, String uri2) {
    	float scoreMax = -1;
		
		// System.out.println();
		// System.out.println(uri1);
		// System.out.println(uri2);
		// System.out.println("--------------------------------------------------------------");
		// hauteur entre racine et noeud
		int h1 = 0;
		int h2 = 0;
		List<String> hierarchie1 = null;
		List<String> hierarchie2 = null;
		
		for (String enfant1 : ontoText.getOntoAPlat().keySet()) {

			List<List<String>> hierarchies1 = ontoText.getOntoAPlat().get(enfant1);
			for (List<String> branche1 : hierarchies1) {

				// uri1 est dans cette hiérarchie ?
				for (int n1 = 0; n1 < branche1.size(); n1++) {
					String noeud1 = branche1.get(n1);
					if (uri1.contentEquals(noeud1)) {
						
						h1 = branche1.size() - n1;
						hierarchie1 = branche1;
						hierarchie1.subList(0, n1).clear();
						// System.out.println("-"+hierarchie1);
						
						for (String enfant2 : ontoText.getOntoAPlat().keySet()) {
							List<List<String>> hierarchies2 = ontoText.getOntoAPlat().get(enfant2);
							for (List<String> branche2 : hierarchies2) {
								// uri2 est dans cette hiérarchie ?
								for (int n2 = 0; n2 < branche2.size(); n2++) {
									String noeud2 = branche2.get(n2);
									if (uri2.contentEquals(noeud2)) {
										h2 = branche2.size() - n2;
										hierarchie2 = branche2;
										hierarchie2.subList(0, n2).clear();
										// System.out.println("    +"+hierarchie2);
										float score = getScore(hierarchie1, hierarchie2, h1, h2);
										// System.out.println(hierarchie1 + "," + hierarchie2 + "," + score);
										// System.out.println(score);
										if (score > scoreMax) {
											scoreMax = score;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return scoreMax;

    }
    
    private float getScore(List<String> hierarchie1, List<String> hierarchie2, int h1, int h2) {
		// System.out.println(hierarchie1);
		List<String> ligne1 = new ArrayList<String>(hierarchie1);
		Collections.reverse(ligne1);
		// System.out.println(hierarchie1);
		// System.out.println(h1);
		List<String> ligne2 = new ArrayList<String>(hierarchie2);
		Collections.reverse(ligne2);
		// System.out.println(hierarchie2);
		// System.out.println(h2);
		
		int ncpc = -1;
		int min = Math.min(ligne1.size(), ligne2.size());
		
		for (int i = 0; i < min; i++) {
			if (ligne1.get(i).contentEquals(ligne2.get(i))) {
				continue;
			}
			// Ils sont égaux
			ncpc = i;
			break;
		}
		if (ncpc == -1) {
			ncpc = min;
		}
		// System.out.println(ncpc);
		
		float score = (2* (float)ncpc) / (h1 + h2);
		return score;
	}
    
    @Override
    public String getNom() {
        return "WuPalmer";
    }
}
