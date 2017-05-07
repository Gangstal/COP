package com.hamsterfurtif.cop.display.GUIMenu;

import java.util.ArrayList;
import java.util.Arrays;

import com.hamsterfurtif.cop.Game;

public class Main extends GUIMenu{
	
	public void get(){
		this.name = "Menu principal";
		this.choices = new ArrayList<String>(Arrays.asList("Jouer", "Quitter"));
		int c = this.showMenu();
		
		switch(c){
		case 1:
			
			break;
		case 2:
			Game.running = false;
			break;
		}
	}
}
