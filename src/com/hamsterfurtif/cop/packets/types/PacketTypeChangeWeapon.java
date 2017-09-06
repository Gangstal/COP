package com.hamsterfurtif.cop.packets.types;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.inventory.WeaponType;
import com.hamsterfurtif.cop.packets.Packet;
import com.hamsterfurtif.cop.packets.PacketChangeWeapon;

public class PacketTypeChangeWeapon extends PacketType {

	public PacketTypeChangeWeapon() {
		super("change_weapon");
	}

	public Packet readPacket(String args, Conn origin) {
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

	public void writePacket(Packet packet, BufferedWriter out) throws IOException {
		if (packet instanceof PacketChangeWeapon) {
			writePacket((PacketChangeWeapon) packet, out);
		} else {
			System.out.println("WARNING: Packet not sended");
		}
	}

	public void writePacket(PacketChangeWeapon packet, BufferedWriter out) throws IOException {
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
		out.write(name + ";" + wtStr + "\n");
		out.flush();
	}
}
