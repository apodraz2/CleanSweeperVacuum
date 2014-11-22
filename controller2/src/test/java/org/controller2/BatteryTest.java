package org.controller2;

import org.junit.Test;

import junit.framework.Assert;



public class BatteryTest {

	@Test
	public void testStartingBatteryLife() {
		Controller.getInstance().initBattery();
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		Assert.assertTrue(batteryLife == 50.0);
	}
	
	@Test
	public void testBatteryLifeDecreaseMovementBareFloorToBareFloor() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().setStartingFloorType(1);
		Controller.getInstance().getBattery().decreaseBatteryMovement(1);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 49. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 49.0);
	}
	
	@Test
	public void testBatteryLifeDecreaseMovementBareFloorToLowPileCarpet() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().setStartingFloorType(1);
		Controller.getInstance().getBattery().decreaseBatteryMovement(2);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 48.5. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 48.5);
	}
	
	@Test
	public void testBatteryLifeDecreaseMovementBareFloorToHighPileCarpet() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().setStartingFloorType(1);
		Controller.getInstance().getBattery().decreaseBatteryMovement(4);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 47.5. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 47.5);
	}
	
	@Test
	public void testBatteryLifeDecreaseMovementLowPileCarpetToBareFloor() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().setStartingFloorType(2);
		Controller.getInstance().getBattery().decreaseBatteryMovement(1);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 48.5. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 48.5);
	}
	
	@Test
	public void testBatteryLifeDecreaseMovementLowPileCarpetToLowPileCarpet() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().setStartingFloorType(2);
		Controller.getInstance().getBattery().decreaseBatteryMovement(2);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 49.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 49.0);
	}
	
	@Test
	public void testBatteryLifeDecreaseMovementLowPileCarpetToHighPileCarpet() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().setStartingFloorType(2);
		Controller.getInstance().getBattery().decreaseBatteryMovement(4);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 48.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 48.0);
	}
	
	
	@Test
	public void testBatteryLifeDecreaseMovementHighPileCarpetToBareFloor() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().setStartingFloorType(4);
		Controller.getInstance().getBattery().decreaseBatteryMovement(1);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 47.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 47.5);
	}
	
	@Test
	public void testBatteryLifeDecreaseMovementHightPileCarpetToLowhPileCarpet() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().setStartingFloorType(4);
		Controller.getInstance().getBattery().decreaseBatteryMovement(2);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 48.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 48.0);
	}
	
	
	@Test
	public void testBatteryLifeDecreaseMovementHighPileCarpetToHighPileCarpet() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().setStartingFloorType(4);
		Controller.getInstance().getBattery().decreaseBatteryMovement(4);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 49.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 49.0);
	}
	
	@Test
	public void testBatteryLifeDecreaseCleaningBareFloor() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().decreaseBatteryCleaning(1);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 49.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 49.0);
	}
	
	@Test
	public void testBatteryLifeDecreaseCleaningLowPileCarpet() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().decreaseBatteryCleaning(2);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 48.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 48.0);
	}
	
	@Test
	public void testBatteryLifeDecreaseMHighPileCarpet() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().decreaseBatteryCleaning(4);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 47.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 47.0);
	}

}