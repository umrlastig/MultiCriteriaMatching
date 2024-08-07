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
package fr.umr.lastig.criteria;

import fr.umr.lastig.appariement.Feature;
import fr.umr.lastig.distance.Distance;
import fr.umr.lastig.distance.semantique.DistanceAbstractSemantique;


/**
 * AutoCloseable.
 * 
 * @author M-D Van Damme
 */
public class CritereSemantique extends CritereAbstract implements Critere {

	/** Le seuil pour le critère WuPalmer. */
	private double seuil = 0.7;

	public CritereSemantique(Distance distance) {
		super(distance);
	}

	public void setFeature(Feature featureRef, Feature featureComp) {
		super.setFeature(featureRef, featureComp);
	}

	public void setSeuil(double seuil) {
		this.seuil = seuil;
	}
	
	public String getSeuil() {
		return "[" + this.seuil + "]";
	}

	/**
	 * Retourne la masse de croyance.
	 * 
	 * @param s : chaine de caractère
	 * @param t : chaine de caractère
	 * @return tableau : tableau[0] = masse(appC), tableau[1] = masse(nonAppC),
	 *         tableau[2] = masse(NSP)
	 */
	@Override
	public double[] getMasse() throws Exception {

		double[] tableau = new double[3];
		
		String valTypeRef = featureRef.getUri();
		String valTypeComp = featureComp.getUri();
		// System.out.println(valTypeRef + "---" + valTypeComp);
		
		((DistanceAbstractSemantique) distance).setType(valTypeComp, valTypeRef);
		double distNorm = distance.getDistance();
		// System.out.println(distNorm);
		
		if (distNorm < seuil) {
			tableau[0] = (-0.4 / seuil) * distNorm + 0.5;
			tableau[1] = (0.8 / seuil) * distNorm;
			tableau[2] = (-0.4 / seuil) * distNorm + 0.5;
		} else {
			tableau[0] = 0.1;
			tableau[1] = 0.8;
			tableau[2] = 0.1;
		}
		
		// Si concept n'est pas dans l'ontologie 
		if (Double.isNaN(distNorm) || Math.abs(distNorm - 2.0) <= 0.000001) {
			tableau[0] = 0;
			tableau[1] = 0;
			tableau[2] = 1;
		}

		try {
			checkSommeMasseEgale1(tableau);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		

		return tableau;
	}

	@Override
	public String getNom() {
		return "Critère sémantique";
	}

}
