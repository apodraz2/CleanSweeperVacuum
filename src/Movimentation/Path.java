package Movimentation;

import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *This package handles paths in the graph
 * @author Marcio
 */
public class Path {
    private ArrayList<FloorCell> path;
    
    public Path(ArrayList<FloorCell> newPath){
        path=newPath;
    }
    
    public Path(){
        path=new ArrayList<FloorCell>();
    }
    
    public boolean add(FloorCell cell){
        path.add(cell);
        return isPath();
    }
    
    public FloorCell dequeue(){
        FloorCell fc = this.path.get(0);
        this.path.remove(0);
        return fc;
    }
    
    public int size(){
        return path.size();
    }

    public boolean isPath(){
        if(path.size()<2)
            return false;
        for(int i=0;i<path.size()-1;i++){
            FloorCell a = path.get(i);
            FloorCell b = path.get(i+1);
            if(!((a.getX()==b.getX()&&(abs(a.getY()-b.getY())==1))||(a.getY()==b.getY()&&(abs(a.getX()-b.getX())==1))))
                return false;
        }       
        return true;
    }
    
    public boolean startsWith(int x, int y){
        return path.get(0).getX()==x && path.get(0).getY()==y;
    }
    
    public boolean endsWith(int x, int y){
        return path.get(path.size()-1).getX()==x && path.get(path.size()-1).getY()==y;
    }
    
    public float cost(){
        float cost = Float.POSITIVE_INFINITY;
        if(path.size()>0)
            cost=0;
        for(int i=0;i<path.size()-1;i++){
            float a = path.get(i).cost();
            float b = path.get(i+1).cost();
            cost+=((a+b)/2);
        } 
        return cost;
    }
    
    public static Path getPath(ArrayList<Path> paths,int x1, int y1, int x2, int y2){
        ArrayList<Path> realPaths = new ArrayList<Path>();
        for(Path auxPath : paths){
            if(auxPath.path.get(0).getX()==x1 && 
                    auxPath.path.get(0).getY()==y1 && 
                    auxPath.path.get(auxPath.path.size()-1).getX()==x2 &&
                    auxPath.path.get(auxPath.path.size()-1).getY()==y2)
                return auxPath;
        }
        return new Path();
    }
    
     public static int getPathIndex(ArrayList<Path> auxPaths,int x1, int y1, int x2, int y2){
        for(Path auxPath : auxPaths){
            if(auxPath.path.get(0).getX()==x1 && 
                    auxPath.path.get(0).getY()==y1 && 
                    auxPath.path.get(auxPath.path.size()-1).getX()==x2 &&
                    auxPath.path.get(auxPath.path.size()-1).getY()==y2)
                return auxPaths.indexOf(auxPath);
        }
        return -1;
    }
     
     public Path clone(){
         return new Path((ArrayList<FloorCell>)this.path.clone());
     }
     
     public String toString(){
         return ("["+this.path.get(0).getX()+","+this.path.get(0).getY()+"] - ["+this.path.get(size()-1).getX()+","+this.path.get(size()-1).getY()+"] - "+this.cost());
     }
}
