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

import fr.ign.cogit.appariement.Feature;
import fr.ign.cogit.distance.Distance;
import fr.ign.cogit.distance.text.DistanceAbstractText;

/**
 * Critère toponymique.
 * 
 * @author M-D Van Damme
 */
public class CritereToponymique extends CritereAbstract implements Critere {
  
	/** Le seuil pour les masses de croyances. */
	private double seuil = 0.6;
  
	public CritereToponymique(Distance d) {
		super(d);
	}
  
	public void setSeuil(double seuil) {
		this.seuil = seuil;
	}
	
	public String getSeuil() {
		return "[" + this.seuil + "]";
	}
  
	public void setFeature(Feature featureRef, Feature featureComp) {
		super.setFeature(featureRef, featureComp);
	}
  
 
	/**
	 * Retourne la masse de croyance.
	 * @param s : chaine de caractère
	 * @param t : chaine de caractère
	 * @return tableau :
	 *    tableau[0] = masse(appC), 
	 *    tableau[1] = masse(nonAppC), 
	 *    tableau[2] = masse(NSP)
	 * @throws Exception 
	 */
	public double[] getMasse() throws Exception {
		
		double[] tableau = new double[3];
    
		String nomTopoComp = "";
		if (featureComp.getNom() != null && featureComp.getNom() != "") {
			nomTopoComp = new String(featureComp.getNom().getBytes("ISO-8859-1"), "UTF-8");
			nomTopoComp = nomTopoComp.toLowerCase();
		} else {
			tableau[0] = 0;
			tableau[1] = 0;
			tableau[2] = 1;
			return tableau;
		}
		    
		String nomTopoRef = "";
		if (featureRef.getNom() != null && featureRef.getNom() != "") {
			nomTopoRef = new String(featureRef.getNom().getBytes("ISO-8859-1"), "UTF-8");
			nomTopoRef=nomTopoRef.toLowerCase();
		} else {
			tableau[0] = 0;
			tableau[1] = 0;
			tableau[2] = 1;
			return tableau;
		}
		
		((DistanceAbstractText)distance).setText(nomTopoRef, nomTopoComp);
		Double distNorm = distance.getDistance();
		double d = 10.0;

		if (featureRef.getGraphies().size() > 0) {
			if (featureComp.getGraphies().size() > 0) {
				for (String graphieRef : featureRef.getGraphies()) {
					graphieRef = new String(graphieRef.getBytes("ISO-8859-1"), "UTF-8");
					graphieRef = graphieRef.toLowerCase();
					((DistanceAbstractText)distance).setText(graphieRef, nomTopoComp);
					d = distance.getDistance();
					if (d < distNorm) {
						distNorm = d;
					}
					for (String graphieComp : featureComp.getGraphies()) {
						graphieComp = new String(graphieComp.getBytes("ISO-8859-1"), "UTF-8");
						graphieComp = graphieComp.toLowerCase();
						((DistanceAbstractText)distance).setText(graphieRef, graphieComp);
						d = distance.getDistance();
						if (d < distNorm) {
							distNorm = d;
						}
					}
				}
			} else {
				for (String graphieRef : featureRef.getGraphies()) {
					graphieRef = new String(graphieRef.getBytes("ISO-8859-1"), "UTF-8");
					graphieRef = graphieRef.toLowerCase();
					((DistanceAbstractText)distance).setText(graphieRef, nomTopoComp);
					d = distance.getDistance();
					// System.out.println(d + "-" + graphieRef + "--" + nomTopoComp);
					if (d < distNorm) {
						distNorm = d;
					}
				}
			}
		} else {
			if (featureComp.getGraphies().size() > 0) {
				//
			} 
		}
		
		if (distNorm.isNaN()) {
            tableau[0] = 0;
            tableau[1] = 0;
            tableau[2] = 1;
        } else if (distNorm < seuil) {
			tableau[0] = (-0.9/seuil)*distNorm + 1;
			tableau[1] = (0.5/seuil)*distNorm;
			tableau[2] = (0.4/seuil)*distNorm;
		} else {
			tableau[0] = 0.1;
			tableau[1] = 0.5;
			tableau[2] = 0.4;
		}
    
		try {
			checkSommeMasseEgale1(tableau);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    
		// 	Return 3 masses sous forme de tableau
		return tableau;
	}
  
  
	public String getNom() {
		return "Critère toponymique";
	}
  
}

