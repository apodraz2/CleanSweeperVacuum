package main.java.Sensor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import main.java.Sensor.RoomSensor.Cell;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;






public class Memory {
	
	public ArrayList<Cell> floorMemory = new ArrayList<Cell>();
	public ArrayList<String> cleanedAreaLog = new ArrayList<String>();
	public ArrayList<String> visitedAreaLog = new ArrayList<String>();

	/**
	 * Reads in XML file that represent floor plan
	 * Floor plan contains information about:
	 *  			XS = x coordinate
	 * 				YS =  y coordinate
	 * 				SS = surface:
	 * 					1 = The cell is bare floor.
	 * 					2 = The cell is covered in low-pile carpet.
	 * 					4 = The cell is covered in high-pile carpet.
	 *				PS = represents restrictions on movement x -x y -y  
	 *					0 = unknown (There are no zeros in a current doc)
	 *					1 = open for movement
	 *					2 = closed for movement
	 *					4 = stairs
	 *				DS = represents units of dirt in the cell
	 *				CS = charging station		
	 */
	public void setFloorPlan(){
		File fXmlFile = new File("floorplan.xml");
    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder dBuilder;
    	Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
		} catch (ParserConfigurationException | SAXException | IOException e1) {
			e1.printStackTrace();
		}

    	NodeList nList = doc.getElementsByTagName("cell");
    	
    	for(int i = 0; nList.getLength() > i; i++){
    		Node nNode = nList.item(i);
    		Element eElement = (Element) nNode;
    		System.out.printf("Cell %d    xs = '%s' ys = '%s' ss = '%s' ps = '%s' ds = '%s' cs = '%s'\n", i, eElement.getAttribute("xs"), 
    				eElement.getAttribute("ys"), eElement.getAttribute("ss"), eElement.getAttribute("ps"), 
    				eElement.getAttribute("ds"), eElement.getAttribute("cs"));
    		
    		Cell cell = new Cell();
    		cell.setXs(Integer.parseInt(eElement.getAttribute("xs")));
    		cell.setYs(Integer.parseInt(eElement.getAttribute("ys")));
    		cell.setSs(Integer.parseInt(eElement.getAttribute("ss")));
    		cell.setPs(Integer.parseInt(eElement.getAttribute("ps")));
    		cell.setDs(Integer.parseInt(eElement.getAttribute("ds")));
    		cell.setCs(Integer.parseInt(eElement.getAttribute("cs")));
    		
    		floorMemory.add(cell);
    		
    	}
    	
    	
    	
	}
	
	public void setSensorChecksLog(){
		
	}
	
	public void setMovementLog(String visited){
		visitedAreaLog.add(visited);
	}
	
	public void setCleaningLog(String cleanedCell){
		cleanedAreaLog.add(cleanedCell);
	}
}
