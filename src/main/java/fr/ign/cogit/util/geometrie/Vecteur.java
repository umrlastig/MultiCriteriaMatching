package fr.ign.cogit.util.geometrie;

import org.locationtech.jts.geom.Coordinate;


public class Vecteur {
	
	protected Coordinate coord = null;

	public Coordinate getCoord() {
		return this.coord;
	}

	public void setCoord(Coordinate coord) {
		this.coord = coord;
	}

	/** Initialise le vecteur 0 -> dp1 (a dp1) */
	public Vecteur(Coordinate dp1) {
		this.coord = dp1;
	}
	
	/** Initialise le vecteur dp1 -> dp2 (a dp2-dp1) */
	public Vecteur(Coordinate dp1, Coordinate dp2) {
		if (!Double.isNaN(dp1.getZ()) && !Double.isNaN(dp2.getZ())) {
			this.coord = new Coordinate(
					dp2.getX() - dp1.getX(), 
					dp2.getY() - dp1.getY(), 
					dp2.getZ() - dp1.getZ());
	    } else {
	    	this.coord = new Coordinate(
	    			dp2.getX() - dp1.getX(), 
	    			dp2.getY() - dp1.getY(), 
	    			Double.NaN);
	    }
	}
	
	public double getX() {
		return this.coord.getX();
	}

	public double getY() {
	    return this.coord.getY();
	}

	public double getZ() {
		return this.coord.getZ();
	}

	public void setX(double X) {
	    this.coord.setX(X);
	}

	public void setY(double Y) {
	    this.coord.setY(Y);
	}

	public void setZ(double Z) {
		this.coord.setZ(Z);
	}
	
	/** Renvoie la norme de this */
	public double norme() {
		if (!Double.isNaN(this.getZ())) {
	      return Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2)
	          + Math.pow(this.getZ(), 2));
	    }
	    return Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2));
	  }
	
	
	/** Renvoie un NOUVEAU vecteur égal au vecteur normé porté par this */
	public Vecteur vectNorme() {
		double normev = this.norme();
	    
		if (!Double.isNaN(this.getZ())) {
	      return new Vecteur(new Coordinate(
	    		  this.getX() / normev, 
	    		  this.getY() / normev, 
	    		  this.getZ() / normev));
	    }
	
	    return new Vecteur(new Coordinate(
	    		this.getX() / normev, 
	    		this.getY() / normev, 
	    		Double.NaN));
	}

}
