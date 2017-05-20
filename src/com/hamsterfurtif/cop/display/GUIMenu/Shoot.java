package com.hamsterfurtif.cop.display.GUIMenu;

import java.util.ArrayList;
import java.util.Arrays;

import com.hamsterfurtif.cop.map.MapPos;

public class Shoot extends GUIMenuCoords{

	public MapPos get(){
		this.name = "Tirer (ex:1,A)";
		this.choices = new ArrayList<String>(Arrays.asList());
		
		MapPos pos;
		do{
			pos=this.show();
		}while(pos==null);
		return pos;
	}
	
}
