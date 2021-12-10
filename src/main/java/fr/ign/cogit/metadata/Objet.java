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


public interface Objet {
  
	/**
	 * Obligatoire.
	 * 
	 * @return
	 */
	public String getCle(); 
  
	/**
	 * Obligatoire. 
	 * C'est aussi le nom de l'attribut pour le critère toponymique.
	 * 
	 * @return
	 */
	public String getNom(); 
  
	// TODO : autres attributs
	public String getAttrNameSemantique();
  
}
