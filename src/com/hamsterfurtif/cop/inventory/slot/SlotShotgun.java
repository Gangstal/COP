package com.hamsterfurtif.cop.inventory.slot;

import com.hamsterfurtif.cop.inventory.Weapon;

public class SlotShotgun extends Slot{

	public SlotShotgun(Weapon w) {
		super(w);
	}
	
	public void reload(){
		ammo++;
	}

}
