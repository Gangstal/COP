package com.hamsterfurtif.cop;

import java.io.File;

public abstract class Utils {
	
	public static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static void print(String s){
		System.out.println(s);
	}
	
	public static enum TextPlacement{
		CENTERED,
		RIGHT,
		LEFT;	
	}
	
	public static String getFileExtension(File file){
		String extension = "";
		String fileName = file.getName();
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    extension = fileName.substring(i+1);
		}
		return extension;
	}

	public static boolean stringIsInteger(String string){

		   try{
		      Integer.parseInt(string);
		      return true;
		   }
		   catch(Exception e){
		      return false;
		   }

	}
	
}
