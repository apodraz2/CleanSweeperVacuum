package Sensor;

import java.util.ArrayList;





/**
 * RoomSensor class has nested Cell class that contains all the necessary information about that Cell.
 * @author adampodraza
 *
 */
public class RoomSensor implements Sensor {
	//Dependent Memory object
	Memory memory;
	ArrayList<Cell> cells;
	
	//Dependent Cell object
	Cell currentCell;
	
	/**
	 * RoomSensor constructor initializes memory and floorplan and sets the current cell.
	 */
	public RoomSensor() {
		
		memory = new Memory();
		memory.setFloorPlan();
		cells = memory.floorMemory;
		
		currentCell = cells.get(0);
		
	}

	/**
	 * returns current cell's x coordinate
	 */
	@Override
	public int getCurrentCellX() {
		// TODO Auto-generated method stub
		return currentCell.getXs();
	}

	/**
	 * returns current cell's y coordinate
	 */
	@Override
	public int getCurrentCellY() {
		// TODO Auto-generated method stub
		return currentCell.getYs();
	}

	/**
	 * returns whether or not the cell is clean
	 */
	@Override
	public boolean isClean() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * boolean informs the vacuum whether west path is obstructed
	 */
	@Override
	public boolean canGoWest() {
		// TODO Auto-generated method stub
		return currentCell.getWestOpen();
	}

	/**
	 * moves sensor west
	 */
	@Override
	public boolean goWest() {
		// TODO Auto-generated method stub
		if(canGoWest()) {
			for (int i = 0; i < cells.size(); i++) {
				if (cells.get(i).getXs() == currentCell.getXs() + 1 && cells.get(i).getYs() == currentCell.getYs()) {
					currentCell = cells.get(i);
					return true;
				}
			}
		}
		return false;
		
	}

	/**
	 * boolean informs the vacuum whether east path is obstructed
	 */
	@Override
	public boolean canGoEast() {
		// TODO Auto-generated method stub
		return currentCell.getEastOpen();
	}

	/**
	 * moves sensor east
	 */
	@Override
	public boolean goEast() {
		// TODO Auto-generated method stub
		if(canGoEast()) {
			for(int i = 0; i < cells.size(); i++) {
				if (cells.get(i).getXs() == currentCell.getXs() - 1 && cells.get(i).getYs() == currentCell.getYs()) {
					currentCell = cells.get(i);
					return true;
				}
			}
		}
		return false;
		
	}

	/**
	 * boolean informs vacuum if north path is obstructed
	 */
	@Override
	public boolean canGoNorth() {
		// TODO Auto-generated method stub
		return currentCell.getNorthOpen();
	}

	/**
	 * moves sensor north
	 */
	@Override
	public boolean goNorth() {
		// TODO Auto-generated method stub
		if (canGoNorth()) {
			for(int i = 0; i < cells.size(); i++) {
				if (cells.get(i).getXs() == currentCell.getXs() && cells.get(i).getYs() == currentCell.getYs() + 1) {
					currentCell = cells.get(i);
					return true;
				}
			}
		}
		return false;
	}

	
	/**
	 * boolean informs vacuum if south path is obstructed
	 */
	@Override
	public boolean canGoSouth() {
		// TODO Auto-generated method stub
		return currentCell.getSouthOpen();
	}
	
	/**
	 * moves vacuum south
	 */
	@Override
	public boolean goSouth() {
		// TODO Auto-generated method stub
		if (canGoSouth()) {
			for (int i = 0; i < cells.size(); i++) {
				if (cells.get(i).getXs() == currentCell.getXs() && cells.get(i).getYs() == currentCell.getYs() - 1) {
					currentCell = cells.get(i);
					return true;
				}
			}
		}
		return false;
		
	}
	
	/**
	 * A local class to store cell objects
	 * @author adampodraza
	 *
	 */
	public static class Cell {
		private int xs;
		private int ys;
		private int ss;
		private int ps;
		private int ds;
		private int cs;
		
		private boolean northOpen;
		private boolean southOpen;
		private boolean eastOpen;
		private boolean westOpen;
		

		public Cell() {
			
		}
		
		public boolean getNorthOpen() {
			return northOpen;
		}
		public void setNorthOpen(boolean x) {
			northOpen = x;
		}
		
		public boolean getSouthOpen() {
			return southOpen;
		}
		public void setSouthOpen(boolean x) {
			southOpen = x;
		}
		
		public boolean getEastOpen() {
			return eastOpen;
		}
		public void setEastOpen(boolean x) {
			eastOpen = x;
		}
		
		public boolean getWestOpen() {
			return westOpen;
		}
		public void setWestOpen(boolean x) {
			westOpen = x;
		}
		
		public int getXs() {
			return xs;
		}

		public void setXs(int xs) {
			this.xs = xs;
		}

		public int getYs() {
			return ys;
		}

		public void setYs(int ys) {
			this.ys = ys;
		}

		public int getSs() {
			return ss;
		}

		public void setSs(int ss) {
			this.ss = ss;
		}

		public int getPs() {
			return ps;
		}

		/**
		 * This method sets the booleans for path obstructions based on the ps variable.
		 * @param ps
		 */
		public void setPs(int ps) {
			this.ps = ps;
			
			String psString = Integer.toString(ps);
			
			String w = psString.substring(0, 1);
			String e = psString.substring(1, 2);
			String n = psString.substring(2,3);
			String s = psString.substring(3,4);
			
			int west = Integer.parseInt(w);
			int east = Integer.parseInt(e);
			int north = Integer.parseInt(n);
			int south = Integer.parseInt(s);
			
			if (west == 1) {
				this.setWestOpen(true);
			} else
				this.setWestOpen(false);
			
			if (east == 1) {
				this.setEastOpen(true);
			} else
				this.setEastOpen(false);
			
			if (north == 1) {
				this.setNorthOpen(true);
			} else
				this.setNorthOpen(false);
			
			if (south == 1) {
				this.setSouthOpen(true);
			} else
				this.setSouthOpen(false);
			
			
		}

		public int getDs() {
			return ds;
		}

		public void setDs(int ds) {
			this.ds = ds;
		}

		public int getCs() {
			return cs;
		}

		public void setCs(int cs) {
			this.cs = cs;
		}

	}

}


