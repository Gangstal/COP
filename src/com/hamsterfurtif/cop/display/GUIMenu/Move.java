package com.hamsterfurtif.cop.display.GUIMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import com.hamsterfurtif.cop.display.Engine;

public class Move extends GUIMenu{
	
	public void get(){
		this.name = "Déplacement";
		this.choices = new ArrayList<String>(Arrays.asList());
		this.show();
	}
	
	private int[] show(){
		
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
		int[] k = {Integer.parseInt(l[0]),Engine.alphabet.indexOf((l[1]))};
		print(""+k);
		return k;
	}

	protected void print(String string){
		System.out.println(string);
	}

}
