package com.hamsterfurtif.cop;

import java.awt.FontFormatException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.gamestates.GSMainMenu;
import com.hamsterfurtif.cop.gamestates.GSPlayerEquip;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.map.MapPos;

public class COP extends StateBasedGame{

	public static final int MODE_CLIENT			= 0;
	public static final int MODE_SERVER			= 1;
	public static final int MODE_SINGLEPLAYER	= 2;

	public static int width = 1008, height = 600;
	public static COP instance = new COP();
	private final static String version = "Pre-Alpha -1.7";
	public static Image background;
	public static AppGameContainer app;
	public static Game game;
	public static Random rd;

	public static int mode;
	public static ServerSocket serverSocket;
	public static List<Conn> conns;
	public static List<String> packets;
	
	public static String savedip;
	public static boolean music;


	public static void main(String[] args) throws SlickException, FontFormatException, IOException{
		
		readSavedIP();
        app = new AppGameContainer(instance, width, height, false);
        app.setShowFPS(false);
        app.start();
	}

	public COP() {
		super("Call of Paper "+version);
	}

	public void initStatesList(GameContainer game) throws SlickException {
		try {
			TextureLoader.load();
			SoundHandler.loadSounds();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Game.init();
		addState(new GSMainMenu());
		addState(new GSPlayerEquip());
		addState(COP.game = new Game());

	}

	public static String serializeMapPos(MapPos mapPos) {
		return mapPos.X + ";" + mapPos.Y + ";" + mapPos.Z;
	}

	public static MapPos unserializeMapPos(String args) {
		String[] sp = args.split(";");
		return new MapPos(Integer.parseUnsignedInt(sp[0]), Integer.parseUnsignedInt(sp[1]), Integer.parseUnsignedInt(sp[2]));
	}

	public static void sendPacket(String packet, Conn ignore) {
		try {
			System.out.println("Sending packet [" + packet + "]");
			for (Conn conn : conns) {
				if (conn != ignore) {
					conn.out.write(packet + "\n");
					conn.out.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendPacket(String packet) {
		sendPacket(packet, null);
	}
	
	private static void readSavedIP() throws IOException{
		FileReader fileReader = new FileReader("config.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        savedip = bufferedReader.readLine();
        music = bufferedReader.readLine()=="true" ? true : false;
        bufferedReader.close();         
	}
	
	public static void writeSavedIP(String string) throws IOException{
		File file = new File("ip.txt");
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(string.getBytes());
		fos.write((music ? "true" : "false").getBytes());
		fos.close();
	}
}
