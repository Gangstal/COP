package com.hamsterfurtif.cop.statics;

import java.util.ArrayList;

import com.hamsterfurtif.cop.inventory.Weapon;
import com.hamsterfurtif.cop.inventory.WeaponType;

public class Weapons {

	public static ArrayList<Weapon> primary   = new ArrayList<Weapon>();
	public static ArrayList<Weapon> secondary = new ArrayList<Weapon>();
	
	private static WeaponType PRIMARY = WeaponType.PRIMARY;
	private static WeaponType SECONDARY = WeaponType.SECONDARY;

	//				Weapon("name", range, damage, ammo)
	
	public static Weapon shotgun = new Weapon("Shotgun", 5, 10, 4, PRIMARY);
	public static Weapon AR = new Weapon("AR", 10, 7, 6, PRIMARY);
	
	public static Weapon handgun = new Weapon("Handgun", 7, 3, 4, SECONDARY);
	public static Weapon revolver = new Weapon("Revolver", 5, 4, 4, SECONDARY); 
	
}
