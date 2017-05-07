package com.hamsterfurtif.cop.map;

public abstract class Tile {

	public boolean isDestructible = false;
	public boolean canShootTrough = false;
	public boolean canWalkThrough = false;
	
	public char symbol;
	
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
