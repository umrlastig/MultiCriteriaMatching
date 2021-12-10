package fr.ign.cogit.ontology;

import junit.framework.TestCase;
import org.junit.Assert;

import fr.ign.cogit.distance.semantique.DistanceWuPalmer;


public class TestWP extends TestCase {

	public void testFeuilles() throws Exception {
		
		DistanceWuPalmer dwp = new DistanceWuPalmer("/home/marie-dominique/CHOUCAS/OOR/oor_V1.0.1_01102021_MDVD.owl");
		String uri1 = "http://purl.org/choucas.ign.fr/oor#borne_géodésique";
		String uri2 = "http://purl.org/choucas.ign.fr/oor#borne_kilométrique";
		double m = dwp.mesureSimilariteWuPalmer(uri1, uri2);
		double d = (float)(1-m);
		Assert.assertEquals(1-0.800000011920929, d, 0.0001);
		
		dwp = new DistanceWuPalmer("/home/marie-dominique/CHOUCAS/OOR/oor_V1.0.1_01102021_MDVD.owl");
		uri1 = "http://purl.org/choucas.ign.fr/oor#caverne";
		uri2 = "http://purl.org/choucas.ign.fr/oor#pic";
		m = dwp.mesureSimilariteWuPalmer(uri1, uri2);
		d = (float)(1-m);
		Assert.assertEquals(1-0.3076923191547394, d, 0.0001);
		
		dwp = new DistanceWuPalmer("/home/marie-dominique/CHOUCAS/OOR/oor_V1.0.1_01102021_MDVD.owl");
		m = dwp.mesureSimilariteWuPalmer(uri1, uri1);
		d = (float)(1-m);
		Assert.assertEquals(1 - 1.00, d, 0.0001);
		
		dwp = new DistanceWuPalmer("/home/marie-dominique/CHOUCAS/OOR/oor_V1.0.1_01102021_MDVD.owl");
		uri1 = "http://purl.org/choucas.ign.fr/oor#glacier";
		uri2 = "http://purl.org/choucas.ign.fr/oor#aiguille";
		m = dwp.mesureSimilariteWuPalmer(uri1, uri2);
		d = (float)(1-m);
		Assert.assertEquals(1 - 0.20000000298023224, d, 0.0001);
		
	}
	
	
	public void testNoeud() throws Exception {
		
		DistanceWuPalmer dwp = new DistanceWuPalmer("/home/marie-dominique/CHOUCAS/OOR/oor_V1.0.1_01102021_MDVD.owl");
		String uri1 = "http://purl.org/choucas.ign.fr/oor#sommet";
		String uri2 = "http://purl.org/choucas.ign.fr/oor#pic";
		double m = dwp.mesureSimilariteWuPalmer(uri1, uri2);
		float d = (float)(1-m);
		Assert.assertEquals(1 - 0.800000011920929, d, 0.0001);
		
	}
	
	
	public void test2Chemins() throws Exception {
		
		DistanceWuPalmer dwp = new DistanceWuPalmer("/home/marie-dominique/CHOUCAS/OOR/oor_V1.0.1_01102021_MDVD.owl");
		String uri1 = "http://purl.org/choucas.ign.fr/oor#ravin";
		String uri2 = "http://purl.org/choucas.ign.fr/oor#DOC";
		double m = dwp.mesureSimilariteWuPalmer(uri1, uri2);
		double d = (double)(1-m);
		Assert.assertEquals(1 - 0.727272727, d, 0.0001);
	
		dwp = new DistanceWuPalmer("/home/marie-dominique/CHOUCAS/OOR/oor_V1.0.1_01102021_MDVD.owl");
		uri1 = "http://purl.org/choucas.ign.fr/oor#ravin";
		uri2 = "http://purl.org/choucas.ign.fr/oor#AD";
		m = dwp.mesureSimilariteWuPalmer(uri1, uri2);
		d = (float)(1-m);
		Assert.assertEquals(1 - (2*4.0)/(6+5), d, 0.001);
		
		// ---
		
		dwp = new DistanceWuPalmer("/home/marie-dominique/CHOUCAS/OOR/oor_V1.0.1_01102021_MDVD.owl");
		uri1 = "http://purl.org/choucas.ign.fr/oor#fort";
		uri2 = "http://purl.org/choucas.ign.fr/oor#gîte";
		m = dwp.mesureSimilariteWuPalmer(uri1, uri2);
		d = (float)(1-m);
		Assert.assertEquals(1 - (2*4.0)/(6+6), d, 0.001);
		
		
		dwp = new DistanceWuPalmer("/home/marie-dominique/CHOUCAS/OOR/oor_V1.0.1_01102021_MDVD.owl");
		uri1 = "http://purl.org/choucas.ign.fr/oor#fort";
		uri2 = "http://purl.org/choucas.ign.fr/oor#bâtiment_religieux";
		m = dwp.mesureSimilariteWuPalmer(uri1, uri2);
		d = (float)(1-m);
		Assert.assertEquals(1 - (2*3.0)/(4+5), d, 0.001);
	
		// ---
		
		dwp = new DistanceWuPalmer("/home/marie-dominique/CHOUCAS/OOR/oor_V1.0.1_01102021_MDVD.owl");
		uri1 = "http://purl.org/choucas.ign.fr/oor#habitation_troglodytique";
		uri2 = "http://purl.org/choucas.ign.fr/oor#coopérative";
		m = dwp.mesureSimilariteWuPalmer(uri1, uri2);
		d = (float)(1-m);
		Assert.assertEquals(1 - (2*3.0)/(5+6), d, 0.001);
		
		dwp = new DistanceWuPalmer("/home/marie-dominique/CHOUCAS/OOR/oor_V1.0.1_01102021_MDVD.owl");
		uri1 = "http://purl.org/choucas.ign.fr/oor#bâtiment_remarquable";
		uri2 = "http://purl.org/choucas.ign.fr/oor#coopérative";
		m = dwp.mesureSimilariteWuPalmer(uri1, uri2);
		d = (float)(1-m);
		Assert.assertEquals(1 - (2*3.0)/(4+6), d, 0.001);
		
		
	}
}