package com.hamsterfurtif.cop.map.tiles;

import org.newdawn.slick.Image;
import com.hamsterfurtif.cop.statics.Tiles;

public abstract class Tile {
	

	public boolean isDestructible = false;
	public boolean canShootTrough = false;
	public boolean canWalkThrough = false;
	public Tile ground = Tiles.floor; //Tile par défaut quand une case est pétée
	
	public char symbol;
	public String name;
	public Image image;
	
	public Tile(String name){
		this.name = name;
		register();
	}
	
	private void register(){
		Tiles.tiles.add(this);
	}
	
	public Tile destroy(){
		if(this.isDestructible)
			return ground;
		else
			return this;
	}
	
	public static class TileWindow extends Tile {
		public TileWindow(){
			super("bricks_window");
			this.canShootTrough=true;
			this.symbol = 'I';
			
		}
	}
	
	public static class TileWall extends Tile {
		public TileWall(){
			super("bricks");
			this.symbol = 'W';
		}
	}
	
	public static class TileFloor extends Tile {
		public TileFloor(){
			super("grass");
			this.canShootTrough=true;
			this.canWalkThrough=true;
			this.symbol = ' ';
		}
	}
	
	public static class TileDoor extends Tile {
		public TileDoor(){
			super("metal_door");
			this.isDestructible=true;
			this.symbol = 'D';
		}
	}
	
	public static class TileStone extends Tile {
		public TileStone(){
			super("stone");
			this.canShootTrough=true;
			this.canWalkThrough=true;
			this.symbol = ' ';
		}
	}
	
}
