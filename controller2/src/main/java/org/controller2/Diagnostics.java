package org.controller2;

import java.util.ArrayList;

import org.sensor2.Memory;
import org.sensor2.RoomSensor.Cell;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.Date;


public class Diagnostics extends Memory{
	
	public volatile static Diagnostics instance;
	public Writer movement = null;
	public Writer cleaning = null;
	public Writer battery = null;
	public Date date= new java.util.Date();
	
	public static Diagnostics getInstance() {
        if (instance == null) {
            synchronized(Controller.class) {
                if (instance == null)
                    instance = new Diagnostics();
            }
        }
        return instance;
    }
	
	public void createMovementLog(){
		try {
			movement = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("logs/Movement_Log.txt"), "UTF-8"));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void createCleaningLog(){
		try {
			cleaning = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("logs/Cleaning_Log.txt"), "UTF-8"));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void createBatteryLog(){
		try {
			battery = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("logs/Battery_Log.txt"), "UTF-8"));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void closeLogs(){
		try {
			movement.close();
			cleaning.close();
			battery.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void recordMovement(String move){
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("logs/Movement_Log.txt", true)))) {
		    out.println(new Timestamp(System.currentTimeMillis()) + " " + move);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void recordCleaning(String clean){
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("logs/Cleaning_Log.txt", true)))) {
		    out.println(new Timestamp(System.currentTimeMillis()) + " " + clean);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void recordBatery(String charge){
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("logs/Battery_Log.txt", true)))) {
		    out.println(charge);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
