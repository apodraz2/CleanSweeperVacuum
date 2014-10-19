package Controller;

import Model.Vacuum;
import Sensor.RoomSensor;
import Sensor.Sensor;


public class Controller {
	
	static boolean on;
	
	public static void main(String[] args) {
		on = true;
		
		RoomSensor rs = new RoomSensor();
		
		Vacuum vacuum = new Vacuum(rs);
		
	
		vacuum.start();
			
			
		
	}

}
