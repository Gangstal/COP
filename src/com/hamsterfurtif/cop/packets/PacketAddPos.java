package com.hamsterfurtif.cop.packets;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.map.MapPos;

public class PacketAddPos extends Packet {
	public static final String PACKET_ID = "add_pos";

	public static Packet read(String args, Conn origin) {
		MapPos mp = COP.unserializeMapPos(args);
		if (mp == null)
			return null;
		return new PacketAddPos(mp, origin);
	}

	public static void write(PacketAddPos packet, BufferedWriter out) throws IOException {
		out.write(COP.serializeMapPos(packet.mp));
	}

	public final MapPos mp;

	public PacketAddPos(MapPos mp) {
		this(mp, null);
	}

	public PacketAddPos(MapPos mp, Conn origin) {
		super(origin);
		this.mp = mp;
	}
}
