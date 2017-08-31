package com.hamsterfurtif.cop.display.menu;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Utils.TextPlacement;
import com.hamsterfurtif.cop.gamestates.GSPlayerEquip;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;
import com.hamsterfurtif.cop.map.Map;
import com.hamsterfurtif.cop.map.MapReader;

public class PickMap extends Menu{
	
	public Button confirmer = new Button("Confirmer", this, width/2, height-80, 100, 30).centered();
	public Button quitter = new Button("Quitter", this, width/2, height-40, 100, 30).centered();

	
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

		choices.add(quitter);
	
}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(source==confirmer){
			GSPlayerEquip g = (GSPlayerEquip)COP.instance.getState(1);
			try {
				g.mainMenu = new PlayerEquip(g.container, g, Game.players.get(0));
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			COP.instance.enterState(1);
		}
		else if(source == quitter){
			try {
				this.state.currentMenu = new Main(container, state);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(choices.contains(source)){
			Button button = (Button)source;
			Game.map = new Map(MapReader.read("assets\\maps\\"+button.name+".txt"));
		}
	}
	
}