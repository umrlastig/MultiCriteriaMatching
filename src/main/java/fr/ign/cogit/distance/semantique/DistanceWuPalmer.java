
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
 */package fr.ign.cogit.distance.semantique;

import java.io.File;

import edu.stanford.smi.protegex.owl.model.RDFResource;
import fr.ign.cogit.distance.Distance;
import fr.ign.cogit.ontology.OntologieOWL;
import fr.ign.cogit.ontology.similarite.MesureSimilariteSemantique;
import fr.ign.cogit.ontology.similarite.WuPalmerSemanticSimilarity;

/**
 * 
 * @author M-D Van Damme
 */
public class DistanceWuPalmer extends DistanceAbstractSemantique implements Distance {
    
    /** Ontologie. */
    private OntologieOWL onto = null;
    
    /** Default URI ontologie. */
    private static final String URI_ONTO = "./data/ontology/FusionTopoCartoExtract.owl";
    // private static final String URI_ONTO = "./data/ontology/GeOnto.owl";
    
    public DistanceWuPalmer(String uri) {
        try {
            File file = new File(uri);
            onto = new OntologieOWL("Onto", file.getPath());
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }
    
    public DistanceWuPalmer() {
    	this(URI_ONTO);
    }
    
    public void close() {
        if (onto != null) {
            onto.close();
        }
    }
    
    public DistanceWuPalmer(OntologieOWL onto) {
        this.onto = onto;
    }
    
    
    @Override
    public double getDistance() {
        float d = (float)(1 - mesureSimilariteWuPalmer(this.attrNameSemRef, this.attrNameSemComp));
        // System.out.println("Distance WP " + this.typeRef + "-" + this.typeComp + " = " + d);
        return d;
    }
    
    
    public double mesureSimilariteWuPalmer(String attrNameSemRef, String typeComp) {
        
        RDFResource rS = onto.getOWLModel().getRDFResource(attrNameSemRef.toLowerCase());
        RDFResource rT = onto.getOWLModel().getRDFResource(typeComp.toLowerCase());
        
        MesureSimilariteSemantique mesureSim = new WuPalmerSemanticSimilarity(onto);
        double scoreSimilariteSemantique = mesureSim.calcule(rS, rT);
        // System.out.println(typeRef.toLowerCase() + ", " + typeComp.toLowerCase() + " = " + scoreSimilariteSemantique);
        // LOGGER.trace("Score similarité sémantique = " + scoreSimilariteSemantique);
        
        return scoreSimilariteSemantique;
    }

    
    @Override
    public String getNom() {
        return "WuPalmer";
    }
}
