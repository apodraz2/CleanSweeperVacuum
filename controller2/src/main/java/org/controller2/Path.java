package controller;

import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 * This package handles paths in the graph
 *
 * @author Marcio
 */
public class Path {

    private ArrayList<FloorCell> path;

    /**
     * Creates a new Path that is points to newPath
     * @param newPath A new Path
     */
    public Path(ArrayList<FloorCell> newPath) {
        path = newPath;
    }

    /**
     * Creates an empty path
     */
    public Path() {
        path = new ArrayList<FloorCell>();
    }

    /**
     * Adds a cell to a path
     * @param cell Cell to be added to the path
     */
    public void add(FloorCell cell) {
        path.add(cell);
    }

    /**
     * Removes and returns the last cell of a path
     * @return The last cell of a path
     */
    public FloorCell dequeue() {
        FloorCell fc = this.path.get(0);
        this.path.remove(0);
        return fc;
    }

    /**
     * Returns the number of cells in a path
     * @return The number of cells in a path
     */
    public int size() {
        return path.size();
    }

    /**
     * Returns whether the current path starts in (x,y)
     * @param x The X coordinate of the possible beginning 
     * @param y The Y coordinate of the possible beginning 
     * @return true if the current path starts in (x,y), false otherwise
     */
    public boolean startsWith(int x, int y) {
        return path.get(0).getX() == x && path.get(0).getY() == y;
    }

    /**
     * Returns whether the current path ends in (x,y)
     * @param x The X coordinate of the possible ending 
     * @param y The Y coordinate of the possible ending 
     * @return true if the current path ends in (x,y), false otherwise
     */
    public boolean endsWith(int x, int y) {
        return path.get(path.size() - 1).getX() == x && path.get(path.size() - 1).getY() == y;
    }

    /**
     * Returns the Battery Usage of moving over a path
     * @return The Battery Usage of moving over a path
     */
    public float cost() {
        float cost = Float.POSITIVE_INFINITY;
        if (path.size() > 0) {
            cost = 0;
        }
        for (int i = 0; i < path.size() - 1; i++) {
            float a = path.get(i).cost();
            float b = path.get(i + 1).cost();
            cost += ((a + b) / 2);
        }
        return cost;
    }

    /**
     * Returns the path that ends in (x,y) 
     * @param paths The ArrayList of paths
     * @param x The X coordinate of the possible ending cell
     * @param y The Y coordinate of the possible ending cell
     * @return The path that ends in (x,y) 
     */
    public static Path getPath(ArrayList<Path> paths, int x, int y) {
        for (Path auxPath : paths) {
            if (auxPath.path.get(auxPath.path.size() - 1).getX() == x
                    && auxPath.path.get(auxPath.path.size() - 1).getY() == y) {
                return auxPath;
            }
        }
        return null;
    }

    /**
     * Returns the index of the path that ends in (x,y) 
     * @param auxPaths The ArrayList of paths
     * @param x The X coordinate of the possible ending cell
     * @param y The Y coordinate of the possible ending cell
     * @return The index of the path that ends in (x,y) 
     */
    public static int getPathIndex(ArrayList<Path> auxPaths, int x, int y) {
        for (Path auxPath : auxPaths) {
            if (auxPath.path.get(auxPath.path.size() - 1).getX() == x
                    && auxPath.path.get(auxPath.path.size() - 1).getY() == y) {
                return auxPaths.indexOf(auxPath);
            }
        }
        return -1;
    }
    
    /**
     * Return the ending cell of a path
     * @return The ending cell of a path
     */
    public FloorCell getLastCell(){
        return path.get(path.size()-1);
    }

    /**
     * Clones a path
     * @return A cloned path
     */
    @Override
    public Path clone() {
        if(this==null)return null;
        return new Path((ArrayList<FloorCell>) this.path.clone());
    }

}
