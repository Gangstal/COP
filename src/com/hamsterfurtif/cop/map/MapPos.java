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
	
	public void sub(int x, int y, int z){
		X-=x;
		Y-=y;
		Z-=z;
	}


	public void add(MapPos pos){
		this.add(pos.X, pos.Y, this.Z);
	}
	
	public void sub(MapPos pos){
		this.sub(pos.X, pos.Y, this.Z);
	}

	public String toString(){
		return ("PosX:"+X+" PosY:"+Y);
	}

	public boolean equals(Object o){
		return o instanceof MapPos && X == ((MapPos) o).X && Y == ((MapPos) o).Y && Z == ((MapPos) o).Z;
	}

	public boolean isAdjacent(MapPos pos){
		return Math.abs(X-pos.X)+Math.abs(Y-pos.Y)==1;
	}
	
	public static double distance(MapPos p1, MapPos p2){
		//Good ol' Pythagore
		return Math.sqrt(Math.pow(Math.abs(p1.X-p2.X),2) + Math.pow(Math.abs(p1.Y-p2.Y),2));
	}
	
	public double distance(MapPos p1){
		return distance(this, p1);
	}
	
	public MapPos copy(){
		return new MapPos(X, Y, Z);
	}

}
