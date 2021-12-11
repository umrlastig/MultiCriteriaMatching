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
package fr.ign.cogit.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import fr.ign.cogit.appariement.AppariementDST;
import fr.ign.cogit.appariement.Feature;
import fr.ign.cogit.appariement.LigneResultat;
import fr.ign.cogit.appli.ExportToCSV;
import fr.ign.cogit.appli.TableauResultatFrame;
import fr.ign.cogit.metadata.Objet;
import fr.ign.cogit.metadata.PAIBDCarto;
import fr.ign.cogit.metadata.PAIBDTopo;
import fr.ign.cogit.criteria.Critere;
import fr.ign.cogit.criteria.CritereGeom;
import fr.ign.cogit.criteria.CritereSemantique;
import fr.ign.cogit.criteria.CritereToponymique;
import fr.ign.cogit.distance.geom.DistanceEuclidienne;
import fr.ign.cogit.distance.semantique.DistanceWuPalmer;
import fr.ign.cogit.distance.text.DistanceSamal;



/**
 * 
 * @author M-D Van Damme
 */
public class MainSiberie {
	
	GeometryFactory factory;
    
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

    public MainSiberie() {
    	factory = new GeometryFactory();
    }
    
    /**
     * Appariement 3 criteres dans l'ordre : toponymie + géométrie + sémantique
     * 
     * @throws Exception
     */
    public void doAppariement() throws Exception {
        
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
        
        // Critere sémantique
        DistanceWuPalmer dwp = new DistanceWuPalmer("./data/ontology/GeOnto.owl");
        CritereSemantique cs = new CritereSemantique(dwp);
        cs.setMetadata(objRef, objComp);
        cs.setSeuil(0.7);
        listCritere.add(cs);
        
        evidenceAlgoFusionCritere.setListCritere(listCritere);
        
        List<Feature> candidatListe = new ArrayList<Feature>();
        candidatListe.add(getCandidat1());
        candidatListe.add(getCandidat2());
        candidatListe.add(getCandidat3());
        
        Feature ref = getRef();
        
        List<LigneResultat> lres = evidenceAlgoFusionCritere.appariementObjet(ref, candidatListe);
        
        TableauResultatFrame tableauPanel = new TableauResultatFrame();
        tableauPanel.displayEnsFrame("tests", lres);
        int[] tab = tableauPanel.analyse();
        System.out.println("NB non-app : " + tab[0]);
        System.out.println("NB app : " + tab[1]);
        System.out.println("NB d'indécis : " + tab[2]);
        // System.out.println("NB sans candidat : " + nbSansCandidat);
        
        // ExportToCSV.exportAppariement(listCritere, evidenceAlgoFusionCritere.getSeuilIndecision(), lres, "./data/resultat/Rando/appariement");
    }

    
    public static void main(String[] args) {
        MainSiberie m = new MainSiberie();
        try {
            m.doAppariement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    private Feature getRef() {
    	Point pt = factory.createPoint(new Coordinate(REF_X, REF_Y));
		Feature defaultFeature = new Feature(pt);
		defaultFeature.addAttribut("cleabs", "1");
		defaultFeature.addAttribut("nom", REF_NOM);
		defaultFeature.addAttribut("nature", REF_NATURE);
		return defaultFeature;
    }
    
    
    private Feature getCandidat1() {
    	Point pt = factory.createPoint(new Coordinate(CANDIDAT1_X, CANDIDAT1_Y));
		Feature defaultFeature = new Feature(pt);
		defaultFeature.addAttribut("cleabs", "1");
		defaultFeature.addAttribut("nom", CANDIDAT1_NOM);
		defaultFeature.addAttribut("nature", CANDIDAT1_NATURE);
		return defaultFeature;
    }
    
    
    private Feature getCandidat2() {
    	Point pt = factory.createPoint(new Coordinate(CANDIDAT2_X, CANDIDAT2_Y));
		Feature defaultFeature = new Feature(pt);
		defaultFeature.addAttribut("cleabs", "2");
		defaultFeature.addAttribut("nom", CANDIDAT2_NOM);
		defaultFeature.addAttribut("nature", CANDIDAT2_NATURE);
		return defaultFeature;
    }
    
    
    private Feature getCandidat3() {
    	Point pt = factory.createPoint(new Coordinate(CANDIDAT3_X, CANDIDAT3_Y));
		Feature defaultFeature = new Feature(pt);
		defaultFeature.addAttribut("cleabs", "3");
		defaultFeature.addAttribut("nom", CANDIDAT3_NOM);
		defaultFeature.addAttribut("nature", CANDIDAT3_NATURE);
		return defaultFeature;
    }
}
