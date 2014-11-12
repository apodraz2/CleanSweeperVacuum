/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.controller2;

/**
 * A basic representation of a floor cell
 *
 * @author adampodraza
 * @author Marcio
 */
public class FloorCell {

    /**
     * The X coordinate of the cell
     */
    private int x;
    /**
     * The Y coordinate of the cell
     */
    private int y;
    /**
     * The floor type of the cell
     */
    int floorType;
    
    /**
     * True if the cell is a charging station, false otherwise
     */
    private boolean chargingStation;

    /**
     * Creates a new floor cell
     *
     * @param x The X coordinate of the cell
     * @param y The X coordinate of the cell
     */
    public FloorCell(int x, int y) {
        this.x = x;
        this.y = y;
        floorType = 0;
    }

    /**
     * Returns the X coordinate of the cell
     *
     * @return The X coordinate of the cell
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y coordinate of the cell
     *
     * @return The Y coordinate of the cell
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the floor type of the cell
     *
     * @param type The type of floor (1-Bare Floor, 2-Low Pile Floor, 4-High
     * Pile Floor)
     */
    public void setFloorType(int type) {
        floorType = type;
    }

    /**
     * Returns the floor type of the cell
     *
     * @return The type of floor (1-Bare Floor, 2-Low Pile Floor, 4-High Pile
     * Floor)
     */
    public int getFloorType() {
        return floorType;
    }

    /**
     * Returns the energy usage to move over a floor of the same type that the
     * current cell
     *
     * @return The energy usage to move on the floor (1-Bare Floor, 2-Low Pile
     * Floor, 3-High Pile Floor)
     */
    public float cost() {
        if (floorType == 1) {
            return 1;
        }
        if (floorType == 2) {
            return 2;
        }
        if (floorType == 4) {
            return 3;
        }
        return 1;
    }

    /**
     * Return whether the current cell is equals to cell, or cell is null, or
     * the cells are different
     *
     * @param cell The compared cell
     * @return true if the current cell is equals to cell, false otherwise
     */
    public boolean equals(FloorCell cell) {
        if (cell == null) {
            return false;
        }
        return this.getX() == cell.getX() && this.getY() == cell.getY();
    }

    /**
     * Returns the coordinates of a cell in Cartesian style
     *
     * @return The coordinates of a cell in Cartesian style
     */
    @Override
    public String toString() {
        return "(" + getX() + "," + getY() + ")";
    }
    
    public boolean isChargingStation(){
        return chargingStation;
    }
    
    public void setChargingStation(boolean chargingStation){
        this.chargingStation = chargingStation;
    }
}
