package Sensor;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;



public interface Sensor {
	
	public int getCurrentCellX();
	public int getCurrentCellY();
	
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
