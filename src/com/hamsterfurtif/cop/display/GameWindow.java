package com.hamsterfurtif.cop.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameWindow extends JFrame{


	public GameWindow(){
		
		this.setTitle("Call of Paper");
		this.setSize(1920/2, 1080/2);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Panel panel = new Panel();
		this.add(panel);
		
		JButton bouton = new JButton("Test");

		panel.add(bouton);
		this.setVisible(true);

	}
	
	
	public class Panel extends JPanel{
	 
		public Panel(){
			this.setBackground(Color.yellow);
		}
		
		public void paintComponent(Graphics g){                
			Font font = new Font("Courier", Font.BOLD, 20);
			g.setFont(font);
			g.setColor(Color.red);          
			g.drawString("Hello world", 10, 20);                
		 }
		
	}
	
}
