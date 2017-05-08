package com.hamsterfurtif.cop.display;

import java.util.Collections;

import com.hamsterfurtif.cop.Game;
import com.hamsterfurtif.cop.map.MapPos;

public class Engine {
	
	
	public static void displayMap(){
		
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String show;
		System.out.println("   "+alphabet.substring(0, Game.map.dimX));
		System.out.println("  "+String.join("", Collections.nCopies(Game.map.dimX+2, "-")));
		for(int y=0; y<Game.map.dimY ;y++){
			
			show = "|";
			
			for(int x=0; x<Game.map.dimX; x++){
				show += Game.map.getTile(new MapPos(y,x,0)).symbol;
			}
			String k="";
			
			if(y+1<10)
				k=" ";
			
			System.out.println(k+(y+1)+show+"|");
		}
		System.out.println("  "+String.join("", Collections.nCopies(Game.map.dimX+2, "-")));

	}
	
}
