package com.hamsterfurtif.cop.packets.types;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.map.MapPos;
import com.hamsterfurtif.cop.packets.Packet;
import com.hamsterfurtif.cop.packets.PacketShoot;

public class PacketTypeShoot extends PacketType {

	public PacketTypeShoot() {
		super("shoot");
	}

	public Packet readPacket(String args, Conn origin) {
		MapPos mp = COP.unserializeMapPos(args);
		if (mp == null)
			return null;
		return new PacketShoot(mp, origin);
	}

	public void writePacket(Packet packet, BufferedWriter out) throws IOException {
		if (packet instanceof PacketShoot) {
			out.write(name + ";" + COP.serializeMapPos(((PacketShoot) packet).mp) + "\n");
			out.flush();
		} else {
			System.out.println("WARNING: Packet not sended");
		}
	}
}
