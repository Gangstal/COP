package com.hamsterfurtif.cop.display.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;

public class TextInput extends TextField {

	public TextInput(Menu menu, int x, int y, int width, int height, String content){
		super(menu.container, new TrueTypeFont(new java.awt.Font(java.awt.Font.SERIF,java.awt.Font.BOLD , 15), false), x, y, width, height, menu);
		setText(content);
		setAcceptingInput(true);
	}

	public TextInput(Menu menu, int x, int y, int width, int height){
		this(menu, x, y, width, height, "");
	}

	public void setCentered(){
		int x=this.getX();
		int y=this.getY();
		x-=getWidth()/2;
		y-=getHeight()/2;
		this.x = x;
		this.y = y;
	}

	public TextInput centered(){
		setCentered();
		return this;
	}

	public TextInput borderColor(Color color){
		setBorderColor(color);
		return this;
	}

	public TextInput textColor(Color color){
		setTextColor(color);
		return this;
	}

	public TextInput allowInput(boolean bool){
		setAcceptingInput(bool);
		return this;
	}

	public TextInput maxLength(int length){
		setMaxLength(length);
		return this;
	}

	public TextInput bgColor(Color color){
		setBackgroundColor(color);
		return this;
	}

}
