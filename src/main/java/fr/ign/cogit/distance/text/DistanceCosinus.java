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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import fr.ign.cogit.distance.Distance;
import fr.ign.cogit.geoxygene.util.string.TraitementChainesDeCaracteres;

public class DistanceCosinus extends DistanceAbstractText implements Distance {

    @Override
    public double getDistance() {
        Map<CharSequence, Integer> motsSeuls1 = getMots(txtRef);
        Map<CharSequence, Integer> motsSeuls2 = getMots(txtComp);
        
        Map<CharSequence, Integer> mots1 = getMotsEtendus(motsSeuls1, motsSeuls2);
        Map<CharSequence, Integer> mots2 = getMotsEtendus(motsSeuls2, motsSeuls1);
        
        // System.out.println("        " + mots1.toString());
        // System.out.println("        " + mots2.toString());
        
        double sim = cosineSimilarity(mots1, mots2);
        double d = 1 - sim;
        return d;
    }
    
    public static double getDistance(String s, String t) {
        // CosineSimilarity cos = new CosineSimilarity();
        Map<CharSequence, Integer> motsSeuls1 = getMots(s);
        Map<CharSequence, Integer> motsSeuls2 = getMots(t);
        
        Map<CharSequence, Integer> mots1 = getMotsEtendus(motsSeuls1, motsSeuls2);
        Map<CharSequence, Integer> mots2 = getMotsEtendus(motsSeuls2, motsSeuls1);
        
        // System.out.println("        " + mots1.toString());
        // System.out.println("        " + mots2.toString());
        
        double sim = cosineSimilarity(mots1, mots2);
        double d = 1 - sim;
        return d;
    }
    
    
    private static Map<CharSequence, Integer> getMots(String titre) {
        
        titre = TraitementChainesDeCaracteres.ignorePunctuation(titre);
        
        Map<CharSequence, Integer> mots = new TreeMap<CharSequence, Integer>();
        StringTokenizer t = new StringTokenizer(titre);
        String word ="";
        // System.out.print("   ");
        
        
        while (t.hasMoreTokens()) {
            word = t.nextToken();
            // System.out.print(word + "-");
            CharSequence cs = word.toLowerCase();
            if (mots.containsKey(cs)) {
                // mots.put(word, 1);
                int f = mots.get(word.toLowerCase()).intValue();
                mots.put(word.toLowerCase(), f + 1);
            } else {
                mots.put(word.toLowerCase(), 1);
            }
        }
        
        return mots;
    }
    
    
    private static Map<CharSequence, Integer> getMotsEtendus(final Map<CharSequence, Integer> vector, final Map<CharSequence, Integer> autreVector) {
        
        // On construit toute la liste des mots
        Set<CharSequence> mots = new HashSet<CharSequence>();
        
        for (CharSequence mapKey : vector.keySet()) {
            mots.add(mapKey);
        }
        for (CharSequence mapKey : autreVector.keySet()) {
            mots.add(mapKey);
        }
        
        // System.out.println(mots);
        
        Map<CharSequence, Integer> newLeftVector = new TreeMap<CharSequence, Integer>();
        for (CharSequence key : mots) {
            if (vector.containsKey(key)) {
                newLeftVector.put(key, vector.get(key));
            } else {
                newLeftVector.put(key, 0);
            }
        }
        
        return newLeftVector;
    }
    
    
    
    /**
     * Calculates the cosine similarity for two given vectors.
     *
     * @param leftVector left vector
     * @param rightVector right vector
     * @return cosine similarity between the two vectors
     */
    private static Double cosineSimilarity(final Map<CharSequence, Integer> leftVector, final Map<CharSequence, Integer> rightVector) {
        
        if (leftVector == null || rightVector == null) {
            throw new IllegalArgumentException("Vectors must not be null");
        }
        
        // fusion(leftVector, rightVector);

        final Set<CharSequence> intersection = getIntersection(leftVector, rightVector);

        final double dotProduct = dot(leftVector, rightVector, intersection);
        
        double d1 = 0.0d;
        for (final Integer value : leftVector.values()) {
            d1 += Math.pow(value, 2);
        }
        double d2 = 0.0d;
        for (final Integer value : rightVector.values()) {
            d2 += Math.pow(value, 2);
        }
        
        double cosineSimilarity;
        if (d1 <= 0.0 || d2 <= 0.0) {
            cosineSimilarity = 0.0;
        } else {
            cosineSimilarity = (double) (dotProduct / (double) (Math.sqrt(d1) * Math.sqrt(d2)));
        }
        return cosineSimilarity;
    }
    
    
    /**
     * Returns a set with strings common to the two given maps.
     *
     * @param leftVector left vector map
     * @param rightVector right vector map
     * @return common strings
     */
    private static Set<CharSequence> getIntersection(final Map<CharSequence, Integer> leftVector, final Map<CharSequence, Integer> rightVector) {
        final Set<CharSequence> intersection = new HashSet<>(leftVector.keySet());
        intersection.retainAll(rightVector.keySet());
        return intersection;
    }
    
    /**
     * Computes the dot product of two vectors. It ignores remaining elements. It means
     * that if a vector is longer than other, then a smaller part of it will be used to compute
     * the dot product.
     *
     * @param leftVector left vector
     * @param rightVector right vector
     * @param intersection common elements
     * @return the dot product
     */
    private static double dot(final Map<CharSequence, Integer> leftVector, final Map<CharSequence, Integer> rightVector, final Set<CharSequence> intersection) {
        
        long dotProduct = 0;
        for (final CharSequence key : intersection) {
            dotProduct += leftVector.get(key) * rightVector.get(key);
        }
        
        return dotProduct;
    }
    
    @Override
    public String getNom() {
        return "Cosinus";
    }
}
