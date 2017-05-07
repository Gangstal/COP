package com.hamsterfurtif.cop.inventory;

import java.util.ArrayList;


public class Weapon {
	
	public String name;
	public int range, damage, ammo;
	
	public static ArrayList<Weapon> primary;
	public static ArrayList<Weapon> secondary;
	
	public Weapon(String name, int range, int damage, int maxAmmo, int slot){
		this.name = name;
		this.range = range;
		this.damage = damage;
		this.ammo = maxAmmo;
		
		if(slot==1)
			primary.add(this);
		else
			secondary.add(this);
	}

}
