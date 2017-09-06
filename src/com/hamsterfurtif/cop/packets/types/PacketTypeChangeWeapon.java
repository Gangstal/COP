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
		switch (((PacketChangeWeapon) packet).wt) {
		case PRIMARY:
			wtStr = "primary";
			break;
		case SECONDARY:
			wtStr = "secondary";
			break;
		default:
			System.out.println("WARNING: Unknown weapon type");
			System.out.println("WARNING: Packet not sended");
			return;
		}
		out.write(name + ";" + wtStr + "\n");
		out.flush();
	}
}
