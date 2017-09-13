package com.hamsterfurtif.cop.display;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.display.menu.Button;
import com.hamsterfurtif.cop.entities.EntityCharacter;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.map.tiles.Tile;
import com.hamsterfurtif.cop.statics.Tiles;

public abstract class TextureLoader {

	public static int size = 16;

	public static void load() throws FileNotFoundException, SlickException{
		loadTilesTextures();
		loadMiscTextures();
		loadPlayerSkins();
	}

	public static void loadTilesTextures(){
		for(Tile tile : Tiles.tiles)
			tile.image=loadTexture("tiles\\"+tile.imagename+".png");
	}

	private static void loadMiscTextures(){
		Button.default_image = loadTexture("GUI\\menu_square.png", Image.FILTER_LINEAR);
		COP.background = loadTexture("GUI\\main_wallpaper.png");
		Game.health_end_empty = loadTexture("GUI\\health_end_empty.png");
		Game.health_end_full = loadTexture("GUI\\health_end_full.png");
		Game.health_middle_empty = loadTexture("GUI\\health_middle_empty.png");
		Game.health_middle_full = loadTexture("GUI\\health_middle_full.png");
		Game.heart= loadTexture("GUI\\heart.png");
	}

	public static void loadPlayerSkins() throws FileNotFoundException, SlickException{
		File folder = new File("assets\\textures\\sprites\\players\\");
		EntityCharacter.skins = new ArrayList<Image>();

		for (File fileEntry : folder.listFiles()) {
		      if (!fileEntry.isDirectory() && Utils.getFileExtension(fileEntry).equals("png")) {
		    	  Image skin = loadTexture("sprites\\players\\"+fileEntry.getName());
		    	  skin.setCenterOfRotation(skin.getWidth()/2, skin.getHeight()/2);
		    	  EntityCharacter.skins.add(skin);
		      }
		}
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

	public static Image getRotatedCopy(Image i, float angleDeg) {
	    Image rotated = i.copy();
	    rotated.setRotation(angleDeg);

	    return rotated;
	}
}
