package com.hamsterfurtif.cop.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.hamsterfurtif.cop.statics.Tiles;

public class MapReader {
	
	public static Tile[][] read(String location){
		
		String line = null;
		Tile[][] map = null;
		int y=0;
		
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(location);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int resX = Integer.parseInt(bufferedReader.readLine());
            int resY = Integer.parseInt(bufferedReader.readLine());
            map = new Tile[resY][resX];

            while((line = bufferedReader.readLine()) != null) {
            	
            	
            	for(int x=0; x<line.length(); x++){
   
            		char c=line.charAt(x);
            		
            		switch(c){	
            			case 'F': map[y][x]=Tiles.floor;
            				break;
	           			case 'D': map[y][x]=Tiles.door;
           					break;
           				case 'W': map[y][x]=Tiles.wall;
           					break;
           				case 'I': map[y][x]=Tiles.window;
           					break;
           				default:  map[y][x]=Tiles.floor;
           			}
            			
            	}
            	
            	
            	y++;
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" +location + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '"+ location + "'");                  

        }
		System.out.println("Done");
        return map;
	}
	
	public static ArrayList<String> scanMapFolder(String directory){
		File folder = new File(directory);
		ArrayList<String>  mapList = new ArrayList<String>();
		
		for (File fileEntry : folder.listFiles()) 
		      if (!fileEntry.isDirectory())
		    	  mapList.add(fileEntry.getName());
		        
		return mapList;
	}
}