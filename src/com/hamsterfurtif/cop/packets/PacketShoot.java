package com.hamsterfurtif.cop.packets;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.map.MapPos;
import com.hamsterfurtif.cop.packets.types.PacketTypes;

public class PacketShoot extends Packet {
	public final MapPos mp;

	public PacketShoot(MapPos mp) {
		this(mp, null);
	}

	public PacketShoot(MapPos mp, Conn origin) {
		super(PacketTypes.shoot, origin);
		this.mp = mp;
	}
}
