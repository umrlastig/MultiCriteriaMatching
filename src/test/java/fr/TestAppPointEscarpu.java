package fr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import fr.ign.cogit.appariement.AppariementDST;
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
import fr.ign.cogit.dao.LigneResultat;
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
	
    private static final String REF_NOM = "l'escarpu";
    private static final String REF_NATURE = "Pic";
    private static final double REF_X = 413721.1;
    private static final double REF_Y = 6208543.3;
    
    private static final String CANDIDAT1_NOM = "col de sesques";
    private static final String CANDIDAT1_NATURE = "Col";
    private static final double CANDIDAT1_X = 413448.6;
    private static final double CANDIDAT1_Y = 6208086;
    
    private static final String CANDIDAT2_NOM = "l'escarpu ou pic de sesques";
    private static final String CANDIDAT2_NATURE = "Pic";
    private static final double CANDIDAT2_X = 413406.6;
    private static final double CANDIDAT2_Y = 6208683.7;
    
    private static final String CANDIDAT3_NOM = "crête de sesques";
    private static final String CANDIDAT3_NATURE = "Sommet";
    private static final double CANDIDAT3_X = 414188.6;
    private static final double CANDIDAT3_Y = 6209026.9;
	
	private static SchemaDefaultFeature schemaCandidat = null;;
	
	
	public TestAppPointEscarpu() {
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
	
	
	/**
	 * Appariement 3 criteres dans l'ordre : toponymie + géométrie + sémantique
	 * 
	 * @throws Exception
	 */
	public void testApp1Critere() throws Exception {
		
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
		// Assert.assertEquals("Nature des objets", ligne1.getTypeTopoRef(), REF_NATURE);
		// Assert.assertEquals("Nature des objets", ligne1.getTypeTopoComp(), CANDIDAT1_NATURE);
		Assert.assertEquals("Distance toponymique", ligne1.getDistance(0), 0.833, 0.001);
		Assert.assertEquals("Distance euclidienne", ligne1.getDistance(1), 532.334, 0.001);
		Assert.assertEquals("Distance sémantique", ligne1.getDistance(2), 0.4286, 0.0001);
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
		tableauPanel.setListeResultat(lres);
        tableauPanel.initResultat();
        int[] tab = tableauPanel.analyse();
        
        Assert.assertEquals("NB NA : ", tab[0], 1);
        Assert.assertEquals("NB Appariement : ", tab[1], 0);
        Assert.assertEquals("NB d'indécis : ", tab[2], 0);
	}

}

/**
 
-- Select nature, toponyme, st_astext(geometrie) as point
-- From bdc_point_remarquable_du_relief
-- Where toponyme like '%escarpu%'


Select graphie_principale, nature, st_astext(geometrie) as point
From pai_orographie
Where graphie_principale like '%sesques%'

*/
