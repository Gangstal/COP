package com.hamsterfurtif.cop.packets;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.packets.types.PacketTypes;

public class PacketStartMovement extends Packet {

	public PacketStartMovement() {
		this(null);
	}

	public PacketStartMovement(Conn origin) {
		super(PacketTypes.startMovement, origin);
	}
}
