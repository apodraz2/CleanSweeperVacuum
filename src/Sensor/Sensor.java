package Sensor;

public interface Sensor {

    public int getCurrentCellX();

    public int getCurrentCellY();

    public int getFloorType();

    public boolean isClean();

    public boolean canGoWest();

    public boolean goWest();

    public boolean canGoEast();

    public boolean goEast();

    public boolean canGoNorth();

    public boolean goNorth();

    public boolean canGoSouth();

    public boolean goSouth();

}
