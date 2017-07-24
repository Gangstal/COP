package com.hamsterfurtif.cop.display.GUIMenu;

import com.hamsterfurtif.cop.Game;
import com.hamsterfurtif.cop.Player;
import com.hamsterfurtif.cop.inventory.WeaponType;
import com.hamsterfurtif.cop.statics.Menus;

public class Play extends GUIMenu {
	
	private Button move = new Button("Bouger"){
		public Object trigger(Object args){
			Player player=(Player)args;
			while(!Game.movePlayer(player, Menus.move.get()));
			player.hasMoved = true;
			return false;
		}
	};
	
	private Button shoot = new Button("Tirer"){
		public Object trigger(Object args){
			Player player=(Player)args;
			while(!Game.shoot(player, Menus.shoot.get(), WeaponType.PRIMARY));
			player.hasShot=true;
			return false;
		}
	};
	
	private Button endTurn = new Button("Fin du tour"){
		public Object trigger(Object args){
			return true;
		}
	};	
	
	public boolean get(Player player){
		
		this.name="Tour de "+player.name+"\r"+getPlayerInfo(player);
		
		choices.clear();;
		
		
		
		if(player.movesLeft>0 && !player.hasMoved)
			this.choices.add(move);
		if(!player.hasShot)
			this.choices.add(shoot);
		
		this.choices.add(endTurn);
			

		int c = this.showMenu();
		
		
		
		if(c-1>=choices.size())
			return false;
		else
			return (boolean)choices.get(c-1).trigger(player);
		

		
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
