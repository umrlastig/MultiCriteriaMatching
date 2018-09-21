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
import fr.ign.cogit.distance.geom.DistanceAbstractGeom;
import fr.ign.cogit.geoxygene.api.feature.IFeature;

/**
 * 
 * 
 *  @author M-D Van Damme
 */
public class CritereOrientation extends CritereAbstract implements Critere {
  
	private double seuilAngle = Math.PI / 2;
  
	public void setSeuil(double seuilAngle) {
		this.seuilAngle = seuilAngle;
	}
  
	public void setFeature(IFeature featureRef, IFeature featureComp) {
		super.setFeature(featureRef, featureComp);
	}
  
	public CritereOrientation(Distance d) {
		super(d);
	}
  
	@Override
	public double[] getMasse() throws Exception {
    
		((DistanceAbstractGeom)distance).setGeom(featureRef.getGeom(), featureComp.getGeom());
		double valeurAngle = distance.getDistance();
    
		double[] tableau = new double[3];
    
		if (valeurAngle < seuilAngle) {
			tableau[0] = -0.5 / seuilAngle * valeurAngle + 0.5;
			tableau[1] = +0.5 / seuilAngle * valeurAngle;
			tableau[2] = +0.5;
		} else {
			tableau[0] = +0.5 / seuilAngle * valeurAngle - 0.5;
			tableau[1] = -0.5 / seuilAngle * valeurAngle + 1;
			tableau[2] = +0.5;
		}
    
    try {
      checkSommeMasseEgale1(tableau);
    } catch (Exception e) {
      LOGGER.error("Erreur 'Somme des masses' pour la distance orientation : " + valeurAngle);
      e.printStackTrace();
      throw e;
    }
    
    // Return 3 masses sous forme de tableau
    return tableau;
  }
  
  
  public String getNom() {
    return "Critère orientation";
  }

}
