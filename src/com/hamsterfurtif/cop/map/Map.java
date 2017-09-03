package com.hamsterfurtif.cop.map;

import com.hamsterfurtif.cop.map.tiles.Tile;

public class Map {
	
	public Tile[][] map = new Tile[21][12];
	public int dimX, dimY, dimZ;
	public boolean locked = true;

	public Map(Tile[][] map){
		this.map = map;
		
		dimY=map.length;
		dimX=map[0].length;

	}
	
	public Map(Tile[][] map, boolean locked) {
		this.map = map;
		dimY=map.length;
		dimX=map[0].length;
		this.locked = locked;
	}

	public Tile getTile(MapPos pos){
		try{
			return map[pos.Y][pos.X];
		}
		catch(Exception e){
			System.out.println("::: ERROR Tile requested on pos: "+pos.toString()+"  |  Map dimensions: X="+dimX+", Y="+dimY+", Z="+dimZ+" :::");
			return null;
		}
	}
	
	public void setTile(MapPos pos, Tile tile){
		map[pos.Y][pos.X] = tile;
	}
	
	public void destroyTile(MapPos pos){
		this.setTile(pos, this.getTile(pos).destroy());
	}
	
}
