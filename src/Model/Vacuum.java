package Model;

import java.util.ArrayList;

import Sensor.Sensor;

/**
 * Class responsible for vacuum movement. Dependent on a Sensor implementation.
 * 
 * Uses it's own "Cell" class to track it's position- minimizes dependency.
 * @author adampodraza
 *
 */

public class Vacuum extends Thread {
	
	private enum Direction{
		EAST, WEST, NORTH, SOUTH
	}
	
	Direction dir = Direction.NORTH;
	
	//List that tracks previously visited cells
	private ArrayList<Cell> visitedCells = new ArrayList<Cell>();
	
	//The current cell location
	private Cell currentCell;
	
	//Dependent Sensor
	private Sensor sensor;
	
	//boolean to turn vacuum on and off
	public boolean on = true;
	
	public Vacuum(Sensor sensor) {
		this.sensor = sensor;
		currentCell = new Cell(sensor.getCurrentCellX(), sensor.getCurrentCellY());
	}
	
	/**
	 * A method that moves the vacuum East
	 * @return
	 */
	public boolean goEast() {
		
			
		visitedCells.add(currentCell);
		sensor.goEast();
			
		currentCell = new Cell(sensor.getCurrentCellX(), sensor.getCurrentCellY());
			
		return true;
		
		
	}
	
	/**
	 * A method that moves the vacuum west
	 * @return
	 */
	public boolean goWest() {
		
		visitedCells.add(currentCell);
			
		sensor.goWest();
			
		currentCell = new Cell(sensor.getCurrentCellX(), sensor.getCurrentCellY());
			
		return true;
		
	}
	
	/**
	 * A method that moves the vacuum North
	 * @return
	 */
	public boolean goNorth() {
		
			
		visitedCells.add(currentCell);
		sensor.goNorth();
			
		currentCell = new Cell(sensor.getCurrentCellX(), sensor.getCurrentCellY());
			
		return true;
		
		
	}
	
	/**
	 * A method that moves the vacuum South
	 * @return
	 */
	public boolean goSouth() {
		//movement in the case that the cell to the south has not been visited
		
		visitedCells.add(currentCell);
		sensor.goSouth();
		currentCell = new Cell(sensor.getCurrentCellX(), sensor.getCurrentCellY());
			
		return true;
		
		
	}
	
	/**
	 * A basic method that "moves" the vacuum.  
	 * @return
	 */
	
	public boolean move() {
		
		if(dir.equals(Direction.NORTH) && sensor.canGoNorth()) {
			goNorth();
			System.out.println("Current cell position is " + currentCell.getX() + ", " + currentCell.getY());
			return true;
		}
		if(dir.equals(Direction.NORTH) && !sensor.canGoNorth()) {
			dir = Direction.WEST;
			goWest();
			System.out.println("Current cell position is " + currentCell.getX() + ", " + currentCell.getY());
			return true;
		}
		if(dir.equals(Direction.WEST) && sensor.canGoWest()) {
			goWest();
			System.out.println("Current cell position is " + currentCell.getX() + ", " + currentCell.getY());
			return true;
		}
		if(dir.equals(Direction.WEST) && !sensor.canGoWest()) {
			dir = Direction.SOUTH;
			goSouth();
			System.out.println("Current cell positon is " + currentCell.getX() + ", " + currentCell.getY());
			return true;
		}
		if(dir.equals(Direction.SOUTH) && sensor.canGoSouth()) {
			goSouth();
			System.out.println("Current cell position is " + currentCell.getX() + ", " + currentCell.getY());
			return true;
		}
		if(dir.equals(Direction.SOUTH) && !sensor.canGoSouth()) {
			dir = Direction.EAST;
			goEast();
			System.out.println("Current cell position is " + currentCell.getX() + ", " + currentCell.getY());
			return true;
		}
		if(dir.equals(Direction.EAST) && sensor.canGoEast()) {
			goEast();
			System.out.println("Current cell position is " + currentCell.getX() + ", " + currentCell.getY());
			return true;
		}
		if(dir.equals(Direction.EAST) && !sensor.canGoEast()) {
			dir = Direction.NORTH;
			goNorth();
			System.out.println("Current cell position is " + currentCell.getX() + ", " + currentCell.getY());
			return true;
		}
		return false;
	}
	
	
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Vacuum is running.");
		System.out.println("Current cell position is " + currentCell.getX() + ", " + currentCell.getY());
		while (on) {
			this.move();
		}
	}
	
	/**
	 * A local class to store cell objects
	 * @author adampodraza
	 *
	 */
	private static class Cell {
		private int x;
		private int y;
		
		Cell(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
		
	}
}
