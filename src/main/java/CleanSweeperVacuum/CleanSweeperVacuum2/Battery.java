package CleanSweeperVacuum.CleanSweeperVacuum2;

import java.util.ArrayList;

/**
 * @author Sergie Zorin
 * @see java.util.ArrayList
 * @see java.util.Collections
 */
public class Battery implements Runnable {

    private volatile boolean running = true;
    private double batteryLife = 50;
    private boolean charging = false;
    private Integer previosFloorType;
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
        switch (currentFloorType) {
            //Bare floor
            case 1:
                if (previosFloorType == 1) {
                    batteryLife = batteryLife - 1;
                    previosFloorType = currentFloorType;
                } else if (previosFloorType == 2) {
                    batteryLife = batteryLife - 1.5;
                    previosFloorType = currentFloorType;
                } else if (previosFloorType == 4) {
                    batteryLife = batteryLife - 2.5;
                    previosFloorType = currentFloorType;
                }
                break;
            //Low-pile carpet
            case 2:
                if (previosFloorType == 1) {
                    batteryLife = batteryLife - 1.5;
                    previosFloorType = currentFloorType;
                } else if (previosFloorType == 2) {
                    batteryLife = batteryLife - 1;
                    previosFloorType = currentFloorType;
                } else if (previosFloorType == 4) {
                    batteryLife = batteryLife - 2;
                    previosFloorType = currentFloorType;
                }
                break;
            //High-pile carpet
            case 4:
                if (previosFloorType == 1) {
                    batteryLife = batteryLife - 2.5;
                    previosFloorType = currentFloorType;
                } else if (previosFloorType == 2) {
                    batteryLife = batteryLife - 2;
                    previosFloorType = currentFloorType;
                } else if (previosFloorType == 4) {
                    batteryLife = batteryLife - 1;
                    previosFloorType = currentFloorType;
                }
                break;
            default:
                break;
        }
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
        switch (surface) {
            //Bare floor
            case 1:
                batteryLife = batteryLife - 1;
                break;
            //Low-pile carpet
            case 2:
                batteryLife = batteryLife - 2;
                break;
            //High-pile carpet
            case 4:
                batteryLife = batteryLife - 3;
                break;
        }
    }

    private void chargeBattery() throws InterruptedException {
        System.out.println("Starting Charge");
        while (batteryLife < 50) {
            Thread.sleep(50);
            System.out.println("Battery life is at " + batteryLife * 2 + "%");
            batteryLife++;
            if (batteryLife > 49) {
                batteryLife = 50;
                System.out.println("Battery life is at " + batteryLife * 2 + "%");
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
                }else if (commandsList.get(0).equals("shutdown")){
                    commandsList.remove(0);
                    this.running = false;
                    System.out.println("Vacuum has been tuned off");
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

    public void setStartingFloorType(int floorType) {
        this.previosFloorType = floorType;
    }

    public Integer getStartingFloorType() {
        return previosFloorType;
    }
}
