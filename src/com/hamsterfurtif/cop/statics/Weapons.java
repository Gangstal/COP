package com.hamsterfurtif.cop.statics;

import java.util.ArrayList;

import com.hamsterfurtif.cop.inventory.Weapon;

public class Weapons {

	public static ArrayList<Weapon> primary   = new ArrayList<Weapon>();
	public static ArrayList<Weapon> secondary = new ArrayList<Weapon>();
	
	//				Weapon("name", range, damage, ammo)
	
	public static Weapon shotgun = new Weapon("Shotgun", 5, 10, 4, 1);
	public static Weapon AR = new Weapon("AR", 10, 7, 6, 1);
	
	public static Weapon handgun = new Weapon("Handgun", 7, 3, 4, 2);
	public static Weapon revolver = new Weapon("Revolver", 5, 4, 4, 2); 
	
}
