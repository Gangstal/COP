package com.hamsterfurtif.cop.packets;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.map.MapPos;
import com.hamsterfurtif.cop.packets.types.PacketTypes;

public class PacketAddPos extends Packet {
	public final MapPos mp;

	public PacketAddPos(MapPos mp) {
		this(mp, null);
	}

	public PacketAddPos(MapPos mp, Conn origin) {
		super(PacketTypes.addPos, origin);
		this.mp = mp;
	}
}
