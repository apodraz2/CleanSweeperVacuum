package Controller;

import Model.Vacuum;
import Sensor.RoomSensor;

/**
 *
 * @author sergie
 */
public class Controller {

    /**
     * Represents whether the vacuum is on or not
     */
    static boolean on;

    /**
     * Run the simulation of cleaning a house
     *
     * @param args System parameters
     */
    public static void main(String[] args) {
        on = true;

        RoomSensor rs = new RoomSensor();

        Vacuum vacuum = new Vacuum(rs);

        vacuum.start();

    }

}
