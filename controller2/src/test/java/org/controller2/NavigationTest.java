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
                if(cell.mustBeVisited()){
                    Assert.assertTrue(hasUnreachableNeighbors(cell));
                }
            }
        }

    }
    
    @Test
    public void shortestPathsWork() throws InterruptedException{
        Controller.reset("traversablefloorplan.xml", false);
        Controller.getInstance().initEverything();
        synchronized (Controller.getInstance().getVacuum()) {
            Controller.getInstance().getVacuum().wait();
            ArrayList<Path> paths = Controller.getInstance().getVacuum().getFloorGraph().shortestPaths(0, 0);
            paths.sort(new PathComparator());
            ArrayList<Path> auxPaths = new ArrayList<>();
            for(Path path : paths){
                boolean isValid;
                isValid = auxPaths.isEmpty();
                for(Path auxPath : auxPaths){
                    Path pathClone = path.clone();
                    Path auxPathClone = auxPath.clone();
                    do{
                        FloorCell cell = pathClone.dequeue();
                        FloorCell auxCell = auxPathClone.dequeue();
                        if(!cell.equals(auxCell)){
                            break;
                        }
                        if(auxPathClone.size()==0 && pathClone.size()==1){
                            isValid = true;
                            break;
                        }
                    }while(auxPathClone.size()!=0);
                }
                Assert.assertTrue(isValid);
                auxPaths.add(path);
                System.out.println(path);
            }
        }
    }

    
    static class PathComparator implements Comparator<Path> {

        
        public int compare(Path p1, Path p2) {
            Integer a1 = p1.size();
            Integer a2 = p2.size();
            return a1.compareTo(a2);
        }
    }
    
    public boolean isReachable(GraphCell cell) {
        return 50 - 2*Controller.getInstance().getVacuum().getFloorGraph().getClosestChargingStationTo(cell.getX(), cell.getY()).cost()> 0;
    }
    public boolean hasUnreachableNeighbors(GraphCell cell) {
        return 50 - 2*Controller.getInstance().getVacuum().getFloorGraph().getClosestChargingStationTo(cell.getX(), cell.getY()).cost() - (cell.getCell().cost()+3) < 0;
    }
}
