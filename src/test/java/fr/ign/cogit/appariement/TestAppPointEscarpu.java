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
package fr.ign.cogit.appariement;

import java.util.ArrayList;

import java.util.List;

import org.junit.Assert;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import fr.ign.cogit.appariement.AppariementDST;
import fr.ign.cogit.appariement.LigneResultat;
import fr.ign.cogit.appli.TableauResultatFrame;
import fr.ign.cogit.criteria.Critere;
import fr.ign.cogit.criteria.CritereGeom;
import fr.ign.cogit.criteria.CritereSemantique;
import fr.ign.cogit.criteria.CritereToponymique;
import fr.ign.cogit.distance.geom.DistanceEuclidienne;
import fr.ign.cogit.distance.semantique.DistanceWuPalmer;
import fr.ign.cogit.distance.text.DistanceSamal;
import junit.framework.TestCase;


/**
 * 
 * Appariement :
 * 
 * 		Objet référence (BDCarto) : "Pic";"l'escarpu";"POINT(413721.1 6208543.3)"
 * 
 *      Candidat BDTopo
 *          Candidat 1 : "col de sesques";"Col";"POINT(413448.6 6208086)"		
 *          Candidat 2 : "l'escarpu ou pic de sesques";"Sommet";"POINT(413406.6 6208683.7)"
 *          Candidat 3 : "crête de sesques";"Sommet";"POINT(414188.6 6209026.9)"
 *
 */
public class TestAppPointEscarpu extends TestCase {
	
	GeometryFactory factory;
	
    private static final String REF_NOM = "l'escarpu";
    private static final String REF_NATURE = "http://www.owl-ontologies.com/Ontology1176999717.owl#pic";
    private static final double REF_X = 413721.1;
    private static final double REF_Y = 6208543.3;
    
    private static final String CANDIDAT1_NOM = "col de sesques";
    private static final String CANDIDAT1_NATURE = "http://www.owl-ontologies.com/Ontology1176999717.owl#col";
    private static final double CANDIDAT1_X = 413448.6;
    private static final double CANDIDAT1_Y = 6208086;
    
    private static final String CANDIDAT2_NOM = "l'escarpu ou pic de sesques";
    private static final String CANDIDAT2_NATURE = "http://www.owl-ontologies.com/Ontology1176999717.owl#pic";
    private static final double CANDIDAT2_X = 413406.6;
    private static final double CANDIDAT2_Y = 6208683.7;
    
    private static final String CANDIDAT3_NOM = "crête de sesques";
    private static final String CANDIDAT3_NATURE = "http://www.owl-ontologies.com/Ontology1176999717.owl#sommet";
    private static final double CANDIDAT3_X = 414188.6;
    private static final double CANDIDAT3_Y = 6209026.9;
	
	
	public TestAppPointEscarpu() {
		factory = new GeometryFactory();
	}
	
	
	private Feature getRef() {
		Point pt = factory.createPoint(new Coordinate(REF_X, REF_Y));
		Feature defaultFeature = new Feature(pt);
		defaultFeature.addAttribut("id", "1");
		defaultFeature.addAttribut("nom", REF_NOM);
		defaultFeature.addAttribut("uri", REF_NATURE);
		return defaultFeature;
	}
	
	
	private Feature getCandidat1() {
		Point pt = factory.createPoint(new Coordinate(CANDIDAT1_X, CANDIDAT1_Y));
		Feature defaultFeature = new Feature(pt);
		defaultFeature.addAttribut("id", "1");
		defaultFeature.addAttribut("nom", CANDIDAT1_NOM);
		defaultFeature.addAttribut("uri", CANDIDAT1_NATURE);
		return defaultFeature;
	}
	
	
	private Feature getCandidat2() {
		Point pt = factory.createPoint(new Coordinate(CANDIDAT2_X, CANDIDAT2_Y));
		Feature defaultFeature = new Feature(pt);
		defaultFeature.addAttribut("id", "2");
		defaultFeature.addAttribut("nom", CANDIDAT2_NOM);
		defaultFeature.addAttribut("uri", CANDIDAT2_NATURE);
		return defaultFeature;
	}
	
	
	private Feature getCandidat3() {
		Point pt = factory.createPoint(new Coordinate(CANDIDAT3_X, CANDIDAT3_Y));
		Feature defaultFeature = new Feature(pt);
		defaultFeature.addAttribut("id", "3");
		defaultFeature.addAttribut("nom", CANDIDAT3_NOM);
		defaultFeature.addAttribut("uri", CANDIDAT3_NATURE);
		return defaultFeature;
	}
	
	
	/**
	 * Appariement 3 criteres dans l'ordre : toponymie + géométrie + sémantique
	 * 
	 * @throws Exception
	 */
	public void testApp1Critere() throws Exception {
		
		AppariementDST evidenceAlgoFusionCritere = new AppariementDST();
        evidenceAlgoFusionCritere.setSeuilIndecision(0.15);
		
		List<Critere> listCritere = new ArrayList<Critere>();
        
        // Critere toponymique
        DistanceSamal ds = new DistanceSamal();
        CritereToponymique ct = new CritereToponymique(ds);
        ct.setSeuil(0.6);
        listCritere.add(ct);
		    
        // Critere géométrique
        DistanceEuclidienne dg = new DistanceEuclidienne();
        CritereGeom cg = new CritereGeom(dg);
        cg.setSeuil(100, 220);
        listCritere.add(cg);
        
        // Critere sémantique
        DistanceWuPalmer dwp = new DistanceWuPalmer("./data/ontology/GeOnto.owl");
        CritereSemantique cs = new CritereSemantique(dwp);
        cs.setSeuil(0.7);
        listCritere.add(cs);
        
        evidenceAlgoFusionCritere.setListCritere(listCritere);
        
        List<Feature> candidatListe = new ArrayList<Feature>();
        candidatListe.add(getCandidat1());
        candidatListe.add(getCandidat2());
        candidatListe.add(getCandidat3());
        
        Feature ref = getRef();
        
        List<LigneResultat> lres = evidenceAlgoFusionCritere.appariementObjet(ref, candidatListe);
		
		Assert.assertEquals("Nombre de candidats testés + incertitude", 4, lres.size());
		
		// NA
		LigneResultat ligne0 = lres.get(0);
		Assert.assertEquals("Nom des objets", ligne0.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne0.getNomTopoComp(), "NA");
        Assert.assertEquals("Prem proba pign", ligne0.getProbaPignistiquePremier(), 0.63087, 0.01);
        Assert.assertEquals("Decision", ligne0.isDecision(), "true");
		
		LigneResultat ligne1 = lres.get(1);
		Assert.assertEquals("Nom des objets", ligne1.getNomTopoRef(), REF_NOM);
		Assert.assertEquals("Nom des objets", ligne1.getNomTopoComp(), CANDIDAT1_NOM);
		// Assert.assertEquals("Nature des objets", ligne1.getAttrTopoRef(0), REF_NATURE);
		// Assert.assertEquals("Nature des objets", ligne1.getTypeTopoComp(), CANDIDAT1_NATURE);
		Assert.assertEquals("Distance toponymique", ligne1.getDistance(0), 0.833, 0.001);
		Assert.assertEquals("Distance euclidienne", ligne1.getDistance(1), 532.334, 0.001);
		Assert.assertEquals("Distance sémantique", 0.4286, ligne1.getDistance(2), 0.0001);
		Assert.assertEquals("Prem proba pign", ligne1.getProbaPignistiquePremier(), 0.05, 0.01);
		Assert.assertEquals("Decision", ligne1.isDecision(), "false");
		
		LigneResultat ligne2 = lres.get(2);
        Assert.assertEquals("Nom des objets", ligne2.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne2.getNomTopoComp(), CANDIDAT2_NOM);
        // Assert.assertEquals("Nature des objets", ligne2.getTypeTopoRef(), REF_NATURE);
        // Assert.assertEquals("Nature des objets", ligne2.getTypeTopoComp(), CANDIDAT2_NATURE);
        Assert.assertEquals("Distance toponymique", ligne2.getDistance(0), 0.5, 0.001);
        Assert.assertEquals("Distance euclidienne", ligne2.getDistance(1), 344.416, 0.001);
        Assert.assertEquals("Distance sémantique", ligne2.getDistance(2), 0.0, 0.001);
        Assert.assertEquals("Prem proba pign", ligne2.getProbaPignistiquePremier(), 0.21, 0.01);
        Assert.assertEquals("Decision", ligne2.isDecision(), "false");
        
        LigneResultat ligne3 = lres.get(3);
        Assert.assertEquals("Nom des objets", ligne3.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne3.getNomTopoComp(), CANDIDAT3_NOM);
        // Assert.assertEquals("Nature des objets", ligne3.getTypeTopoRef(), REF_NATURE);
        // Assert.assertEquals("Nature des objets", ligne3.getTypeTopoComp(), CANDIDAT3_NATURE);
        Assert.assertEquals("Distance toponymique", ligne3.getDistance(0), 0.833, 0.001);
        Assert.assertEquals("Distance euclidienne", ligne3.getDistance(1), 672.625, 0.001);
        Assert.assertEquals("Distance sémantique", ligne3.getDistance(2), 0.142, 0.001);
        Assert.assertEquals("Prem proba pign", ligne3.getProbaPignistiquePremier(), 0.10, 0.01);
        Assert.assertEquals("Decision", ligne3.isDecision(), "false");
		
		TableauResultatFrame tableauPanel = new TableauResultatFrame();
        tableauPanel.computeRes(lres);
        int[] tab = tableauPanel.analyse();
        
        Assert.assertEquals("NB NA : ", tab[0], 1);
        Assert.assertEquals("NB Appariement : ", tab[1], 0);
        Assert.assertEquals("NB d'indécis : ", tab[2], 0);
	}

}


