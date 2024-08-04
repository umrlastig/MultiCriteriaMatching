package fr.umr.lastig.appariement;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import fr.umr.lastig.appariement.AppariementDST;
import fr.umr.lastig.appariement.LigneResultat;
import fr.umr.lastig.appli.TableauResultatFrame;
import fr.umr.lastig.criteria.Critere;
import fr.umr.lastig.criteria.CritereGeom;
import fr.umr.lastig.criteria.CritereSemantique;
import fr.umr.lastig.criteria.CritereToponymique;
import fr.umr.lastig.distance.geom.DistanceEuclidienne;
import fr.umr.lastig.distance.semantique.DistanceWuPalmer;
import fr.umr.lastig.distance.text.DistanceSamal;
import junit.framework.TestCase;

/**
 *  Appariement :
 * 
 *       -- "Col, passage";"col de la sibérie";"POINT(826910.2 6574293.6)"
 *
 *       -- "tête du pis";"Sommet";"POINT(826665.2 6574272.7)"
 *       -- "grande montagne";"Sommet";"POINT(827361.1 6574327.7)"   "69035" "CENVES"
 *       -- "col de la sibérie";"Col";"POINT(826596.7 6574083.4)"
 *
 */
public class TestAppPointSiberie extends TestCase {
	
	GeometryFactory factory;

    private static final String REF_NOM = "col de Sibérie";
    private static final String REF_NATURE = "http://www.owl-ontologies.com/Ontology1176999717.owl#col";
    private static final double REF_X = 826910.2;
    private static final double REF_Y = 6574293.6;
    
    private static final String CANDIDAT1_NOM = "tête du pis";
    private static final String CANDIDAT1_NATURE = "http://www.owl-ontologies.com/Ontology1176999717.owl#sommet";
    private static final double CANDIDAT1_X = 826665.2;
    private static final double CANDIDAT1_Y = 6574272.7;
    
    private static final String CANDIDAT2_NOM = "grande montagne";
    private static final String CANDIDAT2_NATURE = "http://www.owl-ontologies.com/Ontology1176999717.owl#sommet";
    private static final double CANDIDAT2_X = 827361.1;
    private static final double CANDIDAT2_Y = 6574327.7;
    
    private static final String CANDIDAT3_NOM = "col de la sibérie";
    private static final String CANDIDAT3_NATURE = "http://www.owl-ontologies.com/Ontology1176999717.owl#col";
    private static final double CANDIDAT3_X = 826596.7;
    private static final double CANDIDAT3_Y = 6574083.4;
    
    public TestAppPointSiberie() {
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
    public void testApp3Critere_1() throws Exception {
        
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
        Assert.assertEquals("Prem proba pign", ligne0.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne0.isDecision(), "false");
        
        LigneResultat ligne1 = lres.get(1);
        Assert.assertEquals("Nom des objets", ligne1.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne1.getNomTopoComp(), CANDIDAT1_NOM);
        // Assert.assertEquals("Nature des objets", ligne1.getTypeTopoRef(), REF_NATURE);
        // Assert.assertEquals("Nature des objets", ligne1.getTypeTopoComp(), CANDIDAT1_NATURE);
        Assert.assertEquals("Distance toponymique", ligne1.getDistance(0), 0.857, 0.001);
        Assert.assertEquals("Distance euclidienne", ligne1.getDistance(1), 245.889, 0.001);
        Assert.assertEquals("Distance sémantique", ligne1.getDistance(2), 0.333, 0.001);
        Assert.assertEquals("Prem proba pign", ligne1.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne1.isDecision(), "false");
        
        LigneResultat ligne2 = lres.get(2);
        Assert.assertEquals("Nom des objets", ligne2.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne2.getNomTopoComp(), CANDIDAT2_NOM);
        // Assert.assertEquals("Nature des objets", ligne2.getTypeTopoRef(), REF_NATURE);
        // Assert.assertEquals("Nature des objets", ligne2.getTypeTopoComp(), CANDIDAT2_NATURE);
        Assert.assertEquals("Distance toponymique", ligne2.getDistance(0), 0.866, 0.001);
        Assert.assertEquals("Distance euclidienne", ligne2.getDistance(1), 452.187, 0.001);
        Assert.assertEquals("Distance sémantique", ligne2.getDistance(2), 0.333, 0.001);
        Assert.assertEquals("Prem proba pign", ligne2.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne2.isDecision(), "false");
        
        LigneResultat ligne3 = lres.get(3);
        Assert.assertEquals("Nom des objets", ligne3.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne3.getNomTopoComp(), CANDIDAT3_NOM);
        // Assert.assertEquals("Nature des objets", ligne3.getTypeTopoRef(), REF_NATURE);
        // Assert.assertEquals("Nature des objets", ligne3.getTypeTopoComp(), CANDIDAT3_NATURE);
        Assert.assertEquals("Distance toponymique", ligne3.getDistance(0), 0.0, 0.001);
        Assert.assertEquals("Distance euclidienne", ligne3.getDistance(1), 377.447, 0.001);
        Assert.assertEquals("Distance sémantique", ligne3.getDistance(2), 0.0, 0.001);
        Assert.assertEquals("Prem proba pign", ligne3.getProbaPignistiquePremier(), 1.0, 0.01);
        Assert.assertEquals("Decision", ligne3.isDecision(), "true");
        
        TableauResultatFrame tableauPanel = new TableauResultatFrame();
        tableauPanel.computeRes(lres);
        int[] tab = tableauPanel.analyse();
        
        Assert.assertEquals("NB NA : ", tab[0], 0);
        Assert.assertEquals("NB Appariement : ", tab[1], 1);
        Assert.assertEquals("NB d'indécis : ", tab[2], 0);

    }
    
    
    /**
     * Appariement 3 criteres dans l'ordre : géométrie + toponymie + sémantique
     * 
     * @throws Exception
     */
    public void testApp3Critere_2() throws Exception {
        
        AppariementDST evidenceAlgoFusionCritere = new AppariementDST();
        evidenceAlgoFusionCritere.setSeuilIndecision(0.15);
        
        List<Critere> listCritere = new ArrayList<Critere>();
        
        // Critere géométrique
        DistanceEuclidienne dg = new DistanceEuclidienne();
        CritereGeom cg = new CritereGeom(dg);
        cg.setSeuil(100, 220);
        listCritere.add(cg);
        
        // Critere toponymique
        DistanceSamal ds = new DistanceSamal();
        CritereToponymique ct = new CritereToponymique(ds);
        ct.setSeuil(0.6);
        listCritere.add(ct);
        
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
        Assert.assertEquals("Prem proba pign", ligne0.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne0.isDecision(), "false");
        
        LigneResultat ligne1 = lres.get(1);
        Assert.assertEquals("Nom des objets", ligne1.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne1.getNomTopoComp(), CANDIDAT1_NOM);
        // Assert.assertEquals("Nature des objets", ligne1.getTypeTopoRef(), REF_NATURE);
        // Assert.assertEquals("Nature des objets", ligne1.getTypeTopoComp(), CANDIDAT1_NATURE);
        Assert.assertEquals(ligne1.getNomDistance(0), ligne1.getDistance(0), 245.889, 0.001);
        Assert.assertEquals(ligne1.getNomDistance(1), ligne1.getDistance(1), 0.857, 0.001);
        Assert.assertEquals(ligne1.getNomDistance(2), ligne1.getDistance(2), 0.333, 0.001);
        Assert.assertEquals("Prem proba pign", ligne1.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne1.isDecision(), "false");
        
        LigneResultat ligne2 = lres.get(2);
        Assert.assertEquals("Nom des objets", ligne2.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne2.getNomTopoComp(), CANDIDAT2_NOM);
        // Assert.assertEquals("Nature des objets", ligne2.getTypeTopoRef(), REF_NATURE);
        // Assert.assertEquals("Nature des objets", ligne2.getTypeTopoComp(), CANDIDAT2_NATURE);
        Assert.assertEquals(ligne2.getNomDistance(0), ligne2.getDistance(0), 452.187, 0.001);
        Assert.assertEquals(ligne2.getNomDistance(1), ligne2.getDistance(1), 0.866, 0.001);
        Assert.assertEquals(ligne2.getNomDistance(2), ligne2.getDistance(2), 0.333, 0.001);
        Assert.assertEquals("Prem proba pign", ligne2.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne2.isDecision(), "false");
        
        LigneResultat ligne3 = lres.get(3);
        Assert.assertEquals("Nom des objets", ligne3.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne3.getNomTopoComp(), CANDIDAT3_NOM);
        // Assert.assertEquals("Nature des objets", ligne3.getTypeTopoRef(), REF_NATURE);
        // Assert.assertEquals("Nature des objets", ligne3.getTypeTopoComp(), CANDIDAT3_NATURE);
        Assert.assertEquals(ligne3.getNomDistance(0), ligne3.getDistance(0), 377.447, 0.001);
        Assert.assertEquals(ligne3.getNomDistance(1), ligne3.getDistance(1), 0.0, 0.001);
        Assert.assertEquals(ligne3.getNomDistance(2), ligne3.getDistance(2), 0.0, 0.001);
        Assert.assertEquals("Prem proba pign", ligne3.getProbaPignistiquePremier(), 1.0, 0.01);
        Assert.assertEquals("Decision", ligne3.isDecision(), "true");
        
        TableauResultatFrame tableauPanel = new TableauResultatFrame();
        tableauPanel.computeRes(lres);
        int[] tab = tableauPanel.analyse();
        
        Assert.assertEquals("NB NA : ", tab[0], 0);
        Assert.assertEquals("NB Appariement : ", tab[1], 1);
        Assert.assertEquals("NB d'indécis : ", tab[2], 0);

    }
    
    /**
     * Appariement 3 criteres dans l'ordre : géométrie + sémantique + toponymie
     * 
     * @throws Exception
     */
    public void testApp3Critere_3() throws Exception {
        
        AppariementDST evidenceAlgoFusionCritere = new AppariementDST();
        evidenceAlgoFusionCritere.setSeuilIndecision(0.15);
        
        List<Critere> listCritere = new ArrayList<Critere>();
        
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
        
        // Critere toponymique
        DistanceSamal ds = new DistanceSamal();
        CritereToponymique ct = new CritereToponymique(ds);
        ct.setSeuil(0.6);
        listCritere.add(ct);
        
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
        Assert.assertEquals("Prem proba pign", ligne0.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne0.isDecision(), "false");
        
        LigneResultat ligne1 = lres.get(1);
        Assert.assertEquals("Nom des objets", ligne1.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne1.getNomTopoComp(), CANDIDAT1_NOM);
        // Assert.assertEquals("Nature des objets", ligne1.getTypeTopoRef(), REF_NATURE);
        // Assert.assertEquals("Nature des objets", ligne1.getTypeTopoComp(), CANDIDAT1_NATURE);
        Assert.assertEquals(ligne1.getNomDistance(0), ligne1.getDistance(0), 245.889, 0.001);
        Assert.assertEquals(ligne1.getNomDistance(1), ligne1.getDistance(1), 0.333, 0.001);
        Assert.assertEquals(ligne1.getNomDistance(2), ligne1.getDistance(2), 0.857, 0.001);
        Assert.assertEquals("Prem proba pign", ligne1.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne1.isDecision(), "false");
        
        LigneResultat ligne2 = lres.get(2);
        Assert.assertEquals("Nom des objets", ligne2.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne2.getNomTopoComp(), CANDIDAT2_NOM);
        //Assert.assertEquals("Nature des objets", ligne2.getTypeTopoRef(), REF_NATURE);
        //Assert.assertEquals("Nature des objets", ligne2.getTypeTopoComp(), CANDIDAT2_NATURE);
        Assert.assertEquals(ligne2.getNomDistance(0), ligne2.getDistance(0), 452.187, 0.001);
        Assert.assertEquals(ligne2.getNomDistance(1), ligne2.getDistance(1), 0.333, 0.001);
        Assert.assertEquals(ligne2.getNomDistance(2), ligne2.getDistance(2), 0.866, 0.001);
        Assert.assertEquals("Prem proba pign", ligne2.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne2.isDecision(), "false");
        
        LigneResultat ligne3 = lres.get(3);
        Assert.assertEquals("Nom des objets", ligne3.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne3.getNomTopoComp(), CANDIDAT3_NOM);
        //Assert.assertEquals("Nature des objets", ligne3.getTypeTopoRef(), REF_NATURE);
        //Assert.assertEquals("Nature des objets", ligne3.getTypeTopoComp(), CANDIDAT3_NATURE);
        Assert.assertEquals(ligne3.getNomDistance(0), ligne3.getDistance(0), 377.447, 0.001);
        Assert.assertEquals(ligne3.getNomDistance(1), ligne3.getDistance(1), 0.0, 0.001);
        Assert.assertEquals(ligne3.getNomDistance(2), ligne3.getDistance(2), 0.0, 0.001);
        Assert.assertEquals("Prem proba pign", ligne3.getProbaPignistiquePremier(), 1.0, 0.01);
        Assert.assertEquals("Decision", ligne3.isDecision(), "true");
        
        TableauResultatFrame tableauPanel = new TableauResultatFrame();
        tableauPanel.computeRes(lres);
        int[] tab = tableauPanel.analyse();
        
        Assert.assertEquals("NB NA : ", tab[0], 0);
        Assert.assertEquals("NB Appariement : ", tab[1], 1);
        Assert.assertEquals("NB d'indécis : ", tab[2], 0);

    }

    
    /**
     * Appariement 2 criteres dans l'ordre : géométrie + toponymie
     * 
     * @throws Exception
     */
    public void testApp2Critere_1() throws Exception {
        
        AppariementDST evidenceAlgoFusionCritere = new AppariementDST();
        evidenceAlgoFusionCritere.setSeuilIndecision(0.15);
        
        List<Critere> listCritere = new ArrayList<Critere>();
        
        // Critere géométrique
        DistanceEuclidienne dg = new DistanceEuclidienne();
        CritereGeom cg = new CritereGeom(dg);
        cg.setSeuil(100, 220);
        listCritere.add(cg);
        
        // Critere toponymique
        DistanceSamal ds = new DistanceSamal();
        CritereToponymique ct = new CritereToponymique(ds);
        ct.setSeuil(0.6);
        listCritere.add(ct);
        
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
        Assert.assertEquals("Prem proba pign", ligne0.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne0.isDecision(), "false");
        
        LigneResultat ligne1 = lres.get(1);
        Assert.assertEquals("Nom des objets", ligne1.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne1.getNomTopoComp(), CANDIDAT1_NOM);
        Assert.assertEquals(ligne1.getNomDistance(0), ligne1.getDistance(0), 245.889, 0.001);
        Assert.assertEquals(ligne1.getNomDistance(1), ligne1.getDistance(1), 0.857, 0.001);
        Assert.assertEquals("Prem proba pign", ligne1.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne1.isDecision(), "false");
        
        LigneResultat ligne2 = lres.get(2);
        Assert.assertEquals("Nom des objets", ligne2.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne2.getNomTopoComp(), CANDIDAT2_NOM);
        Assert.assertEquals(ligne2.getNomDistance(0), ligne2.getDistance(0), 452.187, 0.001);
        Assert.assertEquals(ligne2.getNomDistance(1), ligne2.getDistance(1), 0.866, 0.001);
        Assert.assertEquals("Prem proba pign", ligne2.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne2.isDecision(), "false");
        
        LigneResultat ligne3 = lres.get(3);
        Assert.assertEquals("Nom des objets", ligne3.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne3.getNomTopoComp(), CANDIDAT3_NOM);
        Assert.assertEquals(ligne3.getNomDistance(0), ligne3.getDistance(0), 377.447, 0.001);
        Assert.assertEquals(ligne3.getNomDistance(1), ligne3.getDistance(1), 0.0, 0.001);
        Assert.assertEquals("Prem proba pign", ligne3.getProbaPignistiquePremier(), 1.0, 0.01);
        Assert.assertEquals("Decision", ligne3.isDecision(), "true");
        
        TableauResultatFrame tableauPanel = new TableauResultatFrame();
        tableauPanel.computeRes(lres);
        int[] tab = tableauPanel.analyse();
        
        Assert.assertEquals("NB NA : ", tab[0], 0);
        Assert.assertEquals("NB Appariement : ", tab[1], 1);
        Assert.assertEquals("NB d'indécis : ", tab[2], 0);

    }
    
    
    /**
     * Appariement 2 criteres dans l'ordre : toponymie + géométrie
     * 
     * @throws Exception
     */
    public void testApp2Critere_2() throws Exception {
        
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
        Assert.assertEquals("Prem proba pign", ligne0.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne0.isDecision(), "false");
        
        LigneResultat ligne1 = lres.get(1);
        Assert.assertEquals("Nom des objets", ligne1.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne1.getNomTopoComp(), CANDIDAT1_NOM);
        Assert.assertEquals(ligne1.getNomDistance(1), ligne1.getDistance(1), 245.889, 0.001);
        Assert.assertEquals(ligne1.getNomDistance(0), ligne1.getDistance(0), 0.857, 0.001);
        Assert.assertEquals("Prem proba pign", ligne1.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne1.isDecision(), "false");
        
        LigneResultat ligne2 = lres.get(2);
        Assert.assertEquals("Nom des objets", ligne2.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne2.getNomTopoComp(), CANDIDAT2_NOM);
        Assert.assertEquals(ligne2.getNomDistance(1), ligne2.getDistance(1), 452.187, 0.001);
        Assert.assertEquals(ligne2.getNomDistance(0), ligne2.getDistance(0), 0.866, 0.001);
        Assert.assertEquals("Prem proba pign", ligne2.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne2.isDecision(), "false");
        
        LigneResultat ligne3 = lres.get(3);
        Assert.assertEquals("Nom des objets", ligne3.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne3.getNomTopoComp(), CANDIDAT3_NOM);
        Assert.assertEquals(ligne3.getNomDistance(1), ligne3.getDistance(1), 377.447, 0.001);
        Assert.assertEquals(ligne3.getNomDistance(0), ligne3.getDistance(0), 0.0, 0.001);
        Assert.assertEquals("Prem proba pign", ligne3.getProbaPignistiquePremier(), 1.0, 0.01);
        Assert.assertEquals("Decision", ligne3.isDecision(), "true");
        
        TableauResultatFrame tableauPanel = new TableauResultatFrame();
        tableauPanel.computeRes(lres);
        int[] tab = tableauPanel.analyse();
        
        Assert.assertEquals("NB NA : ", tab[0], 0);
        Assert.assertEquals("NB Appariement : ", tab[1], 1);
        Assert.assertEquals("NB d'indécis : ", tab[2], 0);

    }
    
    
    /**
     * Appariement 1 critere dans l'ordre : géométrie
     * 
     * @throws Exception
     */
    public void testApp1Critere_1() throws Exception {
        
        AppariementDST evidenceAlgoFusionCritere = new AppariementDST();
        evidenceAlgoFusionCritere.setSeuilIndecision(0.15);
        
        List<Critere> listCritere = new ArrayList<Critere>();
        
        // Critere géométrique
        DistanceEuclidienne dg = new DistanceEuclidienne();
        CritereGeom cg = new CritereGeom(dg);
        cg.setSeuil(100, 220);
        listCritere.add(cg);
        
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
        Assert.assertEquals("Prem proba pign", ligne0.getProbaPignistiquePremier(), 0.63, 0.01);
        Assert.assertEquals("Decision", ligne0.isDecision(), "true");
        
        LigneResultat ligne1 = lres.get(1);
        Assert.assertEquals("Nom des objets", ligne1.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne1.getNomTopoComp(), CANDIDAT1_NOM);
        Assert.assertEquals(ligne1.getNomDistance(0), ligne1.getDistance(0), 245.889, 0.001);
        Assert.assertEquals("Prem proba pign", ligne1.getProbaPignistiquePremier(), 0.12, 0.01);
        Assert.assertEquals("Decision", ligne1.isDecision(), "false");
        
        LigneResultat ligne2 = lres.get(2);
        Assert.assertEquals("Nom des objets", ligne2.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne2.getNomTopoComp(), CANDIDAT2_NOM);
        Assert.assertEquals(ligne2.getNomDistance(0), ligne2.getDistance(0), 452.187, 0.001);
        Assert.assertEquals("Prem proba pign", ligne2.getProbaPignistiquePremier(), 0.12, 0.01);
        Assert.assertEquals("Decision", ligne2.isDecision(), "false");
        
        LigneResultat ligne3 = lres.get(3);
        Assert.assertEquals("Nom des objets", ligne3.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne3.getNomTopoComp(), CANDIDAT3_NOM);
        Assert.assertEquals(ligne3.getNomDistance(0), ligne3.getDistance(0), 377.447, 0.001);
        Assert.assertEquals("Prem proba pign", ligne3.getProbaPignistiquePremier(), 0.12, 0.01);
        Assert.assertEquals("Decision", ligne3.isDecision(), "false");
        
        TableauResultatFrame tableauPanel = new TableauResultatFrame();
        tableauPanel.computeRes(lres);
        int[] tab = tableauPanel.analyse();
        
        Assert.assertEquals("NB NA : ", tab[0], 1);
        Assert.assertEquals("NB Appariement : ", tab[1], 0);
        Assert.assertEquals("NB d'indécis : ", tab[2], 0);

    }
    
    /**
     * Appariement 1 critere dans l'ordre : toponymie
     * 
     * @throws Exception
     */
    public void testApp1Critere_2() throws Exception {
        
        AppariementDST evidenceAlgoFusionCritere = new AppariementDST();
        evidenceAlgoFusionCritere.setSeuilIndecision(0.15);
        
        List<Critere> listCritere = new ArrayList<Critere>();
        
        // Critere toponymique
        DistanceSamal ds = new DistanceSamal();
        CritereToponymique ct = new CritereToponymique(ds);
        ct.setSeuil(0.6);
        listCritere.add(ct);
        
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
        Assert.assertEquals("Prem proba pign", ligne0.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne0.isDecision(), "false");
        
        LigneResultat ligne1 = lres.get(1);
        Assert.assertEquals("Nom des objets", ligne1.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne1.getNomTopoComp(), CANDIDAT1_NOM);
        Assert.assertEquals(ligne1.getNomDistance(0), ligne1.getDistance(0), 0.857, 0.001);
        Assert.assertEquals("Prem proba pign", ligne1.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne1.isDecision(), "false");
        
        LigneResultat ligne2 = lres.get(2);
        Assert.assertEquals("Nom des objets", ligne2.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne2.getNomTopoComp(), CANDIDAT2_NOM);
        Assert.assertEquals(ligne2.getNomDistance(0), ligne2.getDistance(0), 0.866, 0.001);
        Assert.assertEquals("Prem proba pign", ligne2.getProbaPignistiquePremier(), 0.0, 0.01);
        Assert.assertEquals("Decision", ligne2.isDecision(), "false");
        
        LigneResultat ligne3 = lres.get(3);
        Assert.assertEquals("Nom des objets", ligne3.getNomTopoRef(), REF_NOM);
        Assert.assertEquals("Nom des objets", ligne3.getNomTopoComp(), CANDIDAT3_NOM);
        Assert.assertEquals(ligne3.getNomDistance(0), ligne3.getDistance(0), 0.0, 0.001);
        Assert.assertEquals("Prem proba pign", ligne3.getProbaPignistiquePremier(), 1.0, 0.01);
        Assert.assertEquals("Decision", ligne3.isDecision(), "true");
        
        TableauResultatFrame tableauPanel = new TableauResultatFrame();
        tableauPanel.computeRes(lres);
        int[] tab = tableauPanel.analyse();
        
        Assert.assertEquals("NB NA : ", tab[0], 0);
        Assert.assertEquals("NB Appariement : ", tab[1], 1);
        Assert.assertEquals("NB d'indécis : ", tab[2], 0);

    }
    
}
