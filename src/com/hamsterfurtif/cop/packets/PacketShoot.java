package com.hamsterfurtif.cop.packets;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.map.MapPos;

public class PacketShoot extends Packet {
	public static final String PACKET_ID = "shoot";

	public static Packet read(String args, Conn origin) {
		MapPos mp = COP.unserializeMapPos(args);
		if (mp == null)
			return null;
		return new PacketShoot(mp, origin);
	}

	public static void write(PacketShoot packet, BufferedWriter out) throws IOException {
		out.write(COP.serializeMapPos(((PacketShoot) packet).mp));
	}

	public final MapPos mp;

	public PacketShoot(MapPos mp) {
		this(mp, null);
	}

	public PacketShoot(MapPos mp, Conn origin) {
		super(origin);
		this.mp = mp;
	}
}
