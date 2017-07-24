package com.hamsterfurtif.cop.display.GUIMenu;

import java.util.ArrayList;
import java.util.Arrays;

import com.hamsterfurtif.cop.Game;
import com.hamsterfurtif.cop.Player;
import com.hamsterfurtif.cop.statics.Menus;
import com.hamsterfurtif.cop.statics.Weapons;

public class PlayerEquip extends GUIMenu{
	

	public boolean get(Player player){
		
		this.name = "Equipement des joueurs: " + player.name;
		String princ = "Aucune actuellement";
		String sec   = "Aucune actuellement";
		if(player.inventory.primary != null)
			princ = player.inventory.primary.name;
		if(player.inventory.secondary != null)
			sec = player.inventory.secondary.name;
		String[] names = {princ, sec};
		
		Button primary = new Button("Arme principale ("+princ+")"){
			public Object trigger(Object args){
				player.inventory.primary = Menus.pickInventory.get(1);
				player.inventory.ammoP = player.inventory.primary.ammo;
				return false;
			}
		};
		
		Button secondary = new Button("Arme secondaire ("+sec+")"){
			public Object trigger(Object args){
				player.inventory.secondary = Menus.pickInventory.get(2);
				player.inventory.ammoS = player.inventory.secondary.ammo;
				return false;
			}
		};
		
		Button confirm = new Button("Confirmer"){
			public Object trigger(Object args){
				return true;
			}
		};
		
		
		
		
		this.choices = new ArrayList<Button>(Arrays.asList(primary, secondary, confirm));
		
		if(Game.test){
			player.inventory.primary = Weapons.AR;
			player.inventory.secondary = Weapons.handgun;
			player.inventory.ammoP = player.inventory.primary.ammo;
			player.inventory.ammoS = player.inventory.secondary.ammo;
			return true;
		}
		else{
			int c = this.showMenu();
			if(c-1>=choices.size())
				return false;
			else
				return (boolean)choices.get(c-1).trigger(names);
		}
	
	}
}
