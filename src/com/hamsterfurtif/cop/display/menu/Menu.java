package com.hamsterfurtif.cop.display.menu;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.ComponentListener;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.gamestates.GameStateMenu;

public abstract class Menu implements ComponentListener{
	

	
	public String name;
	public ArrayList<Button> choices =  new ArrayList<Button>();
	public int x=0, y=0;
	public int width= COP.width, height=COP.height;
	public int titleX = width/2, titleY = 20;
	public GameContainer container;
	GameStateMenu state;
	
	public Menu(GameContainer container, String name, GameStateMenu state)throws SlickException { 
		this.container=container;
		this.name = name;
		this.state = state;
	}
	  
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.drawString(name, titleX+x, titleY+y);
		for(Button button : choices)
			button.render(g);
	}
	
	public void update(){};

}
