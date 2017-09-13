package com.hamsterfurtif.cop.map;

import com.hamsterfurtif.cop.map.tiles.Tile;

public class Map {

	public Tile[] map;
	public int dimX, dimY, dimZ;
	public boolean locked;
	public MapEventListener listener;

	public Map(Tile[] map, int dimX, boolean locked) {
		this.map = map;
		this.dimX = dimX;
		this.dimY = map.length / dimX;
		this.locked = locked;
		listener = MapEventListenerNil.nil;
	}

	public Map(Tile[] map, int dimX){
		this(map, dimX, true);
	}

	public Tile getTile(MapPos pos){
		try{
			return map[pos.Y * dimX + pos.X];
		}
		catch(Exception e){
			System.out.println("::: ERROR Tile requested on pos: "+pos.toString()+"  |  Map dimensions: X="+dimX+", Y="+dimY+", Z="+dimZ+" :::");
			return null;
		}
	}

	public void setTile(MapPos pos, Tile tile){
		map[pos.Y * dimX + pos.X] = tile;
		listener.onTileChanged(this, pos);
	}

	public void destroyTile(MapPos pos){
		setTile(pos, getTile(pos).destroy());
		listener.onTileChanged(this, pos);
	}

	public boolean isInMap(MapPos loc) {
		return loc.X >= 0 && loc.X < dimX && loc.Y >= 0 && loc.Y < dimY;
	}
}
