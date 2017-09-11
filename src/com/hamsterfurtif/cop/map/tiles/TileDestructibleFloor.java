package com.hamsterfurtif.cop.map.tiles;

import com.hamsterfurtif.cop.map.tiles.Tile.TileFloor;

public class TileDestructibleFloor extends TileFloor{

	public TileDestructibleFloor(String id, String name, String imagename, Tile ground) {
		super(id, name, imagename);
		isDestructible = true;
		this.ground = ground;
	}

}
