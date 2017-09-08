package com.hamsterfurtif.cop.gamestates;

import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.hamsterfurtif.cop.display.menu.Menu;

public abstract class GameStateMenu extends BasicGameState{
	public 	Menu currentMenu;
	public  StateBasedGame game;
}
