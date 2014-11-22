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
		
		String failureDetail = "Expected: 47.5. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 47.5);
	}
	
	@Test
	public void testBatteryLifeDecreaseMovementBareFloorToHighPileCarpet() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().setStartingFloorType(1);
		Controller.getInstance().getBattery().decreaseBatteryMovement(4);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 45.5. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 45.5);
	}
	
	@Test
	public void testBatteryLifeDecreaseMovementLowPileCarpetToBareFloor() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().setStartingFloorType(2);
		Controller.getInstance().getBattery().decreaseBatteryMovement(1);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 44.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 44.0);
	}
	
	@Test
	public void testBatteryLifeDecreaseMovementLowPileCarpetToLowPileCarpet() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().setStartingFloorType(2);
		Controller.getInstance().getBattery().decreaseBatteryMovement(2);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 42.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 42.0);
	}
	
	@Test
	public void testBatteryLifeDecreaseMovementLowPileCarpetToHighPileCarpet() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().setStartingFloorType(2);
		Controller.getInstance().getBattery().decreaseBatteryMovement(4);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 39.5. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 39.5);
	}
	
	
	@Test
	public void testBatteryLifeDecreaseMovementHighPileCarpetToBareFloor() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().setStartingFloorType(4);
		Controller.getInstance().getBattery().decreaseBatteryMovement(1);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 37.5. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 37.5);
	}
	
	@Test
	public void testBatteryLifeDecreaseMovementHightPileCarpetToLowhPileCarpet() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().setStartingFloorType(4);
		Controller.getInstance().getBattery().decreaseBatteryMovement(2);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 35.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 35.0);
	}
	
	
	@Test
	public void testBatteryLifeDecreaseMovementHighPileCarpetToHighPileCarpet() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().setStartingFloorType(4);
		Controller.getInstance().getBattery().decreaseBatteryMovement(4);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 32.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 32.0);
	}
	
	@Test
	public void testBatteryLifeDecreaseCleaningBareFloor() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().decreaseBatteryCleaning(1);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 31.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 31.0);
	}
	
	@Test
	public void testBatteryLifeDecreaseCleaningLowPileCarpet() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().decreaseBatteryCleaning(2);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 29.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 29.0);
	}
	
	@Test
	public void testBatteryLifeDecreaseMHighPileCarpet() {
		Controller.getInstance().initBattery();
		Controller.getInstance().getBattery().decreaseBatteryCleaning(4);
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		String failureDetail = "Expected: 26.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 26.0);
	}

}