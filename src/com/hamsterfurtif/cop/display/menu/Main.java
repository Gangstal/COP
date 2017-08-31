package com.hamsterfurtif.cop.display.menu;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;

public class Main extends Menu{



	private Button play = new Button("Jouer", this, COP.width/2, COP.height/2, 100, 30).centered();
	public Button quit = new Button("Quitter", this, width/2, height-40, 100, 30).centered();
	
	public Main(GameContainer container, GameStateMenu state) throws SlickException {
		super(container, "Call Of Paper", state);
		this.choices = new ArrayList<Button>(Arrays.asList(play,quit));
		this.titleX=width/2-50;
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		if(source==play){
			try {
				this.state.currentMenu = new PlayerAmount(container, state);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(source==quit){
			container.exit();
		}
				
	}

}
