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
import fr.ign.cogit.distance.text.DistanceAbstractText;
import fr.ign.cogit.geoxygene.api.feature.IFeature;

/**
 * Critère toponymique.
 * 
 * @author M-D Van Damme
 */
public class CritereToponymique extends CritereAbstract implements Critere  {
  
	/** Le seuil pour les masses de croyances. */
	private double seuil = 0.6;
  
	private String nomAttRef;
	private String nomAttComp;
  
	public CritereToponymique(Distance d) {
		super(d);
	}
  
	public void setMetadata(Objet objRef, Objet objetComp) {
		this.nomAttRef = objRef.getNom();
		this.nomAttComp = objetComp.getNom();
	}
  
	public void setSeuil(double seuil) {
		this.seuil = seuil;
	}
  
	public void setFeature(IFeature featureRef, IFeature featureComp) {
		super.setFeature(featureRef, featureComp);
		//    this.nomTopoComp = featureComp.getAttribute(nomAttComp).toString().toLowerCase();
		//    if (featureRef.getAttribute(nomAttRef) != null) {
		//      this.nomTopoRef = featureRef.getAttribute(nomAttRef).toString().toLowerCase();
		//    } else {
		//      this.nomTopoRef = "";
		//    }
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
    
		String nomTopoComp = "";
		/*if (featureComp.getAttribute(nomAttComp) != null && featureComp.getAttribute(nomAttComp) != "") {
			nomTopoComp = featureComp.getAttribute(nomAttComp).toString().toLowerCase();
		}
		String nomTopoRef = "";
		if (featureRef.getAttribute(nomAttRef) != null) {
			nomTopoRef = featureRef.getAttribute(nomAttRef).toString().toLowerCase();
		}*/ 
		
		if (featureComp.getAttribute(nomAttComp) != null && featureComp.getAttribute(nomAttComp) != "") {
		        nomTopoComp=new String(featureComp.getAttribute(nomAttComp).toString().getBytes("ISO-8859-1"), "UTF-8");
		        nomTopoComp=nomTopoComp.toLowerCase();
		 }
		    
		String nomTopoRef = "";
		if (featureRef.getAttribute(nomAttRef) != null) {
		       nomTopoRef = new String(featureRef.getAttribute(nomAttRef).toString().getBytes("ISO-8859-1"), "UTF-8");
		       nomTopoRef=nomTopoRef.toLowerCase();
		} 
		
		((DistanceAbstractText)distance).setText(nomTopoRef, nomTopoComp);
		Double distNorm = distance.getDistance();
    
		double[] tableau = new double[3];
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
    
		// Si nom est NULL, on écrase 
		if (nomTopoRef == null ) {
			tableau[0] = 0;
			tableau[1] = 0;
			tableau[2] = 1;
		}
		// Si nom objet Ref = NR, on écrase 
		if (nomTopoComp == null || nomTopoComp.equals("nr")) {
		    tableau[0] = 0;
		    tableau[1] = 0;
		    tableau[2] = 1;
		}
	    
	    /*if (distNorm < seuil) {
	      tableau[0] = (-0.4/seuil)*distNorm + 0.5;
	      tableau[1] = (0.8/seuil)*distNorm;
	      tableau[2] = (-0.4/seuil)*distNorm + 0.5;
	    } else {
	      tableau[0] = 0.1;
	      tableau[1] = 0.8;
	      tableau[2] = 0.1;
	    }*/
    
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

