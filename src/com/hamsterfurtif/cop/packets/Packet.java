package com.hamsterfurtif.cop.packets;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.packets.types.PacketType;

public class Packet {
	public final PacketType pt;
	public final Conn origin;

	public Packet(PacketType pt, Conn origin) {
		this.pt = pt;
		this.origin = origin;
	}

	public void send(BufferedWriter out) throws IOException {
		pt.writePacket(this, out);
	}
}
