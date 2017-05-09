package com.hamsterfurtif.cop.display.GUIMenu;

import java.util.ArrayList;
import java.util.Arrays;

import com.hamsterfurtif.cop.Game;
import com.hamsterfurtif.cop.Player;

public class ConfirmGameSettings extends GUIMenu{
	
	private static ChangeHP menuChangeHP = new ChangeHP();
	private static ChangeSpawns menuChangeSpawns = new ChangeSpawns();
	
	public boolean get(){
		this.name = "Confirmer les paramètres de jeu ?";
		this.choices = new ArrayList<String>(Arrays.asList("Points de vie: "+ Game.maxHP, "Respawns disponibles: "+ Game.maxSpawn, "Confirmer"));
		int c = this.showMenu();
		
		switch(c){
		case 1:
			menuChangeHP.get();
			break;
		case 2:
			menuChangeSpawns.get();
			break;
		case 3:
			for(Player player : Game.players){
				player.health = Game.maxHP;
				player.repsawnsLeft = Game.maxSpawn;
				return true;
			}
		}
		return false;
	}
	
	private static class ChangeHP extends GUIMenu{
		private  void get(){
			this.name="Combien de PV ?";
			int c = this.showMenu();
			Game.maxHP=c;
		}
	}

	private static class ChangeSpawns extends GUIMenu{
		private  void get(){
			this.name="Combien de respawns ?";
			int c = this.showMenu();
			Game.maxSpawn=c;
		}
	}
}
