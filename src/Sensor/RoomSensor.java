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
	 * RoomSensor constructor initializes memory and floor plan and sets the current cell.
	 */
	public RoomSensor() {
		
		memory = new Memory();
		memory.setFloorPlan();
		cells = memory.floorMemory;
		
		currentCell = cells.get(0);
		
	}
        
        public void setCurrentCell(int x, int y){
            for(Cell cell:cells){
                if(cell.xs==x&&cell.ys==y){
                    currentCell=cell;
                    break;
                }
            }
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
		return currentCell.getIsClean();
	}
	
	/**
	 * returns amount of dirt remaining
	 */
	public int getDirtRemaining() {
		return currentCell.getDs();
	}
	
	/**
	 * set amount of dirt remaining at location (need to update to clean or partially clean after every "cleaning")
	 */
	public void setDirtRemaining(int dirt) {
		System.out.println("Setting dirt remaining to: " + dirt);
		currentCell.setDs(dirt);
	}
	
	/**
	 * returns whether or not the charging station is present at this location
	 * @return boolean
	 */
	public boolean getIsChargingStation() {
		return currentCell.getIsCharginStation();
	}
	
	/**
	 * returns what type of floor is present at this location
	 */
	public int getFloorType() {
		return currentCell.getSs();
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
		
		private boolean isClean;
		
		private boolean isChargingStation;

		public Cell() {
			
		}

        public Cell(int i, int i0) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
			
			String e = psString.substring(0, 1);
			String w = psString.substring(1, 2);
			String n = psString.substring(2,3);
			String s = psString.substring(3,4);
			
			int east = Integer.parseInt(e);
			int west = Integer.parseInt(w);
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
		/**
		 * method returns amount of dirt present at current location
		 * 
		 * @return ds
		 */

		public int getDs() {
			return ds;
		}
		
		/**
		 * method sets the amount of dirt remaining at location
		 * @param ds
		 */
		public void setDs(int ds) {
			if (ds == 0) {
				this.isClean = true;
			}
			else
				this.isClean = false;
			
			this.ds = ds;
		}
		
		/**
		 * Returns whether or not dirt is present at the current location
		 * 
		 * @return isClean
		 */
		public boolean getIsClean() {
			return this.isClean;
		}

		
		public int getCs() {
			return cs;
		}

		public void setCs(int cs) {
			if(cs == 1) {
				this.isChargingStation = true;
			}
			else
				this.isChargingStation = false;
			this.cs = cs;
		}
		
		public boolean getIsCharginStation() {
			return this.isChargingStation;
		}

	}

}


