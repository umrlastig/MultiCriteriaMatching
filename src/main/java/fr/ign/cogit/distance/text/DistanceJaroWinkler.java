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
package fr.ign.cogit.distance.text;

import org.apache.lucene.search.spell.JaroWinklerDistance;

import fr.ign.cogit.distance.Distance;

/**
 * 
 * @author M-D Van Damme
 */
public class DistanceJaroWinkler extends DistanceAbstractText implements Distance {
  
  @Override
  public double getDistance() {
    double distNorm = 1 - getSimilarite(txtRef, txtComp);
    return distNorm;
  }
  
  public static double getDistance(String s, String t) {
    double distNorm = 1 - DistanceJaroWinkler.getSimilarite(s, t);
    return distNorm;
  }
  
  public static double getSimilarite(String s1, String s2) {
    JaroWinklerDistance j = new JaroWinklerDistance();
    double jaro = j.getDistance(s1, s2);
    return jaro;
  }
  
  @Override
  public String getNom() {
      return "JaroWinkler";
  }

}
