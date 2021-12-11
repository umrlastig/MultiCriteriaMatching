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
package fr.ign.cogit.distance.geom;

import org.locationtech.jts.geom.Geometry;

import fr.ign.cogit.distance.Distance;

/**
 * 
 * @author M-D Van Damme
 */
public class DistanceAbstractGeom implements Distance {
  
  protected Geometry geomRef;
  protected Geometry geomComp;
  
  public void setGeom(Geometry geomRef, Geometry geomComp) {
    this.geomRef = geomRef;
    this.geomComp = geomComp;    
  }

  @Override
  public double getDistance() {
    return 0;
  }

  @Override
  public String getNom() {
      return "None";
  }
}
