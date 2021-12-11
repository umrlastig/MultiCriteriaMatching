package fr.ign.cogit.distance.geom;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.locationtech.jts.geom.Geometry;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

import fr.ign.cogit.distance.Distance;
//import fr.ign.cogit.geoxygene.api.spatial.coordgeom.ILineString;
//import fr.ign.cogit.geoxygene.api.spatial.geomroot.IGeometry;
//import fr.ign.cogit.geoxygene.contrib.geometrie.Distances;
//import fr.ign.cogit.geoxygene.spatial.geomaggr.GM_MultiCurve;

/**
 * 
 * @author M-D Van Damme
 */
public class DistanceMoyMinDistanceHausdorff extends DistanceAbstractGeom implements Distance {

    public static double getDistance(Geometry geomRef, Geometry geomComp) {
//        if (geomRef instanceof GM_MultiCurve && geomComp instanceof GM_MultiCurve) {
//            
//            int cpt = 1;
//            Map<Integer, ILineString> geom1 = new HashMap<Integer, ILineString>();
//            GM_MultiCurve l1 = (GM_MultiCurve)geomRef;
//            for (int i = 0; i < l1.size(); i++) {
//                ILineString l = (ILineString)l1.get(i);
//                geom1.put(cpt, l);
//                cpt++;
//            }
//            
//            cpt = 1;
//            Map<Integer, ILineString> geom2 = new HashMap<Integer, ILineString>();
//            GM_MultiCurve l2 = (GM_MultiCurve)geomComp;
//            for (int i = 0; i < l2.size(); i++) {
//                ILineString l = (ILineString)l2.get(i);
//                geom2.put(cpt, l);
//                cpt++;
//            }
//            
//            double d = getHausdorffMoy(geom1, geom2);
//            return d;
//        }
//        
        
        return Float.MAX_VALUE;
    }
    
    
    @Override
    public double getDistance() {
        /* if (this.geomRef instanceof ILineString && this.geomComp instanceof ILineString) {
            ILineString geomLigneRef = (ILineString) this.geomRef;
            ILineString geomLigneComp = (ILineString) this.geomComp;
            return Distances.premiereComposanteHausdorff(geomLigneRef, geomLigneComp);
        } */
        
        
//        if (this.geomRef instanceof GM_MultiCurve && this.geomComp instanceof GM_MultiCurve) {
//            int cpt = 1;
//            Map<Integer, ILineString> geom1 = new HashMap<Integer, ILineString>();
//            GM_MultiCurve l1 = (GM_MultiCurve)this.geomRef;
//            for (int i = 0; i < l1.size(); i++) {
//                ILineString l = (ILineString)l1.get(i);
//                geom1.put(cpt, l);
//                cpt++;
//            }
//            cpt = 1;
//            Map<Integer, ILineString> geom2 = new HashMap<Integer, ILineString>();
//            GM_MultiCurve l2 = (GM_MultiCurve)this.geomComp;
//            for (int i = 0; i < l2.size(); i++) {
//                ILineString l = (ILineString)l2.get(i);
//                geom2.put(cpt, l);
//                cpt++;
//            }
//            
//            double d = getHausdorffMoy(geom1, geom2);
//            return d;
//        }
//        
//        
        return Float.MAX_VALUE;
    }
    

//    public static double getHausdorffMoy(Map<Integer, ILineString> geom1, Map<Integer, ILineString> geom2) {
//        
//        double[] dist1 = new double[geom1.size()];
//        int cpt = 0;
//        for (Map.Entry<Integer, ILineString> entry : geom1.entrySet()) {
//            ILineString t1 = entry.getValue();
//            double d = getDistanceHausdorf(t1, geom2);
//            dist1[cpt] = d;
//            cpt++;
//        }
//        
//        double[] dist2 = new double[geom2.size()];
//        cpt = 0;
//        for (Map.Entry<Integer, ILineString> entry : geom2.entrySet()) {
//            ILineString t2 = entry.getValue();
//            double d = getDistanceHausdorf(t2, geom1);
//            dist2[cpt] = d;
//            cpt++;
//        }
//        
//        // On fait la moyenne des moyennes
//        Mean avg = new Mean();
//        double avgValue1 = avg.evaluate(dist1);
//        double avgValue2 = avg.evaluate(dist2);
//        return (avgValue1 + avgValue2) / 2;
//        
//        // On fait la moyenne des médianes
////      Median median = new Median();
////        double medianValue1 = median.evaluate(dist1);
////        double medianValue2 = median.evaluate(dist2);
////        return (medianValue1 + medianValue2) / 2;
//    }
//    
//        
//    private static double getDistanceHausdorf(ILineString l1, Map<Integer, ILineString> ls2) {
//            double dmin = Double.MAX_VALUE;
//            for (Map.Entry<Integer, ILineString> entry : ls2.entrySet()) {
//                ILineString l2 = entry.getValue();
//                double d = Distances.premiereComposanteHausdorff(l1, l2);
//                if (d < dmin) {
//                    dmin = d;
//                }
//            }
//            return dmin;
//        }

    @Override
    public String getNom() {
        return "DistanceMoyMinDistanceHausdorff";
    }
}
