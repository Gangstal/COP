package com.hamsterfurtif.cop.display.GUIMenu;

import com.hamsterfurtif.cop.Game;
import com.hamsterfurtif.cop.map.Map;
import com.hamsterfurtif.cop.map.MapReader;

public class PickMap extends GUIMenu{
	
	public boolean get(){
		this.name = "Choisir une map";
		this.choices = MapReader.scanMapFolder("C:\\Users\\Jean-Baptiste\\workspace\\COP\\assets\\");
		if(choices.isEmpty()){
			System.out.println("Aucune map trouvée...");
			return false;
		}
		else{
			int c = this.showMenu()-1;

			Game.map = new Map(MapReader.read("C:\\Users\\Jean-Baptiste\\workspace\\COP\\assets\\"+choices.get(c)));
			
			return true;
		}
		
	}
}
