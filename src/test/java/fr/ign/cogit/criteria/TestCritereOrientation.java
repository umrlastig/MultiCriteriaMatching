package fr.ign.cogit.criteria;

import org.junit.Assert;
import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

import fr.ign.cogit.distance.geom.DistanceEcartOrientation;

public class TestCritereOrientation {
	
	@Test
	public void testTroisheures() {
		
		GeometryFactory factory = new GeometryFactory();
		
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
		
		//System.out.println(deo.getOrientationGenerale(l1));
		// System.out.println(Math.PI/2);
		Assert.assertEquals("", Math.PI/2, deo.getDistance(), 0.0001);
		
	}

}
