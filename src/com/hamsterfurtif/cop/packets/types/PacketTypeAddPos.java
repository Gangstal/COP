package com.hamsterfurtif.cop.packets.types;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.map.MapPos;
import com.hamsterfurtif.cop.packets.Packet;
import com.hamsterfurtif.cop.packets.PacketAddPos;

public class PacketTypeAddPos extends PacketType {

	public PacketTypeAddPos() {
		super("add_pos");
	}

	public Packet readPacket(String args, Conn origin) {
		MapPos mp = COP.unserializeMapPos(args);
		if (mp == null)
			return null;
		return new PacketAddPos(mp, origin);
	}

	public void writePacket(Packet packet, BufferedWriter out) throws IOException {
		if (packet instanceof PacketAddPos) {
			writePacket((PacketAddPos) packet, out);
		} else {
			System.out.println("WARNING: Packet not sended");
		}
	}

	public void writePacket(PacketAddPos packet, BufferedWriter out) throws IOException {
		out.write(name + ";" + COP.serializeMapPos(packet.mp) + "\n");
		out.flush();
	}
}
