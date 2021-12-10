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
 * Distance(s1, s2) = DistanceLevenshtein(s1, s2) / max(long(s1), long(s2))
 * @author M-D Van Damme
 */
public class DistanceSamal extends DistanceAbstractText implements Distance {
  
	public static double mesureRessemblanceToponymeSamal(String string1, String string2) {
		return MesureRessemblance.getMesureRessemblance(string1, string2);
	}
  
	@Override
	public double getDistance() {
	    if (txtComp.equals("nr")) {
            return Double.NaN;
        } 
        return Math.abs(1 - mesureRessemblanceToponymeSamal(txtRef, txtComp));
	}
  
	public static double getDistance(String s, String t) {
		return 1 - mesureRessemblanceToponymeSamal(s, t);
	}
  
	@Override
	public String getNom() {
		return "Samal";
	}
}
