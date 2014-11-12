package controller.src.main.java.org.controller2;

import sensor.src.main.java.org.sensor2.RoomSensor;







/**
 *
 * @author sergie
 */
public class Controller {
	public volatile static Controller instance;
	public Battery battery;
	public Vacuum vacuum;
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
    
    public void initEverything() throws InterruptedException{
    	on = true;
		initBattery();
		initVacuum();
	}
    
    private void initVacuum() throws InterruptedException {
		RoomSensor rs = new RoomSensor();
		vacuum = new Vacuum(rs);
		new Thread(vacuum).start();
	}
    
	private void initBattery() {
		battery = new Battery();
		new Thread(battery).start();
	}

}
