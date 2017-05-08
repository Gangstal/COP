package com.hamsterfurtif.cop.inventory;

import com.hamsterfurtif.cop.statics.Weapons;


public class Weapon {
	
	public String name;
	public int range, damage, ammo;
	
	public Weapon(String name, int range, int damage, int maxAmmo, int slot){
		this.name = name;
		this.range = range;
		this.damage = damage;
		this.ammo = maxAmmo;
		
		if(slot==1)
			Weapons.primary.add(this);
		else
			Weapons.secondary.add(this);
	}

}
