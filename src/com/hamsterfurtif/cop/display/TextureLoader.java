package com.hamsterfurtif.cop.display;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Player;
import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.display.menu.Button;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.map.tiles.Tile;
import com.hamsterfurtif.cop.statics.Tiles;

public abstract class TextureLoader {
	
	public static int textureRes = 16;

	public static void load() throws FileNotFoundException, SlickException{
		loadTilesTextures();
		loadMiscTextures();
		loadPlayerSkins();
	}
	
	public static void loadTilesTextures(){
		for(Tile tile : Tiles.tiles){
			try {
				tile.image=new Image("assets\\textures\\tiles\\"+tile.imagename+".png");
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void loadMiscTextures(){
		try {
			Button.default_image = new Image("assets\\textures\\GUI\\menu_square.png");
			COP.background = TextureLoader.loadTexture("GUI\\main_wallpaper.png");
			Game.health_end_empty = TextureLoader.loadTexture("GUI\\health_end_empty.png");
			Game.health_end_full = TextureLoader.loadTexture("GUI\\health_end_full.png");
			Game.health_middle_empty = TextureLoader.loadTexture("GUI\\health_middle_empty.png");
			Game.health_middle_full = TextureLoader.loadTexture("GUI\\health_middle_full.png");
			Game.heart= TextureLoader.loadTexture("GUI\\heart.png");


			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadPlayerSkins() throws FileNotFoundException, SlickException{
		File folder = new File("assets\\textures\\sprites\\players\\");
		Player.skins = new ArrayList<Image>();
		
		for (File fileEntry : folder.listFiles()) 
		      if (!fileEntry.isDirectory() && Utils.getFileExtension(fileEntry).equals("png"))
		    	  Player.skins.add(new Image("assets\\textures\\sprites\\players\\"+fileEntry.getName()));
	}
	
	public static Image loadTexture(String location, int filter){
		try {
			Image i = new Image("assets\\textures\\"+location);
			i.setFilter(filter);
			return i;
		}
		catch (SlickException e) {
			e.printStackTrace();
			return Button.default_image;
		}
	}
	
	public static Image loadTexture(String location){
		return loadTexture(location, Image.FILTER_NEAREST);
	}
	
}
