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
package fr.ign.cogit.criteria;

import fr.ign.cogit.distance.Distance;
import fr.ign.cogit.geoxygene.api.feature.IFeature;

/**
 * Interface to implement criteria.
 *          
 * @author M-D Van Damme
 */
public interface Critere {
  
  public String getNom();
  public void setFeature(IFeature featureRef, IFeature featureComp);
  public void checkSommeMasseEgale1(double[] tableau) throws Exception;
  public Distance getDistance();
  public double[] getMasse() throws Exception;
  // public static double getDistance(String s, String t);

}
