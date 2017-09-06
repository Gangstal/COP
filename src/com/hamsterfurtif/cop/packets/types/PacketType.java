package com.hamsterfurtif.cop.packets.types;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.packets.Packet;

public abstract class PacketType {
	public final String name;

	public PacketType(String name) {
		this.name = name;
	}

	public String toString() {
		return "[packet \"" + name + "\"]";
	}

	public abstract Packet readPacket(String args, Conn origin);

	public Packet readPacket(String args) {
		return readPacket(args, null);
	}

	public abstract void writePacket(Packet packet, BufferedWriter out) throws IOException;
}
