package com.hamsterfurtif.cop.map.tiles;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

import com.hamsterfurtif.cop.ITrigger.TriggerType;
import com.hamsterfurtif.cop.statics.Tiles;

public abstract class Tile {


	public boolean isDestructible = false;
	public boolean canShootTrough = false;
	public boolean canWalkThrough = false;
	public Tile ground = Tiles.grass; //Tile par défaut quand une case est pétée

	public char symbol;
	public String name;
	public final String id;
	public String imagename;
	public Image image;
	public static ArrayList<Sound> destroy = new ArrayList<Sound>();

	public Tile(String id, String name, String imagename){
		this.id = id;
		this.name = name;
		this.imagename = imagename;
		register();
	}

	public Tile(String id, String name, Image image){
		this.image = image;
		this.name = name;
		this.id = id;
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
	
	public void trigger(TriggerType source){}

	public static class TileWindow extends Tile {
		public TileWindow(String id, String name, String imagename){
			super(id, name, imagename);
			this.canShootTrough=true;
			this.symbol = 'I';

		}
	}

	public static class TileWall extends Tile {
		public TileWall(String id, String name, String imagename){
			super(id, name, imagename);
			this.symbol = 'W';
		}
	}

	public static class TileFloor extends Tile {
		public TileFloor(String id, String name, String imagename){
			super(id, name, imagename);
			this.canShootTrough=true;
			this.canWalkThrough=true;
			this.symbol = ' ';
		}
	}

	public static class TileDoor extends Tile {
		public TileDoor(String id, String name, String imagename,Tile tile){
			super(id, name, imagename);
			this.isDestructible=true;
			this.symbol = 'D';
			this.ground = tile;
		}
		
		public void trigger(TriggerType source){
			switch (source){
			case EXPLOSION:
			}
		}

	}

	public static class CustomTile extends Tile {

		public CustomTile(String name, Image image, boolean dest, boolean canShoot, boolean canWalk, Tile grd) {
			super("custom_tile", name, image);
			isDestructible = dest;
			canShootTrough = canShoot;
			canWalkThrough = canWalk;
			ground =grd;
		}

	}

	public Object clone() throws CloneNotSupportedException{
		return super.clone();

	}
}
