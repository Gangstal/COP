package com.hamsterfurtif.cop.statics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hamsterfurtif.cop.inventory.Weapon;
import com.hamsterfurtif.cop.inventory.WeaponShield;
import com.hamsterfurtif.cop.inventory.WeaponSniper;
import com.hamsterfurtif.cop.inventory.WeaponType;

public class Weapons {

	public static ArrayList<Weapon> primary   = new ArrayList<Weapon>();
	public static ArrayList<Weapon> secondary = new ArrayList<Weapon>();

	public static final Map<String, Weapon> weaponsByID = new HashMap<String, Weapon>();

	//				Weapon("name", range, damage, ammo)

	public static Weapon shotgun	= new Weapon("shotgun", "Shotgun", 5, 9, 2, WeaponType.PRIMARY);
	public static Weapon AR			= new Weapon("ar", "AR", 10, 7, 4, WeaponType.PRIMARY);
	public static Weapon sniper     = new WeaponSniper("sniper", "Sniper", 25, 8, 12, 2, WeaponType.PRIMARY);

	public static Weapon handgun	= new Weapon("handgun", "Handgun", 7, 3, 3, WeaponType.SECONDARY);
	public static Weapon revolver	= new Weapon("revolver", "Revolver", 5, 4, 2, WeaponType.SECONDARY);
	public static Weapon shield     = new WeaponShield("Bouclier", "shield");

	public static List<Weapon> getWeaponsByType(WeaponType type){
		switch (type) {
		case PRIMARY:
		default:
			return primary;
		case SECONDARY:
			return secondary;
		}
	}

	public static Weapon getWeaponByID(String id) {
		return weaponsByID.get(id);
	}

}
