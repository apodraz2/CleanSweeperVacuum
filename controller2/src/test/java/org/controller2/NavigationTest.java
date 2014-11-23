/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.controller2;

import java.util.ArrayList;
import java.util.Comparator;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Marcio
 */
public class NavigationTest {

    @Test
    public void testTraversableFloor() throws Exception {
        Controller.reset("traversablefloorplan.xml", false);
        Controller.getInstance().initEverything();
        synchronized (Controller.getInstance().getVacuum()) {
            Controller.getInstance().getVacuum().wait();
            Assert.assertTrue(Controller.getInstance().getVacuum().hasToBeVisited().isEmpty());
            Assert.assertEquals((float) Controller.getInstance().getBattery().getBatteryLife(), (float) 50);
            for (GraphCell cell : Controller.getInstance().getVacuum().getFloorGraph().getGraph()) {
                Assert.assertTrue(cell.isClean());
                Assert.assertTrue(isReachable(cell));
                Assert.assertTrue(!cell.mustBeVisited());
            }
        }

    }

    @Test
    public void testNontraversableFloor() throws Exception {
        Controller.reset("nontraversablefloorplan.xml", false);
        Controller.getInstance().initEverything();
        synchronized (Controller.getInstance().getVacuum()) {
            Controller.getInstance().getVacuum().wait();
            Assert.assertEquals((float) Controller.getInstance().getBattery().getBatteryLife(), (float) 50);
            Assert.assertTrue(!Controller.getInstance().getVacuum().hasToBeVisited().isEmpty());
            for (GraphCell cell : Controller.getInstance().getVacuum().getFloorGraph().getGraph()) {
                Assert.assertTrue(isReachable(cell));
                if (cell.mustBeVisited()) {
                    Assert.assertTrue(hasUnreachableNeighbors(cell));
                }
            }
        }

    }

    @Test
    public void shortestPathsWork() throws InterruptedException {
        Controller.reset("traversablefloorplan.xml", false);
        Controller.getInstance().initEverything();
        synchronized (Controller.getInstance().getVacuum()) {
            Controller.getInstance().getVacuum().wait();
            for (GraphCell source : Controller.getInstance().getVacuum().getFloorGraph().getGraph()) {
                ArrayList<Path> paths = Controller.getInstance().getVacuum().getFloorGraph().shortestPaths(source.getX(), source.getY());
                Assert.assertEquals(paths.size(), Controller.getInstance().getVacuum().getFloorGraph().getGraph().size());
                sort(paths);
                ArrayList<Path> auxPaths = new ArrayList<>();
                for (Path path : paths) {
                    boolean isValid;
                    isValid = auxPaths.isEmpty();
                    for (Path auxPath : auxPaths) {
                        Path pathClone = path.clone();
                        Path auxPathClone = auxPath.clone();
                        do {
                            FloorCell cell = pathClone.dequeue();
                            FloorCell auxCell = auxPathClone.dequeue();
                            if (!cell.equals(auxCell)) {
                                break;
                            }
                            if (auxPathClone.size() == 0 && pathClone.size() == 1) {
                                isValid = true;
                                break;
                            }
                        } while (auxPathClone.size() != 0);
                    }
                    Assert.assertTrue(isValid);
                    auxPaths.add(path);
                    System.out.println(path);
                }
            }
        }
    }

    static ArrayList<Path> sort(ArrayList<Path> paths){
        ArrayList<Path> newPaths = new ArrayList<Path>();
        ArrayList<Path> pathsClone = new ArrayList<>();
        for(Path path : paths){
            pathsClone.add(path);
        }
        while(!pathsClone.isEmpty()){
            Path minimum = pathsClone.get(0);
            for(Path path : pathsClone){
                if(path.size()<minimum.size())
                    minimum=path;
            }
            newPaths.add(minimum);
            pathsClone.remove(minimum);
        }        
        return newPaths;
    }

    public boolean isReachable(GraphCell cell) {
        return 50 - 2 * Controller.getInstance().getVacuum().getFloorGraph().getClosestChargingStationTo(cell.getX(), cell.getY()).cost() > 0;
    }

    public boolean hasUnreachableNeighbors(GraphCell cell) {
        return 50 - 2 * Controller.getInstance().getVacuum().getFloorGraph().getClosestChargingStationTo(cell.getX(), cell.getY()).cost() - (cell.getCell().cost() + 3) < 0;
    }
}
