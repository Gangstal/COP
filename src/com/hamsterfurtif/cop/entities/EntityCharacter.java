package com.hamsterfurtif.cop.entities;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

import com.hamsterfurtif.cop.Player;
import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.Utils.Facing;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.inventory.Inventory;
import com.hamsterfurtif.cop.inventory.Weapon;
import com.hamsterfurtif.cop.inventory.WeaponType;
import com.hamsterfurtif.cop.map.MapPos;

public class EntityCharacter {

	public static String defSym = "@#&$¤£%";
	public static ArrayList<Image> skins;

	public final Player player;
	public final int id;
	public String name;
	public MapPos pos = new MapPos();
	public int health = Game.maxHP;
	public Inventory inventory = new Inventory();
	public int repsawnsLeft = 5;
	public char symbol;
	public boolean turnIsOver = false;
	public int maxMoves = 5;
	public int movesLeft = maxMoves;
	public boolean configured;
	public Facing orientation = Facing.NORTH;

	//animation
	public int xgoffset=0, ygoffset=0;

	public static ArrayList<Sound> hurtSounds = new ArrayList<Sound>();
	public static ArrayList<Sound> deathSounds = new ArrayList<Sound>();


	public boolean hasShot, hasMoved;
	public Image skin;

	public EntityCharacter(Player player, int id, String name){
		this.player = player;
		this.id = id;
		this.name = name;
		configured = false;
	}

	public Weapon getWeapon(WeaponType type){
		switch(type){
		case PRIMARY:
			return inventory.primary;
		case SECONDARY:
			return inventory.secondary;
		default:
			return inventory.primary;
		}
	}

	public void resetTurnStats(){
		this.movesLeft = maxMoves;
		this.turnIsOver = false;
		this.hasShot = false;
		this.hasMoved = false;
	}

	public void reset(){
		resetTurnStats();
		inventory.ammoP=inventory.primary.ammo;
		inventory.ammoS=inventory.secondary.ammo;
		health=Game.maxHP;
	}
	
	public void setOrientation(Facing f){
		
		orientation = f;
		Utils.print(f.toString());
		switch (f) {
		case NORTH:
			skin.setRotation(0);
			break;
		case EAST:
			skin.setRotation(90);
		case SOUTH:
			skin.setRotation(180);
			break;
		case WEST:
			skin.setRotation(270);
			break;
		}
	}
}
