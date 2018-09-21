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

import fr.ign.cogit.appariement.AppariementDST;
import fr.ign.cogit.dao.LigneResultat;
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
import fr.ign.cogit.io.ExportToCSV;


/**
 * 
 * 
 * @author M-D Van Damme
 */
public class MainSiberie {
    
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

    
    private static SchemaDefaultFeature schemaCandidat = null;;
    
    
    
    
    
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
        DistanceWuPalmer dwp = new DistanceWuPalmer();
        CritereSemantique cs = new CritereSemantique(dwp);
        cs.setMetadata(objRef, objComp);
        cs.setSeuil(0.7);
        listCritere.add(cs);
        
        evidenceAlgoFusionCritere.setListCritere(listCritere);
        
        IPopulation<IFeature> candidatListe = new Population<IFeature>();
        candidatListe.add(getCandidat1());
        candidatListe.add(getCandidat2());
        candidatListe.add(getCandidat3());
        
        IFeature ref = getRef();
        
        List<LigneResultat> lres = evidenceAlgoFusionCritere.appariementObjet(ref, candidatListe);
        
        TableauResultatFrame tableauPanel = new TableauResultatFrame();
        tableauPanel.displayEnsFrame("tests", lres);
        int[] tab = tableauPanel.analyse();
        System.out.println("NB non-app : " + tab[0]);
        System.out.println("NB app : " + tab[1]);
        System.out.println("NB d'indécis : " + tab[2]);
        // System.out.println("NB sans candidat : " + nbSansCandidat);
        
        ExportToCSV.exportAppariement(lres, "./data/resultat/Rando/appariement");
    }

    
    public static void main(String[] args) {
        MainSiberie m = new MainSiberie();
        try {
            m.initMainSiberie();
            m.doAppariement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void initMainSiberie() {
        
        // On initialise les schémas et les feature type  
        
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
}
