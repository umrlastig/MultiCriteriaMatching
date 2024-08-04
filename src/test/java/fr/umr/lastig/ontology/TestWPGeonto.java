package fr.umr.lastig.ontology;

import org.junit.Assert;

import fr.umr.lastig.distance.semantique.DistanceWuPalmer;
import junit.framework.TestCase;

public class TestWPGeonto extends TestCase {

	public void testEscarpu() throws Exception {
		
		DistanceWuPalmer dwp = new DistanceWuPalmer("./data/ontology/GeOnto.owl");
		
		String uri1 = "http://www.owl-ontologies.com/Ontology1176999717.owl#col";
		String uri2 = "http://www.owl-ontologies.com/Ontology1176999717.owl#sommet";
		double m = dwp.mesureSimilariteWuPalmer(uri1, uri2);
		double d = (float)(1-m);
		Assert.assertEquals(0.333, d, 0.001);
		
		uri1 = "http://www.owl-ontologies.com/Ontology1176999717.owl#pic";
		uri2 = "http://www.owl-ontologies.com/Ontology1176999717.owl#sommet";
		m = dwp.mesureSimilariteWuPalmer(uri1, uri2);
		d = (float)(1-m);
		Assert.assertEquals(0.14285713, d, 0.001);
		
		uri1 = "http://www.owl-ontologies.com/Ontology1176999717.owl#sommet";
		uri2 = "http://www.owl-ontologies.com/Ontology1176999717.owl#pic";
		m = dwp.mesureSimilariteWuPalmer(uri1, uri2);
		d = (float)(1-m);
		Assert.assertEquals(0.14285713, d, 0.001);
		
		// http://www.owl-ontologies.com/Ontology1176999717.owl#col
		// http://www.owl-ontologies.com/Ontology1176999717.owl#pic
	}
}
