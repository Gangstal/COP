package com.hamsterfurtif.cop.inventory.slot;

import com.hamsterfurtif.cop.entities.EntityCharacter;
import com.hamsterfurtif.cop.inventory.Weapon;
import com.hamsterfurtif.cop.map.MapPos;

public class Slot {

	
	public Weapon weapon;
	public int ammo;
	
	public Slot(Weapon w){
		weapon = w;
		ammo = w.ammo;
	}

	public void reload(){
			ammo = weapon.ammo;
	}
	
	public boolean shoot(EntityCharacter character, MapPos pos){
		if(this.ammo > 0 && weapon.shoot(character, pos)){
				ammo--;
				return true;
		}
		return false;
	}
}
