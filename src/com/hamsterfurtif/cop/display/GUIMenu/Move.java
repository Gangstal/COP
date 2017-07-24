package com.hamsterfurtif.cop.display.GUIMenu;

import com.hamsterfurtif.cop.map.MapPos;

public class Move extends GUIMenuCoords{
	
	public MapPos get(){
		this.name = "Déplacement (ex:1,A)";
		
		MapPos pos;
		do{
			pos=this.show();
		}while(pos==null);
		return pos;
	}
}
