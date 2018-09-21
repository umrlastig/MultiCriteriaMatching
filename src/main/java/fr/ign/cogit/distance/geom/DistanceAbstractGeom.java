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

import fr.ign.cogit.distance.Distance;
import fr.ign.cogit.geoxygene.api.spatial.geomroot.IGeometry;

/**
 * 
 * @author M-D Van Damme
 */
public class DistanceAbstractGeom implements Distance {
  
  protected IGeometry geomRef;
  protected IGeometry geomComp;
  
  public void setGeom(IGeometry geomRef, IGeometry geomComp) {
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
