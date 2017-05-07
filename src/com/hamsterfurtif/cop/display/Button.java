package com.hamsterfurtif.cop.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Button extends JButton{

	private String name;
	
	public Button(String str){
		super(str);
		this.name = str;
	}
	
	
	public void paintComponent(Graphics g){

		this.setBackground(Color.red);
	    Graphics2D g2d = (Graphics2D)g;
	    g2d.drawString(this.name, this.getWidth() / 2 - (this.getWidth()/ 2 /4), (this.getHeight() / 2) + 5);

	}        

	
}
