package com.hamsterfurtif.cop.inventory;

import com.hamsterfurtif.cop.statics.Weapons;

public class Slot {

	
	public Weapon weapon;
	public int ammo;
	
	public Slot(Weapon w){
		weapon = w;
		ammo = w.ammo;
	}

	public void reload(){
		if(weapon==Weapons.shotgun)
			ammo++;
		else
			ammo = weapon.ammo;
	}
}
