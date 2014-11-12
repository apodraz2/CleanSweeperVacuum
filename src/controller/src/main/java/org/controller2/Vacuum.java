package controller.src.main.java.org.controller2;





import java.util.ArrayList;

import sensor.src.main.java.org.sensor2.RoomSensor;



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

    //boolean to turn vacuum on and off
    public boolean on = true;

    public boolean startingPointSet = false;

    /**
     * Starts up the vacuum
     *
     * @param sensor The Vacuum sensor
     * @throws InterruptedException
     */
    public Vacuum(RoomSensor sensor) throws InterruptedException {
        this.sensor = sensor;
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
        ArrayList<FloorCell> toVisit = new ArrayList<>();
        do {
            toVisit = floorGraph.hasToBeVisited();
            //The sensor tries to go to a adjacent cell if it has not been visited

            Path closestChargingStation = floorGraph.getClosestChargingStation(currentCell);

            if (!canMakeOneStep(closestChargingStation) || !canStillClean(closestChargingStation)) {
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
                Path shortestPath = null;
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
        moveThroughPath(floorGraph.getClosestChargingStation(currentCell));
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
        while (path != null && path.size() != 0) {
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
            currentCell = cell;
            sensor.setCurrentCell(currentCell.getX(), currentCell.getY());
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

        System.out.println("Visiting now : " + currentCell);
        System.out.println("Dirt Remaining before cleaning: " + sensor.getDirtRemaining());
        if (sensor.getDirtRemaining() != 0 && startingPointSet == true) {
            Controller.getInstance().getBattery().decreaseBatteryMovement(sensor.getFloorType());
            performCleaningFunction();
            System.out.println(Controller.getInstance().getBattery().getBatteryLife());
        }

//        
    }

    private void performCleaningFunction() throws InterruptedException {
        while (!sensor.isClean() && sensor.getDirtRemaining() != 0) {
            Controller.getInstance().getBattery().decreaseBatteryCleaning(sensor.getFloorType());
            sensor.setDirtRemaining(sensor.getDirtRemaining() - 1);
            System.out.println("Dirt Remaining after cleaning: " + sensor.getDirtRemaining());
        }
        floorGraph.findCell(currentCell.getX(), currentCell.getY()).clean();
    }

    private boolean canStillClean(Path closestChargingStation) {
        double remainingBattery = Controller.getInstance().getBattery().getBatteryLife();
        return remainingBattery - closestChargingStation.cost() >= 1;
    }

    private boolean canGoTo(Path shortestPath) {
        double remainingBattery = Controller.getInstance().getBattery().getBatteryLife();
        FloorCell destination = shortestPath.getLastCell();
        return remainingBattery - floorGraph.getClosestChargingStationTo(destination.getX(), destination.getY()).cost() - shortestPath.cost() >= 1;
    }

    private boolean canMakeOneStep(Path closestChargingStation) {
        double remainingBattery = Controller.getInstance().getBattery().getBatteryLife();
        return remainingBattery - closestChargingStation.cost() >= 6;
    }

    /**
     * Turns the vacuum on
     */
    public void run() {
        // TODO Auto-generated method stub
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
            System.out.println(Controller.getInstance().getBattery().getStartingFloorType());
        }
    }

}
