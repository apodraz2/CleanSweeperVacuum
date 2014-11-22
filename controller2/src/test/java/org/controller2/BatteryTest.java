package controller2;

import org.junit.Assert;
import org.junit.Test;
public class BatteryTest {

	@Test
	public void testStartingBatteryLife() {
		Controller.getInstance().initBattery();
		double batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		Assert.assertTrue(batteryLife == 50.0);
		
		Controller.getInstance().getBattery().setStartingFloorType(1);
		Controller.getInstance().getBattery().decreaseBatteryMovement(1);
		batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		//ER1-1
		String failureDetail = "ER1-1: Expected: 49. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 49.0);
		
		Controller.getInstance().getBattery().setStartingFloorType(1);
		Controller.getInstance().getBattery().decreaseBatteryMovement(2);
		batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		//ER1-2
		failureDetail = "ER1-2: Expected: 47.5. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 47.5);
		
		Controller.getInstance().getBattery().setStartingFloorType(1);
		Controller.getInstance().getBattery().decreaseBatteryMovement(4);
		batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		// ER1-3
		failureDetail = "ER1-3: Expected: 45.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 45.0);
		
		Controller.getInstance().getBattery().setStartingFloorType(2);
		Controller.getInstance().getBattery().decreaseBatteryMovement(1);
		batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		// ER1-4
		failureDetail = "ER1-4: Expected: 43.5. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 43.5);
	
		Controller.getInstance().getBattery().setStartingFloorType(2);
		Controller.getInstance().getBattery().decreaseBatteryMovement(1);
		batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		// ER1-5
		failureDetail = "ER1-5: Expected: 42.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 42.0);
		
		Controller.getInstance().getBattery().setStartingFloorType(2);
		Controller.getInstance().getBattery().decreaseBatteryMovement(2);
		batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		// ER1-6
		failureDetail = "ER1-6: Expected: 41.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 41.0);
		
		Controller.getInstance().getBattery().setStartingFloorType(2);
		Controller.getInstance().getBattery().decreaseBatteryMovement(4);
		batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		// ER1-7
		failureDetail = "ER1-7: Expected: 39.0. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 39.0);
		
		Controller.getInstance().getBattery().setStartingFloorType(4);
		Controller.getInstance().getBattery().decreaseBatteryMovement(1);
		batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		//ER1-8
		failureDetail = "ER1-8: Expected: 36.5. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 36.5);
		
		Controller.getInstance().getBattery().setStartingFloorType(4);
		Controller.getInstance().getBattery().decreaseBatteryMovement(2);
		batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		//ER1-9
		failureDetail = "ER1-9: Expected: 34.5. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 34.5);
		
		Controller.getInstance().getBattery().setStartingFloorType(4);
		Controller.getInstance().getBattery().decreaseBatteryMovement(4);
		batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		//ER1-10
		failureDetail = "ER1-10: Expected: 33.5. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 33.5);
		
		Controller.getInstance().getBattery().decreaseBatteryCleaning(1);
		batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		//ER1-11
		failureDetail = "ER1-11: Expected: 32.5. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 32.5);
		
		Controller.getInstance().getBattery().decreaseBatteryCleaning(2);
		batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		//ER1-12
		failureDetail = "ER1-12: Expected: 30.5. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 30.5);
		
		Controller.getInstance().getBattery().decreaseBatteryCleaning(4);
		batteryLife = Controller.getInstance().getBattery().getBatteryLife();
		
		//ER1-13
		failureDetail = "ER1-13: Expected: 27.5. Actual: " + batteryLife;
		Assert.assertTrue(failureDetail, batteryLife == 27.5);
	}
}
