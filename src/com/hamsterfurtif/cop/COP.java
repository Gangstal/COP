package com.hamsterfurtif.cop;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.gamestates.GSGame;
import com.hamsterfurtif.cop.gamestates.GSMainMenu;
import com.hamsterfurtif.cop.gamestates.GSPlayerEquip;

public class COP extends StateBasedGame{
	
	public static Game game;
	public static int width = 1008, height = 600;
	public static COP instance = new COP();
	private final static String version = "Pre-Alpha -1.0";
	public static Image background;
	
	
	public static void main(String[] args) throws SlickException, FontFormatException, IOException{
		
		game = new Game();
		game.init();
        AppGameContainer app = new AppGameContainer(instance, width, height, false);
        app.setShowFPS(false);
        app.start();
        	
	}
	
	public COP() {
		super("Call of Paper "+version);
		// TODO Auto-generated constructor stub
	}

	public void initStatesList(GameContainer game) throws SlickException {
		try {
			TextureLoader.load();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		addState(new GSMainMenu());
		addState(new GSPlayerEquip());
		addState(new GSGame());
		
	}
}
