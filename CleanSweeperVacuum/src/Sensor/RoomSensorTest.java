package Sensor;
import junit.framework.Assert;

import org.junit.Test;



public class RoomSensorTest {
	
	RoomSensor rs;
	
	@Test
	public void testNotNull() {
		rs = new RoomSensor();
		Assert.assertTrue(!rs.equals(null));
	}
	
	@Test
	public void testStartingPosition() {
		rs = new RoomSensor();
		
		
		Assert.assertTrue(rs.canGoNorth());
		Assert.assertTrue(rs.canGoEast());
		Assert.assertFalse(rs.canGoWest());
		Assert.assertFalse(rs.canGoSouth());
		
		Assert.assertEquals(rs.getCurrentCellX(), 0);
		Assert.assertEquals(rs.getCurrentCellY(), 0);
		
		Assert.assertTrue(rs.getIsChargingStation());
		Assert.assertFalse(rs.isClean());
		
		Assert.assertEquals(rs.getFloorType(), 2);
	}
	
	@Test
	public void testGoNorth() {
		rs = new RoomSensor();
		
		Assert.assertTrue(rs.goNorth());
		
		Assert.assertTrue(rs.canGoEast());
		Assert.assertTrue(rs.canGoSouth());
		Assert.assertFalse(rs.canGoWest());
		Assert.assertTrue(rs.canGoNorth());
		
		Assert.assertEquals(rs.getCurrentCellX(), 0);
		Assert.assertEquals(rs.getCurrentCellY(), 1);
		
		Assert.assertFalse(rs.getIsChargingStation());
		Assert.assertFalse(rs.isClean());
		
	}
	
	@Test
	public void testGoEast() {
		rs = new RoomSensor();
		rs.goNorth();
		
		Assert.assertEquals(rs.getCurrentCellX(), 0);
		Assert.assertEquals(rs.getCurrentCellY(), 1);
		
		Assert.assertTrue(rs.goEast());
		
		Assert.assertTrue(rs.canGoEast());
		Assert.assertTrue(rs.canGoSouth());
		Assert.assertTrue(rs.canGoWest());
		Assert.assertTrue(rs.canGoNorth());
		
		Assert.assertEquals(rs.getCurrentCellX(), 1);
		Assert.assertEquals(rs.getCurrentCellY(), 1);
		
		Assert.assertFalse(rs.getIsChargingStation());
		Assert.assertFalse(rs.isClean());
	}
	
	@Test
	public void testGoWest() {
		rs = new RoomSensor();
		rs.goNorth();
		rs.goEast();
		
		Assert.assertEquals(rs.getCurrentCellX(), 1);
		Assert.assertEquals(rs.getCurrentCellY(), 1);
		
		Assert.assertTrue(rs.goWest());
		
		Assert.assertTrue(rs.canGoEast());
		Assert.assertFalse(rs.canGoWest());
		Assert.assertTrue(rs.canGoNorth());
		Assert.assertTrue(rs.canGoSouth());
		
		Assert.assertFalse(rs.getIsChargingStation());
		Assert.assertFalse(rs.isClean());
		
		Assert.assertEquals(rs.getCurrentCellX(), 0);
		Assert.assertEquals(rs.getCurrentCellY(), 1);
		
	}
	
	@Test
	public void testGoSouth() {
		rs = new RoomSensor();
		rs.goNorth();
		rs.goEast();
		rs.goWest();
		
		Assert.assertEquals(rs.getCurrentCellX(), 0);
		Assert.assertEquals(rs.getCurrentCellY(), 1);
		
		Assert.assertTrue(rs.goSouth());
		
		Assert.assertTrue(rs.canGoEast());
		Assert.assertFalse(rs.canGoWest());
		Assert.assertTrue(rs.canGoNorth());
		Assert.assertFalse(rs.canGoSouth());
		
		Assert.assertTrue(rs.getIsChargingStation());
		Assert.assertFalse(rs.isClean());
		
		Assert.assertEquals(rs.getCurrentCellX(), 0);
		Assert.assertEquals(rs.getCurrentCellY(), 0);
	}
	
	public void main (String [] args) {
		testNotNull();
		testStartingPosition();
		testGoNorth();
		testGoEast();
	}
	

}
