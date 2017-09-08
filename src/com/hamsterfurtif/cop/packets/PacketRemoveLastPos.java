package com.hamsterfurtif.cop.packets;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;

public class PacketRemoveLastPos extends Packet {
	public static final String PACKET_ID = "remove_last_pos";

	public static Packet read(String args, Conn origin) {
		if (args.length() != 0) {
			System.out.println("WARNING: No arguments expected");
			return null;
		}
		return new PacketRemoveLastPos(origin);
	}

	public static void write(PacketRemoveLastPos packet, BufferedWriter out) throws IOException {
	}

	public PacketRemoveLastPos() {
		this(null);
	}

	public PacketRemoveLastPos(Conn origin) {
		super(origin);
	}
}
