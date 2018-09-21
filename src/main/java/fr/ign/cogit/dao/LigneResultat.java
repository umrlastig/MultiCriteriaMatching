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
package fr.ign.cogit.dao;

import fr.ign.cogit.geoxygene.api.spatial.geomroot.IGeometry;

/**
 * Data object to return feature result in matching process.
 * 
 * @author M-D Van Damme
 */
public class LigneResultat {

  private int compteurC;
  
  private String idTopoRef;
  private String nomTopoRef;
  private String[] attrsTopoRef;
  
  private String idTopoComp;
  private String nomTopoComp;
  private String[] attrsTopoComp;
  
  private double[] distances;
  private String[] nomsDistance;
  
  private double probaPignistiquePremier;
  private double probaPignistiqueSecond;
  private String isDecision;
  // private double conflit;
  
  private IGeometry geomRef;
  private IGeometry geomComp;
  
  
  /**
   * Constructeur.
   * 
   */
  public LigneResultat(String idTopoRef, String nomTopoRef, String[] attrsTopoRef, 
          int compteurC, 
          String idTopoComp, String nomTopoComp, String[] attrsTopoComp, 
      double[] distances, String[] nomsDistance, double probaPignistiquePremier) {
    
      this.idTopoRef = idTopoRef;
      this.nomTopoRef = nomTopoRef;
      this.attrsTopoRef = attrsTopoRef;
    
      this.compteurC = compteurC;
      
      this.idTopoComp = idTopoComp;
      this.nomTopoComp = nomTopoComp;
      this.attrsTopoComp = attrsTopoComp;
    
      this.probaPignistiquePremier = probaPignistiquePremier;
      this.distances = distances;
      this.nomsDistance = nomsDistance;
  }
  
  public void setGeom(IGeometry geomRef, IGeometry geomComp) {
	  this.geomRef = geomRef;
	  this.geomComp = geomComp;
  }

  public IGeometry getGeomRef() {
    return this.geomRef;
  }
  
  public IGeometry getGeomComp() {
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
  
  public String getAttrTopoRef(int i) {
      if (i < this.attrsTopoRef.length) {
          return this.attrsTopoRef[i];
      } 
      return "";
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
  
  public String getAttrTopoComp(int i) {
      if (i < this.attrsTopoComp.length) {
          return this.attrsTopoComp[i];
      }
      return "";
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
  
}
