package fr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import fr.ign.cogit.appariement.AppariementDST;
import fr.ign.cogit.appariement.LigneResultat;
import fr.ign.cogit.criteria.Critere;
import fr.ign.cogit.criteria.CritereGeom;
import fr.ign.cogit.criteria.CritereSemantique;
import fr.ign.cogit.criteria.CritereToponymique;
import fr.ign.cogit.distance.geom.DistanceEuclidienne;
import fr.ign.cogit.distance.semantique.DistanceWuPalmer;
import fr.ign.cogit.distance.text.DistanceSamal;
import fr.ign.cogit.geoxygene.api.feature.IFeature;
import fr.ign.cogit.geoxygene.api.feature.IPopulation;
import fr.ign.cogit.geoxygene.api.spatial.geomprim.IPoint;
import fr.ign.cogit.geoxygene.feature.DefaultFeature;
import fr.ign.cogit.geoxygene.feature.Population;
import fr.ign.cogit.geoxygene.feature.SchemaDefaultFeature;
import fr.ign.cogit.geoxygene.schema.schemaConceptuelISOJeu.AttributeType;
import fr.ign.cogit.geoxygene.schema.schemaConceptuelISOJeu.FeatureType;
import fr.ign.cogit.geoxygene.spatial.coordgeom.DirectPosition;
import fr.ign.cogit.geoxygene.spatial.geomprim.GM_Point;
import fr.ign.cogit.gui.TableauResultatFrame;
import fr.ign.cogit.metadata.PAIBDCarto;
import fr.ign.cogit.metadata.PAIBDTopo;
import fr.ign.cogit.metadata.Objet;
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

    private static final String REF_NOM = "col de Sibérie";
    private static final String REF_NATURE = "Col";
    private static final double REF_X = 826910.2;
    private static final double REF_Y = 6574293.6;
    
    private static final String CANDIDAT1_NOM = "tête du pis";
    private static final String CANDIDAT1_NATURE = "Sommet";
    private static final double CANDIDAT1_X = 826665.2;
    private static final double CANDIDAT1_Y = 6574272.7;
    
    private static final String CANDIDAT2_NOM = "grande montagne";
    private static final String CANDIDAT2_NATURE = "Sommet";
    private static final double CANDIDAT2_X = 827361.1;
    private static final double CANDIDAT2_Y = 6574327.7;
    
    private static final String CANDIDAT3_NOM = "col de la sibérie";
    private static final String CANDIDAT3_NATURE = "Col";
    private static final double CANDIDAT3_X = 826596.7;
    private static final double CANDIDAT3_Y = 6574083.4;
    
    
    private static SchemaDefaultFeature schemaCandidat = null;
    
    
    public TestAppPointSiberie() {
        // On initialise les schémas et les features type  
        
        // Candidat
        FeatureType featureTypeCandidat = new FeatureType();
        featureTypeCandidat.setTypeName("Candidat");
        featureTypeCandidat.setGeometryType(IPoint.class);
                        
        AttributeType idPoint = new AttributeType("cleabs", "String");
        AttributeType nomPoint = new AttributeType("nom", "String");
        AttributeType naturePoint = new AttributeType("nature", "String");
        
        featureTypeCandidat.addFeatureAttribute(idPoint);
        featureTypeCandidat.addFeatureAttribute(nomPoint);
        featureTypeCandidat.addFeatureAttribute(naturePoint);
            
        // Création d'un schéma associé au featureType
        schemaCandidat = new SchemaDefaultFeature();
        
        featureTypeCandidat.setSchema(schemaCandidat);
        
        Map<Integer, String[]> attLookup = new HashMap<Integer, String[]>(0);
        attLookup.put(new Integer(0), new String[] { idPoint.getNomField(), idPoint.getMemberName() });
        attLookup.put(new Integer(1), new String[] { nomPoint.getNomField(), nomPoint.getMemberName() });
        attLookup.put(new Integer(2), new String[] { naturePoint.getNomField(), naturePoint.getMemberName() });
        schemaCandidat.setAttLookup(attLookup);
        
    }
    
    
    private IFeature getRef() {
        
        DefaultFeature defaultFeature = new DefaultFeature();
        defaultFeature.setFeatureType(schemaCandidat.getFeatureType());
        defaultFeature.setSchema(schemaCandidat);
        
        Object[] attributes = new Object[] { "1", REF_NOM, REF_NATURE };
        defaultFeature.setAttributes(attributes);
        defaultFeature.setGeom(new GM_Point(new DirectPosition(REF_X, REF_Y)));
        
        return defaultFeature;
    }
    
    
    private IFeature getCandidat1() {
        
        DefaultFeature defaultFeature = new DefaultFeature();
        defaultFeature.setFeatureType(schemaCandidat.getFeatureType());
        defaultFeature.setSchema(schemaCandidat);
        
        Object[] attributes = new Object[] { "1", CANDIDAT1_NOM, CANDIDAT1_NATURE };
        defaultFeature.setAttributes(attributes);
        defaultFeature.setGeom(new GM_Point(new DirectPosition(CANDIDAT1_X, CANDIDAT1_Y)));
        
        return defaultFeature;
    }
    
    
    private IFeature getCandidat2() {
        
        DefaultFeature defaultFeature = new DefaultFeature();
        defaultFeature.setFeatureType(schemaCandidat.getFeatureType());
        defaultFeature.setSchema(schemaCandidat);
        
        Object[] attributes = new Object[] { "2", CANDIDAT2_NOM, CANDIDAT2_NATURE };
        defaultFeature.setAttributes(attributes);
        defaultFeature.setGeom(new GM_Point(new DirectPosition(CANDIDAT2_X, CANDIDAT2_Y)));
        
        return defaultFeature;
    }
    
    
    private IFeature getCandidat3() {
        
        DefaultFeature defaultFeature = new DefaultFeature();
        defaultFeature.setFeatureType(schemaCandidat.getFeatureType());
        defaultFeature.setSchema(schemaCandidat);
        
        Object[] attributes = new Object[] { "3", CANDIDAT3_NOM, CANDIDAT3_NATURE };
        defaultFeature.setAttributes(attributes);
        defaultFeature.setGeom(new GM_Point(new DirectPosition(CANDIDAT3_X, CANDIDAT3_Y)));
        
        return defaultFeature;
    }
    
    
    /**
     * Appariement 3 criteres dans l'ordre : toponymie + géométrie + sémantique
     * 
     * @throws Exception
     */
//    public void testApp3Critere_1() throws Exception {
//        
//        AppariementDST evidenceAlgoFusionCritere = new AppariementDST();
//        evidenceAlgoFusionCritere.setSeuilIndecision(0.15);
//        
//        Objet objRef = new PAIBDTopo();
//        Objet objComp = new PAIBDCarto();
//        evidenceAlgoFusionCritere.setMetadata(objRef, objComp);
//    
//        List<Critere> listCritere = new ArrayList<Critere>();
//        
//        // Critere toponymique
//        DistanceSamal ds = new DistanceSamal();
//        CritereToponymique ct = new CritereToponymique(ds);
//        ct.setMetadata(objRef, objComp);
//        ct.setSeuil(0.6);
//        listCritere.add(ct);
//            
//        // Critere géométrique
//        DistanceEuclidienne dg = new DistanceEuclidienne();
//        CritereGeom cg = new CritereGeom(dg);
//        cg.setSeuil(100, 220);
//        listCritere.add(cg);
//        
//        // Critere sémantique
//        DistanceWuPalmer dwp = new DistanceWuPalmer();
//        CritereSemantique cs = new CritereSemantique(dwp);
//        cs.setMetadata(objRef, objComp);
//        cs.setSeuil(0.7);
//        listCritere.add(cs);
//        
//        evidenceAlgoFusionCritere.setListCritere(listCritere);
//        
//        IPopulation<IFeature> candidatListe = new Population<IFeature>();
//        candidatListe.add(getCandidat1());
//        candidatListe.add(getCandidat2());
//        candidatListe.add(getCandidat3());
//        
//        IFeature ref = getRef();
//        
//        List<LigneResultat> lres = evidenceAlgoFusionCritere.appariementObjet(ref, candidatListe);
//        
//        Assert.assertEquals("Nombre de candidats testés + incertitude", 4, lres.size());
//        
//        // NA
//        LigneResultat ligne0 = lres.get(0);
//        Assert.assertEquals("Nom des objets", ligne0.getNomTopoRef(), REF_NOM);
//        Assert.assertEquals("Nom des objets", ligne0.getNomTopoComp(), "NA");
//        Assert.assertEquals("Prem proba pign", ligne0.getProbaPignistiquePremier(), 0.0, 0.01);
//        Assert.assertEquals("Decision", ligne0.isDecision(), "false");
//        
//        LigneResultat ligne1 = lres.get(1);
//        Assert.assertEquals("Nom des objets", ligne1.getNomTopoRef(), REF_NOM);
//        Assert.assertEquals("Nom des objets", ligne1.getNomTopoComp(), CANDIDAT1_NOM);
//        // Assert.assertEquals("Nature des objets", ligne1.getTypeTopoRef(), REF_NATURE);
//        // Assert.assertEquals("Nature des objets", ligne1.getTypeTopoComp(), CANDIDAT1_NATURE);
//        Assert.assertEquals("Distance toponymique", ligne1.getDistance(0), 0.857, 0.001);
//        Assert.assertEquals("Distance euclidienne", ligne1.getDistance(1), 245.889, 0.001);
//        Assert.assertEquals("Distance sémantique", ligne1.getDistance(2), 0.333, 0.001);
//        Assert.assertEquals("Prem proba pign", ligne1.getProbaPignistiquePremier(), 0.0, 0.01);
//        Assert.assertEquals("Decision", ligne1.isDecision(), "false");
//        
//        LigneResultat ligne2 = lres.get(2);
//        Assert.assertEquals("Nom des objets", ligne2.getNomTopoRef(), REF_NOM);
//        Assert.assertEquals("Nom des objets", ligne2.getNomTopoComp(), CANDIDAT2_NOM);
//        // Assert.assertEquals("Nature des objets", ligne2.getTypeTopoRef(), REF_NATURE);
//        // Assert.assertEquals("Nature des objets", ligne2.getTypeTopoComp(), CANDIDAT2_NATURE);
//        Assert.assertEquals("Distance toponymique", ligne2.getDistance(0), 0.866, 0.001);
//        Assert.assertEquals("Distance euclidienne", ligne2.getDistance(1), 452.187, 0.001);
//        Assert.assertEquals("Distance sémantique", ligne2.getDistance(2), 0.333, 0.001);
//        Assert.assertEquals("Prem proba pign", ligne2.getProbaPignistiquePremier(), 0.0, 0.01);
//        Assert.assertEquals("Decision", ligne2.isDecision(), "false");
//        
//        LigneResultat ligne3 = lres.get(3);
//        Assert.assertEquals("Nom des objets", ligne3.getNomTopoRef(), REF_NOM);
//        Assert.assertEquals("Nom des objets", ligne3.getNomTopoComp(), CANDIDAT3_NOM);
//        // Assert.assertEquals("Nature des objets", ligne3.getTypeTopoRef(), REF_NATURE);
//        // Assert.assertEquals("Nature des objets", ligne3.getTypeTopoComp(), CANDIDAT3_NATURE);
//        Assert.assertEquals("Distance toponymique", ligne3.getDistance(0), 0.0, 0.001);
//        Assert.assertEquals("Distance euclidienne", ligne3.getDistance(1), 377.447, 0.001);
//        Assert.assertEquals("Distance sémantique", ligne3.getDistance(2), 0.0, 0.001);
//        Assert.assertEquals("Prem proba pign", ligne3.getProbaPignistiquePremier(), 1.0, 0.01);
//        Assert.assertEquals("Decision", ligne3.isDecision(), "true");
//        
//        TableauResultatFrame tableauPanel = new TableauResultatFrame();
//        tableauPanel.displayEnsFrame("tests", lres);
//        int[] tab = tableauPanel.analyse();
//        
//        Assert.assertEquals("NB NA : ", tab[0], 0);
//        Assert.assertEquals("NB Appariement : ", tab[1], 1);
//        Assert.assertEquals("NB d'indécis : ", tab[2], 0);
//
//    }
//    
//    
//    /**
//     * Appariement 3 criteres dans l'ordre : géométrie + toponymie + sémantique
//     * 
//     * @throws Exception
//     */
//    public void testApp3Critere_2() throws Exception {
//        
//        AppariementDST evidenceAlgoFusionCritere = new AppariementDST();
//        evidenceAlgoFusionCritere.setSeuilIndecision(0.15);
//        
//        Objet objRef = new PAIBDTopo();
//        Objet objComp = new PAIBDCarto();
//        evidenceAlgoFusionCritere.setMetadata(objRef, objComp);
//    
//        List<Critere> listCritere = new ArrayList<Critere>();
//        
//        // Critere géométrique
//        DistanceEuclidienne dg = new DistanceEuclidienne();
//        CritereGeom cg = new CritereGeom(dg);
//        cg.setSeuil(100, 220);
//        listCritere.add(cg);
//        
//        // Critere toponymique
//        DistanceSamal ds = new DistanceSamal();
//        CritereToponymique ct = new CritereToponymique(ds);
//        ct.setMetadata(objRef, objComp);
//        ct.setSeuil(0.6);
//        listCritere.add(ct);
//        
//        // Critere sémantique
//        DistanceWuPalmer dwp = new DistanceWuPalmer();
//        CritereSemantique cs = new CritereSemantique(dwp);
//        cs.setMetadata(objRef, objComp);
//        cs.setSeuil(0.7);
//        listCritere.add(cs);
//        
//        evidenceAlgoFusionCritere.setListCritere(listCritere);
//        
//        IPopulation<IFeature> candidatListe = new Population<IFeature>();
//        candidatListe.add(getCandidat1());
//        candidatListe.add(getCandidat2());
//        candidatListe.add(getCandidat3());
//        
//        IFeature ref = getRef();
//        
//        List<LigneResultat> lres = evidenceAlgoFusionCritere.appariementObjet(ref, candidatListe);
//        
//        Assert.assertEquals("Nombre de candidats testés + incertitude", 4, lres.size());
//        
//        // NA
//        LigneResultat ligne0 = lres.get(0);
//        Assert.assertEquals("Nom des objets", ligne0.getNomTopoRef(), REF_NOM);
//        Assert.assertEquals("Nom des objets", ligne0.getNomTopoComp(), "NA");
//        Assert.assertEquals("Prem proba pign", ligne0.getProbaPignistiquePremier(), 0.0, 0.01);
//        Assert.assertEquals("Decision", ligne0.isDecision(), "false");
//        
//        LigneResultat ligne1 = lres.get(1);
//        Assert.assertEquals("Nom des objets", ligne1.getNomTopoRef(), REF_NOM);
//        Assert.assertEquals("Nom des objets", ligne1.getNomTopoComp(), CANDIDAT1_NOM);
//        // Assert.assertEquals("Nature des objets", ligne1.getTypeTopoRef(), REF_NATURE);
//        // Assert.assertEquals("Nature des objets", ligne1.getTypeTopoComp(), CANDIDAT1_NATURE);
//        Assert.assertEquals(ligne1.getNomDistance(0), ligne1.getDistance(0), 245.889, 0.001);
//        Assert.assertEquals(ligne1.getNomDistance(1), ligne1.getDistance(1), 0.857, 0.001);
//        Assert.assertEquals(ligne1.getNomDistance(2), ligne1.getDistance(2), 0.333, 0.001);
//        Assert.assertEquals("Prem proba pign", ligne1.getProbaPignistiquePremier(), 0.0, 0.01);
//        Assert.assertEquals("Decision", ligne1.isDecision(), "false");
//        
//        LigneResultat ligne2 = lres.get(2);
//        Assert.assertEquals("Nom des objets", ligne2.getNomTopoRef(), REF_NOM);
//        Assert.assertEquals("Nom des objets", ligne2.getNomTopoComp(), CANDIDAT2_NOM);
//        // Assert.assertEquals("Nature des objets", ligne2.getTypeTopoRef(), REF_NATURE);
//        // Assert.assertEquals("Nature des objets", ligne2.getTypeTopoComp(), CANDIDAT2_NATURE);
//        Assert.assertEquals(ligne2.getNomDistance(0), ligne2.getDistance(0), 452.187, 0.001);
//        Assert.assertEquals(ligne2.getNomDistance(1), ligne2.getDistance(1), 0.866, 0.001);
//        Assert.assertEquals(ligne2.getNomDistance(2), ligne2.getDistance(2), 0.333, 0.001);
//        Assert.assertEquals("Prem proba pign", ligne2.getProbaPignistiquePremier(), 0.0, 0.01);
//        Assert.assertEquals("Decision", ligne2.isDecision(), "false");
//        
//        LigneResultat ligne3 = lres.get(3);
//        Assert.assertEquals("Nom des objets", ligne3.getNomTopoRef(), REF_NOM);
//        Assert.assertEquals("Nom des objets", ligne3.getNomTopoComp(), CANDIDAT3_NOM);
//        // Assert.assertEquals("Nature des objets", ligne3.getTypeTopoRef(), REF_NATURE);
//        // Assert.assertEquals("Nature des objets", ligne3.getTypeTopoComp(), CANDIDAT3_NATURE);
//        Assert.assertEquals(ligne3.getNomDistance(0), ligne3.getDistance(0), 377.447, 0.001);
//        Assert.assertEquals(ligne3.getNomDistance(1), ligne3.getDistance(1), 0.0, 0.001);
//        Assert.assertEquals(ligne3.getNomDistance(2), ligne3.getDistance(2), 0.0, 0.001);
//        Assert.assertEquals("Prem proba pign", ligne3.getProbaPignistiquePremier(), 1.0, 0.01);
//        Assert.assertEquals("Decision", ligne3.isDecision(), "true");
//        
//        TableauResultatFrame tableauPanel = new TableauResultatFrame();
//        tableauPanel.displayEnsFrame("tests", lres);
//        int[] tab = tableauPanel.analyse();
//        
//        Assert.assertEquals("NB NA : ", tab[0], 0);
//        Assert.assertEquals("NB Appariement : ", tab[1], 1);
//        Assert.assertEquals("NB d'indécis : ", tab[2], 0);
//
//    }
//    
//    /**
//     * Appariement 3 criteres dans l'ordre : géométrie + sémantique + toponymie
//     * 
//     * @throws Exception
//     */
//    public void testApp3Critere_3() throws Exception {
//        
//        AppariementDST evidenceAlgoFusionCritere = new AppariementDST();
//        evidenceAlgoFusionCritere.setSeuilIndecision(0.15);
//        
//        Objet objRef = new PAIBDTopo();
//        Objet objComp = new PAIBDCarto();
//        evidenceAlgoFusionCritere.setMetadata(objRef, objComp);
//    
//        List<Critere> listCritere = new ArrayList<Critere>();
//        
//        // Critere géométrique
//        DistanceEuclidienne dg = new DistanceEuclidienne();
//        CritereGeom cg = new CritereGeom(dg);
//        cg.setSeuil(100, 220);
//        listCritere.add(cg);
//        
//        // Critere sémantique
//        DistanceWuPalmer dwp = new DistanceWuPalmer();
//        CritereSemantique cs = new CritereSemantique(dwp);
//        cs.setMetadata(objRef, objComp);
//        cs.setSeuil(0.7);
//        listCritere.add(cs);
//        
//        // Critere toponymique
//        DistanceSamal ds = new DistanceSamal();
//        CritereToponymique ct = new CritereToponymique(ds);
//        ct.setMetadata(objRef, objComp);
//        ct.setSeuil(0.6);
//        listCritere.add(ct);
//        
//        evidenceAlgoFusionCritere.setListCritere(listCritere);
//        
//        IPopulation<IFeature> candidatListe = new Population<IFeature>();
//        candidatListe.add(getCandidat1());
//        candidatListe.add(getCandidat2());
//        candidatListe.add(getCandidat3());
//        
//        IFeature ref = getRef();
//        
//        List<LigneResultat> lres = evidenceAlgoFusionCritere.appariementObjet(ref, candidatListe);
//        
//        Assert.assertEquals("Nombre de candidats testés + incertitude", 4, lres.size());
//        
//        // NA
//        LigneResultat ligne0 = lres.get(0);
//        Assert.assertEquals("Nom des objets", ligne0.getNomTopoRef(), REF_NOM);
//        Assert.assertEquals("Nom des objets", ligne0.getNomTopoComp(), "NA");
//        Assert.assertEquals("Prem proba pign", ligne0.getProbaPignistiquePremier(), 0.0, 0.01);
//        Assert.assertEquals("Decision", ligne0.isDecision(), "false");
//        
//        LigneResultat ligne1 = lres.get(1);
//        Assert.assertEquals("Nom des objets", ligne1.getNomTopoRef(), REF_NOM);
//        Assert.assertEquals("Nom des objets", ligne1.getNomTopoComp(), CANDIDAT1_NOM);
//        // Assert.assertEquals("Nature des objets", ligne1.getTypeTopoRef(), REF_NATURE);
//        // Assert.assertEquals("Nature des objets", ligne1.getTypeTopoComp(), CANDIDAT1_NATURE);
//        Assert.assertEquals(ligne1.getNomDistance(0), ligne1.getDistance(0), 245.889, 0.001);
//        Assert.assertEquals(ligne1.getNomDistance(1), ligne1.getDistance(1), 0.333, 0.001);
//        Assert.assertEquals(ligne1.getNomDistance(2), ligne1.getDistance(2), 0.857, 0.001);
//        Assert.assertEquals("Prem proba pign", ligne1.getProbaPignistiquePremier(), 0.0, 0.01);
//        Assert.assertEquals("Decision", ligne1.isDecision(), "false");
//        
//        LigneResultat ligne2 = lres.get(2);
//        Assert.assertEquals("Nom des objets", ligne2.getNomTopoRef(), REF_NOM);
//        Assert.assertEquals("Nom des objets", ligne2.getNomTopoComp(), CANDIDAT2_NOM);
//        //Assert.assertEquals("Nature des objets", ligne2.getTypeTopoRef(), REF_NATURE);
//        //Assert.assertEquals("Nature des objets", ligne2.getTypeTopoComp(), CANDIDAT2_NATURE);
//        Assert.assertEquals(ligne2.getNomDistance(0), ligne2.getDistance(0), 452.187, 0.001);
//        Assert.assertEquals(ligne2.getNomDistance(1), ligne2.getDistance(1), 0.333, 0.001);
//        Assert.assertEquals(ligne2.getNomDistance(2), ligne2.getDistance(2), 0.866, 0.001);
//        Assert.assertEquals("Prem proba pign", ligne2.getProbaPignistiquePremier(), 0.0, 0.01);
//        Assert.assertEquals("Decision", ligne2.isDecision(), "false");
//        
//        LigneResultat ligne3 = lres.get(3);
//        Assert.assertEquals("Nom des objets", ligne3.getNomTopoRef(), REF_NOM);
//        Assert.assertEquals("Nom des objets", ligne3.getNomTopoComp(), CANDIDAT3_NOM);
//        //Assert.assertEquals("Nature des objets", ligne3.getTypeTopoRef(), REF_NATURE);
//        //Assert.assertEquals("Nature des objets", ligne3.getTypeTopoComp(), CANDIDAT3_NATURE);
//        Assert.assertEquals(ligne3.getNomDistance(0), ligne3.getDistance(0), 377.447, 0.001);
//        Assert.assertEquals(ligne3.getNomDistance(1), ligne3.getDistance(1), 0.0, 0.001);
//        Assert.assertEquals(ligne3.getNomDistance(2), ligne3.getDistance(2), 0.0, 0.001);
//        Assert.assertEquals("Prem proba pign", ligne3.getProbaPignistiquePremier(), 1.0, 0.01);
//        Assert.assertEquals("Decision", ligne3.isDecision(), "true");
//        
//        TableauResultatFrame tableauPanel = new TableauResultatFrame();
//        tableauPanel.displayEnsFrame("tests", lres);
//        int[] tab = tableauPanel.analyse();
//        
//        Assert.assertEquals("NB NA : ", tab[0], 0);
//        Assert.assertEquals("NB Appariement : ", tab[1], 1);
//        Assert.assertEquals("NB d'indécis : ", tab[2], 0);
//
//    }
//
//    
    /**
     * Appariement 2 criteres dans l'ordre : géométrie + toponymie
     * 
     * @throws Exception
     */
    public void testApp2Critere_1() throws Exception {
        
        AppariementDST evidenceAlgoFusionCritere = new AppariementDST();
        evidenceAlgoFusionCritere.setSeuilIndecision(0.15);
        
        Objet objRef = new PAIBDTopo();
        Objet objComp = new PAIBDCarto();
        evidenceAlgoFusionCritere.setMetadata(objRef, objComp);
    
        List<Critere> listCritere = new ArrayList<Critere>();
        
        // Critere géométrique
        DistanceEuclidienne dg = new DistanceEuclidienne();
        CritereGeom cg = new CritereGeom(dg);
        cg.setSeuil(100, 220);
        listCritere.add(cg);
        
        // Critere toponymique
        DistanceSamal ds = new DistanceSamal();
        CritereToponymique ct = new CritereToponymique(ds);
        ct.setMetadata(objRef, objComp);
        ct.setSeuil(0.6);
        listCritere.add(ct);
        
        evidenceAlgoFusionCritere.setListCritere(listCritere);
        
        IPopulation<IFeature> candidatListe = new Population<IFeature>();
        candidatListe.add(getCandidat1());
        candidatListe.add(getCandidat2());
        candidatListe.add(getCandidat3());
        
        IFeature ref = getRef();
        
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
        tableauPanel.displayEnsFrame("tests", lres);
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
        
        Objet objRef = new PAIBDTopo();
        Objet objComp = new PAIBDCarto();
        evidenceAlgoFusionCritere.setMetadata(objRef, objComp);
    
        List<Critere> listCritere = new ArrayList<Critere>();
        
        // Critere toponymique
        DistanceSamal ds = new DistanceSamal();
        CritereToponymique ct = new CritereToponymique(ds);
        ct.setMetadata(objRef, objComp);
        ct.setSeuil(0.6);
        listCritere.add(ct);
        
        // Critere géométrique
        DistanceEuclidienne dg = new DistanceEuclidienne();
        CritereGeom cg = new CritereGeom(dg);
        cg.setSeuil(100, 220);
        listCritere.add(cg);
        
        evidenceAlgoFusionCritere.setListCritere(listCritere);
        
        IPopulation<IFeature> candidatListe = new Population<IFeature>();
        candidatListe.add(getCandidat1());
        candidatListe.add(getCandidat2());
        candidatListe.add(getCandidat3());
        
        IFeature ref = getRef();
        
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
        tableauPanel.displayEnsFrame("tests", lres);
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
        
        Objet objRef = new PAIBDTopo();
        Objet objComp = new PAIBDCarto();
        evidenceAlgoFusionCritere.setMetadata(objRef, objComp);
    
        List<Critere> listCritere = new ArrayList<Critere>();
        
        // Critere géométrique
        DistanceEuclidienne dg = new DistanceEuclidienne();
        CritereGeom cg = new CritereGeom(dg);
        cg.setSeuil(100, 220);
        listCritere.add(cg);
        
        evidenceAlgoFusionCritere.setListCritere(listCritere);
        
        IPopulation<IFeature> candidatListe = new Population<IFeature>();
        candidatListe.add(getCandidat1());
        candidatListe.add(getCandidat2());
        candidatListe.add(getCandidat3());
        
        IFeature ref = getRef();
        
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
        tableauPanel.displayEnsFrame("tests", lres);
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
        
        Objet objRef = new PAIBDTopo();
        Objet objComp = new PAIBDCarto();
        evidenceAlgoFusionCritere.setMetadata(objRef, objComp);
    
        List<Critere> listCritere = new ArrayList<Critere>();
        
        // Critere toponymique
        DistanceSamal ds = new DistanceSamal();
        CritereToponymique ct = new CritereToponymique(ds);
        ct.setMetadata(objRef, objComp);
        ct.setSeuil(0.6);
        listCritere.add(ct);
        
        evidenceAlgoFusionCritere.setListCritere(listCritere);
        
        IPopulation<IFeature> candidatListe = new Population<IFeature>();
        candidatListe.add(getCandidat1());
        candidatListe.add(getCandidat2());
        candidatListe.add(getCandidat3());
        
        IFeature ref = getRef();
        
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
        tableauPanel.displayEnsFrame("tests", lres);
        int[] tab = tableauPanel.analyse();
        
        Assert.assertEquals("NB NA : ", tab[0], 0);
        Assert.assertEquals("NB Appariement : ", tab[1], 1);
        Assert.assertEquals("NB d'indécis : ", tab[2], 0);

    }
    
}
