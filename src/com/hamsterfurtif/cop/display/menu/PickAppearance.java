package com.hamsterfurtif.cop.display.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.entities.EntityCharacter;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;

public class PickAppearance extends Menu{

	public PickAppearance(GameContainer container, GameStateMenu state, EntityCharacter character) throws SlickException {
		super(container, "Choisir son apparence", state);
		width= COP.width/2;
		height=COP.height;
		titleX = width/2-120;
		titleY = 40;
		x=width;
		y=0;
		
		int xoffset = 0;
		int yoffset = 0;
		for(Image s : EntityCharacter.skins){
			s.setFilter(Image.FILTER_NEAREST);
			
			Button choice = new Button("", this, s, 50+xoffset, 100+yoffset, 16*3, 16*3){
				@Override
				public Object trigger(Object arg){
					character.skin = s;
					return null;
				}
				@Override
				public void preRender(Graphics g){
					g.setColor(Color.white);
					g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
				}
			};
			choice.setMouseOverColor(null);

			choices.add(choice);
			xoffset+=16+3*16;
			if(xoffset>=7*3*16){
				xoffset=0;
				yoffset+=16+3*16;
			}
		}
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		
		if(choices.contains(source)){
			Button button = (Button)source;
			button.trigger(null);
		}
		
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0, 0.5f));
		g.fillRect(x+25, 75, width-70, height-100);
		g.setColor(Color.black);
		g.drawString(name, titleX+x, titleY+y);
		for(Button button : choices)
			button.render(g);
	}

}
