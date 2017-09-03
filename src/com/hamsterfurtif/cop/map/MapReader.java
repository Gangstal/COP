package com.hamsterfurtif.cop.map;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.map.tiles.Tile;
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
            			case 'F': map[y][x]=Tiles.grass;
            				break;
	           			case 'D': map[y][x]=Tiles.door_grass;
           					break;
           				case 'W': map[y][x]=Tiles.wall;
           					break;
           				case 'I': map[y][x]=Tiles.window;
           					break;
           				case 'S': map[y][x]=Tiles.stone;
           						break;
           				default:  map[y][x]=Tiles.grass;
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
	
	public static Tile[][] readMap(String location) throws NumberFormatException, IOException{
		FileReader fr = new FileReader(location);
		BufferedReader bf = new BufferedReader(fr);
        int resX = Integer.parseInt(bf.readLine());
        int resY = Integer.parseInt(bf.readLine());
        Tile[][] map = new Tile[resY][resX];
        String line;
        HashMap<Character, String> dic = new HashMap<Character, String>();
        while((line = bf.readLine()) != null && line.contains(">")){
        	String[] split = line.split(">");
        	dic.put(split[1].charAt(0), split[0]);

        }
        int y=0;
        do{
        	String[] split = line.split("\\|");
        	for(int x=0;x<resX;x++){
        		String[] tilesplit = split[x].split(":");
        		Tile tile = Tiles.getTile(dic.get(tilesplit[0].charAt(0)));
        		map[y][x]=tile;
        	}
        	y++;
        }while((line = bf.readLine()) != null );
        
        bf.close();
        return map;

	}
	
	public static void writeMap(Map m, String path) throws IOException{
		Tile[][] map = m.map;
        HashMap<String, Character> dic = new HashMap<String, Character>();
        ArrayList<String> lines = new ArrayList<String>();
        lines.add(""+m.dimX);
        lines.add(""+m.dimY);
        for(int y=0; y<m.dimY; y++){
        	String line = "";
        	for(int x=0; x<m.dimX;x++){
        		if(!dic.containsKey(map[y][x].name)){
        			dic.put(map[y][x].name, Utils.alphabet.charAt(dic.size()));
        			lines.add(dic.size()+1, map[y][x].name+">"+ Utils.alphabet.charAt(dic.size()-1));
        		}
        		line += dic.get(map[y][x].name)+"|";
   
        	}
        	lines.add(line);
        }
        java.nio.file.Path p = Paths.get(path);
        Files.write(p, lines, StandardCharsets.UTF_8);

	}

}