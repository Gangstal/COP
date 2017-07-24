package com.hamsterfurtif.cop.display.GUIMenu;

import java.util.ArrayList;

import com.hamsterfurtif.cop.Game;
import com.hamsterfurtif.cop.map.Map;
import com.hamsterfurtif.cop.map.MapReader;

public class PickMap extends GUIMenu{
	
	public boolean get(){

		this.name = "Choisir une map";
		ArrayList<String> mapList = MapReader.scanMapFolder("C:\\Users\\Jean-Baptiste\\workspace\\COP\\assets\\");
		
		for(String map : mapList){
			choices.add(new Button(map){
				public Object trigger(Object args){
					Game.map = new Map(MapReader.read("C:\\Users\\Jean-Baptiste\\workspace\\COP\\assets\\"+name));
					return true;
				}
			});
		}
		
		
		if(choices.isEmpty()){
			System.out.println("Aucune map trouvée...");
			return false;
		}

		else{
			int c;
			if(Game.test)
				choices.get(0).trigger(null);
			else{
				c = this.showMenu();
				if(c-1>=choices.size())
					return false;
				else
					return (boolean)choices.get(c-1).trigger(null);
			}
			
			return true;
		}
		
	}
}
