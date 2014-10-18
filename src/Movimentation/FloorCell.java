/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Movimentation;

/**
 *
 *@author adampodraza
 * @author Marcio
 */
public class FloorCell {
		private int x;
		private int y;
                int floorType;
		
		public FloorCell(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
                
                public void setFloorType(int type){
                    floorType = type;
                }
                
                public int getFloorType(){
                    return floorType;
                }
                
                public float cost(){
//                    if(floorType==1)
//                        return 1;
//                    if(floorType==2)
//                        return 2;
//                    if(floorType==4)
//                        return 3;
//                    return 0;
                    return 1;
                }
                
                public boolean equals(FloorCell cell){
                    if(cell==null)return false;
                    return this.getX()==cell.getX() && this.getY()==cell.getY();
                }
		
	}
