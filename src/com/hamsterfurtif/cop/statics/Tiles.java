package com.hamsterfurtif.cop.statics;

import java.util.ArrayList;

import com.hamsterfurtif.cop.map.tiles.Tile;
import com.hamsterfurtif.cop.map.tiles.Tile.TileDoor;
import com.hamsterfurtif.cop.map.tiles.Tile.TileFloor;
import com.hamsterfurtif.cop.map.tiles.Tile.TileWall;
import com.hamsterfurtif.cop.map.tiles.Tile.TileWindow;

public class Tiles {

	public static ArrayList<Tile> tiles = new ArrayList<Tile>();

	
	public static Tile wall = new TileWall("Mur de brique", "bricks");
	public static Tile grass = new TileFloor("Herbe", "grass");
	public static Tile window = new TileWindow("Fenetre", "bricks_window");
	public static Tile door_grass = new TileDoor("Porte en metal", "metal_door", grass);
	public static Tile stone = new TileFloor("Dalles", "stone");
	public static Tile door_stone = new TileDoor("Porte en metal", "metal_door", stone);

	
	
	public static Tile getTile(String name){
		for(Tile t : Tiles.tiles)
			if(t.name.equals(name))
				return t;
		return Tiles.grass;
	}
}
