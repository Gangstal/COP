package com.hamsterfurtif.cop.gamestates;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Player;
import com.hamsterfurtif.cop.display.Engine;
import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.display.menu.MainGame;
import com.hamsterfurtif.cop.display.poseffects.MouseHover;
import com.hamsterfurtif.cop.display.poseffects.MoveSelect;
import com.hamsterfurtif.cop.inventory.WeaponType;
import com.hamsterfurtif.cop.map.Map;
import com.hamsterfurtif.cop.map.MapPos;
import com.hamsterfurtif.cop.map.Path;
import com.hamsterfurtif.cop.map.tiles.Tile;

public class Game extends GameStateMenu {

	public static final int ID = 2;
	public static Image health_end_full, health_end_empty, health_middle_full, health_middle_empty, heart;
	public static float scale = 2.5f;
	public boolean showGrid = false;
	public boolean movementSelection = false;
	private int mx, my;
	private boolean leftClick = false;
	public ArrayList<MapPos> path = new ArrayList<MapPos>();
	public WeaponType shootingMode=null;

	public static boolean running = false, match = false;
	public static ArrayList<Player> players = new ArrayList<Player>();
	public Player currentPlayer;
	public static Map map;
	public static int maxHP=20;
	public static int maxSpawn=5;
	public static boolean test = false;

	//Animation
	public boolean animation = false;
	public int pathpos=0;
	public int speed=1;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		currentMenu = new MainGame(container, this);
	}

	@Override
	   public void enter(GameContainer container, StateBasedGame game){
	      try {
			super.enter(container, game);
		} catch (SlickException e) {
			e.printStackTrace();
		}
			currentPlayer=players.get(0);
	   }
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(COP.background, 0, 0);
		currentMenu.render(g);
		Engine.drawMapWithPlayers(g, scale,(int)(COP.width-TextureLoader.textureRes*Game.map.dimX*scale), 0, showGrid);

		int x=168, y=480;

		g.setColor(Color.lightGray);
		g.fillRect(x, y, 840, 120);
		g.setColor(Color.black);
		g.drawRect(x, y, 419, 119);
		g.drawRect(x+420, y, 419, 119);

		drawPlayerInfoBox(x, y, currentPlayer, g);


		if(mx>(int)(COP.width-TextureLoader.textureRes*21*scale) && my<(int)(TextureLoader.textureRes*12*scale)){
			renderInfoBox(mx-(int)(COP.width-TextureLoader.textureRes*21*scale), my, g);
		}

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		try {
			synchronized (COP.packets) {
				for (String packet : COP.packets) {
					System.out.println("Treating packet [" + packet + "]");
					String type, args;
					int ind = packet.indexOf(";");
					if (ind >= 0) {
						type = packet.substring(0, ind);
						args = packet.substring(ind + 1);
					} else {
						type = packet;
						args = "";
					}
					if (type.equals("next_player")) {
						COP.game.nextPlayer();
					} else if (type.equals("change_weapon")) {
						Engine.removePosEffect(MoveSelect.class);
						WeaponType wt;
						if (args.equals("primary"))
							wt = WeaponType.PRIMARY;
						else if (args.equals("secondary"))
							wt = WeaponType.SECONDARY;
						else if (args.equals("null"))
							wt = null;
						else
							throw new Exception("Unknown Weapon Type \"" + args + "\"");
						COP.game.shootingMode = wt;
					} else if (type.equals("reload")) {
						Game.reload(COP.game.currentPlayer);
						COP.game.currentPlayer.turnIsOver = true;
					} else if (type.equals("start_moving")) {
						COP.game.shootingMode = null;
					} else if (type.equals("start_movement")) {
						startMovement();
					} else if (type.equals("cancel_movement")) {
						cancelMovement();
					} else if (type.equals("remove_last_pos")) {
						removeLastPos();
					} else if (type.equals("add_pos")) {
						clickOnMap(COP.unserializeMapPos(args));
					} else if (type.equals("shoot")) {
						if (Game.shoot(currentPlayer, COP.unserializeMapPos(args), shootingMode)) {
							shootingMode = null;
						}
					} else {
						throw new Exception("Unknown packet type \"" + type + "\"");
					}
				}
				COP.packets.clear();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Input input = container.getInput();
		mx = input.getMouseX();
		my = input.getMouseY();
		if(!animation){
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				if(mx>(int)(COP.width-TextureLoader.textureRes*21*scale) && my<(int)(TextureLoader.textureRes*12*scale)){
					MapPos clickPos = getMapPos(mx-(int)(COP.width-TextureLoader.textureRes*21*scale), my);

					if(shootingMode==null && !currentPlayer.turnIsOver){
						if(!leftClick){
							if(!path.isEmpty())
								if(clickPos.equals(path.get(path.size()-1))){
									if(path.size()>1 && path.size()-1 <= currentPlayer.movesLeft && Game.movePlayer(currentPlayer, path)){
										startMovement();
										COP.sendPacket("start_movement");
									}
									else{
										cancelMovement();
										COP.sendPacket("cancel_movement");
									}
								}
						}
						else if(path.size()>2 && clickPos.equals(path.get(path.size()-2))){
							removeLastPos();
							COP.sendPacket("remove_last_pos");
						}
						else if(clickPos.equals(currentPlayer.pos) || !path.isEmpty()) {
							if(path.size()==0 || (!clickPos.equals(path.get(path.size()-1)) && clickPos.isAdjacent(path.get(path.size()-1)) && path.size()<=currentPlayer.movesLeft)){
								clickOnMap(clickPos);
								COP.sendPacket("add_pos;" + COP.serializeMapPos(clickPos));
							}
						}
					}

					else if(!currentPlayer.hasShot){
						if(Game.shoot(currentPlayer, clickPos, shootingMode)) {
							shootingMode=null;
							COP.sendPacket("shoot;" + COP.serializeMapPos(clickPos));
						}
					}
				}
			}

			Engine.removePosEffect(MouseHover.class);
			if(mx>(int)(COP.width-TextureLoader.textureRes*21*scale) && my<(int)(TextureLoader.textureRes*12*scale) && !showGrid){
				MapPos clickPos = getMapPos(mx-(int)(COP.width-TextureLoader.textureRes*21*scale), my);
				if(shootingMode==null)
					Engine.addPosEffect(new MouseHover(clickPos, Color.black));
				else
					if(currentPlayer.inventory.getWeapon(shootingMode).inRange(currentPlayer.pos, clickPos))
						Engine.addPosEffect(new MouseHover(clickPos, Color.green));
					else
						Engine.addPosEffect(new MouseHover(clickPos, Color.red));

			}

			leftClick = input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
		}
		else{
			if(Math.abs(currentPlayer.xgoffset)>=Math.abs(TextureLoader.textureRes*scale) || Math.abs(currentPlayer.ygoffset)>=Math.abs(TextureLoader.textureRes*scale) || pathpos==path.size()-1){
				pathpos++;

				if(pathpos==path.size()){
					animation=false;
					pathpos=0;
					currentPlayer.movesLeft-=path.size()-1;
					Engine.removePosEffect(MoveSelect.class);
					path.clear();
				}
				else
					currentPlayer.pos = path.get(pathpos);

				currentPlayer.xgoffset=0;
				currentPlayer.ygoffset=0;
			}
			else{
				currentPlayer.xgoffset+=speed*(path.get(pathpos+1).X - path.get(pathpos).X);
				currentPlayer.ygoffset+=speed*(path.get(pathpos+1).Y - path.get(pathpos).Y);
			}
		}
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
		if(path.size()==0 || (!pos.equals(path.get(path.size()-1)) && pos.isAdjacent(path.get(path.size()-1)) && path.size()<=currentPlayer.movesLeft)){
			Engine.addPosEffect(new MoveSelect(pos));
			path.add(pos);
		}
	}

	private void drawPlayerInfoBox(int x, int y, Player player, Graphics g){
		g.drawString(player.name, x+10, y+20);
		g.drawImage(player.skin.getScaledCopy(2.5f), x+420-50, y+10);
		if(Game.maxHP>1){
			g.setColor(Color.black);
			g.drawImage(health_end_full, x+10, y+50);
			for(int i=0; i<Game.maxHP-2;i++){
				if(player.health>i+1)
					g.drawImage(health_middle_full, x+10+17*(i+1), y+50);
				else
					g.drawImage(health_middle_empty, x+10+17*(i+1), y+50);

				g.drawLine(x+9+17*(i+1), y+53, x+9+17*(i+1), y+61);
			}
			int i = Game.maxHP-2;
			g.drawLine(x+9+17*(i+1), y+53, x+9+17*(i+1), y+61);
			if(player.health==Game.maxHP)
				g.drawImage(health_end_full.getFlippedCopy(true, false),x+10+17*(i+1), y+50);
			else
				g.drawImage(health_end_empty.getFlippedCopy(true, false),x+10+17*(i+1), y+50);


		}

		for(int i=0; i<player.repsawnsLeft;i++){
			g.drawImage(heart, x+20+i*18, y+80);
		}
	}

	private void renderInfoBox(int xpos, int ypos, Graphics g){
		MapPos pos = getMapPos(xpos, ypos);
		int x = 168+420;
		int y = 480;
		Player player = Game.checkForPlayer(pos);
		if(player==null){
			Tile tile = Game.map.getTile(pos);
			g.drawImage(tile.image.getScaledCopy(4), x+320, y+60-2*16);
			g.drawString(tile.name, x+10, y+5);
			g.drawString("Can Walk Over = "+tile.canWalkThrough, x+10, y+30);
			g.drawString("Can Shoot Through = "+tile.canShootTrough, x+10, y+55);
			g.drawString("Is Destructible = "+tile.isDestructible, x+10, y+80);
		}
		else{
			drawPlayerInfoBox(x, y, player, g);
		}

	}

	private MapPos getMapPos(int xpos, int ypos){
		int X =(int)((xpos-xpos%(scale*16))/(scale*16));
		int Y =(int)((ypos-ypos%(scale*16))/(scale*16));
		return new MapPos(X, Y, 0);
	}

	public void nextPlayer() {
		if(Game.players.indexOf(currentPlayer)==Game.players.size()-1)
			currentPlayer=players.get(0);
		else
			currentPlayer=players.get(players.indexOf(currentPlayer)+1);

		currentPlayer.resetTurnStats();

		shootingMode = null;

	}

	public static void setPlayerInitialSpawn(){
		for(Player player : players){
			MapPos pos = new MapPos();

			do{
				 pos.set(COP.rd.nextInt(map.dimX),COP.rd.nextInt(map.dimY),0);

			}while(!map.getTile(pos).canWalkThrough);

			player.pos = pos;
		}

	}

	public static boolean movePlayer(Player player, ArrayList<MapPos> path){
		if(path.get(0).equals(player.pos)){
			for(int i=1;i<path.size()-1;i++){
				if(!map.getTile(path.get(i)).canWalkThrough || !path.get(i).isAdjacent(path.get(i-1)))
					return false;
			}
			return true;
		}
		else
			return false;
	}

	public static boolean shoot(Player player, MapPos pos, WeaponType type){

		if(Path.directLOS(player.pos, pos) && player.inventory.getWeapon(type).inRange(player.pos,  pos) && player.inventory.getAmmo(type)>0){

			player.inventory.getWeapon(type).playSound();
			Random r = new Random(1);
			
			if(checkForPlayer(pos) != null){
				Player target = checkForPlayer(pos);
				target.health -= player.getWeapon(type).damage;
				player.inventory.addAmmo(type, -1);
				player.turnIsOver = true;
				player.hasShot=true;
				if(target.health<=0){
					kill(target);
					Player.deathSounds.get(r.nextInt(1)).play();
				}
			}
			else if(map.getTile(pos).isDestructible){
				map.destroyTile(pos);
				player.hasShot=true;
				player.turnIsOver = true;
				player.inventory.addAmmo(type, -1);
				Tile.destroy.get(r.nextInt(1)).play(0, 0.5f);;
			}

			return true;
		}

		return false;

	}

	public static Player checkForPlayer(MapPos pos){

		for(Player player : players)
			if(player.pos.equals(pos))
				return player;

		return null;
	}

	public static void reload(Player player){
		player.inventory.ammoP = player.inventory.primary.ammo;
		player.inventory.ammoS = player.inventory.secondary.ammo;
	}

	public static void init(){

	}

	private static void kill(Player player){
		MapPos p = new MapPos();

		do{
			 p.set(COP.rd.nextInt(map.dimX),COP.rd.nextInt(map.dimY),0);

		}while(!map.getTile(p).canWalkThrough);

		player.pos = p;
		player.reset();
		player.repsawnsLeft--;
	}
}
