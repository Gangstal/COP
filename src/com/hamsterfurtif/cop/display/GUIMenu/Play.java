package com.hamsterfurtif.cop.display.GUIMenu;

import java.util.ArrayList;
import java.util.Arrays;

import com.hamsterfurtif.cop.Game;
import com.hamsterfurtif.cop.Player;
import com.hamsterfurtif.cop.statics.Menus;

public class Play extends GUIMenu {
	
	public boolean get(Player player){
		
		this.name="Tour de "+player.name+"\r"+getPlayerInfo(player);
		
		if(player.movesLeft>0)
			this.choices = new ArrayList<String>(Arrays.asList("Bouger","Tirer","Fin du tour"));
		else
			this.choices = new ArrayList<String>(Arrays.asList("Tirer","Fin du tour"));

		int c = this.showMenu();
		c += player.movesLeft>0 ? 0 : 1;
		switch(c){
		case 1:
			while(!Game.movePlayer(player, Menus.move.get()))
			return false;
		case 2:
			return false;
		case 3:
			return true;
		default:
			return false;
		}
	}
	
	private static String getPlayerInfo(Player player){
		String str="PV:"+player.health+"/"+Game.maxHP+"\r"
				  +"Respawns:"+player.repsawnsLeft+"/"+Game.maxSpawn+"\r"
				  +player.inventory.primary.name+":"+player.inventory.ammoP+"/"+player.inventory.primary.ammo+"\r"
				  +player.inventory.secondary.name+":"+player.inventory.ammoS+"/"+player.inventory.secondary.ammo+"\r"
				  +player.movesLeft+" déplacements restants";
		return str;
	}
	

}
