package fr.umr.lastig.criteria;

import org.junit.Assert;

import fr.umr.lastig.appariement.Feature;
import fr.umr.lastig.distance.semantique.DistanceWuPalmer;
import junit.framework.TestCase;

public class TestCritereSemantique extends TestCase {
	
	public void testGeOnto1() throws Exception {
		DistanceWuPalmer dwp = new DistanceWuPalmer("./data/ontology/GeOnto.owl");
        CritereSemantique cs = new CritereSemantique(dwp);
        cs.setSeuil(0.7);
        
        Feature f1 = new Feature(null);
        f1.addAttribut("uri", "http://www.owl-ontologies.com/Ontology1176999717.owl#col");
        
        Feature f2 = new Feature(null);
        f2.addAttribut("uri", "http://www.owl-ontologies.com/Ontology1176999717.owl#col");
        
        cs.setFeature(f1, f2);
		
		Assert.assertEquals("", cs.getMasse()[0], 0.5, 0.0001);
		Assert.assertEquals("", cs.getMasse()[1], 0.0, 0.0001);
		Assert.assertEquals("", cs.getMasse()[2], 0.5, 0.0001);
	}
	
	public void testGeOnto2() throws Exception {
		DistanceWuPalmer dwp = new DistanceWuPalmer("./data/ontology/GeOnto.owl");
        CritereSemantique cs = new CritereSemantique(dwp);
        cs.setSeuil(0.7);
        
        Feature f1 = new Feature(null);
        f1.addAttribut("uri", "http://www.owl-ontologies.com/Ontology1176999717.owl#col");
        
        Feature f2 = new Feature(null);
        f2.addAttribut("uri", "http://www.owl-ontologies.com/Ontology1176999717.owl#vélodrome");
        
        cs.setFeature(f1, f2);
		
		Assert.assertEquals("", cs.getMasse()[0], 0.1, 0.0001);
		Assert.assertEquals("", cs.getMasse()[1], 0.8, 0.0001);
		Assert.assertEquals("", cs.getMasse()[2], 0.1, 0.0001);
	}
	
	public void testGeOnto3() throws Exception {
		DistanceWuPalmer dwp = new DistanceWuPalmer("./data/ontology/GeOnto.owl");
        CritereSemantique cs = new CritereSemantique(dwp);
        cs.setSeuil(0.7);
        
        Feature f1 = new Feature(null);
        f1.addAttribut("uri", "http://www.owl-ontologies.com/Ontology1176999717.owl#col");
        
        Feature f2 = new Feature(null);
        f2.addAttribut("uri", "http://www.owl-ontologies.com/Ontology1176999717.owl#lande");
        
        cs.setFeature(f1, f2);
		
		Assert.assertEquals("", cs.getMasse()[0], 0.1190, 0.0001);
		Assert.assertEquals("", cs.getMasse()[1], 0.7619, 0.0001);
		Assert.assertEquals("", cs.getMasse()[2], 0.1190, 0.0001);
	}
	
	
	public void testGeOnto4() throws Exception {
		DistanceWuPalmer dwp = new DistanceWuPalmer("./data/ontology/GeOnto.owl");
        CritereSemantique cs = new CritereSemantique(dwp);
        cs.setSeuil(0.7);
        
        Feature f1 = new Feature(null);
        f1.addAttribut("uri", "http://www.owl-ontologies.com/Ontology1176999717.owl#pivert");
        
        Feature f2 = new Feature(null);
        f2.addAttribut("uri", "http://www.owl-ontologies.com/Ontology1176999717.owl#lande");
        
        cs.setFeature(f1, f2);
		
		Assert.assertEquals("", cs.getMasse()[0], 0.0, 0.0001);
		Assert.assertEquals("", cs.getMasse()[1], 0.0, 0.0001);
		Assert.assertEquals("", cs.getMasse()[2], 1.0, 0.0001);
	}
	
	
	public void testGeOnto5() throws Exception {
		DistanceWuPalmer dwp = new DistanceWuPalmer("./data/ontology/GeOnto.owl");
        CritereSemantique cs = new CritereSemantique(dwp);
        cs.setSeuil(0.7);
        
        Feature f1 = new Feature(null);
        f1.addAttribut("uri", "http://www.owl-ontologies.com/Ontology1176999717.owl#sommet");
        
        Feature f2 = new Feature(null);
        f2.addAttribut("uri", "http");
        
        cs.setFeature(f1, f2);
		
		Assert.assertEquals("", cs.getMasse()[0], 0.0, 0.0001);
		Assert.assertEquals("", cs.getMasse()[1], 0.0, 0.0001);
		Assert.assertEquals("", cs.getMasse()[2], 1.0, 0.0001);
	}
	
	
	public void testGeOnto6() throws Exception {
		DistanceWuPalmer dwp = new DistanceWuPalmer("./data/ontology/GeOnto.owl");
        CritereSemantique cs = new CritereSemantique(dwp);
        cs.setSeuil(0.7);
        
        Feature f1 = new Feature(null);
        f1.addAttribut("uri", "http://www.owl-ontologies.com/Ontology1176999717.owl#sommet");
        
        Feature f2 = new Feature(null);
        f2.addAttribut("uri", "http://www.owl-ontologies.com/Ontology1176999717.owl#col");
        
        cs.setFeature(f1, f2);
		
		Assert.assertEquals("", cs.getMasse()[0], 0.30952, 0.0001);
		Assert.assertEquals("", cs.getMasse()[1], 0.38095, 0.0001);
		Assert.assertEquals("", cs.getMasse()[2], 0.30952, 0.0001);
	}
	
	
	public void testGeOnto7() throws Exception {
		DistanceWuPalmer dwp = new DistanceWuPalmer("./data/ontology/GeOnto.owl");
        CritereSemantique cs = new CritereSemantique(dwp);
        cs.setSeuil(0.7);
        
        Feature f1 = new Feature(null);
        f1.addAttribut("uri", "http://www.owl-ontologies.com/Ontology1176999717.owl#sommet");
        
        Feature f2 = new Feature(null);
        f2.addAttribut("uri", "http://www.owl-ontologies.com/Ontology1176999717.owl#route");
        
        cs.setFeature(f1, f2);
		
		Assert.assertEquals("", cs.getMasse()[0], 0.1, 0.0001);
		Assert.assertEquals("", cs.getMasse()[1], 0.8, 0.0001);
		Assert.assertEquals("", cs.getMasse()[2], 0.1, 0.0001);
	}
}
