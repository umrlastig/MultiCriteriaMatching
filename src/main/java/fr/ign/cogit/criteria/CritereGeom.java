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
 * @author M-D Van Damme
 */
public class CritereGeom extends CritereAbstract implements Critere {
  
	private double seuilT1 = 35;
	private double seuilT2 = 100;
  
	public void setSeuil(double t1, double t2) {
		this.seuilT1 = t1;
		this.seuilT2 = t2;
	}
	
	public String getSeuil() {
		return "[" + this.seuilT1 + ", " + this.seuilT2 + "]";
	}
  
	public void setFeature(IFeature featureRef, IFeature featureComp) {
		super.setFeature(featureRef, featureComp);
	}
  
	public CritereGeom(Distance d) {
		super(d);
	}

	@Override
	public double[] getMasse() throws Exception {
    
		((DistanceAbstractGeom)distance).setGeom(featureRef.getGeom(), featureComp.getGeom());
		double distNorm = distance.getDistance();
    
	    double[] tableau = new double[3];
	    if (distNorm < seuilT1) {
	    	tableau[0] = (-0.9/seuilT2) * distNorm + 1;
	    	tableau[1] = 0;
	    	tableau[2] = (0.9/seuilT2) * distNorm;
	    } else if (distNorm < seuilT2) {
	    	tableau[0] = (-0.9/seuilT2) * distNorm + 1;
	    	tableau[1] = 0.8 / (seuilT2 - seuilT1) * distNorm - 0.8 * seuilT1 / (seuilT2 - seuilT1);
	    	tableau[2] = (0.1*seuilT2 - 0.9*seuilT1) / (seuilT2*(seuilT2 - seuilT1)) * distNorm + 0.8 * seuilT1 / (seuilT2 - seuilT1);
	    } else {
	    	tableau[0] = 0.1;
	    	tableau[1] = 0.8;
	    	tableau[2] = 0.1;
	    }
    
	    try {
	    	checkSommeMasseEgale1(tableau);
	    } catch (Exception e) {
	    	LOGGER.error("Erreur 'Somme des masses' pour la distance euclidienne : " + distNorm);
	    	e.printStackTrace();
	    	throw e;
	    }
    
	    // Return 3 masses sous forme de tableau
	    return tableau;
	}
  
  
	public String getNom() {
		return "Critère géométrique";
	}
  
}
