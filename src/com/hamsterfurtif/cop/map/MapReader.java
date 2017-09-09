package com.hamsterfurtif.cop.map;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.map.tiles.Tile;
import com.hamsterfurtif.cop.statics.Tiles;

public class MapReader {

	public static Tile[][] read(BufferedReader bufferedReader) throws IOException {

		String line = null;
		Tile[][] map = null;
		int y=0;

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
        return map;
	}

	public static Tile[][] read(String location) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(location)));
			Tile[][] tiles = read(bufferedReader);
			bufferedReader.close();
			return tiles;
		} catch(FileNotFoundException ex) {
			System.out.println("Unable to open file \"" + location + "\"");
		} catch(IOException ex) {
			System.out.println("Error reading file \"" + location + "\"");
		}
		return null;
	}

	public static ArrayList<String> scanMapFolder(String directory){
		File folder = new File(directory);
		ArrayList<String>  mapList = new ArrayList<String>();

		for (File fileEntry : folder.listFiles())
		      if (!fileEntry.isDirectory())
		    	  mapList.add(fileEntry.getName());

		return mapList;
	}

	public static Map readMap(BufferedReader bf) throws NumberFormatException, IOException {
		boolean locked = bf.readLine() == "locked" ? true : false;
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
        		String[] args ;
        		Tile tile;
        		if(tilesplit[0].equals("custom_tile")){
        			args = new String[tilesplit.length-1];
        			System.arraycopy(tilesplit, 1, args, 0, tilesplit.length-1);
        			tile = Tiles.getCustomTile(args);
        			Utils.print("customtile");
        		}
        		else{
        			tile = Tiles.getTile(dic.get(tilesplit[0].charAt(0)));
        		}
        		map[y][x]=tile;
        	}
        	y++;
        }while((line = bf.readLine()) != null );

        return new Map(map, locked);

	}

	public static Map readMap(String location) throws NumberFormatException, IOException {
		try {
			Map map;
			BufferedReader br = new BufferedReader(new FileReader(new File(location)));
			map = readMap(br);
			br.close();
			return map;
		} catch(FileNotFoundException ex) {
			System.out.println("Unable to open file \"" + location + "\"");
		}
		return null;
	}

	public static void writeMap(Map m, BufferedWriter bw) throws IOException {
		Tile[][] map = m.map;
        HashMap<String, Character> dic = new HashMap<String, Character>();
        ArrayList<String> lines = new ArrayList<String>();
		lines.add("unlocked");
        lines.add(""+m.dimX);
        lines.add(""+m.dimY);
        for(int y=0; y<m.dimY; y++){
        	String line = "";
        	for(int x=0; x<m.dimX;x++){
        		if(!dic.containsKey(map[y][x].name)){
        			dic.put(map[y][x].name, Utils.alphabet.charAt(dic.size()));
        			lines.add(dic.size()+2, map[y][x].name+">"+ Utils.alphabet.charAt(dic.size()-1));
        		}
        		line += dic.get(map[y][x].name)+"|";

        	}
        	lines.add(line);
        }
        for (String line : lines)
        	bw.write(line + "\n");
	}

	public static void writeMap(Map m, String location) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(location)));
		writeMap(m, bw);
		bw.close();
	}
}