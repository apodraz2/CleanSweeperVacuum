package org.controller2;

import java.util.Scanner;

import org.junit.Assert;

import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class DirtCapacityTest 
   
{
	DirtCapacity dc;
	
    /**
     * Tests to ensure DirtCapacity is not null when initialized
     *
     */
	
	@Test
	public void testNotNull(){
		dc = new DirtCapacity(0,50);
		Assert.assertTrue(!dc.equals(null));
	}
	
	 /**
     * Tests adding dirt to vacuum
	 * @throws InterruptedException 
     *
     */
	
	@Test
	public void testAddDirt() throws InterruptedException{
		dc = new DirtCapacity(0,50);
		dc.addDirt(5);
		Assert.assertEquals(dc.getDirtLevel(), 5);
		
		dc.addDirt(5);
		Assert.assertEquals(dc.getDirtLevel(), 10);
		
		dc.addDirt(5);
		Assert.assertEquals(dc.getDirtLevel(), 15);
	}
	
	 /**
     * Tests getting max dirt level
	 * @throws InterruptedException 
     *
     */
	
	@Test
	public void testMaxDirtLevel() throws InterruptedException{
		dc = new DirtCapacity(0,50);
		
		Assert.assertEquals(dc.getMaxDirtLevel(), 50);
		
	}
	
	/**
     * Tests checking if vacuum is full
	 * @throws InterruptedException 
     *
     */
	
	@Test
	public void testGetIsFull() throws InterruptedException{
		dc = new DirtCapacity(0,50);
		
		dc.setIsFull(false);
		Assert.assertFalse(dc.getIsFull());
		
		dc.setIsFull(true);
		Assert.assertTrue(dc.getIsFull());
		
	}
	
	 /**
     * Tests checking if vacuum is full
	 * @throws InterruptedException 
     *
     */
	
	@Test
	public void testCheckIsFull() throws InterruptedException{
		dc = new DirtCapacity(0,50);
		
		Assert.assertFalse(dc.checkIsFull());
		
		dc.addDirt(46);
		Assert.assertTrue(dc.checkIsFull());
		
	}
	
	
    /**
     * Tests to make sure the vacuum empties dirt
     * @throws InterruptedException 
     */
	@Test
	public void testEmptyVacuum() throws InterruptedException{
		String strInput = "y";
		Scanner input = new Scanner(strInput);
		
		dc = new DirtCapacity(44,50);
		Assert.assertEquals(dc.getDirtLevel(), 44);
		
		dc.emptyMe(input);
		Assert.assertEquals(dc.getDirtLevel(), 0);
	
		
	}


    /**
     * Test Controller - Runs all tests
     * @throws InterruptedException 
     */
	
	public void main (String [] args) throws InterruptedException {
		testNotNull();
		testAddDirt();
		testMaxDirtLevel();
		testGetIsFull();
		testCheckIsFull();
		testEmptyVacuum();
		
	}
	
  
}

