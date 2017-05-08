package com.hamsterfurtif.cop;

import com.hamsterfurtif.cop.inventory.Inventory;
import com.hamsterfurtif.cop.map.MapPos;

public class Player {
	public String name;
	public MapPos pos = new MapPos();
	public int health = 10;
	public Inventory inventory = new Inventory();
	
	public Player(String name){
		this.name = name;
	}
}
