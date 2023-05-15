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

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.operation.distance.DistanceOp;

import fr.ign.cogit.distance.Distance;

/**
 * 
 * @author M-D Van Damme
 */
public class DistanceDirectedHausdorff extends DistanceAbstractGeom implements Distance {
	  
   /**
	* Approximation de la première composante de Hausdorff d'une ligne vers une
	* autre. Elle est calculee comme le maximum des distances des points
	* intermédiaires de la première ligne L1 à l'autre ligne L2.
	*/
	@Override
	public double getDistance() {
		LineString l1 = (LineString) this.geomComp;
		LineString l2 = (LineString) this.geomRef;
		
		GeometryFactory factory = new GeometryFactory();
		
		double result = 0;
	    for (int i = 0; i < l1.getCoordinates().length; i++) {
	    	Point p = factory.createPoint(l1.getCoordinateN(i));
	    	DistanceOp dist = new DistanceOp(p, l2);
	    	double d = dist.distance();
	    	result = Math.max(d, result);
	    }
	    return result;
	}

	@Override
	public String getNom() {
		return "premiereComposanteHausdorff";
	}
}
