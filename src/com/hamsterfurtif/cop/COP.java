package com.hamsterfurtif.cop;

import java.awt.FontFormatException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.display.menu.ConnectionLost;
import com.hamsterfurtif.cop.display.menu.CharacterEquip;
import com.hamsterfurtif.cop.display.menu.ServerRefused;
import com.hamsterfurtif.cop.entities.EntityCharacter;
import com.hamsterfurtif.cop.gamestates.GSMainMenu;
import com.hamsterfurtif.cop.gamestates.GSMap;
import com.hamsterfurtif.cop.gamestates.GSMapEditor;
import com.hamsterfurtif.cop.gamestates.GSCharacterEquip;
import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.inventory.WeaponType;
import com.hamsterfurtif.cop.map.MapPos;
import com.hamsterfurtif.cop.packets.Packet;
import com.hamsterfurtif.cop.packets.PacketCharacterReady;
import com.hamsterfurtif.cop.packets.PacketPlayerInfo;
import com.hamsterfurtif.cop.packets.PacketSendSettings;
import com.hamsterfurtif.cop.packets.PacketServerFull;

public class COP extends StateBasedGame{
	public static final String version = "Pre-Alpha -1.13";
	public static final String[] leadDevelopers = new String[] {
		"Hamster_Furtif",
	};
	public static final String[] developers = Utils.concat(leadDevelopers, new String[] {
		"gaston147",
	});
	public static final String[] contributors = Utils.concat(developers, new String[] {
		"CactusKipic",
	});
	public static final String[] testers = Utils.concat(contributors, new String[] {
		"H3xiQ", "Haborym Lemegenton Æsahættr",
	});

	public enum Mode {
		CLIENT,
		SERVER,
		SINGLEPLAYER,
	}

	public enum ConnState {
		NOT_CONNECTED,
		CONNECTING,
		CONNECTED,
		FAILED,
	}

	public static int width = 1008, height = 600;
	public static COP instance = new COP();
	public static Image background;
	public static AppGameContainer app;
	public static Game game;
	public static Random rd;

	public static Mode mode;
	public static ServerSocket serverSocket;
	public static Queue<Conn> conns;
	public static Queue<Packet> packets;
	public static int selfId;
	public static Player self;
	public static Socket sockToServ;
	public static Conn connToServ;
	public static boolean serverSockShuttingDown;

	public static String savedip;
	public static boolean music;
	public static int playersConnected, playersReady;
	public static boolean settingsDone;
	public static boolean started;
	public static ConnState connState;


	public static void main(String[] args) throws SlickException, FontFormatException, IOException{
		started = false;
		connState = ConnState.NOT_CONNECTED;
		serverSockShuttingDown = false;
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
			e.printStackTrace();
		}
		addState(new GSMainMenu());
		addState(new GSCharacterEquip());
		addState(COP.game = new Game());
		addState(new GSMapEditor());
	}

	public static String serializeMapPos(MapPos mapPos) {
		return mapPos.X + ";" + mapPos.Y + ";" + mapPos.Z;
	}

	public static MapPos unserializeMapPos(String args) {
		String[] sp = args.split(";");
		if (sp.length != 3) {
			System.out.println("WARNING: MapPos should contains 3 coordinates, got " + sp.length);
			return null;
		}
		int[] coords = new int[3];
		for (int i = 0; i < 3; i++) {
			try {
				coords[i] = Integer.parseInt(sp[i]);
			} catch (NumberFormatException e) {
				System.out.println("WARNING: \"" + sp[i] + "\" isn't a number");
				return null;
			}
		}
		return new MapPos(coords[0], coords[1], coords[2]);
	}

	public static void sendPacket(Packet packet, Conn ignore) {
		try {
			for (Player player : Game.players) {
				if (player.conn != null && player.conn != ignore)
					player.conn.send(packet);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendPacket(Packet packet) {
		sendPacket(packet, null);
	}

	private static void readSavedIP() throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader("config.txt"));
        savedip = bufferedReader.readLine();
        music = bufferedReader.readLine()=="true" ? true : false;
        bufferedReader.close();
	}

	public static void writeSavedIP(String string) throws IOException{
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("config.txt")));
		bufferedWriter.write(string);
		bufferedWriter.write("\n");
		bufferedWriter.write((music ? "true" : "false"));
		bufferedWriter.close();
	}

	public static void sendSettings(Conn conn) throws IOException {
		conn.send(new PacketSendSettings(Game.map, Game.maxHP, Game.maxSpawn));
	}

	public static void sendSettings() throws IOException {
		for (Player player : Game.players)
			if (player.conn != null)
				sendSettings(player.conn);
	}

	public static void setupFromSettings() {
		try {
			for (int i = 0; i < Game.playersCount; i++) {
				Player p = Game.players[i];
				for (int j = 0; j < Game.charactersCount; j++) {
					EntityCharacter c = p.characters[j];
					c.repsawnsLeft = Game.maxSpawn;
					c.health = Game.maxHP;
				}
			}
			float xscale = (float)840/(float)(Game.map.dimX*TextureLoader.size);
			float yscale = (float)480/(float)(Game.map.dimY*TextureLoader.size);
			float optimalScale = xscale > yscale ? yscale : xscale;
			optimalScale -= optimalScale%0.25f;
			GSMap.optimalScale=optimalScale;
			GSMap.scale=optimalScale;
			float c = TextureLoader.size*optimalScale;
			if(c*Game.map.dimX<=COP.width-168)
				Game.mapx=(int)(168+COP.width-c*Game.map.dimX)/2;
			if(c*Game.map.dimY<=480)
				Game.mapy=(int)(480-c*Game.map.dimY)/2;
			Game.setCharactersInitialSpawn();
			GSCharacterEquip state = (GSCharacterEquip) COP.instance.getCurrentState();
			state.currentCharacter = (COP.mode == Mode.SINGLEPLAYER ? Game.players[0] : COP.self).characters[0];
			state.mainMenu = new CharacterEquip(state.container, state, state.currentCharacter);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updateRemote() {
		if (started) {
			COP.checkServerConn();
			if (mode == Mode.SERVER) {
				while (true) {
					Conn conn;
					synchronized (conns) {
						if ((conn = conns.poll()) == null)
							break;
					}
					if (playersConnected == Game.playersCount) {
						try {
							conn.send(new PacketServerFull());
							conn.socket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						continue;
					}
					for (Player player : Game.players) {
						if (!player.used) {
							player.used = true;
							player.conn = conn;
							conn.player = player;
							try {
								conn.send(new PacketPlayerInfo(Game.playersCount, Game.charactersCount, player.id));
							} catch (IOException e) {
								throw new RuntimeException(e);
							}
							break;
						}
					}
					if (settingsDone) {
						try {
							sendSettings(conn);
							for (Player player : Game.players)
								for (EntityCharacter character : player.characters)
									if (character.configured)
										sendCharacter(conn, character);
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}
					playersConnected++;
				}
			}
			while (true) {
				Packet packet;
				synchronized (packets) {
					if ((packet = packets.poll()) == null)
						break;
				}

				if (packet instanceof PacketServerFull) {
					try {
						started = false;
						sockToServ.close();
						connState = ConnState.NOT_CONNECTED;
						((GSCharacterEquip) instance.getCurrentState()).mainMenu = new ServerRefused(instance.getContainer(), (GSCharacterEquip) instance.getCurrentState(), "Le serveur est plein");
					} catch (SlickException | IOException e) {
						e.printStackTrace();
					}
				} else if (packet instanceof PacketPlayerInfo) {
					Game.playersCount = ((PacketPlayerInfo) packet).playersCount;
					Game.charactersCount = ((PacketPlayerInfo) packet).charactersCount;
					selfId = ((PacketPlayerInfo) packet).playerID;
					setupPlayers();
					Game.players[0].conn = connToServ;
				} else if (packet instanceof PacketSendSettings) {
					Game.map = ((PacketSendSettings) packet).map;
					Game.maxHP = ((PacketSendSettings) packet).maxHP;
					Game.maxSpawn = ((PacketSendSettings) packet).maxSpawn;
					setupFromSettings();
				} else if (packet instanceof PacketCharacterReady) {
					if (mode == Mode.SERVER)
						sendPacket(packet);
					EntityCharacter c = Game.players[((PacketCharacterReady) packet).playerID].characters[((PacketCharacterReady) packet).characterID];
					c.skin = EntityCharacter.skins.get(((PacketCharacterReady) packet).skinID);
					c.inventory.primary.weapon = ((PacketCharacterReady) packet).primary;
					c.inventory.secondary.weapon = ((PacketCharacterReady) packet).secondary;
					c.name = ((PacketCharacterReady) packet).name;
					c.reset();
					c.configured = true;
					boolean ok = true;
					for (EntityCharacter character : Game.players[((PacketCharacterReady) packet).playerID].characters) {
						if (!character.configured) {
							ok = false;
							break;
						}
					}
					if (ok) {
						playersReady++;
						if (playersReady == Game.playersCount) {
							instance.enterState(2);
							return;
						}
					}
				} else {
					System.out.println("WARNING: Ignoring unexpected packet \"" + Utils.getPacketID(packet) + "\"");
				}
			}
		}
	}

	public static void setupPlayers() {
		Game.players = new Player[Game.playersCount];
		for (int i = 0; i < Game.playersCount; i++) {
			Game.players[i] = new Player(i, "Joueur " + (i + 1), new EntityCharacter[Game.charactersCount]);
			for (int j = 0; j < Game.charactersCount; j++)
				Game.players[i].characters[j] = new EntityCharacter(Game.players[i], j, "?");
		}
		if (mode != Mode.SINGLEPLAYER) {
			COP.self = Game.players[COP.selfId];
			COP.self.used = true;
		}
	}

	public static void sendCharacterToAll(EntityCharacter character) {
		sendCharacterToAll(character, null);
	}

	public static void sendCharacterToAll(EntityCharacter character, Conn ignore) {
		COP.sendPacket(new PacketCharacterReady(character.player.id, character.id, EntityCharacter.skins.indexOf(character.skin), character.getWeapon(WeaponType.PRIMARY), character.getWeapon(WeaponType.SECONDARY), character.name), ignore);
	}

	public static void sendCharacter(Conn conn, EntityCharacter character) throws IOException {
		conn.send(new PacketCharacterReady(character.player.id, character.id, EntityCharacter.skins.indexOf(character.skin), character.getWeapon(WeaponType.PRIMARY), character.getWeapon(WeaponType.SECONDARY), character.name));
	}

	public static void checkServerConn() {
		if (mode == Mode.CLIENT) {
			if (connToServ.socket.isClosed()) {
				started = false;
				connState = ConnState.NOT_CONNECTED;
				try {
					GSCharacterEquip state = (GSCharacterEquip) instance.getState(1);
					state.mainMenu = new ConnectionLost(instance.getContainer(), state);
					if (state != instance.getCurrentState())
						instance.enterState(1);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
