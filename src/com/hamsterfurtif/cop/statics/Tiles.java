package com.hamsterfurtif.cop.statics;

import java.util.ArrayList;

import com.hamsterfurtif.cop.map.tiles.Tile;
import com.hamsterfurtif.cop.map.tiles.Tile.TileDoor;
import com.hamsterfurtif.cop.map.tiles.Tile.TileFloor;
import com.hamsterfurtif.cop.map.tiles.Tile.TileStone;
import com.hamsterfurtif.cop.map.tiles.Tile.TileWall;
import com.hamsterfurtif.cop.map.tiles.Tile.TileWindow;

public class Tiles {

	public static ArrayList<Tile> tiles = new ArrayList<Tile>();

	
	public static Tile wall = new TileWall();
	public static Tile floor = new TileFloor();
	public static Tile window = new TileWindow();
	public static Tile door = new TileDoor();
	public static Tile stone = new TileStone();
	
}
