package com.hamsterfurtif.cop.packets;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;

public class PacketStartMovement extends Packet {
	public static final String PACKET_ID = "start_movement";

	public static Packet read(String args, Conn origin) {
		if (args.length() != 0) {
			System.out.println("WARNING: No arguments expected");
			return null;
		}
		return new PacketStartMovement(origin);
	}

	public static void write(PacketStartMovement packet, BufferedWriter out) throws IOException {
	}

	public PacketStartMovement() {
		this(null);
	}

	public PacketStartMovement(Conn origin) {
		super(origin);
	}
}
