package Model;

import Sensor.RoomSensor;
import Movimentation.FloorCell;
import java.util.ArrayList;

import Movimentation.FloorGraph;
import Movimentation.Path;

/**
 * Class responsible for vacuum movement. Dependent on a Sensor implementation.
 * 
 * Uses it's own "Cell" class to track it's position- minimizes dependency.
 * @author adampodraza
 * @author Marcio
 *
 */

public class Vacuum extends Thread {
	
	//List that tracks previously visited cells
	private FloorGraph visitedCells  = new FloorGraph();
	
	//The current cell location
	private FloorCell currentCell;
	
	//Dependent Sensor
	private RoomSensor sensor;
	
	//boolean to turn vacuum on and off
	public boolean on = true;
	
	public Vacuum(RoomSensor sensor) {
		this.sensor = sensor;
		currentCell = new FloorCell(sensor.getCurrentCellX(), sensor.getCurrentCellY());
                visitedCells.add(0, 0);
                sensor.setCurrentCell(0, 0);
                stepInto(visitedCells.findCell(currentCell.getX(), currentCell.getY()).getCell());
	}
	
	/**
	 * Moves the robot, based in the closest unknown cell first  
	 */
	
	public void move() {
            ArrayList<FloorCell> toVisit = new ArrayList<>();
            do{
                toVisit = visitedCells.unvisitedCellsNeighbor();
                //The sensor tries to go to a adjacent cell if it has not been visited
                
                if(sensor.canGoSouth() && visitedCells.findCell(currentCell.getX(),currentCell.getY()-1)==null){
                        visitedCells.add(currentCell.getX(),currentCell.getY()-1);
                        stepInto(visitedCells.findCell(currentCell.getX(),currentCell.getY()-1).getCell());
                }else if(sensor.canGoWest() && visitedCells.findCell(currentCell.getX()-1,currentCell.getY())==null){
                        visitedCells.add(currentCell.getX()-1,currentCell.getY());
                        stepInto(visitedCells.findCell(currentCell.getX()-1,currentCell.getY()).getCell());
                }else if(sensor.canGoEast() && visitedCells.findCell(currentCell.getX()+1,currentCell.getY())==null){
                        visitedCells.add(currentCell.getX()+1,currentCell.getY());
                        stepInto(visitedCells.findCell(currentCell.getX()+1,currentCell.getY()).getCell());
                }else if(sensor.canGoNorth() && visitedCells.findCell(currentCell.getX(),currentCell.getY()+1)==null){
                        visitedCells.add(currentCell.getX(),currentCell.getY()+1);
                        stepInto(visitedCells.findCell(currentCell.getX(),currentCell.getY()+1).getCell());
                }
                //If all adjacent cells have been visited, the robot tries to acces the closest cell
                //with unvisited adjancent cells
                else{
                    ArrayList<Path> shortestPaths = visitedCells.shortestPaths(currentCell.getX(), currentCell.getY());
                    Path shortestPath = null;
                    FloorGraph graph = visitedCells;
                    for(Path path : shortestPaths){
                        if(path.startsWith(currentCell.getX(), currentCell.getY()))
                            for(FloorCell cell : toVisit)
                                if(path.endsWith(cell.getX(), cell.getY()))
                                    if(shortestPath!=null){
                                        if(path.cost()<shortestPath.cost())
                                            shortestPath=path;
                                    } else{
                                        shortestPath=path;
                                    }                                    
                    }
                    moveThroughPath(shortestPath);
                }   
            }while(!toVisit.isEmpty());
            on=false;
	}
        
        public void moveThroughPath(Path path){
            while(path!=null && path.size()!=0){
                FloorCell nextCell = path.dequeue();
                stepInto(nextCell);
            }
        }
	
        public void stepInto(FloorCell cell){
                if(!currentCell.equals(cell)){
                    currentCell=cell;
                    sensor.setCurrentCell(currentCell.getX(),currentCell.getY());
                }
                if(!sensor.canGoEast()){
                    visitedCells.findCell(cell.getX(), cell.getY()).setEast(null);
                }
                if(!sensor.canGoWest()){
                    visitedCells.findCell(cell.getX(), cell.getY()).setWest(null);
                }
                if(!sensor.canGoNorth()){
                    visitedCells.findCell(cell.getX(), cell.getY()).setNorth(null);
                }
                if(!sensor.canGoSouth()){
                    visitedCells.findCell(cell.getX(), cell.getY()).setSouth(null);
                }
                System.out.println("Visiting now : ["+currentCell.getX()+","+currentCell.getY()+"]");
        }
	
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Vacuum is running.");
//		System.out.println("Current cell position is " + currentCell.getX() + ", " + currentCell.getY());
		while (on) {
			this.move();
                        System.out.println("All the following cells have been visited:");
                        System.out.println(visitedCells);
		}
	}
	
}
