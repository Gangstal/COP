package com.hamsterfurtif.cop.inventory;

public class Inventory {
	
	public Weapon primary;
	public Weapon secondary;
	
	public int ammoP, ammoS;
	
	public void setAmmo(WeaponType type, int qtt){
		switch (type){
		case PRIMARY:
			this.ammoP=qtt;
		case SECONDARY:
			this.ammoS=qtt;
		}
			 
	}

	public void addAmmo(WeaponType type, int offset){
		switch (type){
		case PRIMARY:
			this.setAmmo(type, ammoP+offset);
		case SECONDARY:
			this.setAmmo(type, ammoS+offset);
		}
	}
}
