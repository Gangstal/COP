package com.hamsterfurtif.cop.display.menu;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Utils.TextPlacement;
import com.hamsterfurtif.cop.gamestates.GSCharacterEquip;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;
import com.hamsterfurtif.cop.map.MapReader;

public class PickMap extends Menu{

	public Button confirmer = new Button("Confirmer", this, width/2, height-80, 100, 30).centered();
	public Button quit = new Button("Quitter", this, COP.width-168, 550, 168, 50);


	public PickMap(GameContainer container, GameStateMenu state) throws SlickException {
		super(container, "Choisir une map", state);
		titleX = this.width/2-60;

		choices.clear();

		ArrayList<String> mapList = MapReader.scanMapFolder("assets\\maps\\");
		int offset = 0;
		for(String map : mapList){
			choices.add(new Button(map.substring(0, map.length()-4), this, width/4, offset+height/6, 250, 40).setTextPlacement(TextPlacement.LEFT).centered());
			offset+=50;
		}

		choices.add(quit);

	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(source==confirmer){
			GSCharacterEquip g = (GSCharacterEquip)COP.instance.getState(1);
			try {
				g.mainMenu = new ConfirmGameSettings(g.container, g);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			COP.instance.enterState(1);
		}
		else if(source == quit){
			try {
				this.state.currentMenu = new Main(container, state);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(choices.contains(source)){
			Button button = (Button)source;
			try {
				Game.map = MapReader.readMap("assets\\maps\\"+button.name+".txt");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}