package com.hamsterfurtif.cop.gamestates;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.COP.Mode;
import com.hamsterfurtif.cop.Player;
import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.display.Engine;
import com.hamsterfurtif.cop.display.menu.MainGame;
import com.hamsterfurtif.cop.display.poseffects.MouseHover;
import com.hamsterfurtif.cop.display.poseffects.MoveSelect;
import com.hamsterfurtif.cop.entities.EntityCharacter;
import com.hamsterfurtif.cop.inventory.Inventory;
import com.hamsterfurtif.cop.inventory.WeaponType;
import com.hamsterfurtif.cop.map.MapPos;
import com.hamsterfurtif.cop.map.tiles.Tile;
import com.hamsterfurtif.cop.packets.Packet;
import com.hamsterfurtif.cop.packets.PacketAddPos;
import com.hamsterfurtif.cop.packets.PacketCancelMovement;
import com.hamsterfurtif.cop.packets.PacketChangeWeapon;
import com.hamsterfurtif.cop.packets.PacketNextCharacter;
import com.hamsterfurtif.cop.packets.PacketReload;
import com.hamsterfurtif.cop.packets.PacketRemoveLastPos;
import com.hamsterfurtif.cop.packets.PacketShoot;
import com.hamsterfurtif.cop.packets.PacketStartMovement;

public class Game extends GSMap {

	public static final int ID = 2;
	public static Image health_end_full, health_end_empty, health_middle_full, health_middle_empty, heart;
	public boolean showGrid = false;
	public boolean movementSelection = false;
	private int mx, my;
	private boolean leftClick = false, rightClick = false;
	public ArrayList<MapPos> path = new ArrayList<MapPos>();
	public WeaponType shootingMode=null;
	public boolean freelook = true;
	private int grabbedx, grabbedy;

	public static boolean running = false, match = false;
	public static Player[] players;
	public EntityCharacter currentCharacter;
	public static int maxHP=20;
	public static int maxSpawn=5;
	public static int playersCount;
	public static int charactersCount;

	//Animation
	public boolean animation = false;
	public int pathpos=0;
	public float speed=0.01f;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		currentMenu = new MainGame(container, this);
		this.container = container;
	}

	@Override
	   public void enter(GameContainer container, StateBasedGame game){
	      try {
			super.enter(container, game);
		} catch (SlickException e) {
			e.printStackTrace();
		}
			currentCharacter=players[0].characters[0];
	   }

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(COP.background, 0, 0);
		Engine.drawMap(g, GSMap.scale,mapx, mapy, showGrid, map, true);
		currentMenu.render(g);

		int x=168, y=480;

		g.setColor(Color.lightGray);
		g.fillRect(x, y, 840, 120);
		g.setColor(Color.black);
		g.setLineWidth(1.0f);
		g.drawRect(x, y, 419, 119);
		g.drawRect(x+420, y, 419, 119);

		drawCharacterInfoBox(x, y, currentCharacter, g);


		if(isMouseOverMap(mx, my)){
			renderInfoBox(mx-x, my, g);
		}
		countFps();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		updateScale();
		int x=168;
		if (COP.mode == Mode.CLIENT)
			COP.checkServerConn();
		if (COP.mode != Mode.SINGLEPLAYER) {
			while (true) {
				Packet packet;
				synchronized (COP.packets) {
					if ((packet = COP.packets.poll()) == null)
						break;
				}

				if (COP.mode == Mode.SERVER)
					COP.sendPacket(packet, packet.origin);

				if (packet instanceof PacketNextCharacter) {
					COP.game.nextCharacter();
				} else if (packet instanceof PacketChangeWeapon) {
					Engine.removePosEffect(MoveSelect.class);
					COP.game.shootingMode = ((PacketChangeWeapon) packet).wt;
				} else if (packet instanceof PacketReload) {
					Game.reload(currentCharacter);
					currentCharacter.turnIsOver = true;
				} else if (packet instanceof PacketStartMovement) {
					startMovement();
				} else if (packet instanceof PacketCancelMovement) {
					cancelMovement();
				} else if (packet instanceof PacketRemoveLastPos) {
					removeLastPos();
				} else if (packet instanceof PacketAddPos) {
					clickOnMap(((PacketAddPos) packet).mp);
				} else if (packet instanceof PacketShoot) {
					if (Game.shoot(currentCharacter, ((PacketShoot) packet).mp, shootingMode)) {
						shootingMode = null;
					}
				} else {
					System.out.println("WARNING: Ignoring unexpected packet \"" + Utils.getPacketID(packet) + "\"");
				}
			}
		}

		Input input = container.getInput();
		mx = input.getMouseX();
		my = input.getMouseY();
		if(!animation){
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				if(isMouseOverMap(mx, my)){
					MapPos clickPos = getMapPos(mx-x, my);

					if(canPlay() && shootingMode==null && !currentCharacter.turnIsOver && !freelook){
						if(!leftClick){
							if(!path.isEmpty())
								if(clickPos.equals(path.get(path.size()-1))){
									if(path.size()>1 && path.size()-1 <= currentCharacter.movesLeft && Game.moveCharacter(currentCharacter, path)){
										startMovement();
										if (COP.mode != Mode.SINGLEPLAYER)
											COP.sendPacket(new PacketStartMovement());
									}
									else{
										cancelMovement();
										if (COP.mode != Mode.SINGLEPLAYER)
											COP.sendPacket(new PacketCancelMovement());
									}
								}
						}
						else if(path.size()>2 && clickPos.equals(path.get(path.size()-2))){
							removeLastPos();
							if (COP.mode != Mode.SINGLEPLAYER)
								COP.sendPacket(new PacketRemoveLastPos());
						}
						else if(clickPos.equals(currentCharacter.pos) || !path.isEmpty()) {
							if(path.size()==0 || (!clickPos.equals(path.get(path.size()-1)) && clickPos.isAdjacent(path.get(path.size()-1)) && path.size()<=currentCharacter.movesLeft)){
								clickOnMap(clickPos);
								if (COP.mode != Mode.SINGLEPLAYER)
									COP.sendPacket(new PacketAddPos(clickPos));
							}
						}
					}

					else if(canPlay() && !currentCharacter.hasShot && shootingMode!=null && !freelook){
						if(Game.shoot(currentCharacter, clickPos, shootingMode)) {
							shootingMode=null;
							if (COP.mode != Mode.SINGLEPLAYER)
								COP.sendPacket(new PacketShoot(clickPos));
						}
					}

					else if(freelook){
						if(!leftClick){
							grabbedx=mx;
							grabbedy=my;
						}
						int lmapx = mapx;
						int lmapy = mapy;
						mapx=lmapx-grabbedx+mx;
						mapy=lmapy-grabbedy+my;
						correctMapLoc();
						grabbedx=mapx-(lmapx-grabbedx);
						grabbedy=mapy-(lmapy-grabbedy);
					}
				}
			} else if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && !rightClick){
				if (freelook) {
					freelook = false;
				} else if (canPlay()) {
					if(shootingMode==null){
						shootingMode=WeaponType.PRIMARY;
					}
					else if(shootingMode==WeaponType.PRIMARY){
						shootingMode=WeaponType.SECONDARY;
					}
					else if(shootingMode==WeaponType.SECONDARY){
						shootingMode = null;
						freelook = true;
					}
					if (COP.mode != Mode.SINGLEPLAYER)
						COP.sendPacket(new PacketChangeWeapon(shootingMode));
				}
			}


			Engine.removePosEffect(MouseHover.class);
			if(isMouseOverMap(mx, my)){
				MapPos clickPos = getMapPos(mx-x, my);
				if(shootingMode==null)
					Engine.addPosEffect(new MouseHover(clickPos, Color.black));
				else
					if(currentCharacter.inventory.getWeapon(shootingMode).inRange(currentCharacter.pos, clickPos))
						Engine.addPosEffect(new MouseHover(clickPos, Color.green));
					else
						Engine.addPosEffect(new MouseHover(clickPos, Color.red));

			}

			leftClick = input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
			rightClick = input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON);

		}
		else{
			if(Math.abs(currentCharacter.xgoffset)>=1.0f || Math.abs(currentCharacter.ygoffset)>=1.0f || pathpos==path.size()-1){
				pathpos++;

				if(pathpos==path.size()){
					animation=false;
					pathpos=0;
					currentCharacter.movesLeft-=path.size()-1;
					Engine.removePosEffect(MoveSelect.class);
					path.clear();
				}
				else{
					currentCharacter.setOrientation(Utils.getOrientationFromPos(currentCharacter.pos, path.get(pathpos)));
					currentCharacter.pos = path.get(pathpos);
				}

				currentCharacter.xgoffset=0.0f;
				currentCharacter.ygoffset=0.0f;
			}
			else{
				currentCharacter.xgoffset+=speed*(path.get(pathpos+1).X - path.get(pathpos).X);
				currentCharacter.ygoffset+=speed*(path.get(pathpos+1).Y - path.get(pathpos).Y);
			}
		}
	}

	public boolean canPlay() {
		return COP.mode == Mode.SINGLEPLAYER || currentCharacter.player == COP.self;
	}

	public void startMovement() {
		 animation = true;
	}

	public void cancelMovement() {
		Engine.removePosEffect(MoveSelect.class);
		path.clear();
	}

	public void removeLastPos() {
		Engine.removePosEffect(MoveSelect.class);
		path.remove(path.size()-1);
		for(MapPos pos : path)
			Engine.addPosEffect(new MoveSelect(pos));
	}

	@Override
	public int getID() {
		return ID;
	}

	public void clickOnMap(MapPos pos){
		if(path.size()==0 || (!pos.equals(path.get(path.size()-1)) && pos.isAdjacent(path.get(path.size()-1)) && path.size()<=currentCharacter.movesLeft)){
			Engine.addPosEffect(new MoveSelect(pos));
			path.add(pos);
		}
	}

	private void drawCharacterInfoBox(int x, int y, EntityCharacter character, Graphics g){
		g.drawString(character.name, x+10, y+20);
		g.drawImage(character.skin.getScaledCopy(2.5f), x+420-50, y+10);
		Font font = g.getFont();
		g.drawString(character.player.name, x + 420 - 10 - font.getWidth(character.player.name), y + 125 - 10 - font.getLineHeight());
		if(Game.maxHP>1){
			g.setColor(Color.black);
			g.drawImage(health_end_full, x+10, y+50);
			g.setLineWidth(1.0f);
			for(int i=0; i<Game.maxHP-2;i++){
				if(character.health>i+1)
					g.drawImage(health_middle_full, x+10+17*(i+1), y+50);
				else
					g.drawImage(health_middle_empty, x+10+17*(i+1), y+50);

				g.drawLine(x+9+17*(i+1), y+53, x+9+17*(i+1), y+61);
			}
			int i = Game.maxHP-2;
			g.drawLine(x+9+17*(i+1), y+53, x+9+17*(i+1), y+61);
			if(character.health==Game.maxHP)
				g.drawImage(health_end_full.getFlippedCopy(true, false),x+10+17*(i+1), y+50);
			else
				g.drawImage(health_end_empty.getFlippedCopy(true, false),x+10+17*(i+1), y+50);


		}

		for(int i=0; i<character.repsawnsLeft;i++){
			g.drawImage(heart, x+20+i*18, y+80);
		}
	}

	private void renderInfoBox(int xpos, int ypos, Graphics g){
		MapPos pos = getMapPos(xpos, ypos);
		int x = 168+420;
		int y = 480;
		EntityCharacter character = Game.checkForCharacter(pos);
		if(character==null){
			Tile tile = Game.map.getTile(pos);
			g.drawImage(tile.image.getScaledCopy(4), x+320, y+60-2*16);
			g.drawString(tile.name, x+10, y+5);
			g.drawString("Can Walk Over = "+tile.canWalkThrough, x+10, y+30);
			g.drawString("Can Shoot Through = "+tile.canShootTrough, x+10, y+55);
			g.drawString("Is Destructible = "+tile.isDestructible, x+10, y+80);
		}
		else{
			drawCharacterInfoBox(x, y, character, g);
		}

	}

	public void nextCharacter() {

		Engine.removePosEffect(MoveSelect.class);
		path.clear();

		int nextPlayerId = currentCharacter.player.id;
		int nextCharacterId = currentCharacter.id;

		nextPlayerId++;
		if (nextPlayerId >= playersCount) {
			nextPlayerId = 0;
			nextCharacterId++;
			if (nextCharacterId >= charactersCount) {
				nextCharacterId = 0;
			}
		}

		currentCharacter = players[nextPlayerId].characters[nextCharacterId];

		currentCharacter.resetTurnStats();
		freelook = true;
		shootingMode = null;

	}

	public static void setCharactersInitialSpawn(){
		for (Player player : players){
			for (EntityCharacter character : player.characters) {
				MapPos pos = new MapPos();

				do{
					 pos.set(COP.rd.nextInt(map.dimX),COP.rd.nextInt(map.dimY),0);

				}while(!map.getTile(pos).canWalkThrough);

				character.pos = pos;
			}
		}

	}

	public static boolean moveCharacter(EntityCharacter character, ArrayList<MapPos> path){
		if(path.get(0).equals(character.pos)){
			for(int i=1;i<path.size();i++){
				if(!map.getTile(path.get(i)).canWalkThrough || !path.get(i).isAdjacent(path.get(i-1)))
					return false;
			}
			return true;
		}
		else
			return false;
	}

	public static boolean shoot(EntityCharacter character, MapPos pos, WeaponType type){
		return character.inventory.getSlot(type).shoot(character, pos);
	}

	public static EntityCharacter checkForCharacter(MapPos pos){
		for (Player player : players)
			for(EntityCharacter character : player.characters)
				if(character.pos.equals(pos))
					return character;
		return null;
	}

	public static void reload(EntityCharacter character){
		character.inventory.primary.reload();
		character.inventory.secondary.reload();
		Random r = new Random(1);
		Inventory.reloadSounds.get(r.nextInt(1)).play();
	}

	public static void kill(EntityCharacter character){
		MapPos p = new MapPos();

		do{
			 p.set(COP.rd.nextInt(map.dimX),COP.rd.nextInt(map.dimY),0);
		}while(!map.getTile(p).canWalkThrough);

		character.pos = p;
		character.reset();
		character.repsawnsLeft--;
	}
}
