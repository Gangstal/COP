package com.hamsterfurtif.cop.inventory;

import java.util.ArrayList;

import org.newdawn.slick.Sound;

public class Inventory {
	
	
	
	public Slot primary;
	public Slot secondary;
		
	public static ArrayList<Sound> reloadSounds = new ArrayList<Sound>();
	
	public void setAmmo(WeaponType type, int qtt){
		switch (type){
		case PRIMARY:
			primary.ammo=qtt;
			break;
		case SECONDARY:
			secondary.ammo=qtt;
			break;
		}
			 
	}
	
	public int getAmmo(WeaponType type){
		switch (type){
		case PRIMARY:
		default:
			return primary.ammo;
		case SECONDARY:
			return secondary.ammo;
		}
			 
	}

	public void addAmmo(WeaponType type, int offset){
		switch (type){
		case PRIMARY:
			this.setAmmo(type, primary.ammo+offset);
			break;
		case SECONDARY:
			this.setAmmo(type, primary.ammo+offset);
			break;
		}
	}
	
	public Weapon getWeapon(WeaponType type){
		switch(type){
		case PRIMARY:
		default:
			return primary.weapon;
		case SECONDARY:
			return secondary.weapon;

		}
	}
	
	public void setWeapon(WeaponType type, Weapon weapon){
		switch(type){
		case PRIMARY:
		default:
			primary.weapon = weapon;
			break;
		case SECONDARY:
			secondary.weapon = weapon;
		}
	}
}
