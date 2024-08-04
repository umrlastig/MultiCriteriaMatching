package fr.umr.lastig.util.geometrie;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;

import Jama.Matrix;



public class Operateurs {
	
	/**
	 * Méthode pour rééchantillonner une CoordinateSequence.
	 * 
	 * 
	 * @param list        a CoordinateSequence
	 * @param maxDistance maximum distance between 2 consecutive points
	 * @return a resampled CoordinateSequence
	 */
	public static CoordinateList resampling(CoordinateList list, double maxDistance) {
		
		CoordinateList resampledList = new CoordinateList();
		
		Coordinate prevPoint = list.getCoordinate(0);
		resampledList.add(prevPoint);
		
		for (int j = 1; j < list.size(); j++) {
			
			Coordinate nextPoint = list.get(j);
			double length = prevPoint.distance(nextPoint);
			Double fseg = length / maxDistance;
			int nseg = fseg.intValue();
			// make sure the distance between the resulting points is smaller
			// than
			// maxDistance
			if (fseg.doubleValue() > nseg) {
				nseg++;
			}
			// compute the actual distance between the resampled points
			double d = length / nseg;
			if (nseg >= 1) {
				Vecteur v = new Vecteur(prevPoint, nextPoint).vectNorme();
				for (int i = 0; i < nseg - 1; i++) {
					Coordinate curPoint = new Coordinate(
							prevPoint.getX() + (i + 1) * d * v.getX(),
							prevPoint.getY() + (i + 1) * d * v.getY(), 
							prevPoint.getZ() + (i + 1) * d * v.getZ());
					resampledList.add(curPoint);
				}
				
			}
			resampledList.add(nextPoint);
			
			prevPoint = nextPoint;
		}
		
		return resampledList;
	}
	
	
	/**
	 * Methode qui donne l'angle (radians) par rapport à l'axe des x de la droite
	 * passant au mieux au milieu d'un nuage de points (regression par moindres
	 * carrés). Cet angle (défini à pi près) est entre 0 et pi. English: Linear
	 * approximation.
	 */
	public static Angle directionPrincipale(CoordinateList listePts) {
		Angle ang = new Angle();
		double angle;
		double x0, y0, x, y, a;
		double moyenneX = 0, moyenneY = 0;
		Matrix Atrans, A, B, X;
		int i;

		// cas où la ligne n'a que 2 pts
		if ((listePts.size() == 2)) {
			ang = new Angle(listePts.get(0), listePts.get(1));
			angle = ang.getValeur();
			if (angle >= Math.PI) {
				return new Angle(angle - Math.PI);
			}
			return new Angle(angle);
		}

		// initialisation des matrices
		// On stocke les coordonnées, en se ramenant dans un repère local sur
		// (x0,y0)
		A = new Matrix(listePts.size(), 1); // X des points de la ligne
		B = new Matrix(listePts.size(), 1); // Y des points de la ligne
		x0 = listePts.get(0).getX();
		y0 = listePts.get(0).getY();
		for (i = 0; i < listePts.size(); i++) {
			x = listePts.get(i).getX() - x0;
			moyenneX = moyenneX + x;
			A.set(i, 0, x);
			y = listePts.get(i).getY() - y0;
			moyenneY = moyenneY + y;
			B.set(i, 0, y);
		}
		moyenneX = moyenneX / listePts.size();
		moyenneY = moyenneY / listePts.size();

		// cas où l'angle est vertical
		if (moyenneX == 0) {
			return new Angle(Math.PI / 2);
		}

		// cas général : on cherche l'angle par régression linéaire
		Atrans = A.transpose();
		X = (Atrans.times(A)).inverse().times(Atrans.times(B));
		a = X.get(0, 0);
		angle = Math.atan(a);
		// on obtient un angle entre -pi/2 et pi/2 ouvert
		// on replace cet angle dans 0 et pi
		if (angle < 0) {
			return new Angle(angle + Math.PI);
		}
		return new Angle(angle);
	}

}
