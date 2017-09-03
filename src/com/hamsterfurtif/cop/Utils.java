package com.hamsterfurtif.cop;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;

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
	
	public static String getClipBoard(){
	    try {
	        return (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
	    } catch (HeadlessException e) {
	        e.printStackTrace();            
	    } catch (UnsupportedFlavorException e) {
	        e.printStackTrace();            
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return "";
	}
	
	public static int bigger(int a, int b){
		return a>b?a:b;
	}
	public static int smaller(int a, int b){
		return a<b?a:b;
	}
	
}
