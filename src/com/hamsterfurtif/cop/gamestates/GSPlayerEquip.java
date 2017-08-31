package com.hamsterfurtif.cop.gamestates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.display.menu.ConfirmGameSettings;
import com.hamsterfurtif.cop.display.menu.Menu;
import com.hamsterfurtif.cop.display.menu.PlayerEquip;

public class GSPlayerEquip extends GameStateMenu{

	public static final int ID = 1;
	
	int currentPlayer;
	private GameContainer container;
	public Menu mainMenu;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		currentPlayer=0;
		this.game=game;
		//mainMenu = new PlayerEquip(container, this, Game.players.get(0));
		currentMenu= mainMenu;
		this.container = container;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(COP.background, 0, 0);
		if(mainMenu != null)
			mainMenu.render(g);
		if(currentMenu != null)
			currentMenu.render(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(mainMenu != null)
			mainMenu.update();
	}

	@Override
	public int getID() {
		return ID;
	}
	
	public void nextPlayer() throws SlickException{
		currentPlayer++;
		if(currentPlayer<Game.players.size()){
			mainMenu = new PlayerEquip(container, this, Game.players.get(currentPlayer));
			currentMenu=mainMenu;
		}
		else{
			mainMenu = new ConfirmGameSettings(container, this);
			currentMenu=null;
		}
	}

}
