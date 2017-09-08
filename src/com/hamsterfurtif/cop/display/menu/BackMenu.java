package com.hamsterfurtif.cop.display.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;

public abstract class BackMenu extends Menu {
	public final Button back;

	public BackMenu(GameContainer container, String name, GameStateMenu state) throws SlickException {
		super(container, name, state);
		choices.add(back = new Button("Retour", this, width/2, height-80, 100, 30).centered());
	}

	public void render(Graphics g) {
		g.drawString(name, (COP.width - g.getFont().getWidth(name)) / 2, 40);
		for(Button button : choices)
			button.render(g);
	}
}
