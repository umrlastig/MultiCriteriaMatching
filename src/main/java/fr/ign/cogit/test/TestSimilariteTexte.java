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

import fr.ign.cogit.distance.text.DistanceSamal;



public class TestSimilariteTexte {

  public static void main(String[] args) {
    
    String s1 = "maraîchers";
    String s2 = "maraîchers - sortie 2 rue des maraichers";
    
    double d = DistanceSamal.getDistance(s1, s2);
    System.out.println("dSamal " + s1 + " - " + s2 + " = " + d);
  }

}
