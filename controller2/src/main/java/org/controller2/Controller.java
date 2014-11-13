package org.controller2;

import org.sensor2.RoomSensor;









/**
 *
 * @author sergie
 */
public class Controller {
	public volatile static Controller instance;
	public Battery battery;
	public Vacuum vacuum;
	
	RoomSensor rs = new RoomSensor();
	DirtCapacity dirtCapacity = new DirtCapacity(0,50);
    /**
     * Represents whether the vacuum is on or not
     */
    static boolean on;
    
    public static Controller getInstance() {
        if (instance == null) {
            synchronized(Controller.class) {
                if (instance == null)
                    instance = new Controller();
            }
        }
        return instance;
    }
	public Battery getBattery(){
		return this.battery;
	}
	
	 /**
     * @author Sabrina Guillaume
     * Gets DirtCapacity object in use
     *
     */
	public DirtCapacity getDirtCapacity(){
		return this.dirtCapacity;
	}
    
    public void initEverything() throws InterruptedException{
    	on = true;
		initBattery();
		initVacuum();
		initDirtCapacity();
	}
    
    private void initVacuum() throws InterruptedException {
		
		vacuum = new Vacuum(rs, dirtCapacity);
		new Thread(vacuum).start();
	}
    
	private void initBattery() {
		battery = new Battery();
		new Thread(battery).start();
	}
	
	 /**
     * @author Sabrina Guillaume
     * Initializes DirtCapacity object
     *
     */
	private void initDirtCapacity() {
		
		
	}

}
