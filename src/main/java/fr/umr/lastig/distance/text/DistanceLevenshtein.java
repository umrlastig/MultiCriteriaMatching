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
package fr.umr.lastig.distance.text;

import org.apache.commons.lang.StringUtils;

import fr.umr.lastig.distance.Distance;

/**
 * Distance(s1, s2) = DistanceLevenshtein(s1, s2) / max(long(s1), long(s2))
 * @author M-D Van Damme
 */
public class DistanceLevenshtein extends DistanceAbstractText implements Distance {
  
  @Override
  public double getDistance() {
    double l = StringUtils.getLevenshteinDistance(txtRef, txtComp);
    double distNorm = l / Math.max (txtRef.length(), txtComp.length());
    return distNorm;
  }
  
  public static double getDistance(String s, String t) {
    double l = StringUtils.getLevenshteinDistance(s, t);
    double distNorm = l / Math.max (s.length(), t.length());
    return distNorm;
  }
  
  @Override
  public String getNom() {
      return "Levenshtein";
  }
}
