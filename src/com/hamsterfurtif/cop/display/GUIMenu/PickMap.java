package com.hamsterfurtif.cop.display.GUIMenu;

import java.util.ArrayList;
import java.util.Arrays;

import com.hamsterfurtif.cop.Game;

public class PickMap extends GUIMenu{
	
	public void get(){
		this.name = "Equipement des joueurs";
		this.choices = new ArrayList<String>(Arrays.asList("Map par défaut :(", "Quitter"));
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
