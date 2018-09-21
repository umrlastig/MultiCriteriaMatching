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
package fr.ign.cogit.io;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.ibm.icu.text.SimpleDateFormat;

import fr.ign.cogit.dao.LigneResultat;

/**
 * Export result to CSV file. 
 * 
 * @author MDVan-Damme
 *
 */
public class ExportToCSV {
  
	/**
	 * Export des résultats dans un fichier CSV.
	 * 
	 * @param listeResultat
	 * @param pathToExportTo
	 */
	public static void exportAppariement (List<LigneResultat> listeResultat, String pathToExportTo) {
    
		try {
			FileWriter excel = new FileWriter(new File(pathToExportTo + "-" + new SimpleDateFormat("yyyyMMdd-HHmmssSSS", Locale.FRANCE).format(new Date()) + ".csv"));
      
			// Nom des colonnes
			String nom_colonne = "ID_REF;NOM_REF;NUM_CANDIDAT;ID_CANDIDAT;NOM_CANDIDAT;";
			for (int c = 0; c < listeResultat.get(0).getDistances().length; c++) {
				nom_colonne = nom_colonne + listeResultat.get(0).getNomDistance(c) + ";";
			}
			nom_colonne = nom_colonne + "Proba pign premier;Proba pign second;Decision;";
			nom_colonne = nom_colonne + "GEOM_REF;GEOM_CANDIDAT;";
			
			excel.write(nom_colonne + "\n");
      
			// Toutes les lignes
			for (int i = 0; i < listeResultat.size(); i++) {
				
				// Boucle sur les distances 
				String ligne = "";
				
				// ID AND NAME
				ligne = ligne + listeResultat.get(i).getIdTopoRef() + ";";
				ligne = ligne + listeResultat.get(i).getNomTopoRef() + ";";
				ligne = ligne + listeResultat.get(i).getCompteurC() + ";";
				ligne = ligne + listeResultat.get(i).getIdTopoComp() + ";";
				ligne = ligne + listeResultat.get(i).getNomTopoComp() + ";";
				
				
				// DISTANCE
				for (int c = 0; c < listeResultat.get(i).getDistances().length; c++) {
				  
					double d = listeResultat.get(i).getDistance(c);
					if (d < 0) {
						ligne = ligne + ";";
					} else {
						ligne = ligne + d + ";";
					}
				
				}
				
				
				// DECISION
				ligne = ligne + listeResultat.get(i).getProbaPignistiquePremier() + ";";
				ligne = ligne + listeResultat.get(i).getProbaPignistiqueSecond() + ";";
				ligne = ligne + listeResultat.get(i).isDecision() + ";";
				  
				
				// WKT GEOMETRY
				ligne = ligne + listeResultat.get(i).getGeomRef() + ";";
				ligne = ligne + listeResultat.get(i).getGeomComp() + ";";
				
				
				// EOL
				excel.write(ligne + "\n");
				
			}		
    
			// Fermeture du fichier
			excel.close();
      
		} catch (Exception e) {
			e.printStackTrace();
		}
    
	}
  

//	public static void exportSansCandidat(List<List<String>> tabSansCandidat) {
//		try {
//			String pathToExportTo = "./data/resultat/sanscandidat-" + new SimpleDateFormat("yyyyMMdd-HHmmssSSS", Locale.FRANCE).format(new Date()) + ".csv";
//			FileWriter excel = new FileWriter(new File(pathToExportTo));
//  
//			for (List<String> tab2 : tabSansCandidat) {
//				excel.write(tab2.get(0) + ";" + tab2.get(1) + "\n");
//			}
//	    
//			// Fermeture du fichier
//			excel.close();
//	  
//		} catch (Exception e) {
//			e.printStackTrace();
//	    }
//	}

	
}
