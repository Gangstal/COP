package com.hamsterfurtif.cop.packets;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.packets.types.PacketTypes;

public class PacketRemoveLastPos extends Packet {

	public PacketRemoveLastPos() {
		this(null);
	}

	public PacketRemoveLastPos(Conn origin) {
		super(PacketTypes.removeLastPos, origin);
	}
}
