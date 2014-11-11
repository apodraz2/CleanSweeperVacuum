package Model;

import java.util.ArrayList;
/**
 * @author Sabrina Guillaume
 * @see java.util.ArrayList
 * @see java.util.Collections
 */

public class DirtCapacity  {
	
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
	
	public Boolean checkIsFull(){
		if (this.dirtLevel == this.maxDirtLevel){
			setIsFull(true);
			System.out.println("Vacuum Dirt is now full");
		}else
			setIsFull(false);
		
		return isFull;
	}
	
	public void setIsFull(Boolean isFull){
		this.isFull = isFull;
	}
	
	public void addDirt(int dirt){
		this.dirtLevel += dirt;
		checkIsFull();
	}
	
	public void emptyMe() throws InterruptedException{
		while (dirtLevel <= 50) {
            Thread.sleep(50);
             dirtLevel--;
          
        }
		setIsFull(false);
		System.out.println("Vacuum Dirt is now empty");
	}


	
	
	
	
}
