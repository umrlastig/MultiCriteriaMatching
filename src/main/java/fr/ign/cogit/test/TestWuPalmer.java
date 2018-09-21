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
package fr.ign.cogit.test;


import java.io.File;

import edu.stanford.smi.protegex.owl.model.RDFResource;
import fr.ign.cogit.distance.semantique.DistanceWuPalmer;
import fr.ign.cogit.ontology.OntologieOWL;
import fr.ign.cogit.ontology.similarite.MesureSimilariteSemantique;
import fr.ign.cogit.ontology.similarite.WuPalmerSemanticSimilarity;

public class TestWuPalmer {

    /** URI ontologie. */
    private static final String URI_ONTO = "./data/ontology/FusionTopoCartoExtract.owl";
    // private static final String URI_ONTO = "./data/ontology/GeOnto.owl";
    
    // "bois"  "maison_forestière";
    // double min = CritereWuPalmer.getDistance("pont", "ruisseau");
    // double min = CritereWuPalmer.getDistance("mer", "océan");
  
  
  public static void test4() {
      
      OntologieOWL ontoTopoCarto = null;
      try {
          File file = new File(URI_ONTO);
          ontoTopoCarto = new OntologieOWL("Onto", file.getPath());
          
          MesureSimilariteSemantique mesureSim = new WuPalmerSemanticSimilarity(ontoTopoCarto);
          
          RDFResource rS = ontoTopoCarto.getOWLModel().getRDFResource("summit");
          RDFResource rT = ontoTopoCarto.getOWLModel().getRDFResource("sommet");
          
          // SOMMET-MONTAGNE
          double scoreSimilariteSemantique = mesureSim.calcule(rS, rT);
          
          System.out.println(scoreSimilariteSemantique);
          
          
        } catch(Exception e) {
          e.printStackTrace();
        } finally {
          if (ontoTopoCarto != null) {
            ontoTopoCarto.close();
          }
        }
  }
  
  
  
  public static void main(String[] args) {
      //    test1();
      //    test2();
      //    test3();
      test4();
      
      DistanceWuPalmer dWP = new DistanceWuPalmer();
      double m = dWP.mesureSimilariteWuPalmer("Col, passage", "Sommet");
      double d = (float)(1 - m);
      System.out.println("d=" + d);
      
      m = dWP.mesureSimilariteWuPalmer("Col, passage", "Col");
      d = (float)(1 - m);
      System.out.println("d=" + d);
      
  }

}
