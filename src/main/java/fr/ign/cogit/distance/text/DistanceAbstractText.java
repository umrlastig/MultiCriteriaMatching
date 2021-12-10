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

import fr.ign.cogit.distance.Distance;

/**
 * 
 * @author M-D Van Damme
 */
public class DistanceAbstractText implements Distance {
  
  protected String txtRef;
  protected String txtComp;
  
  public void setText(String txtRef, String txtComp) {
    this.txtRef = txtRef;
    this.txtComp = txtComp;    
  }
  
  public String getTxtRef() {
    return this.txtRef;
  }
  
  public String getTxtComp() {
    return this.txtComp;
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
