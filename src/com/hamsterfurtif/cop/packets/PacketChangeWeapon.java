package com.hamsterfurtif.cop.packets;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.inventory.WeaponType;

public class PacketChangeWeapon extends Packet {
	public static final String PACKET_ID = "change_weapon";

	public static Packet read(String args, Conn origin) {
		WeaponType wt;
		if (args.equals("primary")) {
			wt = WeaponType.PRIMARY;
		} else if (args.equals("secondary")) {
			wt = WeaponType.SECONDARY;
		} else if (args.equals("null")) {
			wt = null;
		} else {
			System.out.println("WARNING: Unknown weapong type \"" + args + "\"");
			return null;
		}
		return new PacketChangeWeapon(wt, origin);
	}

	public static void write(PacketChangeWeapon packet, BufferedWriter out) throws IOException {
		String wtStr;
		if (((PacketChangeWeapon) packet).wt == WeaponType.PRIMARY) {
			wtStr = "primary";
		} else if (((PacketChangeWeapon) packet).wt == WeaponType.SECONDARY) {
			wtStr = "secondary";
		} else if (((PacketChangeWeapon) packet).wt == null) {
			wtStr = "null";
		} else {
			System.out.println("WARNING: Unknown weapon type");
			System.out.println("WARNING: Packet not sended");
			return;
		}
		out.write(wtStr);
	}

	public final WeaponType wt;

	public PacketChangeWeapon(WeaponType wt) {
		this(wt, null);
	}

	public PacketChangeWeapon(WeaponType wt, Conn origin) {
		super(origin);
		this.wt = wt;
	}
}
