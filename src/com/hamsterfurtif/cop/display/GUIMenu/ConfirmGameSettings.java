package com.hamsterfurtif.cop.display.GUIMenu;

import java.util.ArrayList;
import java.util.Arrays;

import com.hamsterfurtif.cop.Game;
import com.hamsterfurtif.cop.Player;

public class ConfirmGameSettings extends GUIMenu{
	
	private static ChangeHP menuChangeHP = new ChangeHP();
	private static ChangeSpawns menuChangeSpawns = new ChangeSpawns();
	
	private Button hp = new Button("Points de vie: "+ Game.maxHP){
		
		public Object trigger(Object args){
			menuChangeHP.get();
			return null;
		}
	};
	
	private Button respawn = new Button("Respawns disponibles: "+ Game.maxSpawn){
		
		public Object trigger(Object args){
			menuChangeSpawns.get();
			return null;
		}
	};
	
	private Button confirm = new Button("Confirmer"){
		public Object trigger(Object args){
			for(Player player : Game.players){
				player.health = Game.maxHP;
				player.repsawnsLeft = Game.maxSpawn;
			}
			return true;
		}
	};
	
	public boolean get(){
		this.name = "Confirmer les paramètres de jeu ?";
		this.choices = new ArrayList<Button>(Arrays.asList(hp, respawn, confirm));

		int c = this.showMenu();
				
		if(c-1>=choices.size())
			return false;
		else
			return (boolean)choices.get(c-1).trigger(null);
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
