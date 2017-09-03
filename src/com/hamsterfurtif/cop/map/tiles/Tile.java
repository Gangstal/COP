package com.hamsterfurtif.cop.map.tiles;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.statics.Tiles;

public abstract class Tile {
	

	public boolean isDestructible = false;
	public boolean canShootTrough = false;
	public boolean canWalkThrough = false;
	public Tile ground = Tiles.grass; //Tile par défaut quand une case est pétée
	
	public char symbol;
	public String name;
	public String imagename;
	public Image image;
	public static ArrayList<Sound> destroy = new ArrayList<Sound>();
	
	public Tile(String name, String imagename){
		this.imagename = imagename;
		this.name = name;
		Utils.print("K:"+name);
		register();
	}
	
	private void register(){
		Tiles.tiles.add(this);
		Utils.print("R:"+name);
	}
	
	public Tile destroy(){
		if(this.isDestructible)
			return ground;
		else
			return this;
	}
	
	public static class TileWindow extends Tile {
		public TileWindow(String name, String imagename){
			super(name, imagename);
			this.canShootTrough=true;
			this.symbol = 'I';
			
		}
	}
	
	public static class TileWall extends Tile {
		public TileWall(String name, String imagename){
			super(name, imagename);
			this.symbol = 'W';
		}
	}
	
	public static class TileFloor extends Tile {
		public TileFloor(String name, String imagename){
			super(name, imagename);
			this.canShootTrough=true;
			this.canWalkThrough=true;
			this.symbol = ' ';
		}
	}
	
	public static class TileDoor extends Tile {
		public TileDoor(String name, String imagename,Tile tile){
			super(name, imagename);
			this.isDestructible=true;
			this.symbol = 'D';
			this.ground = tile;
		}
	}
	
	
	
	
	public void setAttribute(String s){
		String[] split = s.split(":");
		switch(split[0]){
		case "ground":
			this.ground = Tiles.getTile(split[1]);
		case "texture":
			this.image = TextureLoader.loadTexture("tiles\\"+split[1]+".gif");
		}
	}
	
	
}
