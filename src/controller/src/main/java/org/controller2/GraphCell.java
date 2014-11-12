package controller.src.main.java.org.controller2;

import java.util.ArrayList;

/**
 * A Class to represent a cell known by the vacuum in the Graph structure
 * @author Marcio
 */
public class GraphCell {

    /**
     * The coordinates and cleanness of the cell
     */
    private FloorCell cell;

    /**
     * A special cell that flags a cell that wasn't visited yet, but that really exists
     */
    public static final GraphCell unknownCell = new GraphCell(-1, -1);

    /**
     * The Neighbors of a cell
     */
    private ArrayList<GraphCell> neighbors;

    /**
     * Represents if a cell is clean (true) or not (false)
     */
    private boolean clean;

    /**
     * Creates a new cell
     * @param x The X coordinate of the new cell
     * @param y The Y coordinate of the new cell
     */
    public GraphCell(int x, int y) {
        cell = new FloorCell(x, y);
        if (x != -1 && y != -1) {
            neighbors = new ArrayList<>(4);
            for (int i = 0; i < 4; i++) {
                neighbors.add(unknownCell);
            }
            clean = false;
        }
    }

    /**
     * Sets the floor type (1-Bare Floor, 2-Low Pile Floor, 4-High Pile Floor)
     * @param type The type of Floor
     */
    public void setFloorType(int type) {
        cell.setFloorType(type);
    }

    /**
     * Sets the floor type (1-Bare Floor, 2-Low Pile Floor, 4-High Pile Floor)
     * @return The Floor Type
     */
    public int getFloorType() {
        return cell.getFloorType();
    }

    /**
     * Returns whether the cell is clean or not
     * @return true if the cell is clean, false otherwise
     */
    public boolean isClean() {
        return clean;
    }

    /**
     * Sets the cell as clean
     */
    public void clean() {
        clean = true;
    }

    /**
     * Return whether the cell wasn't visited yet, but exists, or not
     * @return true if the cell wasn't visited yet, but exists, false otherwise
     */
    public boolean isFlag() {
        return (getX() == -1 && getY() == -1);
    }

    /**
     * Returns the X coordinate of the cell
     * @return The X coordinate of the cell
     */
    public int getX() {
        return cell.getX();
    }

    /**
     * Returns the Y coordinate of the cell
     * @return The Y coordinate of the cell
     */
    public int getY() {
        return cell.getY();
    }

    /**
     * Sets the current cell's west neighbor as cell, and the cell's east neighbor as the current cell, if cell is not null
     * @param cell The west neighbor of the current cell
     */
    public void setWest(GraphCell cell) {
        if (!this.equals(cell)) {
            if (cell != null && !cell.isFlag()) {
                neighbors.set(0, cell);
                neighbors.get(0).neighbors.set(1, this);
            }
        }
        if (cell == null) {
            neighbors.set(0, cell);
        }

    }

    /**
     * Sets the current cell's east neighbor as cell, and the cell's west neighbor as the current cell, if cell is not null
     * @param cell The east neighbor of the current cell
     */
    public void setEast(GraphCell cell) {
        if (!this.equals(cell)) {
            if (cell != null && !cell.isFlag()) {
                neighbors.set(1, cell);
                neighbors.get(1).neighbors.set(0, this);
            }
        }
        if (cell == null) {
            neighbors.set(1, cell);
        }

    }

    /**
     * Sets the current cell's north neighbor as cell, and the cell's south neighbor as the current cell, if cell is not null
     * @param cell The north neighbor of the current cell
     */
    public void setNorth(GraphCell cell) {
        if (!this.equals(cell)) {
            if (cell != null && !cell.isFlag()) {
                neighbors.set(2, cell);
                neighbors.get(2).neighbors.set(3, this);
            }
        }
        if (cell == null) {
            neighbors.set(2, cell);
        }

    }

    /**
     * Sets the current cell's south neighbor as cell, and the cell's north neighbor as the current cell, if cell is not null
     * @param cell The south neighbor of the current cell
     */
    public void setSouth(GraphCell cell) {
        if (!this.equals(cell)) {
            if (cell != null && !cell.isFlag()) {
                neighbors.set(3, cell);
                neighbors.get(3).neighbors.set(2, this);
            }
        }
        if (cell == null) {
            neighbors.set(3, cell);
        }
    }

    /**
     * Returns whether the cell must be visited to be cleaned or have it's neighbors detected or not
     * @return true if the cell must be visited to be cleaned or have it's neighbors detected, false otherwise
     */
    public boolean mustBeVisited() {
        for (GraphCell c : neighbors) {
            if ((c != null && c.isFlag())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return an ArrayList of the cell's known neighbors
     * @return The cell's known neighbors
     */
    public ArrayList<GraphCell> getNeighbors() {
        ArrayList<GraphCell> cells = new ArrayList<>();
        for (GraphCell c : neighbors) {
            if ((c != null && !c.isFlag())) {
                cells.add(c);
            }
        }
        return cells;
    }

    /**
     * Return the cell coordinates and cleanness
     * @return The cell coordinates and cleanness
     */
    public FloorCell getCell() {
        return cell;
    }

    /**
     * Return whether the current cell is equals to the parameter cell or not
     * @param cell The cell to be compared
     * @return true if the the cells are equal to each other, false otherwise or if the parameter is null
     */
    public boolean equals(GraphCell cell) {
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

}
