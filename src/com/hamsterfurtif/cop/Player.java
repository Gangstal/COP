package com.hamsterfurtif.cop;

import com.hamsterfurtif.cop.inventory.Inventory;
import com.hamsterfurtif.cop.map.MapPos;

public class Player {
	
	public static String defSym = "@#&$¤£%";
	
	public String name;
	public MapPos pos = new MapPos();
	public int health = 10;
	public Inventory inventory = new Inventory();
	public int repsawnsLeft = 5;
	public char symbol;
	
	public Player(String name){
		this.name = name;
	}
}
