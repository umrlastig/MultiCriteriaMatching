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

import fr.ign.cogit.appariement.Feature;
import fr.ign.cogit.distance.Distance;


/**
 * Interface to implement criteria.
 *          
 * @author M-D Van Damme
 */
public interface Critere {
  
  public String getNom();
  public String getSeuil();
  public void setFeature(Feature featureRef, Feature featureComp);
  public void checkSommeMasseEgale1(double[] tableau) throws Exception;
  public Distance getDistance();
  public double[] getMasse() throws Exception;
  // public static double getDistance(String s, String t);

}
