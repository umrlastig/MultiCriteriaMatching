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

import fr.ign.cogit.metadata.Objet;
import fr.ign.cogit.distance.Distance;
import fr.ign.cogit.distance.semantique.DistanceAbstractSemantique;
import fr.ign.cogit.geoxygene.api.feature.IFeature;

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

	private String attrSemantiqueNameRef;
	private String attrSemantiqueNameComp;

	public void setMetadata(Objet objRef, Objet objComp) {
		this.attrSemantiqueNameRef = objRef.getAttrNameSemantique();
		this.attrSemantiqueNameComp = objComp.getAttrNameSemantique();
	}

	public void setFeature(IFeature featureRef, IFeature featureComp) {
		super.setFeature(featureRef, featureComp);
	}

	public void setSeuil(double seuil) {
		this.seuil = seuil;
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

		String valTypeRef = (featureRef.getAttribute(attrSemantiqueNameRef).toString());
		String valTypeComp = (featureComp.getAttribute(attrSemantiqueNameComp).toString());
		
		((DistanceAbstractSemantique) distance).setType(valTypeComp, valTypeRef);
		double distNorm = distance.getDistance();
		double[] tableau = new double[3];
		
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
                if (Double.isNaN(distNorm)) {
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
