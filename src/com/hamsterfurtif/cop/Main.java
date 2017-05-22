package com.hamsterfurtif.cop;

import com.hamsterfurtif.cop.display.Engine;
import com.hamsterfurtif.cop.map.Map;
import com.hamsterfurtif.cop.map.MapReader;

public class Main {
	
	public static Game game;
	
	public static void main(String[] args){

		
		game = new Game();
		Game.map = new Map(MapReader.read("C:\\Users\\Jean-Baptiste\\workspace\\COP\\assets\\map_test.txt"));
		Engine.displayMap();
		game.init();
		game.start();
		
	}

}
