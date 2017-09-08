package com.hamsterfurtif.cop.packets;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;

public class PacketServerFull extends Packet {
	public static final String PACKET_ID = "server_full";

	public static Packet read(String args, Conn origin) {
		if (args.length() != 0) {
			System.out.println("WARNING: No arguments expected");
			return null;
		}
		return new PacketServerFull(origin);
	}

	public static void write(PacketServerFull packet, BufferedWriter out) throws IOException {
	}

	public PacketServerFull() {
		this(null);
	}

	public PacketServerFull(Conn origin) {
		super(origin);
	}
}
