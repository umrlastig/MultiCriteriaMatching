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
package fr.umr.lastig.appariement;

import org.locationtech.jts.geom.Geometry;

/**
 * Data object to return feature result in matching process.
 * 
 * @author M-D Van Damme
 */
public class LigneResultat {

  private int compteurC;
  
  private String idTopoRef;
  private String nomTopoRef;
  private String uriTopoRef;
  
  private String idTopoComp;
  private String nomTopoComp;
  private String uriTopoComp;
  
  private double[] distances;
  private String[] nomsDistance;
  
  private double probaPignistiquePremier;
  private double probaPignistiqueSecond;
  private String isDecision;
  // private double conflit;
  
  private Geometry geomRef;
  private Geometry geomComp;
  
  
  /**
   * Constructeur.
   * 
   */
  public LigneResultat(String idTopoRef, String nomTopoRef, String uriTopoRef, 
          int compteurC, 
          String idTopoComp, String nomTopoComp, String uriTopoComp, 
      double[] distances, String[] nomsDistance, double probaPignistiquePremier) {
    
      this.idTopoRef = idTopoRef;
      this.nomTopoRef = nomTopoRef;
      this.uriTopoRef = uriTopoRef;
    
      this.compteurC = compteurC;
      
      this.idTopoComp = idTopoComp;
      this.nomTopoComp = nomTopoComp;
      this.uriTopoComp = uriTopoComp;
    
      this.probaPignistiquePremier = probaPignistiquePremier;
      this.distances = distances;
      this.nomsDistance = nomsDistance;
  }
  
  public void setGeom(Geometry geomRef, Geometry geomComp) {
	  this.geomRef = geomRef;
	  this.geomComp = geomComp;
  }

  public Geometry getGeomRef() {
    return this.geomRef;
  }
  
  public Geometry getGeomComp() {
    return this.geomComp;
  }
  
  public void initDecision(String isDecision) {
    this.isDecision = isDecision;
  }
  
  public void initProbaPignistiqueSecond(double probaPignistiqueSecond) {
    this.probaPignistiqueSecond = probaPignistiqueSecond;
  }
   
  public String getIdTopoRef() {
    return this.idTopoRef;
  }
  
  public String getNomTopoRef() {
    return this.nomTopoRef;
  }
  
  public String getUriTopoRef() {
      return this.uriTopoRef;
  }
  
  public int getCompteurC() {
    return this.compteurC;
  }
  
  public String getIdTopoComp() {
    return this.idTopoComp;
  }
  
  public String getNomTopoComp() {
    return this.nomTopoComp;
  }
  
  public String getUriTopoComp() {
	  return this.uriTopoComp;
  }
  
  public double getProbaPignistiquePremier() {
    return this.probaPignistiquePremier;
  }
  
  public String isDecision() {
    return this.isDecision;
  }
  
  public double getProbaPignistiqueSecond() {
    return this.probaPignistiqueSecond;
  }
  
  public double getDistance(int i) {
      if (i < this.distances.length) {
          return this.distances[i];
      } 
      return -1;
  }
  
  public double[] getDistances() {
      return this.distances;
  }
  
  public String getNomDistance(int i) {
      if (i < this.nomsDistance.length) {
          return this.nomsDistance[i];
      }
      return "";
  }
  
  @Override
  public String toString() {
	  return "[" + this.getNomDistance(0) + ":" + this.getDistance(0) + ", " 
			  + this.getNomDistance(1) + ":" + this.getDistance(1) + ", " 
			  + this.getNomDistance(2) + ":" + this.getDistance(2) + "]";
  }
}
