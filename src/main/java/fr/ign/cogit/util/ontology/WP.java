package fr.ign.cogit.util.ontology;

import java.util.ArrayList;
// import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.RDFNode;

public class WP {
	
	OntModel owlmodel;
	public Map<String, List<String>> enfantParents;
	public Map<String, List<List<String>>> ontoAPlat;

	public WP(OntModel owlmodel) {
		this.owlmodel = owlmodel;
		ontoAPlat = new HashMap<String, List<List<String>>>();
		enfantParents = new HashMap<String, List<String>>();
		do1();
	}
	
	
	public double getScoreSimilarite(String uri1, String uri2) {
		
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
		
		for (String enfant1 : ontoAPlat.keySet()) {

			List<List<String>> hierarchies1 = ontoAPlat.get(enfant1);
			for (List<String> branche1 : hierarchies1) {

				// uri1 est dans cette hiérarchie ?
				for (int n1 = 0; n1 < branche1.size(); n1++) {
					String noeud1 = branche1.get(n1);
					if (uri1.contentEquals(noeud1)) {
						
						h1 = branche1.size() - n1;
						hierarchie1 = branche1;
						hierarchie1.subList(0, n1).clear();
						// System.out.println("-"+hierarchie1);
						
						for (String enfant2 : ontoAPlat.keySet()) {
							List<List<String>> hierarchies2 = ontoAPlat.get(enfant2);
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
	
	private void do1() {
		
		// On charge l'ontologie
		load();
		// loadA();
		
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
			doAPlat(enfant, enfant);
			
			/*
			for (List<String> hierarchie : hierarchies) {
				System.out.println(enfant + ":" + hierarchie);
			}
			System.out.println("===");
			*/
		}
	}
	
	
	private void doAPlat(String noeud, String feuille) {
		
		
		//System.out.println("  " + noeud + "," + feuille);

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
			doAPlat(parent, feuille);
			// List<String> hierarchie = getHierarchie(feuille);
			// hierarchies.add(hierarchie);
			// System.out.println(feuille + "--" + hierarchie);
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
	
	
	private void load() {
		Iterator<OntClass> it = owlmodel.listClasses();
		while (it.hasNext()) {
			OntClass oclass = (OntClass) it.next();
			OntoClasse gclass = new OntoClasse(oclass);
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
		owlmodel.close();
	}
	
	
//	private void loadA() {
//
//		enfantParents = new HashMap<String, List<String>>();
//		enfantParents.put("A", Arrays.asList("B", "F", "L"));
//		enfantParents.put("B", Arrays.asList("C"));
//		enfantParents.put("C", Arrays.asList("D"));
//		enfantParents.put("D", Arrays.asList("E"));
//		enfantParents.put("E", Arrays.asList("Z"));
//		
//		enfantParents.put("F", Arrays.asList("G", "I"));
//		enfantParents.put("G", Arrays.asList("H"));
//		enfantParents.put("H", Arrays.asList("Z"));
//		
//		enfantParents.put("I", Arrays.asList("J"));
//		enfantParents.put("J", Arrays.asList("K"));
//		enfantParents.put("K", Arrays.asList("Z"));
//		
//		enfantParents.put("L", Arrays.asList("M"));
//		enfantParents.put("M", Arrays.asList("N", "P"));
//		enfantParents.put("N", Arrays.asList("O"));
//		enfantParents.put("O", Arrays.asList("Z"));
//		
//		enfantParents.put("P", Arrays.asList("Z"));
//		
//		enfantParents.put("Q", Arrays.asList("R"));
//		enfantParents.put("R", Arrays.asList("Z"));
//		
//		enfantParents.put("S", Arrays.asList("T"));
//		enfantParents.put("T", Arrays.asList("U", "V"));
//		enfantParents.put("U", Arrays.asList("Z"));
//		enfantParents.put("V", Arrays.asList("Z"));
//	}

}
