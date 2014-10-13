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
		//movement in the case that the cell to the East is a new cell.
		if(sensor.canGoEast() && !visitedCells.contains(currentCell)){
			
			visitedCells.add(currentCell);
			sensor.goEast();
			
			currentCell = new Cell(sensor.getCurrentCellX(), sensor.getCurrentCellY());
			
			return true;
		} 
		//movement in the case that the cell to the East has been visited previously.
		else if (sensor.canGoEast() && visitedCells.contains(currentCell)) {
			
			sensor.goWest();
			
			
			
			currentCell = new Cell(sensor.getCurrentCellX(), sensor.getCurrentCellY()); 
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * A method that moves the vacuum west
	 * @return
	 */
	public boolean goWest() {
		//movement in the case that the cell to the "West" has not been visited
		if(sensor.canGoWest() && !visitedCells.contains(currentCell)){
			
			visitedCells.add(currentCell);
			
			sensor.goWest();
			
			currentCell = new Cell(sensor.getCurrentCellX(), sensor.getCurrentCellY());
			
			return true;
		} 
		//movement in the case that the cell to the "West" has been visited
		else if(sensor.canGoWest() && visitedCells.contains(currentCell)) {
			sensor.goNorth();
			currentCell = new Cell(sensor.getCurrentCellX(), sensor.getCurrentCellY());
			return true;
		}
		
		return false;
	}
	
	/**
	 * A method that moves the vacuum North
	 * @return
	 */
	public boolean goNorth() {
		
		//movement in the case that cell to the north has not been visited
		if(sensor.canGoNorth() && !visitedCells.contains(currentCell)) {
			
			visitedCells.add(currentCell);
			sensor.goNorth();
			
			currentCell = new Cell(sensor.getCurrentCellX(), sensor.getCurrentCellY());
			
			return true;
		} 
		//movement in the case that the cell to the north has been visited
		else if (sensor.canGoNorth() && visitedCells.contains(currentCell)) {
			sensor.goSouth();
			currentCell = new Cell(sensor.getCurrentCellX(), sensor.getCurrentCellY());
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * A method that moves the vacuum South
	 * @return
	 */
	public boolean goSouth() {
		//movement in the case that the cell to the south has not been visited
		if(sensor.canGoSouth() && visitedCells.contains(currentCell)) {
			
			visitedCells.add(currentCell);
			sensor.goSouth();
			currentCell = new Cell(sensor.getCurrentCellX(), sensor.getCurrentCellY());
			
			return true;
		} 
		//movement in the case that the cell to the south has been visited
		else if (sensor.canGoNorth() && visitedCells.contains(currentCell)) {
			sensor.goEast();
			currentCell = new Cell(sensor.getCurrentCellX(), sensor.getCurrentCellY());
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * A basic method that "moves" the vacuum.  
	 * @return
	 */
	
	public boolean move() {
		System.out.println("Current cell position is " + currentCell.getX() + ", " + currentCell.getY());
		if (goNorth()) {
			System.out.println("New cell position is " + currentCell.getX() + ", " + currentCell.getY());
			System.out.println("Vacuum moved North.");
			return true;
		}if(goWest()) {
			System.out.println("New cell position is " + currentCell.getX() + ", " + currentCell.getY());
			System.out.println("Vacuum moved West."); 
			return true;
		}if(goSouth()) {
			System.out.println("New cell position is " + currentCell.getX() + ", " + currentCell.getY());
			System.out.println("Vacuum moved South.");
			return true;
		} 
		if (goEast()) {
			System.out.println("New cell position is " + currentCell.getX() + ", " + currentCell.getY());
			System.out.println("Vacuum moved East.");
			return true;
		}
		return false;
	}
	
	
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Vacuum is running.");
		
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
