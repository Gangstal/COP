package com.hamsterfurtif.cop.inventory;

import java.util.ArrayList;

import org.newdawn.slick.Sound;

public class Inventory {
	
	public Weapon primary;
	public Weapon secondary;
	
	public int ammoP, ammoS;
	
	public static ArrayList<Sound> reloadSounds = new ArrayList<Sound>();
	
	public void setAmmo(WeaponType type, int qtt){
		switch (type){
		case PRIMARY:
			this.ammoP=qtt;
			break;
		case SECONDARY:
			this.ammoS=qtt;
			break;
		}
			 
	}
	
	public int getAmmo(WeaponType type){
		switch (type){
		case PRIMARY:
		default:
			return ammoP;
		case SECONDARY:
			return ammoS;
		}
			 
	}

	public void addAmmo(WeaponType type, int offset){
		switch (type){
		case PRIMARY:
			this.setAmmo(type, ammoP+offset);
			break;
		case SECONDARY:
			this.setAmmo(type, ammoS+offset);
			break;
		}
	}
	
	public Weapon getWeapon(WeaponType type){
		switch(type){
		case PRIMARY:
			return primary;
		case SECONDARY:
			return secondary;
		default:
			return primary;
		}
	}
	
	public void setWeapon(WeaponType type, Weapon weapon){
		switch(type){
		case PRIMARY:
		default:
			primary=weapon;
			break;
		case SECONDARY:
			secondary = weapon;
		}
	}
}
