package com.hamsterfurtif.cop.statics;

import com.hamsterfurtif.cop.map.Tile;
import com.hamsterfurtif.cop.map.Tile.TileDebug;
import com.hamsterfurtif.cop.map.Tile.TileDoor;
import com.hamsterfurtif.cop.map.Tile.TileFloor;
import com.hamsterfurtif.cop.map.Tile.TileWall;
import com.hamsterfurtif.cop.map.Tile.TileWindow;

public class Tiles {

	public static Tile wall = new TileWall();
	public static Tile floor = new TileFloor();
	public static Tile window = new TileWindow();
	public static Tile door = new TileDoor();
	
	public static Tile horizontal = new TileDebug('H');
	public static Tile vertical = new TileDebug('V');
	public static Tile crossed = new TileDebug('+');
	public static Tile both = new TileDebug('B');

	
}
