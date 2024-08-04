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
package fr.umr.lastig.util.ontology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.RDFNode;

public class OntoText {
	
	private OntModel owlmodel;
	private Map<String, List<String>> enfantParents;
	private Map<String, List<List<String>>> ontoAPlat;
	
	public OntoText(OntModel owlmodel) {
		this.owlmodel = owlmodel;
		ontoAPlat = new HashMap<String, List<List<String>>>();
		enfantParents = new HashMap<String, List<String>>();
		loadOntologyOwl();
		miseAPlat();
		
		/*for (String key : ontoAPlat.keySet()) {
			List<List<String>> hierarchies = ontoAPlat.get(key);
			for (List<String> hierarchie : hierarchies) {
				System.out.println(key + ":" + hierarchie);
			}
		}*/
		// System.out.println("===");
	}
	
	public Map<String, List<List<String>>> getOntoAPlat() {
		return this.ontoAPlat;
	}
	
    
    private void loadOntologyOwl() {
		Iterator<OntClass> it = owlmodel.listClasses();
		while (it.hasNext()) {
			OntClass oclass = (OntClass) it.next();
			if (oclass.getURI() != null) {
				// System.out.println(oclass.getURI());
				OntoClasse gclass = new OntoClasse(oclass);
				// System.out.println("   " + gclass.getPrefLabel());
				String sparent = "";
				for (RDFNode parent : gclass.getParents()) {
					sparent = parent.toString();
					List<String> parents = null;
					if (enfantParents.get(gclass.getUri()) == null) {
						parents = new ArrayList<String>();
						enfantParents.put(gclass.getUri(), parents);
					} else {
						parents = enfantParents.get(gclass.getUri());
					}
					enfantParents.get(gclass.getUri()).add(sparent);
				}
				// System.out.println(gclass.getUri() + " --- " + sparent);
			}
		}
	}
    
    private boolean isFeuille(String enfant) {
		boolean isEnfant = true;
		for (List<String> parents : enfantParents.values()) {
			for (String parent : parents) {
				if (enfant.contentEquals(parent)) {
					isEnfant = false;
				}
			}
		}
		return isEnfant;
	}
	
	private boolean isRacine(String enfant) {
		boolean isRacine = true;
		for (String enfantcandidat : enfantParents.keySet()) {
			if (enfant.contentEquals(enfantcandidat) && enfantParents.get(enfant).size() > 0) {
				isRacine = false;
			}
		}
		return isRacine;
	}
	
	
	private void miseAPlat() {
		// On boucle sur tous les enfant-parent
    	for (String enfant : enfantParents.keySet()) {
    	
    		// Est-ce que l'enfant est une feuille ?
    		if (!isFeuille(enfant) || isRacine(enfant)) {
    			continue;
    		}
    				
    		// On a une feuille, il faut remonter jusqu'à la racine
    		List<List<String>> hierarchies = new ArrayList<List<String>>();
    				
    		List<String> singleton = new ArrayList<String>();
    		singleton.add(enfant);
    		hierarchies.add(singleton);
    				
    		ontoAPlat.put(enfant, hierarchies);
    		doAPlat(enfant, enfant, 1);
    	}
    }
	
	private void doAPlat(String noeud, String feuille, int cptG) {
		// System.out.println("  " + noeud + "," + feuille);
		// System.out.println(cptG);

		List<List<String>> NOU = new ArrayList<List<String>>();
		// Quelle hiérarchie ?
		for (List<String> hierarchie : ontoAPlat.get(feuille)) {
			// System.out.println("    " + hierarchie);

			if (hierarchie.get(hierarchie.size() - 1).contentEquals(noeud)) {

				int cpt = 0;
				for (String parent : enfantParents.get(noeud)) {
					if (cpt > 0) {
						// autre parent, il faut dupliquer la hiérarchie
						
						ArrayList<String> copy = new ArrayList<String> ();
						for (int i = 0; i < hierarchie.size() - 1; i++) {
							String s = hierarchie.get(i);
							copy.add(s);
						}
						copy.add(parent);
						// System.out.println("          " + copy);
						
						// ontoAPlat.get(feuille).add(copy);
						NOU.add(copy);
					} else {
						hierarchie.add(parent);
					}
					cpt++;
					
					// doAPlat(parent);
				}
			}
		}

		ontoAPlat.get(feuille).addAll(NOU);
		
		for (String parent : enfantParents.get(noeud)) {
			
			//for (List<String> hierarchie : ontoAPlat.get(feuille)) {
			//	hierarchie.add(parent);
			//}
			if (isRacine(parent)) {
				continue;
			}
			
			if (cptG < 10) {
				doAPlat(parent, feuille, cptG+1);
			} /*else {
				System.out.println(parent + "--" + feuille);
			}*/
			// List<String> hierarchie = getHierarchie(feuille);
			// hierarchies.add(hierarchie);
			// System.out.println(feuille + "--" + hierarchie);
			 
		}
		
	}
}
