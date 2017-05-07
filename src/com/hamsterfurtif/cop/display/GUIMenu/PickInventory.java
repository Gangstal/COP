package com.hamsterfurtif.cop.display.GUIMenu;

import java.util.ArrayList;

import com.hamsterfurtif.cop.inventory.Weapon;

public class PickInventory extends GUIMenu{

	public void get(int classe){
		ArrayList<Weapon> weapons;
		
		if(classe == 2)
			weapons = Weapon.secondary;
		else
			weapons = Weapon.primary;
		
	}
	
}
