package fr.ign.cogit.ontology;

import org.junit.Assert;

import fr.ign.cogit.distance.semantique.DistanceWuPalmer;

public class TestWPGeonto  {

	public static void main(String[] args) throws Exception {
		DistanceWuPalmer dwp = new DistanceWuPalmer("./data/ontology/GeOnto.owl");
		String uri1 = "http://www.owl-ontologies.com/Ontology1176999717.owl#col";
		String uri2 = "http://www.owl-ontologies.com/Ontology1176999717.owl#sommet";
		double m = dwp.mesureSimilariteWuPalmer(uri1, uri2);
		double d = (float)(1-m);
		Assert.assertEquals(0.333, d, 0.001);
	}
}
