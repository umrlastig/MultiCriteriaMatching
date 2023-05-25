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

import java.util.logging.Level;

import fr.ign.cogit.appariement.Feature;
import fr.ign.cogit.distance.Distance;
import fr.ign.cogit.distance.geom.DistanceAbstractGeom;


/**
 * 
 * 
 */
public class CritereOrientation extends CritereAbstract implements Critere {
  
	private double seuilAngle = Math.PI / 2;
  
	public void setSeuil(double seuilAngle) {
		this.seuilAngle = seuilAngle;
	}
	
	public String getSeuil() {
		return "[" + this.seuilAngle + "]";
	}
  
	public void setFeature(Feature featureRef, Feature featureComp) {
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
			// System.out.println("1, " + valeurAngle);
			tableau[0] = -0.5 / seuilAngle * valeurAngle + 0.5;
			tableau[1] = +0.5 / seuilAngle * valeurAngle;
			tableau[2] = +0.5;
		} else { // if (valeurAngle > seuilAngle && valeurAngle <= Math.PI) {
			// System.out.println("2, " + valeurAngle);
			tableau[0] = +0.5 / seuilAngle * valeurAngle - 0.5;
			tableau[1] = -0.5 / seuilAngle * valeurAngle + 1;
			tableau[2] = +0.5;
		} 
		
		// System.out.println(Arrays.toString(tableau));
    
    try {
      checkSommeMasseEgale1(tableau);
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Erreur 'Somme des masses' pour la distance orientation : " + valeurAngle);
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
