/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Movimentation;

import java.util.ArrayList;

/**
 *
 * @author Marcio
 */
public class FloorGraph {
    ArrayList<GraphCell> graph;

    public FloorGraph() {
        graph = new ArrayList<GraphCell>();
    }

    
    public void add(int x, int y){  
         
        
        if(this.findCell(x, y)==null){
            GraphCell newCell = new GraphCell(x, y);

            GraphCell north,south,west,east;
            north=this.findCell(x, y+1);
            south=this.findCell(x, y-1);
            east=this.findCell(x+1, y);
            west=this.findCell(x-1, y);
            if(north==null)
                newCell.setNorth(GraphCell.unknownCell);
            else
                newCell.setNorth(north);
            if(south==null)
                newCell.setSouth(GraphCell.unknownCell);
            else
                newCell.setSouth(south);
            if(east==null)
                newCell.setEast(GraphCell.unknownCell);
            else
                newCell.setEast(east);
            if(west==null)
                newCell.setWest(GraphCell.unknownCell);
            else
                newCell.setWest(west);     
            graph.add(newCell);

        }
    }
    
    public GraphCell findCell(int x, int y){
        for(GraphCell cell : graph){
            if(cell.getX()==x && cell.getY()==y)
                return cell;
        }
        return null;        
    }
    
    //Uses Dijikstra algorithm to calculate the shortest path between the cell1 (x1,y1) and cell2 (x2,y2)
    public ArrayList<Path> shortestPaths(int x1, int y1){
        ArrayList<GraphCell> cellsQueue = new ArrayList<GraphCell>();
        ArrayList<GraphCell> cellsPool = (ArrayList<GraphCell>)graph.clone();
        ArrayList<Path> paths = new ArrayList<Path>();
        if(findCell(x1, y1)!=null){
            cellsQueue.add(findCell(x1, y1));
            cellsPool.remove(findCell(x1, y1));
            Path firstPath = new Path();
            firstPath.add(findCell(x1, y1).getCell());
            paths.add(firstPath);
            while(cellsQueue.size()>0){
                GraphCell cell = deuqueue(cellsQueue);
                for(GraphCell auxCell : cell.getNeighbors()){
                    if(cellsPool.contains(auxCell)){
                        Path shortestPathToCell = (Path)Path.getPath(paths, x1, y1, cell.getX(), cell.getY()).clone();
                        shortestPathToCell.add(auxCell.getCell());
                        if(shortestPathToCell.cost()<Path.getPath(paths, x1, y1, auxCell.getX(), auxCell.getY()).cost())
                            if(paths.contains(Path.getPath(paths, x1, y1, auxCell.getX(), auxCell.getY())))
                                paths.set(Path.getPathIndex(paths, x1, y1, auxCell.getX(), auxCell.getY()),shortestPathToCell);
                            else
                                paths.add(shortestPathToCell);
                        cellsPool.remove(auxCell);
                        cellsQueue.add(auxCell);
                    }
                }  
            }
            return paths;
        }else{
            return null;
        }
    }
    
    private static GraphCell deuqueue(ArrayList<GraphCell> cellsQueue){
        GraphCell cell = cellsQueue.get(0);
        cellsQueue.remove(0);
        return cell;
    }
    
    public boolean hasUnvisitedCells(){
        for(GraphCell cell : graph)
            if(cell.hasUnvisitedNeighbors())
                return true;
        return false;
    }
    
    public ArrayList<FloorCell> unvisitedCellsNeighbor(){
        ArrayList<FloorCell> toVisit = new ArrayList<FloorCell>();
        for(GraphCell cell : graph)
            if(cell.hasUnvisitedNeighbors())
                toVisit.add(cell.getCell());
        return toVisit;
    }
    
    public String toString(){
        String s = "";
        for(GraphCell cell : graph)
            s+=cell.toString()+" ";
        return s;
    }
}
