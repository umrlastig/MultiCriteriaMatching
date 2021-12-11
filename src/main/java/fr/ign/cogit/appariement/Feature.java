package fr.ign.cogit.appariement;

import java.util.HashMap;
import java.util.Map;

import org.locationtech.jts.geom.Geometry;

public class Feature {
	
	private Geometry geom;
	private Map<String, Object> attributs;

	public Geometry getGeom() { return this.geom; }
	public Object getAttribute(String nom) { return attributs.get(nom); }
	
	public Feature(Geometry geom) {
		this.geom = geom;
		this.attributs = new HashMap<String, Object>();
	}
	
	public void addAttribut(String nom, Object val) {
		this.attributs.put(nom,  val);
	}
}
