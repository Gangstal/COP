package com.hamsterfurtif.cop;

import java.util.ArrayList;
import java.util.Random;

import com.hamsterfurtif.cop.inventory.WeaponType;
import com.hamsterfurtif.cop.map.Map;
import com.hamsterfurtif.cop.map.MapPos;
import com.hamsterfurtif.cop.map.Path;

public class Game {

	public static boolean running = false, match = false;
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static int currentPlayer=0;
	public static Map map;
	public static int maxHP=10;
	public static int maxSpawn=5;
	public static boolean test = false;
	
	
	public void init(){
		players.add(new Player("Joueur 1"));
		players.add(new Player("Joueur 2"));
	}
	
	public void start(){
		
	}

	//TODO Créer dans les fichier de map une selection de points de spawn
	public void setPlayerInitialSpawn(){
		for(Player player : players){
			Random rd = new Random();
			MapPos pos = new MapPos();
			
			do{
				 pos.set(rd.nextInt(map.dimX),rd.nextInt(map.dimY),0);

			}while(!map.getTile(pos).canWalkThrough);
			
			player.pos = pos;
		}
		
	}
	
	@SuppressWarnings("unused")
	private void initMatch(){
		this.setPlayerInitialSpawn();
		for(Player player : players){
			player.symbol = Player.defSym.charAt(players.indexOf(player));
		}
	}
	
	//TODO Utiliser un chemin plutot qu'une position
	public static boolean movePlayer(Player player, MapPos pos){
		
		if(map.getTile(pos).canWalkThrough && checkForPlayer(pos) == null){
			player.pos=pos;
			player.movesLeft=0;
			return true;
		}
		
		return false;	
	}
	
	public static boolean shoot(Player player, MapPos pos, WeaponType type){
				
		if(Path.directLOS(player.pos, pos) && player.inventory.getWeapon(type).inRange(player.pos,  pos)){
			
			if(checkForPlayer(pos) != null){
				Player target = checkForPlayer(pos);
				target.health -= player.getWeapon(type).damage;
				player.inventory.addAmmo(type, -1);
				player.turnIsOver = true;
				player.hasShot=true;
			}
			else if(map.getTile(pos).isDestructible){
				map.destroyTile(pos);
				player.hasShot=true;
				player.turnIsOver = true;
				player.inventory.addAmmo(type, -1);
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
}
