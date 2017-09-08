package com.hamsterfurtif.cop.display.menu;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;

public class Connecting extends BackMenu {

	public Connecting(GameContainer container, GameStateMenu state) throws SlickException {
		super(container, "Connexion", state);
	}

	public void render(Graphics g) {
		super.render(g);
		String line;
		switch (COP.connState) {
		case CONNECTING:
			line = "Connexion au serveur, veuillez patienter...";
			break;
		case FAILED:
			line = "Impossible de se connecter au serveur.";
			break;
		default:
			line = "";
		}
		int w = g.getFont().getWidth(line), h = g.getFont().getLineHeight();
		g.drawString(line, (COP.width - w) / 2, (COP.height - h) / 2);
	}

	public void componentActivated(AbstractComponent source) {
		if (source == back) {
			COP.serverSockShuttingDown = true;
			if (!COP.sockToServ.isClosed()) {
				try {
					COP.sockToServ.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
