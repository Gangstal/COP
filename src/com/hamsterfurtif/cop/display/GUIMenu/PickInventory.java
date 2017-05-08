package com.hamsterfurtif.cop.display.GUIMenu;

import java.util.ArrayList;

import com.hamsterfurtif.cop.inventory.Weapon;
import com.hamsterfurtif.cop.statics.Weapons;

public class PickInventory extends GUIMenu{

	public Weapon get(int classe){
		ArrayList<Weapon> weapons = new ArrayList<Weapon>();
		this.choices = new ArrayList<String>();
		
		switch(classe){
		case 2:weapons = Weapons.secondary;
		name = "Secondaire";
			break;
		case 1:weapons = Weapons.primary;
		name="Principale";
			break;
		default:
			name="Principale";
			weapons = Weapons.primary;
		}
				
		for(Weapon weapon : weapons)
			this.choices.add(weapon.name +" (R:"+weapon.range+" /D:"+weapon.damage+" /A:"+weapon.ammo+")");
		
		int c = this.showMenu()-1;

		return weapons.get(c);
			
		
		
	}
	
}
