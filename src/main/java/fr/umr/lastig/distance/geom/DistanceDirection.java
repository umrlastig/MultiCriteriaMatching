package fr.umr.lastig.distance.geom;

import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.LineString;

import fr.umr.lastig.distance.Distance;
import fr.umr.lastig.util.geometrie.Angle;
import fr.umr.lastig.util.geometrie.Operateurs;



public class DistanceDirection extends DistanceAbstractGeom implements Distance {
	
	@Override
	public double getDistance() {
		LineString l1 = (LineString) this.geomComp;
		LineString l2 = (LineString) this.geomRef;
		double valeurAngle = calculValeurAngleRegression(l2, l1);
		return valeurAngle;
	}
	
	public double calculValeurAngleRegression(LineString objetRef, LineString objetComp) {
		double valeurAngle = 0;
		Angle angleArcRef = directionArcEchantillone(objetRef, 2.0);
		Angle angleArcComp = directionArcEchantillone(objetComp, 2.0);
		Angle angle=Angle.ecart(angleArcRef, angleArcComp);
		valeurAngle = angle.getValeur();
		return valeurAngle;
	}
	
	protected static Angle directionArcEchantillone(LineString arc, double pasEchantillonage) {
		CoordinateList arcEchantillone = null; 
		if (pasEchantillonage == 0) arcEchantillone = new CoordinateList(arc.getCoordinates());
		else  arcEchantillone = Operateurs.resampling(new CoordinateList(arc.getCoordinates()), pasEchantillonage);
		Angle angle = Operateurs.directionPrincipale(arcEchantillone);
		return 	angle;
	}
	
	@Override
	public String getNom() {
		return "Direction";
	}
	
}
