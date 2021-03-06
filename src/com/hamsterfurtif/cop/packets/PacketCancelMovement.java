package com.hamsterfurtif.cop.packets;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;

public class PacketCancelMovement extends Packet {
	public static final String PACKET_ID = "cancel_movement";

	public static Packet read(String args, Conn origin) {
		if (args.length() != 0) {
			System.out.println("WARNING: No arguments expected");
			return null;
		}
		return new PacketCancelMovement(origin);
	}

	public static void write(PacketCancelMovement packet, BufferedWriter out) throws IOException {
	}

	public PacketCancelMovement() {
		this(null);
	}

	public PacketCancelMovement(Conn origin) {
		super(origin);
	}
}
