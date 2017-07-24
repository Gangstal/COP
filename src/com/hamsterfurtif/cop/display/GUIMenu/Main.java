package com.hamsterfurtif.cop.display.GUIMenu;

import java.util.ArrayList;
import java.util.Arrays;

import com.hamsterfurtif.cop.Game;

public class Main extends GUIMenu{
	
	private Button play = new Button("Jouer"){
		public Object trigger(Object args){
			return true;
		}
	};
	
	private Button quit = new Button("Jouer"){
		public Object trigger(Object args){
			Game.running = false;
			return false;
		}
	};
	
	public boolean get(){
		this.name = "Menu principal";
		this.choices = new ArrayList<Button>(Arrays.asList(play,quit));
		int c = this.showMenu();
		
		if(c-1>=choices.size())
			return false;
		else
			return (boolean)choices.get(c-1).trigger(null);
	}
}
