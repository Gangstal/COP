package com.hamsterfurtif.cop.packets;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.packets.types.PacketTypes;

public class PacketNextPlayer extends Packet {

	public PacketNextPlayer() {
		this(null);
	}

	public PacketNextPlayer(Conn origin) {
		super(PacketTypes.nextPlayer, origin);
	}
}
