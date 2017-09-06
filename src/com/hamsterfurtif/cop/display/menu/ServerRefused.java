package com.hamsterfurtif.cop.display.menu;

import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;

public class ServerRefused extends Menu {
	public final String reason;

	public Button quit = new Button("Quitter", this, COP.width-168, 550, 168, 50);

	public ServerRefused(GameContainer container, GameStateMenu state, String reason) throws SlickException {
		super(container, "Impossible de se connecter au serveur", state);
		choices = Arrays.asList(quit);
		this.reason = reason;
	}

	public void render(Graphics g) {
		g.drawString(name, (COP.width - g.getFont().getWidth(name)) / 2, 40);
		for(Button button : choices)
			button.render(g);
		String line = "Le serveur a refuser la connexion pour le motif: " + reason;
		int w = g.getFont().getWidth(line), h = g.getFont().getLineHeight();
		g.drawString(line, (COP.width - w) / 2, (COP.height - h) / 2);
	}

	public void componentActivated(AbstractComponent arg0) {
	}
}
