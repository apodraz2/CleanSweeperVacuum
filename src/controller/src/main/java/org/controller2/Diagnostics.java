package controller;

import java.util.ArrayList;

import sensor.Memory;
import sensor.RoomSensor.Cell;

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
