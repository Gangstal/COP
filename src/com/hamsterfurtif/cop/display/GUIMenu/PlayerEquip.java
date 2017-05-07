package com.hamsterfurtif.cop.display.GUIMenu;

import java.util.ArrayList;
import java.util.Arrays;

import com.hamsterfurtif.cop.Game;
import com.hamsterfurtif.cop.Player;

public class PlayerEquip extends GUIMenu{

	public void get(Player player){
		this.name = "Equipement des joueurs";
		
		String princ = "Aucune actuellement";
		String sec   = "Aucune actuellement";
		
		if(player.inventory.primary != null)
			princ = player.inventory.primary.name;
		if(player.inventory.secondary != null)
			sec = player.inventory.secondary.name;
		
		this.choices = new ArrayList<String>(Arrays.asList("Arme principale ("+princ+")", "Arme secondaire ("+sec+")", "Confirmer", "Quitter"));
		int c = this.showMenu();
		
		switch(c){
		case 1:
			
			break;
		
		case 2:
			
			break;
			
		case 3:
			Game.running = false;
			break;
		}
	}
	
}
