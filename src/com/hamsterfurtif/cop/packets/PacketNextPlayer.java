package com.hamsterfurtif.cop.packets;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;

public class PacketNextPlayer extends Packet {
	public static final String PACKET_ID = "next_player";

	public static Packet read(String args, Conn origin) {
		if (args.length() != 0) {
			System.out.println("WARNING: No arguments expected");
			return null;
		}
		return new PacketNextPlayer(origin);
	}

	public static void write(PacketNextPlayer packet, BufferedWriter out) throws IOException {
	}

	public PacketNextPlayer() {
		this(null);
	}

	public PacketNextPlayer(Conn origin) {
		super(origin);
	}
}
