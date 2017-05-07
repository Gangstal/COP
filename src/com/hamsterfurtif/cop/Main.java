package com.hamsterfurtif.cop;

import com.hamsterfurtif.cop.map.ReadMap;

public class Main {
	
	public static Game game;
	
	public static void main(String[] args){

		
		game = new Game();
		Game.map = ReadMap.read("C:\\Users\\Jean-Baptiste\\workspace\\COP\\assets\\map_test.txt");
		//Engine.displayMap();
		game.init();
		game.start();
		
	}

}
