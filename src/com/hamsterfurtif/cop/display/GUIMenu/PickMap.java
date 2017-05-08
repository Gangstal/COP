package com.hamsterfurtif.cop.display.GUIMenu;

import java.util.ArrayList;
import java.util.Arrays;

import com.hamsterfurtif.cop.Game;

public class PickMap extends GUIMenu{
	
	public boolean get(){
		this.name = "Choisir une map";
		this.choices = new ArrayList<String>(Arrays.asList("Map par défaut :(", "Quitter"));
		int c = this.showMenu();

		switch(c){
		case 1:
			return true;
		case 2:
			Game.running = false;
			break;
		}
		return false;
		
	}
}
