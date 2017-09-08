package com.hamsterfurtif.cop.packets;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;

public class PacketReload extends Packet {
	public static final String PACKET_ID = "reload";

	public static Packet read(String args, Conn origin) {
		if (args.length() != 0) {
			System.out.println("WARNING: No arguments expected");
			return null;
		}
		return new PacketReload(origin);
	}

	public static void write(PacketReload packet, BufferedWriter out) throws IOException {
	}

	public PacketReload() {
		this(null);
	}

	public PacketReload(Conn origin) {
		super(origin);
	}
}
