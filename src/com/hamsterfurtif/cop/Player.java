package com.hamsterfurtif.cop;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import com.hamsterfurtif.cop.inventory.Inventory;
import com.hamsterfurtif.cop.inventory.Weapon;
import com.hamsterfurtif.cop.inventory.WeaponType;
import com.hamsterfurtif.cop.map.MapPos;

public class Player {
	
	public static String defSym = "@#&$¤£%";
	public static ArrayList<Image> skins;
	
	public String name;
	public MapPos pos = new MapPos();
	public int health = 10;
	public Inventory inventory = new Inventory();
	public int repsawnsLeft = 5;
	public char symbol;
	public boolean turnIsOver = false;
	public int maxMoves = 8;
	public int movesLeft = maxMoves;
	
	//animation
	public int xgoffset=0, ygoffset=0;

	public boolean hasShot, hasMoved;
	public Image skin;
	
	public Player(String name){
		this.name = name;
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
		this.turnIsOver=false;
		this.hasShot=false;
		this.hasMoved=false;
	}
}
