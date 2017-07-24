package com.hamsterfurtif.cop.display.GUIMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GUIMenu {
		
	protected String name;
	protected ArrayList<Button> choices =  new ArrayList<Button>();
	
	public int showMenu(){
		
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		
		String header = String.join("", Collections.nCopies(name.length()+2, "="));
		print(header);
		print(" "+name);
		print(header);
		if(!choices.isEmpty())
			for(int i=0; i<choices.size();i++)
				print("["+(i+1)+"]"+choices.get(i).name);
		
		
		
		int n = reader.nextInt();
		
		return n;
	}

	protected void print(String string){
		System.out.println(string);
	}
	
}
