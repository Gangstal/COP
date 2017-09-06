package com.hamsterfurtif.cop.packets.types;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.packets.Packet;
import com.hamsterfurtif.cop.packets.PacketReload;

public class PacketTypeReload extends PacketType {

	public PacketTypeReload() {
		super("reload");
	}

	public Packet readPacket(String args, Conn origin) {
		if (args.length() != 0) {
			System.out.println("WARNING: No arguments expected");
			return null;
		}
		return new PacketReload(origin);
	}

	public void writePacket(Packet packet, BufferedWriter out) throws IOException {
		if (packet instanceof PacketReload) {
			out.write(name + "\n");
			out.flush();
		} else {
			System.out.println("WARNING: Packet not sended");
		}
	}
}
