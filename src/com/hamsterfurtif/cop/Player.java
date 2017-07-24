package com.hamsterfurtif.cop;

import com.hamsterfurtif.cop.inventory.Inventory;
import com.hamsterfurtif.cop.inventory.Weapon;
import com.hamsterfurtif.cop.inventory.WeaponType;
import com.hamsterfurtif.cop.map.MapPos;

public class Player {
	
	public static String defSym = "@#&$¤£%";
	
	public String name;
	public MapPos pos = new MapPos();
	public int health = 10;
	public Inventory inventory = new Inventory();
	public int repsawnsLeft = 5;
	public char symbol;
	public int movesLeft = 5;
	public boolean turnIsOver = false;
	public int maxMoves = 5;
	public boolean hasShot, hasMoved;
	
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
