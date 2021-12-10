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
package fr.ign.cogit.metadata;

/**
 * 
 * Les tests unitaires dépendent des noms de ces attributs.
 * 
 * 
 * @author M-D Van Damme
 */
public class PAIBDCarto implements Objet {
  
  public final String ATT_CLE = "cleabs";
  public final String ATT_NOM = "nom";
  public final String ATT_NATURE = "nature";
  
  public String getCle() {
    return this.ATT_CLE;
  }
  
  public String getNom() {
    return this.ATT_NOM;
  }
  
  public String getAttrNameSemantique() {
    return this.ATT_NATURE;
  }

}
