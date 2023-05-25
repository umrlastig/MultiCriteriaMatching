package fr.ign.cogit.criteria;

import org.junit.Assert;
import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;



public class TestCritereOrientation {
	
	@Test
	public void testTroisheures() {
		/*GeometryFactory factory = new GeometryFactory();
		
		Coordinate[] cl1 = new Coordinate[2];
		cl1[0] = new Coordinate(0,0);
		cl1[1] = new Coordinate(1,0);
		LineString l1 = factory.createLineString(cl1);
		
		Coordinate[] cl2 = new Coordinate[2];
		cl2[0] = new Coordinate(0,0);
		cl2[1] = new Coordinate(0,1);
		LineString l2 = factory.createLineString(cl2);
		
		DistanceEcartOrientation deo = new DistanceEcartOrientation();
		deo.setGeom(l1, l2);
		System.out.println(deo.getDistance());
		// System.out.println(deo.getOrientationGenerale(l1));
		// System.out.println(Math.PI/2);
		// Assert.assertEquals("", Math.PI/2, deo.getDistance(), 0.0001);*/
	}
	
	@Test
	public void testTroisheuresMoinsDix() {
		/*GeometryFactory factory = new GeometryFactory();
		
		Coordinate[] cl1 = new Coordinate[2];
		cl1[0] = new Coordinate(0,0);
		cl1[1] = new Coordinate(1,0);
		LineString l1 = factory.createLineString(cl1);
		
		Coordinate[] cl2 = new Coordinate[2];
		cl2[0] = new Coordinate(0,0);
		cl2[1] = new Coordinate(-Math.sqrt(2)/2, Math.sqrt(2)/2);
		LineString l2 = factory.createLineString(cl2);
		
		DistanceEcartOrientation deo = new DistanceEcartOrientation();
		deo.setGeom(l1, l2);
		System.out.println(deo.getDistance());*/
	}
	
	@Test
	public void testTroisheuresDix() {
		/*GeometryFactory factory = new GeometryFactory();
		
		Coordinate[] cl1 = new Coordinate[2];
		cl1[0] = new Coordinate(0,0);
		cl1[1] = new Coordinate(1,0);
		LineString l1 = factory.createLineString(cl1);
		
		Coordinate[] cl2 = new Coordinate[2];
		cl2[0] = new Coordinate(0,0);
		cl2[1] = new Coordinate(Math.sqrt(2)/2, Math.sqrt(2)/2);
		LineString l2 = factory.createLineString(cl2);
		
		DistanceEcartOrientation deo = new DistanceEcartOrientation();
		deo.setGeom(l1, l2);
		System.out.println(deo.getDistance());*/
	}
	
	
	
	@Test
	public void testTroncon6446() throws Exception {
		/*WKTReader wktRdr = new WKTReader();
		LineString l1 = (LineString) wktRdr.read("LineString (517989.200 6462639.900, 517977.099 6462643.599, 517963.099 6462646, "
				+ "517937.599 6462650.599, 517927.799 6462651.200, 517906.910 6462650.707, 517890.799 6462651.099, "
				+ "517868 6462655.599, 517856.299 6462660.700, 517846 6462665.299, 517834.200 6462674, "
				+ "517822.900 6462682.400, 517813.599 6462688.299, 517807.400 6462689.900, 517802.5 6462688.700, "
				+ "517796.900 6462683.700, 517791.199 6462676.107, 517783.967 6462663.054, 517777.264 6462654.587, "
				+ "517772.854 6462651.060)");
		LineString l2 = (LineString) wktRdr.read("LineString (517989.200 6462639.904, 517977.100 6462643.604, "
				+ "517963.100 6462646.004, 517948.900 6462648.904, 517937.600 6462650.604, 517927.800 6462651.204, "
				+ "517918.500 6462651.004, 517906.900 6462651.104, 517897.700 6462650.804, 517890.800 6462651.104, "
				+ "517880.300 6462652.804, 517868.000 6462655.604, 517856.300 6462660.704, 517846.000 6462665.304, "
				+ "517834.200 6462674.004, 517822.900 6462682.404, 517813.600 6462688.304, 517807.400 6462689.904, "
				+ "517802.500 6462688.704, 517796.900 6462683.704, 517780.300 6462674.804, 517770.000 6462672.404)");
		DistanceEcartOrientation deo = new DistanceEcartOrientation();
		deo.setGeom(l1, l2);
		System.out.println(deo.getDistance());*/
	}
	
	
	@Test
	public void testTroncon6265() throws Exception {
		/*WKTReader wktRdr = new WKTReader();
		LineString l1 = (LineString) wktRdr.read("LineString (518484.682 6463380.650, 518483.307 6463376.981, "
				+ "518487.200 6463366.599, 518465 6463338.900)");
		LineString l2 = (LineString) wktRdr.read("LineString (518485.800 6463384.004, 518483.600 6463376.904, "
				+ "518487.200 6463366.604, 518465.000 6463338.904)");
		DistanceEcartOrientation deo = new DistanceEcartOrientation();
		deo.setGeom(l1, l2);
		System.out.println(deo.getDistance());*/
	}

}
