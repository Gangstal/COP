package com.hamsterfurtif.cop.display.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;

public class WaitingForPlayers extends Menu {

	public WaitingForPlayers(GameContainer container, GameStateMenu state) throws SlickException {
		super(container, "Attente des joueurs", state);
	}

	public void render(Graphics g) {
		g.drawString(name, (COP.width - g.getFont().getWidth(name)) / 2, 40);
		for(Button button : choices)
			button.render(g);
		String line = "Joueurs prêts: " + COP.playersReady + " / " + Game.playersCount;
		int w = g.getFont().getWidth(line), h = g.getFont().getLineHeight();
		g.drawString(line, (COP.width - w) / 2, (COP.height - h) / 2);
	}

	public void componentActivated(AbstractComponent arg0) {
	}
}
