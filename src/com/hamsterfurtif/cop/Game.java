package com.hamsterfurtif.cop;

import java.util.ArrayList;

import com.hamsterfurtif.cop.map.Tile;
import com.hamsterfurtif.cop.statics.Menus;

public class Game {

	public static boolean running = false;
	public static Tile[][] map = new Tile[21][12];
	public static ArrayList<Player> players;
	public static int currentPlayer=0;
	
	public void init(){
		
	}
	
	public void start(){
		
		running = true;
		while(running){
			Menus.main.get();
		}
	}
	
	public void setUpGame(){
		Menus.pickMap.get();
		for(Player player : players)
			Menus.playerEquip.get(player);
	}

}
