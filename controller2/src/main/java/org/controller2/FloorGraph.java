package org.controller2;

import java.util.ArrayList;

/**
 * Class responsible for discovering and storing the known floor cells, and for
 * calculating the vacuum's route
 *
 * @author Marcio
 */
public class FloorGraph {

    /**
     * The known cells of the rooms
     */
    private ArrayList<GraphCell> graph;

    /**
     * Creates a new graph of the floor
     */
    public FloorGraph() {
        graph = new ArrayList<GraphCell>();
    }

    /**
     * Adds a new cell to the known floor
     *
     * @param x The X coordinate of the new cell
     * @param y The Y coordinate of the new cell
     */
    public void add(int x, int y) {

        if (this.findCell(x, y) == null) {
            GraphCell newCell = new GraphCell(x, y);

            GraphCell north, south, west, east;
            north = this.findCell(x, y + 1);
            south = this.findCell(x, y - 1);
            east = this.findCell(x + 1, y);
            west = this.findCell(x - 1, y);
            if (north == null) {
                newCell.setNorth(GraphCell.unknownCell);
            } else {
                newCell.setNorth(north);
            }
            if (south == null) {
                newCell.setSouth(GraphCell.unknownCell);
            } else {
                newCell.setSouth(south);
            }
            if (east == null) {
                newCell.setEast(GraphCell.unknownCell);
            } else {
                newCell.setEast(east);
            }
            if (west == null) {
                newCell.setWest(GraphCell.unknownCell);
            } else {
                newCell.setWest(west);
            }
            graph.add(newCell);

        }
    }

    /**
     * Returns the cell at the coordinates (x,y) of the graph
     *
     * @param x The X coordinate of the searched cell
     * @param y The Y coordinate of the searched cell
     * @return The cell at the coordinate (x,y) if it exists, or null if there's
     * no cell on (x,y)
     */
    public GraphCell findCell(int x, int y) {
        for (GraphCell cell : graph) {
            if (cell.getX() == x && cell.getY() == y) {
                return cell;
            }
        }
        return null;
    }

    /**
     * Returns an ArrayList of the shortest paths to all the cells on the floor
     *
     * @param x The X coordinate of the source cell
     * @param y The Y coordinate of the source cell
     * @return The shortest paths to all the cells on the floor
     */
    public ArrayList<Path> shortestPaths(int x, int y) {

        ArrayList<GraphCell> borderCells = new ArrayList<GraphCell>();
        ArrayList<GraphCell> unvisited = (ArrayList<GraphCell>) graph.clone();
        ArrayList<Path> paths = new ArrayList<Path>();
        GraphCell cell;

        if (findCell(x, y) != null) {

            borderCells.add(findCell(x, y));
            unvisited.remove(findCell(x, y));

            Path basePath = new Path();
            basePath.add(findCell(x, y).getCell());
            paths.add(basePath);

            while (unvisited.size() > 0) {

                cell = getClosestBorderCell(paths, borderCells);
                borderCells.remove(cell);
                unvisited.remove(cell);
                Path shortestPathToCell = Path.getPath(paths, cell.getX(), cell.getY());

                for (GraphCell auxCell : cell.getNeighbors()) {
                    Path shortetPathToAuxCell = shortestPathToCell.clone();
                    shortetPathToAuxCell.add(auxCell.getCell());
                    if (Path.getPath(paths, auxCell.getX(), auxCell.getY()) == null) {
                        paths.add(shortetPathToAuxCell);
                    } else {
                        if (Path.getPath(paths, auxCell.getX(), auxCell.getY()).cost() > shortetPathToAuxCell.cost()) {
                            paths.set(Path.getPathIndex(paths, auxCell.getX(), auxCell.getY()), shortetPathToAuxCell);
                        }
                    }
                    if (unvisited.contains(auxCell)) {
                        unvisited.remove(auxCell);
                        borderCells.add(auxCell);
                    }
                }
            }
            return paths;
        } else {
            return null;
        }
    }

    /**
     * Return an ArrayList of all cells that have to be visited
     * visited
     *
     * @return all cells that their neighbors have not been visited
     */
    public ArrayList<FloorCell> hasToBeVisited() {
        ArrayList<FloorCell> toVisit = new ArrayList<>();
        for (GraphCell cell : graph) {
            if (cell.mustBeVisited()||!cell.isClean()) {
                toVisit.add(cell.getCell());
            }
        }
        return toVisit;
    }

    /**
     * Return the closest cell that is known, but that was not visited by the
     * Dijkstra's Algorithm
     *
     * @param paths The ArrayList of the known paths so far
     * @param borderCells The ArrayList of the cells that are known, but that
     * were not visited by the Dijkstra's Algorithm
     * @return
     */
    private GraphCell getClosestBorderCell(ArrayList<Path> paths, ArrayList<GraphCell> borderCells) {
        Path pathOfClosestBorderCell = null;
        for (Path path : paths) {
            for (GraphCell cell : borderCells) {
                if (path.endsWith(cell.getX(), cell.getY()) && cell.mustBeVisited()) {
                    if (pathOfClosestBorderCell != null && path.size() < pathOfClosestBorderCell.size()) {
                        pathOfClosestBorderCell = path;
                    } else if (pathOfClosestBorderCell == null && path.endsWith(cell.getX(), cell.getY())) {
                        pathOfClosestBorderCell = path;
                    }
                } else if (pathOfClosestBorderCell == null && path.endsWith(cell.getX(), cell.getY())) {
                    pathOfClosestBorderCell = path;
                }
            }
        }
        FloorCell cell;
        cell = pathOfClosestBorderCell.getLastCell();
        return this.findCell(cell.getX(), cell.getY());
    }

    /**
     * Returns the Cartesian coordinates of all visited cells
     *
     * @return The Cartesian coordinates of all visited cells
     */
    @Override
    public String toString() {
        String output = "";
        for (GraphCell cell : graph) {
            output += " " + cell.toString();
        }
        return output;
    }

    public Path getClosestChargingStation(FloorCell currentCell) {
        return getClosestChargingStationTo(currentCell.getX(), currentCell.getY());
    }

    public Path getClosestChargingStationTo(int x, int y) {
        ArrayList<Path> shortestPaths = this.shortestPaths(x, y);
        Path shortestPath = null;
        for (Path path : shortestPaths) {
            if (path.getLastCell().isChargingStation()) {
                return path;
            }
        }
        return shortestPath;
    }
    
    public ArrayList<GraphCell> getGraph() {
        return graph;
    }
    
}
