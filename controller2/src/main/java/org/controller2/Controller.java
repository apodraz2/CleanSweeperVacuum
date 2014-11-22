package org.controller2;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.sensor2.RoomSensor;

/**
 *
 * @author sergie
 */
public class Controller {

    public volatile static Controller instance = null;
    public Battery battery;
    public Vacuum vacuum;
    public boolean userIO;

    public RoomSensor rs;
    public DirtCapacity dirtCapacity;
    /**
     * Represents whether the vacuum is on or not
     */
    static boolean on;

    public static Controller getInstance() {
            synchronized(Controller.class) {
                if (instance == null){
                        instance = new Controller();
                    }
                }
            return instance;
    }

    private Controller(){
        try{
            userIO = true;
            dirtCapacity = new DirtCapacity(0, 50);
            rs = new RoomSensor();
            vacuum = new Vacuum(rs, dirtCapacity);
            battery = new Battery();
        } catch (InterruptedException e) {
            System.out.println("Couldn't initiate one of the vacuum's module");
        }
    }

    public Battery getBattery() {
        return this.battery;
    }

    /**
     * @author Sabrina Guillaume Gets DirtCapacity object in use
     *
     */
    public DirtCapacity getDirtCapacity() {
        return this.dirtCapacity;
    }

    public void initEverything() throws InterruptedException {
        on = true;
        initBattery();
        initVacuum();
        initDirtCapacity();
    }

    private void initVacuum() throws InterruptedException {
        new Thread(vacuum).start();
    }

    void initBattery() {
        new Thread(battery).start();
    }

    /**
     * @author Sabrina Guillaume Initializes DirtCapacity object
     *
     */
    private void initDirtCapacity() {

    }
    
    public boolean hasUserIO(){
        return this.userIO;
    }

    public static void reset(String floorPlan, boolean userIO) {
        synchronized (Controller.class) {
            instance = new Controller();
            getInstance().userIO = userIO;
            getInstance().rs = new RoomSensor(floorPlan);
            try {
                getInstance().vacuum = new Vacuum(getInstance().rs, getInstance().dirtCapacity);
            } catch (InterruptedException ex) {
                System.err.println("The vacuum module could not be initialized");
            }
        }
    }

    public Vacuum getVacuum(){
        return vacuum;
    }
}
