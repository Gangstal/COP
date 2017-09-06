package com.hamsterfurtif.cop.packets;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.inventory.Weapon;
import com.hamsterfurtif.cop.packets.types.PacketTypes;

public class PacketCharacterReady extends Packet {
	public final int playerID, characterID, skinID;
	public final Weapon primary, secondary;
	public final String name;

	public PacketCharacterReady(int playerID, int characterID, int skinID, Weapon primary, Weapon secondary, String name) {
		this(playerID, characterID, skinID, primary, secondary, name, null);
	}

	public PacketCharacterReady(int playerID, int characterID, int skinID, Weapon primary, Weapon secondary, String name, Conn origin) {
		super(PacketTypes.characterReady, origin);
		this.playerID = playerID;
		this.characterID = characterID;
		this.skinID = skinID;
		this.primary = primary;
		this.secondary = secondary;
		this.name = name;
	}
}
