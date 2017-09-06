package com.hamsterfurtif.cop.gamestates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.display.Engine;
import com.hamsterfurtif.cop.display.menu.Main;
import com.hamsterfurtif.cop.display.menu.PickMap;

public class GSMainMenu extends GameStateMenu {

	  public static final int ID = 0;

	StateBasedGame game;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		currentMenu = new Main(container, this);
		Game.map = null;
		this.game=game;
		if(COP.music){
			Sound sound = new Sound("\\assets\\sounds\\main_theme.wav");
			sound.play();
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame arg1, Graphics g) throws SlickException {
		g.drawImage(COP.background, 0, 0);
		currentMenu.render(g);
		if(currentMenu instanceof PickMap){
			g.setColor(Color.orange);
			g.fillRect(COP.width/2-5, COP.height/3-5, 21*16+10, 12*16+10);
			if(Game.map != null)
				Engine.drawMap(g, 1f, COP.width/2, COP.height/3, false, Game.map);

		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		COP.updatePackets();
		if(currentMenu instanceof PickMap){
			PickMap menu = (PickMap)currentMenu;
			if(!menu.choices.contains(menu.confirmer) && Game.map!=null)
				menu.choices.add(menu.choices.size()-1, menu.confirmer);
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
