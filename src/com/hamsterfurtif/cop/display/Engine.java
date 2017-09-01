package com.hamsterfurtif.cop.display;

import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.hamsterfurtif.cop.Player;
import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.display.poseffects.PosEffect;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.map.MapPos;

public class Engine {
	
	//RealEngine 1.0 Copyright 1970
	
	private static int r = TextureLoader.textureRes;
	
	public static ArrayList<PosEffect> posEffects = new ArrayList<PosEffect>();
	
	public static void displayMap(){
				
		
		String show;
		String xAxe = "";
		for(int i=0; i<Game.map.dimX;i++)
			xAxe+=Utils.alphabet.charAt(i)+" ";
		
		System.out.println("   "+xAxe);
		System.out.println("  "+String.join("", Collections.nCopies(2*Game.map.dimX+2, "-")));
		for(int y=0; y<Game.map.dimY ;y++){
			
			show = "|";
			
			for(int x=0; x<Game.map.dimX; x++){
				MapPos pos = new MapPos(x,y,0);
				if(checkPlayerOnPos(pos) == null)
					show +=  Game.map.getTile(pos).symbol+" ";
				else
					show += checkPlayerOnPos(pos).symbol+" ";
			}
			String k="";
			
			if(y+1<10)
				k=" ";
			
			System.out.println(k+(y+1)+show+"|");
		}
		System.out.println("  "+String.join("", Collections.nCopies(2*Game.map.dimX+2, "-")));

	}
	
	public static void drawMap(Graphics g){
		drawMap(g, 1, 0, 0);
	}
	
	public static void drawMap(Graphics g, float scale, int posX, int posY){		
		for(int y=0; y<Game.map.dimY ;y++){
			for(int x=0; x<Game.map.dimX; x++){
				MapPos pos = new MapPos(x,y,0);
				Image scaledImage = Game.map.getTile(pos).image.getScaledCopy(scale);
				scaledImage.setFilter(Image.FILTER_NEAREST);
				g.drawImage(scaledImage, posX+pos.X*r*scale, posY+pos.Y*r*scale);
			}
		}
	}
	
	public static void drawMapWithPlayers(Graphics g, float scale, int posX, int posY, boolean squares){
		
		for(int y=0; y<Game.map.dimY ;y++){
			for(int x=0; x<Game.map.dimX; x++){
				MapPos pos = new MapPos(x,y,0);
				Image scaledImage = Game.map.getTile(pos).image.getScaledCopy(scale);
				scaledImage.setFilter(Image.FILTER_NEAREST);
				g.drawImage(scaledImage, posX+pos.X*r*scale, posY+pos.Y*r*scale);
				g.setColor(Color.black);
				if(squares)
					g.drawRect(posX+pos.X*r*scale,  posY+pos.Y*r*scale, r*scale, r*scale);
				
				for(PosEffect effect : posEffects){
					if(effect.pos.equals(pos))
						effect.render(g, (int)(posX+pos.X*r*scale), (int)(posY+pos.Y*r*scale));
				}
				
				for(Player player : Game.players){
					if(player.pos != null){
						Image playerSkin = player.skin.getScaledCopy(scale);
						playerSkin.setFilter(Image.FILTER_NEAREST);
						g.drawImage(playerSkin, posX+player.pos.X*r*scale+player.xgoffset, posY+player.pos.Y*r*scale+player.ygoffset);
					}
				}		
			}
		}
	}
	
	private static Player checkPlayerOnPos(MapPos pos){

		for(Player player : Game.players){

			if(player.pos.equals(pos)){
				return player;
			}
		}
		return null;
	}
	
	public static boolean addPosEffect(PosEffect effect){
		for(PosEffect e : posEffects)
			if(e.equals(effect))
				return false;
		posEffects.add(effect);
		return true;
	}
	
	public static void removePosEffect(Class<?> c){
		for (int i = 0; i < posEffects.size(); i++) {
		    if (posEffects.get(i).getClass() == c) {
		        posEffects.remove(i--);
		    }
		}
	}
	 
}
