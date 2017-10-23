package com.hamsterfurtif.cop.inventory.slot;

import com.hamsterfurtif.cop.entities.EntityCharacter;
import com.hamsterfurtif.cop.inventory.Weapon;
import com.hamsterfurtif.cop.map.MapPos;

public class SlotRPG extends Slot {

	public int total_ammo = 2;
	
	public SlotRPG(Weapon w) {
		super(w);
	}
	
	public void reload(){
		if(total_ammo > 0){
			total_ammo--;
			ammo = 1;
		}
	}
	
	public boolean shoot(EntityCharacter character, MapPos pos){
		if(weapon.inRange(character.pos, pos) && ammo > 0)
			return weapon.shoot(character, pos);
		return false;
	}

}
