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
package fr.umr.lastig.distance.geom;

import fr.umr.lastig.distance.Distance;

/**
 * 
 * @author M-D Van Damme
 */
public class DistanceEuclidienne extends DistanceAbstractGeom implements Distance {
  
  @Override
  public double getDistance() {
    return (float) this.geomComp.distance(this.geomRef);
  }
  
  @Override
  public String getNom() {
      return "Euclidienne";
  }

}
