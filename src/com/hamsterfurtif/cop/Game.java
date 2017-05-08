package com.hamsterfurtif.cop;

import java.util.ArrayList;
import java.util.Random;

import com.hamsterfurtif.cop.map.Map;
import com.hamsterfurtif.cop.map.MapPos;
import com.hamsterfurtif.cop.statics.Menus;

public class Game {

	public static boolean running = false;
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static int currentPlayer=0;
	public static Map map;
	
	public void init(){
		players.add(new Player("Joueur 1"));
		players.add(new Player("Joueur 2"));
	}
	
	public void start(){
		
		running = true;
		while(running){
			if(Menus.main.get())
				setUpGame();
		}
	}
	
	public void setUpGame(){
		Menus.pickMap.get();
		for(Player player : players)
			while(!Menus.playerEquip.get(player));
	}

	//TODO Créer dans les fichier de map une selection de points de spawn
	public void setPlayerInitialSpawn(){
		for(Player player : players){
			Random rd = new Random();
			MapPos pos = new MapPos();
			
			do{
				 pos.set(rd.nextInt(map.dimX),rd.nextInt(map.dimY),0);

			}while(!map.getTile(pos).canWalkThrough);
			
			player.pos=pos;
		}
		
	}
}
