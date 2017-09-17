package com.hamsterfurtif.cop.inventory;

import org.newdawn.slick.Image;

import com.hamsterfurtif.cop.Utils;
import com.hamsterfurtif.cop.Utils.Facing;
import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.entities.EntityCharacter;
import com.hamsterfurtif.cop.map.MapPos;

public class WeaponShield extends Weapon{
	
	public static Image map_overlay;
	
	public WeaponShield(String id, String name) {
		super(id, name, 1, 0, 0, WeaponType.SECONDARY);
		hasSound = false;
		map_overlay = TextureLoader.loadTexture("\\sprites\\weapons\\shield_overlay.png");
	}
	
	public void shoot(EntityCharacter shooter, MapPos pos, WeaponType type){
		 Facing face = Utils.getOrientationFromPos(shooter.pos, pos);
		 if(face == null)
			 face=shooter.orientation;
		 shooter.orientation = face;
		 shooter.isShieled = true;
	}

}
