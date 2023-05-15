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

import org.locationtech.jts.algorithm.MinimumDiameter;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.Polygon;

import fr.ign.cogit.distance.Distance;



/**
 * Mesure le degré de co-linéarité local entre 2 polylignes.
 * 
 * @author M-D Van Damme
 */
public class DistanceEcartOrientation extends DistanceAbstractGeom implements Distance {

	@Override
	public double getDistance() {
		if (this.geomRef instanceof LineString && this.geomComp instanceof LineString) {
			
			LineString geomLigneRef = (LineString) this.geomRef;
			double mesOrientationRef = getOrientationGenerale(geomLigneRef);
			
			LineString geomLigneComp = (LineString) this.geomComp;
			double mesOrientationComp = getOrientationGenerale(geomLigneComp);
			
			double alpha = mesOrientationRef - mesOrientationComp;
			// System.out.println("alpha 1 = " + alpha + "(" + mesOrientationRef + ")(" + mesOrientationComp);
			if (alpha < 0) {
				alpha = alpha + Math.PI;
			}
			if (alpha > Math.PI) {
				alpha = alpha - Math.PI;
			}

			// System.out.println(alpha);
			return alpha;

		} else if (this.geomComp instanceof MultiLineString) {
			System.out.println("Multi linestring");
			/*
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
			*/
		} else {
			System.out.println(this.geomRef);
			System.out.println(this.geomComp.getClass());
			System.out.println("!!!!!!!!!!!!!!!!!!!!!");
			return Float.MAX_VALUE;
		}
		return Float.MAX_VALUE;
	}

	@Override
	public String getNom() {
		return "EcartOrientation";
	}
	
	
	
	/**
	 * Orientation d'une geometrie (en radian entre 0 et Pi, par rapport a l'axe Ox). 
	 * C'est l'orientation du PPRE.
	 * @return l'orientation de la géométrie : 999.9 si le PPRE n'est pas defini, ou s'il est carré
	 */
	public double getOrientationGenerale(Geometry geom) {
		
		// On triche si la ligne est un segment
		if (geom.getNumPoints() == 2 && geom instanceof LineString) {
			geom = (LineString) geom.buffer(0.1).getBoundary();
		}
		
		MinimumDiameter min = new MinimumDiameter(geom);
		Polygon ppre = (Polygon) min.getMinimumRectangle();
		
		if (ppre == null)
			return 999.9;

		// Recupere le plus long cote
		Coordinate[] coords = ppre.getCoordinates();
		double lg1 = coords[0].distance(coords[1]);
		double lg2 = coords[1].distance(coords[2]);
		if (lg1 == lg2) return 999.9;

		// l'orientation est suivant c1,c2
		Coordinate c1,c2;
		if (lg1>lg2) {c1=coords[0]; c2=coords[1]; }
		else {c1=coords[1]; c2=coords[2]; }

		// calcul de l'orientation du plus long cote
		double angle = Math.atan((c1.y - c2.y) / (c1.x - c2.x));
		if (angle < 0) angle += Math.PI;
		return angle;
	}
}
