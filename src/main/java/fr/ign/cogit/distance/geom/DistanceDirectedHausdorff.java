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
//import fr.ign.cogit.geoxygene.api.spatial.coordgeom.ILineString;
//import fr.ign.cogit.geoxygene.contrib.geometrie.Distances;
//import fr.ign.cogit.geoxygene.spatial.coordgeom.GM_LineString;
//import fr.ign.cogit.geoxygene.spatial.geomaggr.GM_MultiCurve;

/**
 * 
 * @author M-D Van Damme
 */
public class DistanceDirectedHausdorff extends DistanceAbstractGeom implements Distance {
	  
	@Override
	public double getDistance() {
//		if (this.geomRef instanceof ILineString && this.geomComp instanceof ILineString) {
//			ILineString geomLigneRef = (ILineString) this.geomRef;
//			ILineString geomLigneComp = (ILineString) this.geomComp;
//			return Distances.premiereComposanteHausdorff(geomLigneRef, geomLigneComp);
//		} else if (this.geomComp instanceof GM_MultiCurve && this.geomRef instanceof ILineString) {
//			GM_LineString geomLigneComp = (GM_LineString)((GM_MultiCurve<?>)this.geomComp).get(0);
//			ILineString geomLigneRef = (ILineString) this.geomRef;
//			return Distances.premiereComposanteHausdorff(geomLigneRef, geomLigneComp);
//		} else {
			return Float.MAX_VALUE;
//		}
	}

	@Override
	public String getNom() {
		return "DirectedHausdorff";
	}
}
