package org.controller2;

import java.util.ArrayList;

/**
 * @author Sergie Zorin
 * @see java.util.ArrayList
 * @see java.util.Collections
 */
public class Battery implements Runnable {

    private volatile boolean running = true;
    private double batteryLife = 50;
    private Integer previousFloorType;
    private ArrayList<String> commandsList = new ArrayList<String>();

    public boolean isRequestsEmpty() {
        if (commandsList.size() == 0) {
            return true;
        }
        return false;
    }

    public Battery() {
    }

    /**
     * Responsible for decreasing battery only for movement
     *
     * @param currentFloorType type of the floor robot is on right now
     */
    public void decreaseBatteryMovement(int currentFloorType) {
        float currentFloorCost=3;
        float previousFloorCost=3;
        switch (currentFloorType) {
            //Bare floor
            case 1:
                currentFloorCost=1;
                break;
            //Low-pile carpet
            case 2:
                currentFloorCost=2;
                break;
            //High-pile carpet
            case 4:
                currentFloorCost=3;
                break;
            default:
                break;
        }
        switch (previousFloorType) {
            //Bare floor
            case 1:
                previousFloorCost=1;
                break;
            //Low-pile carpet
            case 2:
                previousFloorCost=2;
                break;
            //High-pile carpet
            case 4:
                previousFloorCost=3;
                break;
            default:
                break;
        }
        previousFloorType=currentFloorType;
        batteryLife-=((currentFloorCost+previousFloorCost)/2);
        if (batteryLife < 0) {
            throw new RuntimeException("Battery has reached 0%");
        }
    }

    /**
     * Responsible for decreasing battery only for cleaning
     *
     * @param surface type of the floor robot is on right now
     */
    public void decreaseBatteryCleaning(int surface) {
        float surfaceCost=0;
        switch (surface) {
            //Bare floor
            case 1:
                surfaceCost =  1;
                break;
            //Low-pile carpet
            case 2:
                surfaceCost =  2;
                break;
            //High-pile carpet
            case 4:
                surfaceCost =  3;
                break;
        }
        batteryLife-=surfaceCost;
    }

    private void chargeBattery() throws InterruptedException {
        System.out.println("Starting Charge");
        while (batteryLife < 50) {
            System.out.println("Battery life is at " + batteryLife * 2 + "%");
            if (Controller.getInstance().hasUserIO()) {
                batteryLife+=5;
                Thread.sleep(50);
            }else{
                batteryLife=50;
                break;
            }
            if (batteryLife > 49) {
                    batteryLife = 50;
                    System.out.println("Finished charging!");
            }
        }
    }

    public double getBatteryLife() {
        return batteryLife;
    }

    /**
     *
     * @throws InterruptedException
     * @throws InvalidInputException
     */
    public void executeCommand() throws InterruptedException {

        synchronized (commandsList) {
            while (!commandsList.isEmpty()) {
                if (commandsList.get(0).equals("charge")) {
                    System.out.println("Executing request charge");
                    chargeBattery();
                    commandsList.remove(0);
                } else if (commandsList.get(0).equals("shutdown")) {
                    commandsList.remove(0);
                    this.running = false;
                    System.out.println("Vacuum has been turned off");
                }
            }
        }
    }

    public void addCommand(String cmd) {

        Thread.yield();
        synchronized (commandsList) {
            System.out.println("Command received " + cmd);
            commandsList.add(cmd);
            commandsList.notifyAll();
            Thread.yield();
        }

    }

    /**
     * run() method is responsible for starting an elevator thread. Keeps it on
     * wait until requests array gets notified After change to rider request
     * calls method move()
     */
    public void run() {
        synchronized (this) {
            long t = 15;
            while (this.running) {
                Thread.yield();
                try {
                    synchronized (commandsList) {
                        commandsList.wait(t);
                    }
                } catch (InterruptedException ex) {
                }

                try {
                    executeCommand();
                } catch (InterruptedException e) {
                }

            }
        }
    }

    public void setStartingFloorType(int floorType) {
        this.previousFloorType = floorType;
    }

    public Integer getStartingFloorType() {
        return previousFloorType;
    }
}
