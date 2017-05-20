package com.hamsterfurtif.cop.map;

import com.hamsterfurtif.cop.statics.Tiles;

public abstract class Tile {

	public boolean isDestructible = false;
	public boolean canShootTrough = false;
	public boolean canWalkThrough = false;
	public Tile ground = Tiles.floor; //Tile par défaut quand une case est pétée
	
	public char symbol;
	
	public Tile destroy(){
		if(this.isDestructible)
			return ground;
		else
			return this;
	}
	
	public static class TileWindow extends Tile {
		public TileWindow(){
			this.canShootTrough=true;
			this.symbol = 'I';
		}
	}
	
	public static class TileWall extends Tile {
		public TileWall(){
			this.symbol = 'W';
		}
	}
	
	public static class TileFloor extends Tile {
		public TileFloor(){
			this.canShootTrough=true;
			this.canWalkThrough=true;
			this.symbol = ' ';
		}
	}
	
	public static class TileDoor extends Tile {
		public TileDoor(){
			this.isDestructible=true;
			this.symbol = 'D';
		}
	}
	
}
