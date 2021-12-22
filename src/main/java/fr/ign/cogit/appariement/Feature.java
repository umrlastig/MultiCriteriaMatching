package fr.ign.cogit.appariement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.Geometry;

public class Feature {
	
	private static final String NOM_CLE_ID = "id";
	private static final String NOM_CLE_NOM = "nom";
	private static final String NOM_CLE_NATURE = "uri";
	
	private Geometry geom;
	private Map<String, Object> attributs;
	private List<String> graphies;
	
	public Feature(Geometry geom) {
		this.geom = geom;
		this.graphies = new ArrayList<String>();
		this.attributs = new HashMap<String, Object>();
	}
	
	public void addAttribut(String nom, Object val) {
		this.attributs.put(nom,  val);
	}
	
	public void addGraphie(String graphie) {
		this.graphies.add(graphie);
	}
	
	public Geometry getGeom() { return this.geom; }
	public Object getAttribute(String nom) { return attributs.get(nom); }
	public List<String> getGraphies() { return this.graphies; }
	
	public String getId() { return this.attributs.get(NOM_CLE_ID).toString(); }
	public String getNom() {
		if (this.attributs.get(NOM_CLE_NOM) == null) return null;
		return this.attributs.get(NOM_CLE_NOM).toString(); 
	}
	public String getUri() { 
		if (this.attributs.get(NOM_CLE_NATURE) == null) return null;
		return this.attributs.get(NOM_CLE_NATURE).toString(); 
	}
}
