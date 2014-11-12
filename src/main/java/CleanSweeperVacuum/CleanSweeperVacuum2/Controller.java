package CleanSweeperVacuum.CleanSweeperVacuum2;



import main.java.Sensor.RoomSensor;

/**
 *
 * @author sergie
 */
public class Controller {
	public volatile static Controller instance;
	public Battery battery;
	public Vacuum vacuum;
	public DirtCapacity dirtCapacity;
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
		RoomSensor rs = new RoomSensor();
		vacuum = new Vacuum(rs);
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
		dirtCapacity = new DirtCapacity(0,50);
		
	}
}
