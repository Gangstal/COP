package com.hamsterfurtif.cop.inventory;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.map.MapPos;
import com.hamsterfurtif.cop.statics.Weapons;


public class Weapon {
	
	public String name;
	public int range, damage, ammo;
	public Image skin;
	public ArrayList<Sound> sounds = new ArrayList<Sound>();
	
	public Weapon(String name, int range, int damage, int maxAmmo, WeaponType type){
		this.name = name;
		this.range = range;
		this.damage = damage;
		this.ammo = maxAmmo;
		this.skin = TextureLoader.loadTexture("sprites\\weapons\\"+name+".png");
		
		switch(type){
		case PRIMARY:
			Weapons.primary.add(this);
			break;
		case SECONDARY:
			Weapons.secondary.add(this);
			break;
		}
	}
	
	public boolean inRange(MapPos pos1, MapPos pos2){
		//Good ol' Pythagore
		double d = Math.sqrt(Math.pow(Math.abs(pos1.X-pos2.X),2) + Math.pow(Math.abs(pos1.Y-pos2.Y),2));
		return d<=range;
	}

	public void playSound() {
		Random r = new Random(1);
		sounds.get(r.nextInt(1)).play();
	}

}
