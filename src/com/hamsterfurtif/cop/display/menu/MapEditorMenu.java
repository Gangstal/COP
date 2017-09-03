package com.hamsterfurtif.cop.display.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.gamestates.GameStateMenu;

public class MapEditorMenu extends Menu{
	
	private boolean newMapSelected;
	
	private Button newMap = new Button("Nouveau", this, 50, 100, 250, 50);
	private TextInput dimensionX = new TextInput(this, 550, 100, width, height);

	public MapEditorMenu(GameContainer container, GameStateMenu state) throws SlickException {
		super(container, "Choisir un niveau", state);
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		
	}
	
	

}
