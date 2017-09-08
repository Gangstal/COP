package com.hamsterfurtif.cop.display.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.gamestates.GSMainMenu;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;

public class ConnectionLost extends BackMenu {

	public ConnectionLost(GameContainer container, GameStateMenu state) throws SlickException {
		super(container, "Connexion perdu", state);
	}


	public void render(Graphics g) {
		super.render(g);
		String line = "Connexion au serveur perdu";
		int w = g.getFont().getWidth(line), h = g.getFont().getLineHeight();
		g.drawString(line, (COP.width - w) / 2, (COP.height - h) / 2);
	}

	public void componentActivated(AbstractComponent source) {
		if (source == back) {
			try {
				GSMainMenu state = (GSMainMenu) COP.instance.getState(0);
				state.currentMenu = new Main(container, state);
				COP.instance.enterState(0);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
}
