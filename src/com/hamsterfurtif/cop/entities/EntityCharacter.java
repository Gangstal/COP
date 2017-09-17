package com.hamsterfurtif.cop.entities;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

import com.hamsterfurtif.cop.Player;
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
	public boolean isShieled;
	
	//animation
	public float xgoffset=0.0f, ygoffset=0.0f;

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
			return inventory.primary.weapon;
		case SECONDARY:
			return inventory.secondary.weapon;
		default:
			return inventory.primary.weapon;
		}
	}

	public void resetTurnStats(){
		movesLeft = maxMoves;
		turnIsOver = false;
		hasShot = false;
		hasMoved = false;
		isShieled = false;
	}

	public void reset(){
		resetTurnStats();
		inventory.primary.ammo=inventory.primary.ammo;
		inventory.secondary.ammo=inventory.secondary.ammo;
		health=Game.maxHP;
	}

	public void setOrientation(Facing f){
		orientation = f;
	}
}
