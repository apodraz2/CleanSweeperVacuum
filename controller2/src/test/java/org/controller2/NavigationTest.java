/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.controller2;

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
    
    public boolean isReachable(GraphCell cell) {
        return 50 - 2*Controller.getInstance().getVacuum().getFloorGraph().getClosestChargingStationTo(cell.getX(), cell.getY()).cost()> 0;
    }
    public boolean hasUnreachableNeighbors(GraphCell cell) {
        return 50 - 2*Controller.getInstance().getVacuum().getFloorGraph().getClosestChargingStationTo(cell.getX(), cell.getY()).cost() - (cell.getCell().cost()+3) < 0;
    }
}
