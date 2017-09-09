package com.hamsterfurtif.cop;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import com.hamsterfurtif.cop.display.menu.Button;
import com.hamsterfurtif.cop.entities.EntityCharacter;
import com.hamsterfurtif.cop.inventory.Inventory;
import com.hamsterfurtif.cop.inventory.Weapon;
import com.hamsterfurtif.cop.map.tiles.Tile;
import com.hamsterfurtif.cop.statics.Weapons;

public class SoundHandler {

	public static void loadSounds() throws SlickException{
		 Button.clicked = new Sound("\\assets\\sounds\\button.wav");
		 Tile.destroy.add(new Sound("\\assets\\sounds\\tiles\\break1.wav"));
		 Tile.destroy.add(new Sound("\\assets\\sounds\\tiles\\break2.wav"));
		 loadWeaponSounds();
		 loadPlayerSounds();
	}
	
	private static void loadWeaponSounds() throws SlickException{
		for(Weapon w : Weapons.primary){
			w.sounds.add(new Sound("\\assets\\sounds\\weapons\\"+w.name+"1.wav"));
			w.sounds.add(new Sound("\\assets\\sounds\\weapons\\"+w.name+"2.wav"));
		}
		for(Weapon w : Weapons.secondary){
			w.sounds.add(new Sound("\\assets\\sounds\\weapons\\"+w.name+"1.wav"));
			w.sounds.add(new Sound("\\assets\\sounds\\weapons\\"+w.name+"2.wav"));
		}
		Inventory.reloadSounds.add(new Sound("\\assets\\sounds\\weapons\\reload1.wav"));
		Inventory.reloadSounds.add(new Sound("\\assets\\sounds\\weapons\\reload2.wav"));
	}
	
	private static void loadPlayerSounds() throws SlickException{
		EntityCharacter.deathSounds.add(new Sound("\\assets\\sounds\\players\\death1.wav"));
		EntityCharacter.deathSounds.add(new Sound("\\assets\\sounds\\players\\death2.wav"));
		EntityCharacter.hurtSounds.add(new Sound("\\assets\\sounds\\players\\hurt1.wav"));
	}
}
