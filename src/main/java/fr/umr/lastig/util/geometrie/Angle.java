package fr.umr.lastig.util.geometrie;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;

import Jama.Matrix;

/**
 * 
 *
 */
public class Angle {
	
	protected double valeur = 0;

	public double getValeur() {
		return this.valeur;
	}

	public void setValeur(double valeur) {
		if (valeur % (2 * Math.PI) >= 0) {
			this.valeur = Math.abs(valeur % (2 * Math.PI));
	    } else {
	    	this.valeur = (valeur % (2 * Math.PI) + 2 * Math.PI);
	    }
	}
	  
	/* Constructeurs */
	public Angle() {
	}

	public Angle(double valeur) {
		this.setValeur(valeur);
	}

	/** Angle entre 2 points dans le plan X,Y (valeur comprise entre 0 et 2*pi) */
	public Angle(Coordinate pt1, Coordinate pt2) {
		double x = pt2.getX() - pt1.getX();
	    double y = pt2.getY() - pt1.getY();
	    this.setValeur(Math.atan2(y, x));
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
	
	
	/**
	 * Ecart de a vers b dans le sens trigonométrique, ex : ecart(pi/4, 7pi/4) =
	 * 3pi/2
	 */
	public static Angle ecarttrigo(Angle a, Angle b) {
		return new Angle(b.valeur - a.valeur);
	}

	/**
	 * Ecart au plus court entre les deux angles, dans [0,pi], ex : ecart(pi/4,
	 * 7pi/4) = pi/2
	 */
	public static Angle ecart(Angle a, Angle b) {
		return new Angle(Math.min(Angle.ecarttrigo(a, b).getValeur(), Angle
	        .ecarttrigo(b, a).getValeur()));
	}
}
