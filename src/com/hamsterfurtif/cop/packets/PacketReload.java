package com.hamsterfurtif.cop.packets;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.packets.types.PacketTypes;

public class PacketReload extends Packet {

	public PacketReload() {
		this(null);
	}

	public PacketReload(Conn origin) {
		super(PacketTypes.reload, origin);
	}
}
