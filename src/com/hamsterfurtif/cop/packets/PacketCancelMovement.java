package com.hamsterfurtif.cop.packets;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.packets.types.PacketTypes;

public class PacketCancelMovement extends Packet {


	public PacketCancelMovement() {
		this(null);
	}

	public PacketCancelMovement(Conn origin) {
		super(PacketTypes.cancelMovement, origin);
	}
}
