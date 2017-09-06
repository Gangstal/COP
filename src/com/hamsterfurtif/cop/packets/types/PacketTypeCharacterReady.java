package com.hamsterfurtif.cop.packets.types;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.packets.Packet;
import com.hamsterfurtif.cop.packets.PacketCharacterReady;
import com.hamsterfurtif.cop.statics.Weapons;

public class PacketTypeCharacterReady extends PacketType {

	public PacketTypeCharacterReady() {
		super("character_ready");
	}

	public Packet readPacket(String args, Conn origin) {
		String[] sp = args.split(";");
		if (sp.length != 6) {
			System.out.println("WARNING: Invalid arguments length, should be 6");
			return null;
		}
		int[] vals = new int[3];
		for (int i = 0; i < 3; i++) {
			try {
				vals[i] = Integer.parseInt(sp[i]);
			} catch (NumberFormatException e) {
				System.out.println("WARNING: Number expected");
				return null;
			}
		}
		return new PacketCharacterReady(vals[0], vals[1], vals[2], Weapons.getWeaponByID(sp[3]), Weapons.getWeaponByID(sp[4]), sp[5], origin);
	}

	public void writePacket(Packet packet, BufferedWriter out) throws IOException {
		if (packet instanceof PacketCharacterReady) {
			writePacket((PacketCharacterReady) packet, out);
		} else {
			System.out.println("WARNING: Packet not sended");
		}
	}

	public void writePacket(PacketCharacterReady packet, BufferedWriter out) throws IOException {
		out.write(name + ";" + packet.playerID + ";" + packet.characterID + ";" + packet.skinID + ";" + packet.primary.id + ";" + packet.secondary.id + ";" + packet.name + "\n");
		out.flush();
	}
}
