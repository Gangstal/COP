package com.hamsterfurtif.cop.inventory;

import com.hamsterfurtif.cop.statics.Weapons;


public class Weapon {
	
	
	
	public String name;
	public int range, damage, ammo;
	
	public Weapon(String name, int range, int damage, int maxAmmo, WeaponType type){
		this.name = name;
		this.range = range;
		this.damage = damage;
		this.ammo = maxAmmo;
		
		switch(type){
		case PRIMARY:
			Weapons.primary.add(this);
			break;
		case SECONDARY:
			Weapons.secondary.add(this);
			break;
		}
	}

}
