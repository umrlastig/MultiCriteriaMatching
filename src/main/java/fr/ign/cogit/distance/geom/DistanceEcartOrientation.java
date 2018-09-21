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
package fr.ign.cogit.distance.geom;

import fr.ign.cogit.distance.Distance;
import fr.ign.cogit.geoxygene.api.spatial.coordgeom.ILineString;
import fr.ign.cogit.geoxygene.spatial.coordgeom.GM_LineString;
import fr.ign.cogit.geoxygene.spatial.geomaggr.GM_MultiCurve;
import fr.ign.cogit.geoxygene.util.algo.JtsUtil;
import fr.ign.cogit.geoxygene.util.algo.MesureOrientation;

/**
 * 
 * @author M-D Van Damme
 */
public class DistanceEcartOrientation extends DistanceAbstractGeom implements Distance {

	@Override
	public double getDistance() {
		if (this.geomRef instanceof ILineString && this.geomComp instanceof ILineString) {

			ILineString geomLigneRef = (ILineString) this.geomRef;
			MesureOrientation mesure = new MesureOrientation(geomLigneRef);
			double mesOrientationRef = mesure.getOrientationGenerale();

			ILineString geomLigneComp = (ILineString) this.geomComp;
			mesure = new MesureOrientation(geomLigneComp);
			double mesOrientationComp = mesure.getOrientationGenerale();

			double alpha = mesOrientationRef - mesOrientationComp;
			System.out.println("alpha 1 = " + alpha);
			if (alpha < 0) {
				alpha = alpha + Math.PI;
			}
			if (alpha > Math.PI) {
				alpha = alpha - Math.PI;
			}

			return alpha;

		} else if (this.geomComp instanceof GM_MultiCurve) {

			try {

				double mesOrientationRef = 0;
				ILineString geomLigneRef = (ILineString) this.geomRef;
				if (geomLigneRef.coord().size() == 2) {
					// Ligne avec 2 points
					JtsUtil.orientationLigne(geomLigneRef.coord().get(0), geomLigneRef.coord().get(1));
				} else {
					MesureOrientation mesure = new MesureOrientation(geomLigneRef);
					mesOrientationRef = mesure.getOrientationGenerale();
				}

				double mesOrientationComp = 0;
				GM_LineString geomLigneComp = (GM_LineString) ((GM_MultiCurve<?>) this.geomComp).get(0);
				if (geomLigneComp.coord().size() == 2) {
					JtsUtil.orientationLigne(geomLigneComp.coord().get(0), geomLigneComp.coord().get(1));
				} else {
					MesureOrientation mesure = new MesureOrientation(geomLigneComp);
					mesOrientationComp = mesure.getOrientationGenerale();
				}

				double alpha = mesOrientationRef - mesOrientationComp;
				// System.out.println("alpha 2 = " + alpha);
				if (alpha < 0) {
					alpha = alpha + Math.PI;
				}
				if (alpha > Math.PI) {
					alpha = alpha - Math.PI;
				}

				return alpha;

			} catch (Exception e) {
				System.out.println("ERROR - geometry is not a LineString.");
				// e.printStackTrace();
				return Float.MAX_VALUE;
			}

		} else {
			System.out.println(this.geomRef);
			System.out.println(this.geomComp.getClass());
			System.out.println("!!!!!!!!!!!!!!!!!!!!!");
			return Float.MAX_VALUE;
		}
	}

	@Override
	public String getNom() {
		return "EcartOrientation";
	}
}
