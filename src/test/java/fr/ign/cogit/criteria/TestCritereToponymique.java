package fr.ign.cogit.criteria;

import org.junit.Assert;

import fr.ign.cogit.appariement.Feature;
import fr.ign.cogit.distance.text.DistanceLevenshtein;
import fr.ign.cogit.distance.text.DistanceSamal;
import junit.framework.TestCase;

public class TestCritereToponymique extends TestCase {
	
	public void testSamal1() throws Exception {
		DistanceSamal ds = new DistanceSamal();
        CritereToponymique ct = new CritereToponymique(ds);
        ct.setSeuil(0.60); 
        
        Feature f1 = new Feature(null);
        f1.addAttribut("nom", "La refuge de la Vanoise");
        
        Feature f2 = new Feature(null);
        f2.addAttribut("nom", "La refuge de la Vanoise");
        
        ct.setFeature(f1, f2);
        
        Assert.assertEquals("", ct.getMasse()[0], 1.0, 0.0001);
        Assert.assertEquals("", ct.getMasse()[1], 0.0, 0.0001);
        Assert.assertEquals("", ct.getMasse()[2], 0.0, 0.0001);
	}
	
	public void testSamal2() throws Exception {
		DistanceSamal ds = new DistanceSamal();
        CritereToponymique ct = new CritereToponymique(ds);
        ct.setSeuil(0.60); 
        
        Feature f1 = new Feature(null);
        f1.addAttribut("nom", "Boulevard du Général Charles de Gaulle");
        
        Feature f2 = new Feature(null);
        f2.addAttribut("nom", "Boulevard du Général de Gaulle");
        
        // d = 0.061224489795918435
        ct.setFeature(f1, f2);
        
        Assert.assertEquals("", ct.getMasse()[0], 0.9082, 0.0001);
        Assert.assertEquals("", ct.getMasse()[1], 0.0510, 0.0001);
        Assert.assertEquals("", ct.getMasse()[2], 0.0408, 0.0001);
	}
	
	public void testSamal3() throws Exception {
		DistanceSamal ds = new DistanceSamal();
        CritereToponymique ct = new CritereToponymique(ds);
        ct.setSeuil(0.60); 
        
        Feature f1 = new Feature(null);
        f1.addAttribut("nom", "Charles de Gaulle");
        
        Feature f2 = new Feature(null);
        f2.addAttribut("nom", "Mike Dickens");
        
        // d = 0.8452380952380952
        ct.setFeature(f1, f2);
        
        Assert.assertEquals("", ct.getMasse()[0], 0.1, 0.0001);
        Assert.assertEquals("", ct.getMasse()[1], 0.5, 0.0001);
        Assert.assertEquals("", ct.getMasse()[2], 0.4, 0.0001);
	}
	
	public void testSamal4() throws Exception {
		DistanceSamal ds = new DistanceSamal();
        CritereToponymique ct = new CritereToponymique(ds);
        ct.setSeuil(0.60); 
        
        Feature f1 = new Feature(null);
        f1.addAttribut("nom", "");
        
        Feature f2 = new Feature(null);
        f2.addAttribut("nom", "Mike Dickens");
        
        // d = 0.8452380952380952
        ct.setFeature(f1, f2);
        
        Assert.assertEquals("", ct.getMasse()[0], 0.0, 0.0001);
        Assert.assertEquals("", ct.getMasse()[1], 0.0, 0.0001);
        Assert.assertEquals("", ct.getMasse()[2], 1.0, 0.0001);
	}
	
//	public void testLevenshtein() {
//		DistanceLevenshtein ds = new DistanceLevenshtein();
//        CritereToponymique ct = new CritereToponymique(ds);
//        ct.setSeuil(0.60);
//	}
	
	
	public void testGraphies() {
		DistanceLevenshtein ds = new DistanceLevenshtein();
        CritereToponymique ct = new CritereToponymique(ds);
        ct.setSeuil(0.60);
	}

}