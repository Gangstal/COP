package com.hamsterfurtif.cop.display;

import java.util.Collections;

import com.hamsterfurtif.cop.Game;

public class Engine {
	
	
	public static void displayMap(){
		
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String show;
		System.out.println("   "+alphabet.substring(0, Game.map[0].length));
		System.out.println("  "+String.join("", Collections.nCopies(Game.map[0].length+2, "-")));
		for(int y=0; y<Game.map.length ;y++){
			
			show = "|";
			
			for(int x=0; x<Game.map[y].length; x++){
				show += Game.map[y][x].symbol;
			}
			String k="";
			
			if(y+1<10)
				k=" ";
			
			System.out.println(k+(y+1)+show+"|");
		}
		System.out.println("  "+String.join("", Collections.nCopies(Game.map[0].length+2, "-")));

	}
	
}
