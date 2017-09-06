package com.hamsterfurtif.cop.packets;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.inventory.WeaponType;
import com.hamsterfurtif.cop.packets.types.PacketTypes;

public class PacketChangeWeapon extends Packet {
	public final WeaponType wt;

	public PacketChangeWeapon(WeaponType wt) {
		this(wt, null);
	}

	public PacketChangeWeapon(WeaponType wt, Conn origin) {
		super(PacketTypes.changeWeapon, origin);
		this.wt = wt;
	}
}
