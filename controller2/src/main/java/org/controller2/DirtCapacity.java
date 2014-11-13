package org.controller2;
import java.util.ArrayList;
import java.util.Scanner;

import org.sensor2.RoomSensor;
/**
 * @author Sabrina Guillaume
 * @see java.util.ArrayList
 * @see java.util.Collections
 */

public class DirtCapacity {
	
	private int dirtLevel;
	private int maxDirtLevel;
	private Boolean isFull;
	
	public DirtCapacity(int dirtLevel, int max){
		this.dirtLevel = dirtLevel;
		this.maxDirtLevel = max;
		
		isFull = false;
	}
	
	
	public int getDirtLevel(){
		return this.dirtLevel;
	}
	
	public int getMaxDirtLevel(){
		return this.maxDirtLevel;
	}
	
	public float getDirtLevelPercent(){
		return (this.dirtLevel*2);
	}
	
	public Boolean checkIsFull() throws InterruptedException{
		
		if (this.dirtLevel >= this.maxDirtLevel - 4){
			setIsFull(true);
			System.out.println("Vacuum Dirt is now full");
		}else
			setIsFull(false);
		
		return isFull;
	}
	
	public boolean getIsFull(){
		return this.isFull;
	}
	
	public void setIsFull(Boolean isFull){
		this.isFull = isFull;
	}
	
	public void addDirt(int dirt) throws InterruptedException{
		this.dirtLevel += dirt;
		checkIsFull();
	}
	
	public void emptyMe() throws InterruptedException{
		Scanner yesEmptyMe = new Scanner( System.in );
		String userResponse;
		
		System.out.println("Do you want to empty the Vacuum (Enter y for yes)?  ");
		userResponse = yesEmptyMe.next();
		
			this.dirtLevel = 0;      
			setIsFull(false);
			System.out.println("Vacuum Dirt is now empty");
		
		
		//yesEmptyMe.close();
		
	}

	public void printDirtLevel(){
		System.out.println("Current Dirt Level:" + getDirtLevel());
		System.out.println("Dirt Level:" + getDirtLevelPercent() + "%");
	}

	
	
	
	
}