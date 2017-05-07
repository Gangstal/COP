package com.hamsterfurtif.cop.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.hamsterfurtif.cop.statics.Tiles;

public class ReadMap {
	
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

            while((line = bufferedReader.readLine()) != null) {
            	
            	if(line.startsWith("<RES=") && line.endsWith(">")){
            		line = (String) line.subSequence(5, line.length()-1);
            		int i=0;
            		String resX = "", resY = "";
            		while(i<line.length()){
            			if(line.charAt(i) != ',')
            				resY+=line.charAt(i);
            			else{
            				resX=resY;
            				resY="";
            			}
            			i++;
            		}
            		map = new Tile[Integer.parseInt(resY)][Integer.parseInt(resX)];
            		y--;
            	}
            	
            	else{
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
}