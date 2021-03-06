package com.hamsterfurtif.cop.display.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.gamestates.GSMainMenu;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;

public class ServerRefused extends BackMenu {
	public final String reason;

	public ServerRefused(GameContainer container, GameStateMenu state, String reason) throws SlickException {
		super(container, "Impossible de se connecter au serveur", state);
		this.reason = reason;
	}


	public void render(Graphics g) {
		super.render(g);
		String[] lines = new String[] { "Le serveur a refus� la connexion pour le motif suivant:", reason };
		int linesY = (COP.height - lines.length * g.getFont().getLineHeight()) / 2;
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			int w = g.getFont().getWidth(line);
			g.drawString(line, (COP.width - w) / 2, linesY + i * g.getFont().getLineHeight());
		}
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
