package com.hamsterfurtif.cop.packets;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;

public class PacketNextCharacter extends Packet {
	public static final String PACKET_ID = "next_player";

	public static Packet read(String args, Conn origin) {
		if (args.length() != 0) {
			System.out.println("WARNING: No arguments expected");
			return null;
		}
		return new PacketNextCharacter(origin);
	}

	public static void write(PacketNextCharacter packet, BufferedWriter out) throws IOException {
	}

	public PacketNextCharacter() {
		this(null);
	}

	public PacketNextCharacter(Conn origin) {
		super(origin);
	}
}
