package controller.src.main.java.org.controller2;

import java.util.ArrayList;

import sensor.src.main.java.org.sensor2.Memory;
import sensor.src.main.java.org.sensor2.RoomSensor.Cell;



public class Diagnostics extends Memory{
	public ArrayList<String> getSensorChecksLog(){
		return null;
	}
	
	public ArrayList<String> getMovementLog(){
		return visitedAreaLog;
	}
	
	public ArrayList<String> getCleaningLog(){
		return cleanedAreaLog;
	}
	
	public ArrayList<Cell> getFloorPlan(){
		return floorMemory;
	}
	
}
