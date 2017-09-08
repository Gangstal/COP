package com.hamsterfurtif.cop.packets;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.inventory.Weapon;
import com.hamsterfurtif.cop.statics.Weapons;

public class PacketCharacterReady extends Packet {
	public static final String PACKET_ID = "character_ready";

	public static Packet read(String args, Conn origin) {
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

	public static void write(PacketCharacterReady packet, BufferedWriter out) throws IOException {
		out.write(packet.playerID + ";" + packet.characterID + ";" + packet.skinID + ";" + packet.primary.id + ";" + packet.secondary.id + ";" + packet.name);
	}

	public final int playerID, characterID, skinID;
	public final Weapon primary, secondary;
	public final String name;

	public PacketCharacterReady(int playerID, int characterID, int skinID, Weapon primary, Weapon secondary, String name) {
		this(playerID, characterID, skinID, primary, secondary, name, null);
	}

	public PacketCharacterReady(int playerID, int characterID, int skinID, Weapon primary, Weapon secondary, String name, Conn origin) {
		super(origin);
		this.playerID = playerID;
		this.characterID = characterID;
		this.skinID = skinID;
		this.primary = primary;
		this.secondary = secondary;
		this.name = name;
	}
}
