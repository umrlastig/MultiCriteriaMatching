package fr.ign.cogit.ontology;

import fr.ign.cogit.distance.semantique.DistanceWuPalmer;

public class TestWPGeonto  {

	public static void main(String[] args) throws Exception {
		DistanceWuPalmer dwp = new DistanceWuPalmer("./data/ontology/GeOnto.owl");
		String uri1 = "http://purl.org/choucas.ign.fr/oor#borne_géodésique";
		String uri2 = "http://purl.org/choucas.ign.fr/oor#borne_kilométrique";
		// double m = dwp.mesureSimilariteWuPalmer(uri1, uri2);
	}
}
