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
package fr.ign.cogit.distance.text;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import fr.ign.cogit.util.text.ApproximateMatcher;
import fr.ign.cogit.util.text.MatriceConfiance;
import fr.ign.cogit.util.text.TraitementChainesDeCaracteres;

/**
 * 
 * 
 * @author
 */
public class MesureRessemblance {

  public static double getMesureRessemblance(String string1, String string2) {

    List<String> tokenLigne = new ArrayList<String>();
    List<String> tokenColonne = new ArrayList<String>();
    ApproximateMatcher matcher = new ApproximateMatcher();
    MatriceConfiance matriceToken;
    StringTokenizer st1, st2;
    matcher.setIgnoreCase(true);
    matcher.setIgnoreAccent(true);
    double ecart = 0, ecartRelatif = 0, confiance = 0;

    string1 = matcher.process(string1);
    string1 = TraitementChainesDeCaracteres.ignorePunctuation(string1);
    string2 = matcher.process(string2);
    string2 = TraitementChainesDeCaracteres.ignorePunctuation(string2);
    st1 = new StringTokenizer(string1);
    st2 = new StringTokenizer(string2);
    matriceToken = new MatriceConfiance(string1, string2);
    // initialisation matrice
    for (int i = 0; i < matriceToken.nbRows; i++) {
      for (int j = 0; j < matriceToken.nbColumns; j++) {
        matriceToken.values[i][j] = 0;
      }
    }
    while (st1.hasMoreElements()) {
      tokenLigne.add(st1.nextToken());
    }
    while (st2.hasMoreElements()) {
      tokenColonne.add(st2.nextToken());
    }
    for (int i = 0; i < tokenLigne.size(); i++) {
      for (int j = 0; j < tokenColonne.size(); j++) {
        ecart = matcher.distance(tokenLigne.get(i).toString(), tokenColonne
            .get(j).toString());
        // l'ecart relatif ici est la distance normalisée
        ecartRelatif = 1 - (ecart / Math.max(tokenLigne.get(i).toString()
            .length(), tokenColonne.get(j).toString().length()));
        matriceToken.values[i][j] = ecartRelatif;
        //   matriceToken.values[i][j] = ecart;
      }
    }
    confiance = matriceToken.confidenceMaxsRows();
    return confiance;
  }
  
}
