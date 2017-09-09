package com.hamsterfurtif.cop.inventory;

import com.hamsterfurtif.cop.map.MapPos;

public class WeaponSniper extends Weapon{
	
	public int minrange;

	public WeaponSniper(String id, String name, int range, int minrange, int damage, int maxAmmo, WeaponType type) {
		super(id, name, range, damage, maxAmmo, type);
		this.minrange = minrange;
	}

	public boolean inRange(MapPos pos1, MapPos pos2){
		double d = Math.sqrt(Math.pow(Math.abs(pos1.X-pos2.X),2) + Math.pow(Math.abs(pos1.Y-pos2.Y),2));
		return d >= minrange && d<=range;
	}
}
