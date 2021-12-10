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

import org.apache.log4j.Logger;

import fr.ign.cogit.distance.Distance;
import fr.ign.cogit.geoxygene.api.feature.IFeature;

/**
 * Criteria class.
 * 
 * @author M-D Van Damme
 */
public abstract class CritereAbstract implements Critere {
  
	/** Journalisation. */
	protected static Logger LOGGER = Logger.getLogger(CritereAbstract.class.getName());
  
	protected Distance distance;
  
	protected IFeature featureComp;
	protected IFeature featureRef;
  
	public CritereAbstract(Distance distance) {
		this.distance = distance;
	}
  
	public void setFeature(IFeature featureRef, IFeature featureComp) {
		this.featureRef = featureRef;
		this.featureComp = featureComp;
	}

	/**
	 * Vérifie si la somme des masses est bien égale à 1. 
	 * 
	 *	 
	 * @param tableau
	 * @throws Exception
	 */
	public void checkSommeMasseEgale1(double[] tableau) throws Exception {
		Double d = new Double(tableau[0] + tableau[1] + tableau[2]);
		if (Math.abs(d - 1) > 0.01) {
			throw new Exception("Somme des masses != 1 (somme = " + d + " = " + tableau[0] + " + " + tableau[1] + " + "+ tableau[2] + ")");
		} 
	}
  
	@Override
	public Distance getDistance() {
		return distance;
	}
  
}
