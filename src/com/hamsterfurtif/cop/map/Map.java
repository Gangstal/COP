package com.hamsterfurtif.cop.map;

public class Map {
	
	public Tile[][] map = new Tile[21][12];
	public int dimX, dimY, dimZ;

	public Map(Tile[][] map){
		this.map = map;
		
		dimY=map.length;
		dimX=map[0].length;

	}
	
	public Tile getTile(MapPos pos){
		return map[pos.Y][pos.X];
	}
	
	public void setTile(MapPos pos, Tile tile){
		map[pos.Y][pos.X] = tile;
	}
	
	public void destroyTile(MapPos pos){
		this.setTile(pos, this.getTile(pos).destroy());
	}
	
}
