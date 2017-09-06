package com.hamsterfurtif.cop.packets;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.map.Map;
import com.hamsterfurtif.cop.packets.types.PacketTypes;

public class PacketSendSettings extends Packet {
	public final Map map;
	public final int maxHP, maxSpawn;

	public PacketSendSettings(Map map, int maxHP, int maxSpawn) {
		this(map, maxHP, maxSpawn, null);
	}

	public PacketSendSettings(Map map, int maxHP, int maxSpawn, Conn origin) {
		super(PacketTypes.sendSettings, origin);
		this.map = map;
		this.maxHP = maxHP;
		this.maxSpawn = maxSpawn;
	}
}
