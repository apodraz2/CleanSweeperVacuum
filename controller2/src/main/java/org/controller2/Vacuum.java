package org.controller2;

import java.util.ArrayList;

import org.sensor2.RoomSensor;

/**
 * Class responsible for vacuum movement. Dependent on a Sensor implementation.
 *
 * Uses it's own "Cell" class to track it's position- minimizes dependency.
 *
 * @author adampodraza
 * @author Marcio i
 */
public class Vacuum extends Thread {

    //List that tracks previously visited cells
    private FloorGraph floorGraph = new FloorGraph();

    //The current cell location
    private FloorCell currentCell;

    //Dependent Sensor
    private RoomSensor sensor;
    private DirtCapacity dc;

    //boolean to turn vacuum on and off
    public boolean on = true;

    public boolean startingPointSet = false;

    private Path closestChargingStation;

    private Battery battery;

    /**
     * Starts up the vacuum
     *
     * @param sensor The Vacuum sensor
     * @throws InterruptedException
     */
    public Vacuum(RoomSensor sensor, DirtCapacity dc) throws InterruptedException {
        this.sensor = sensor;
        this.dc = dc;
        currentCell = new FloorCell(sensor.getCurrentCellX(), sensor.getCurrentCellY());
        floorGraph.add(0, 0);
        sensor.setCurrentCell(0, 0);
        stepInto(floorGraph.findCell(currentCell.getX(), currentCell.getY()).getCell());
    }

    /**
     * Moves the robot, based in the closest unknown cells first, and returns to
     * the charging station at the end
     *
     * @throws InterruptedException
     */
    public void move() throws InterruptedException {
        System.out.println("Setting starting floor type for cell (" + sensor.getCurrentCellX() + ", " + sensor.getCurrentCellY() + ")");
        Controller.getInstance().getBattery().setStartingFloorType(sensor.getFloorType());
        startingPointSet = true;
        ArrayList<FloorCell> toVisit;
        Path shortestPath = null;
        battery = Controller.getInstance().battery;
        do {
            toVisit = floorGraph.hasToBeVisited();
            closestChargingStation = floorGraph.getClosestChargingStation(currentCell);

            if (toVisit.isEmpty()) {
                continue;
            }

            if (!canMakeOneStep()) {
                goRecharge();
                continue;
            }
            if (sensor.canGoSouth() && floorGraph.findCell(currentCell.getX(), currentCell.getY() - 1) == null) {
                floorGraph.add(currentCell.getX(), currentCell.getY() - 1);
                stepInto(floorGraph.findCell(currentCell.getX(), currentCell.getY() - 1).getCell());
            } else if (sensor.canGoWest() && floorGraph.findCell(currentCell.getX() - 1, currentCell.getY()) == null) {
                floorGraph.add(currentCell.getX() - 1, currentCell.getY());
                stepInto(floorGraph.findCell(currentCell.getX() - 1, currentCell.getY()).getCell());
            } else if (sensor.canGoEast() && floorGraph.findCell(currentCell.getX() + 1, currentCell.getY()) == null) {
                floorGraph.add(currentCell.getX() + 1, currentCell.getY());
                stepInto(floorGraph.findCell(currentCell.getX() + 1, currentCell.getY()).getCell());
            } else if (sensor.canGoNorth() && floorGraph.findCell(currentCell.getX(), currentCell.getY() + 1) == null) {
                floorGraph.add(currentCell.getX(), currentCell.getY() + 1);
                stepInto(floorGraph.findCell(currentCell.getX(), currentCell.getY() + 1).getCell());
            } //If all adjacent cells have been visited, the robot tries to acces the closest cell
            //with unvisited adjancent cells
            else {
                ArrayList<Path> shortestPaths = floorGraph.shortestPaths(currentCell.getX(), currentCell.getY());
                for (Path path : shortestPaths) {
                    if (path.startsWith(currentCell.getX(), currentCell.getY())) {
                        for (FloorCell cell : toVisit) {
                            if (path.endsWith(cell.getX(), cell.getY())) {
                                if (shortestPath != null) {
                                    if (path.cost() < shortestPath.cost()) {
                                        shortestPath = path;
                                    }
                                } else {
                                    shortestPath = path;
                                }
                            }
                        }
                    }
                }

                if (shortestPath != null && !canGoTo(shortestPath) && currentCell.isChargingStation()) {
                    break;
                }

                if (shortestPath != null && canGoTo(shortestPath)) {
                    moveThroughPath(shortestPath);
                } else {
                    goRecharge();
                }

            }
        } while (!toVisit.isEmpty());
        goRecharge();
        on = false;
        Controller.getInstance().getBattery().addCommand("shutdown");
        Controller.getInstance().getBattery().executeCommand();
    }

    /**
     * Returns to the charging station
     *
     * @throws InterruptedException
     */
    public void goRecharge() throws InterruptedException {
        System.out.println("Going Recharge");
        Path path = floorGraph.getClosestChargingStation(currentCell);
        while (path.size() != 0) {
            FloorCell nextCell = path.dequeue();
            stepInto(nextCell);
        }
        Controller.getInstance().getBattery().addCommand("charge");
        Controller.getInstance().getBattery().executeCommand();
    }

    /**
     * Moves the vacuum through a path
     *
     * @param path The path to be made
     * @throws InterruptedException
     */
    public void moveThroughPath(Path path) throws InterruptedException {
        while (path != null && path.size() != 0 && canMakeOneStep()) {
            FloorCell nextCell = path.dequeue();
            stepInto(nextCell);
        }
    }

    /**
     * Moves the vacuum into a adjacent cell
     *
     * @param cell The cell that the vacuum will go over
     * @throws InterruptedException
     */
    public void stepInto(FloorCell cell) throws InterruptedException {
        if (cell.getX() == 0 && cell.getY() == 0) {
            cell.setChargingStation(true);
        }
        if (!currentCell.equals(cell)) {
            System.out.println("Moving from "+currentCell+" to "+cell+" | Battery "+Controller.getInstance().getBattery().getBatteryLife()+" units | Cost "+((currentCell.cost()+cell.cost())/2));
            currentCell = cell;
            closestChargingStation = floorGraph.getClosestChargingStation(currentCell);
            sensor.setCurrentCell(currentCell.getX(), currentCell.getY());
            currentCell.floorType = sensor.getFloorType();
            Controller.getInstance().getBattery().decreaseBatteryMovement(sensor.getFloorType());

        }
        if (!sensor.canGoEast()) {
            floorGraph.findCell(cell.getX(), cell.getY()).setEast(null);
        }
        if (!sensor.canGoWest()) {
            floorGraph.findCell(cell.getX(), cell.getY()).setWest(null);
        }
        if (!sensor.canGoNorth()) {
            floorGraph.findCell(cell.getX(), cell.getY()).setNorth(null);
        }
        if (!sensor.canGoSouth()) {
            floorGraph.findCell(cell.getX(), cell.getY()).setSouth(null);
        }
        if (sensor.getDirtRemaining() != 0 && startingPointSet == true) {
            performCleaningFunction();
        }
    }

    private void performCleaningFunction() throws InterruptedException {
            while (!sensor.isClean() && canStillClean()) {
                Controller.getInstance().getBattery().decreaseBatteryCleaning(sensor.getFloorType());
                System.out.println("Cleaning "+currentCell+" | Battery "+Controller.getInstance().getBattery().getBatteryLife()+" units | Cost "+currentCell.cost()+" | Dirt Capacity "+(50-dc.getDirtLevel()));
                sensor.setDirtRemaining(sensor.getDirtRemaining() - 1);
                dc.addDirt(1);
                if (dc.checkIsFull()) {
                    dc.emptyMe();
                }
                //System.out.println("Dirt Remaining after cleaning: " + sensor.getDirtRemaining());
            }
        if (sensor.getDirtRemaining() == 0) {
            floorGraph.findCell(currentCell.getX(), currentCell.getY()).clean();
        }
    }

    public boolean canStillClean() {
        double remainingBattery = Controller.getInstance().getBattery().getBatteryLife();
        return remainingBattery - closestChargingStation.cost() > currentCell.cost();
    }

    public boolean canGoTo(Path shortestPath) {
        double remainingBattery = Controller.getInstance().getBattery().getBatteryLife();
        FloorCell destination = shortestPath.getLastCell();
        return remainingBattery - floorGraph.getClosestChargingStationTo(destination.getX(), destination.getY()).cost() - shortestPath.cost() > (currentCell.cost() + 3);
    }

    public boolean canMakeOneStep() {
        double remainingBattery = Controller.getInstance().getBattery().getBatteryLife();
        return remainingBattery - closestChargingStation.cost() > (currentCell.cost() + 3);
    }

    public String currentPosition() {
        return currentCell.toString();
    }

    public ArrayList<FloorCell> hasToBeVisited() {
        return this.floorGraph.hasToBeVisited();
    }
    
    public FloorGraph getFloorGraph() {
        return this.floorGraph;
    }


    /**
     * Turns the vacuum on
     */
    public void run() {
        // TODO Auto-generated method stub
        synchronized (this) {
            System.out.println("Vacuum is running.");
//		System.out.println("Current cell position is " + currentCell.getX() + ", " + currentCell.getY());
            while (on) {
                try {
                    this.move();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("All the following cells have been visited:");
                System.out.println(floorGraph);
                if (floorGraph.hasToBeVisited().size() > 0) {
                    System.out.println("The vacuum has finished cleaning but some locations couldn't be reached!");
                } else {
                    System.out.println("The vacuum has finished cleaning and all the floor has been cleaned!");
                }
            }
            notifyAll();
        }
    }

}
