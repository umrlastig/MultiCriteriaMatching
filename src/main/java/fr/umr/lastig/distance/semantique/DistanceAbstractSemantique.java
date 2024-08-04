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
package fr.umr.lastig.distance.semantique;

import fr.umr.lastig.distance.Distance;

/**
 * 
 * @author M-D Van Damme
 */
public class DistanceAbstractSemantique implements Distance {
    
    protected String attrNameSemRef;
    protected String attrNameSemComp;
    
    public void setType(String attrNameSemRef, String attrNameSemComp) {
      this.attrNameSemRef = attrNameSemRef;
      this.attrNameSemComp = attrNameSemComp;    
    }
    
    public String getAttrNameSemRef() {
      return this.attrNameSemRef;
    }
    
    public String getAttrNameSemComp() {
      return this.attrNameSemComp;
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
