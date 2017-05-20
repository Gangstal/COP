package com.hamsterfurtif.cop.display.GUIMenu;

import java.util.Collections;
import java.util.Scanner;

import com.hamsterfurtif.cop.display.Engine;
import com.hamsterfurtif.cop.map.MapPos;

public class GUIMenuCoords extends GUIMenu {

	
protected MapPos show(){
		
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		
		String header = String.join("", Collections.nCopies(name.length()+2, "="));
		print(header);
		print(" "+name);
		print(header);
		if(!choices.isEmpty())
			for(int i=0; i<choices.size();i++)
				print("["+(i+1)+"]"+choices.get(i));
		
		
		String n = reader.nextLine();
		String[] l = n.split(",");
		try{
			return new MapPos(Integer.parseInt(l[0]),Engine.alphabet.indexOf((l[1])), 0);
		} catch(Exception e) {
			System.out.println("::: Error while parsin MapPos in menu\""+this.name+"\" :::");
			return null;
		}
		
	}
}
