package com.hamsterfurtif.cop;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.gamestates.GSMainMenu;
import com.hamsterfurtif.cop.gamestates.GSPlayerEquip;
import com.hamsterfurtif.cop.map.MapPos;

public class COP extends StateBasedGame{

	public static final int MODE_CLIENT			= 0;
	public static final int MODE_SERVER			= 1;
	public static final int MODE_SINGLEPLAYER	= 2;

	public static int width = 1008, height = 600;
	public static COP instance = new COP();
	private final static String version = "Pre-Alpha -1.5";
	public static Image background;
	public static AppGameContainer app;
	public static Game game;
	public static Random rd;

	public static int mode;
	public static ServerSocket serverSocket;
	public static List<Conn> conns;
	public static List<String> packets;


	public static void main(String[] args) throws SlickException, FontFormatException, IOException{
		rd = new Random(1);
		conns = new ArrayList<Conn>();
		packets = new ArrayList<String>();
		try {
			if (args.length < 1)
				throw new Exception("Not enough arguments, expected at least 1 (mode)");
			if (args[0].equals("client")) {
				mode = MODE_CLIENT;
				if (args.length < 3)
					throw new Exception("Not enough arguments, expected at least 3 (mode ip port)");
				conns.add(new Conn(new Socket(args[1], Integer.parseUnsignedInt(args[2]))));
			} else if (args[0].equals("server")) {
				mode = MODE_SERVER;
				serverSocket = new ServerSocket(42069);
				new ServerThread().start();
			} else if (args[0].equals("singleplayer")) {
				mode = MODE_SINGLEPLAYER;
			} else {
				throw new Exception("Unknown mode \"" + args[0] + "\"");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

        app = new AppGameContainer(instance, width, height, false);
        app.setShowFPS(false);
        app.start();
	}

	public COP() {
		super("Call of Paper "+version);
		// TODO Auto-generated constructor stub
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
}
