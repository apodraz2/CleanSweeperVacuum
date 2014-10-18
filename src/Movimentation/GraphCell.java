package Movimentation;

import java.util.ArrayList;

/**
 * A Class to represent a cell known by the vacuum in the Graph structure
 *@author Marcio
 */
public class GraphCell {
		FloorCell cell;
                
    /**
     *
     */
    public static final GraphCell unknownCell=new GraphCell(-1,-1);
                
                private ArrayList<GraphCell> neighbors;
                
                private boolean clean;
		
		public GraphCell(int x, int y) {
                    cell = new FloorCell(x, y);
                    if(x!=-1 && y!=-1){
                        neighbors = new ArrayList<>(4);
                        for(int i=0;i<4;i++){
                            neighbors.add(unknownCell);
                        }
                        clean=false;
                    }
		}
                
                public void setFloorType(int type){
                    cell.setFloorType(type);
                }
                
                public void getFloorType(){
                    cell.getFloorType();
                }
                
                public boolean isClean(){
                    return clean;
                }
                
                public void clean(){
                    clean=true;
                }
                
                public boolean isFlag(){
                    return (getX()==-1&&getY()==-1);
                }

		public int getX() {
			return cell.getX();
		}

		public int getY() {
			return cell.getY();
		}
                
                public void setWest(GraphCell c){
                    if(!this.equals(c))
                    if(c!= null && !c.isFlag()){   
                        neighbors.set(0, c);
                        neighbors.get(0).neighbors.set(1, this);
                    }
                    if(c==null)
                        neighbors.set(0, c);
                    
                }
                
                public void setEast(GraphCell c){
                    if(!this.equals(c))
                    if(c!= null && !c.isFlag()){
                        neighbors.set(1, c);
                        neighbors.get(1).neighbors.set(0, this);
                    }
                    if(c==null)
                        neighbors.set(1, c);
                    
                }
                
                public void setNorth(GraphCell c){
                    if(!this.equals(c))
                    if(c!= null && !c.isFlag()){
                        neighbors.set(2, c);
                        neighbors.get(2).neighbors.set(3, this);
                    }
                    if(c==null)
                        neighbors.set(2, c);

                }
                
                public void setSouth(GraphCell c){
                    if(!this.equals(c))
                    if(c!= null && !c.isFlag()){
                        neighbors.set(3, c);
                        neighbors.get(3).neighbors.set(2, this);
                    }
                    if(c==null)
                        neighbors.set(3, c);
                }
                
                public boolean hasUnvisitedNeighbors(){
                    for(GraphCell c : neighbors){
                        if((c!=null && c.isFlag()))
                            return true;
                    }
                    return false;
                }
                
                public ArrayList<GraphCell> getNeighbors(){
                    ArrayList<GraphCell> cells = new ArrayList<GraphCell>();
                    for(GraphCell c : neighbors){
                        if((c!=null&&!c.isFlag()))
                            cells.add(c);
                    }
                    return cells;
                }
                
                public FloorCell getCell(){
                    return cell;
                }
                
                public String toString(){
                    return ("["+getY()+","+getX()+"]");
                }
                
                public boolean equals(GraphCell cell){
                    if(cell==null)return false;
                    return this.getX()==cell.getX() && this.getY()==cell.getY();
                }
                
}
