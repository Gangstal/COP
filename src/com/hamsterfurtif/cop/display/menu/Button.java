package com.hamsterfurtif.cop.display.menu;



import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.MouseOverArea;

import com.hamsterfurtif.cop.Utils.TextPlacement;
import com.hamsterfurtif.cop.display.TextureLoader;

public class Button extends MouseOverArea{
	
	public String name;
	public GameContainer container;
	public int textmargin = 5;
	public int elevation = 0;
	public static Image default_image;
	public TextPlacement textPlacement = TextPlacement.CENTERED;


	public Button(String name, Menu menu, Image image, int x, int y, int width, int height) {
		super(menu.container, image.getScaledCopy(width, height), x+menu.x, y+menu.y, width, height, menu);
		this.setMouseDownColor(Color.darkGray);
		this.setMouseOverColor(Color.gray);
		this.container = menu.container;
		this.name = name;

	}
	
	public Button(String name, Menu menu, String location, int x, int y, int width, int height) {
		super(menu.container, TextureLoader.loadTexture("GUI\\"+location+".gif").getScaledCopy(width, height), x+menu.x, y+menu.y, width, height, menu);
		this.setMouseDownColor(Color.darkGray);
		this.setMouseOverColor(Color.gray);
		this.container = menu.container;
		this.name = name;

	}
	
	public Button(String name, Menu menu, int x, int y, int width, int height) {
		super(menu.container, default_image.getScaledCopy(width, height), x+menu.x, y+menu.y, width, height, menu);
		this.setMouseDownColor(Color.darkGray);
		this.setMouseOverColor(Color.gray);
		this.container = menu.container;
		this.name = name;

	}
	
	public void render(Graphics g){
		preRender(g);
		
		this.render(container, g);
		g.setColor(Color.black);
		g.drawRect(this.getX(), this.getY(), this.getWidth()-1, this.getHeight()-1);
		Font font = g.getFont();
		int w = font.getWidth(name);
		int h = font.getLineHeight();
		int x=0;
		int y = this.getY()-(h-this.getHeight())/2-elevation;

		
		switch (textPlacement) {
		case LEFT:
			x=this.getX()+textmargin;
			break;

		case RIGHT:
			x=this.getX()+this.getWidth()-w-textmargin;
			break;
			
		case CENTERED:
		default:
			x = this.getX()-(w-this.getWidth())/2;
			break;
		}
		
		font.drawString(x, y, name);

		additionalRender(g);
	}
	
	public Object trigger(Object args){
		return null;
	}
	
	public void center(){
		int x=this.getX();
		int y=this.getY();
		x-=getWidth()/2;
		y-=getHeight()/2;
		setX(x);
		setY(y);
	}
	
	public Button centered(){
		this.center();
		return this;
	}
	
	public void textPlacement(TextPlacement placement){
		textPlacement = placement;
	}
	
	public Button setTextPlacement(TextPlacement placement){
		textPlacement(placement);
		return this;
	}
	
	public Button setTextMargins(int horizontal, int vertical){
		this.textmargin = horizontal;
		this.elevation = vertical;
		
		return this;
	}
	
	public void additionalRender(Graphics g){}
	public void preRender(Graphics g){}
	
	

}
