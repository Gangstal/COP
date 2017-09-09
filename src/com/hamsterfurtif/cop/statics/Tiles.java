package com.hamsterfurtif.cop.statics;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.map.tiles.Tile;
import com.hamsterfurtif.cop.map.tiles.Tile.TileDoor;
import com.hamsterfurtif.cop.map.tiles.Tile.TileFloor;
import com.hamsterfurtif.cop.map.tiles.Tile.TileWall;
import com.hamsterfurtif.cop.map.tiles.Tile.TileWindow;
import com.hamsterfurtif.cop.map.tiles.Tile.CustomTile;

public class Tiles {

	public static ArrayList<Tile> tiles = new ArrayList<Tile>();

	public static Tile wall = new TileWall("brick_wall", "Mur de brique", "bricks");
	public static Tile grass = new TileFloor("grass","Herbe", "grass");
	public static Tile window = new TileWindow("bricks_window", "Fenetre", "bricks_window");
	public static Tile door_grass = new TileDoor("metal_door", "Porte en metal", "metal_door", grass);
	public static Tile stone = new TileFloor("stone_slab", "Dalles", "stone");
	public static Tile door_stone = new TileDoor("metal_door", "Porte en metal", "metal_door", stone);
	public static Tile water = new TileWindow("water", "Eau", "water");
	public static Tile planks = new TileFloor("planks", "Planches", "planks");

	
	
	public static Tile getTile(String id){
		for(Tile t : Tiles.tiles)
			if(t.id.equals(id))
				return t;
		return Tiles.grass;
	}
	
	public static Tile getCustomTile(String[] args){
		boolean walk = false, destroy = false, shoot = false;
		String name = "";
		Image image = grass.image;
		Tile grd = grass;
		
		for(String arg : args){
			String[] split = arg.split("=");
			switch(split[0]){
				case "ground":
					grd = Tiles.getTile(split[1]);
					break;
				case "texture":
					Utils.print(split[1]);
					image = TextureLoader.loadTexture("tiles\\"+split[1]+".png");
					break;
				case "destroy":
					destroy = split[1]=="true" ? true : false;
					break;
				case "walk":
					walk = split[1]=="true" ? true : false;
					break;
				case "shoot":
					shoot = split[1]=="true" ? true : false;
					break;
				case "name":
					name = split[1];
				}
			}
		
		
		Tile tile = new CustomTile(name, image, destroy, shoot, walk, grd);
		return tile;
	}
}
