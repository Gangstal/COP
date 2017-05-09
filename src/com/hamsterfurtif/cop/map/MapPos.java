package com.hamsterfurtif.cop.map;

public class MapPos {
	
	public int X,Y,Z;

	
	public MapPos(){
		
	}
	
	public MapPos(int x, int y, int z){
		this.set(x, y, z);
	}
	
	public void set(int x, int y, int z){
		X=x;
		Y=y;
		Z=z;
	}
	
	public void add(int x, int y, int z){
		X+=x;
		Y+=y;
		Z+=z;
	}
	
	public void add(MapPos pos){
		this.add(pos.X, pos.Y, this.Z);
	}
	
	public String toString(){
		return ("PosX:"+X+" PosY:"+Y+" PosZ:"+Z);
	}
	
	public boolean equals(MapPos pos){
		return(this.X == pos.X && this.Y == pos.Y && this.Z == pos.Z);
	}

}
