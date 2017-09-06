package com.hamsterfurtif.cop.packets;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.packets.types.PacketTypes;

public class PacketPlayerInfo extends Packet {
	public final int playersCount, charactersCount;
	public final int playerID;

	public PacketPlayerInfo(int playersCount, int charactersCount, int playerID) {
		this(playersCount, charactersCount, playerID, null);
	}

	public PacketPlayerInfo(int playersCount, int charactersCount, int playerID, Conn origin) {
		super(PacketTypes.playerInfo, origin);
		this.playersCount = playersCount;
		this.charactersCount = charactersCount;
		this.playerID = playerID;
	}
}
