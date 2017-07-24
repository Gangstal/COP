package com.hamsterfurtif.cop.display.GUIMenu;

import java.util.ArrayList;

import com.hamsterfurtif.cop.inventory.Weapon;
import com.hamsterfurtif.cop.statics.Weapons;

public class PickInventory extends GUIMenu{

	public Weapon get(int classe){
		ArrayList<Weapon> weapons = new ArrayList<Weapon>();
		this.choices = new ArrayList<Button>();
		
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
			choices.add(new Button(weapon.name +" (R:"+weapon.range+" /D:"+weapon.damage+" /A:"+weapon.ammo+")", weapon){
				public Object trigger(Object args){
					return params;
				}
		});
		
		int c = this.showMenu();
		if(c-1>=choices.size())
			return null;
		else
			return (Weapon)choices.get(c-1).trigger(null);
			
		
		
	}
	
}
