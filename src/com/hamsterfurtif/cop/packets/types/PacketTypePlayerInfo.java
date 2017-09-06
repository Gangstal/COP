package com.hamsterfurtif.cop.packets.types;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.packets.Packet;
import com.hamsterfurtif.cop.packets.PacketPlayerInfo;

public class PacketTypePlayerInfo extends PacketType {

	public PacketTypePlayerInfo() {
		super("player_info");
	}

	public Packet readPacket(String args, Conn origin) {
		String[] sp = args.split(";");
		if (sp.length != 3) {
			System.out.println("WARNING: Invalid arguments length, should be 3");
			return null;
		}
		int[] vals = new int[3];
		try {
			for (int i = 0; i < 3; i++)
				vals[i] = Integer.parseInt(sp[i]);
		} catch (NumberFormatException e) {
			System.out.println("WARNING: Number expected");
			return null;
		}
		return new PacketPlayerInfo(vals[0], vals[1], vals[2], origin);
	}

	public void writePacket(Packet packet, BufferedWriter out) throws IOException {
		if (packet instanceof PacketPlayerInfo) {
			writePacket((PacketPlayerInfo) packet, out);
		} else {
			System.out.println("WARNING: Packet not sended");
		}
	}

	public void writePacket(PacketPlayerInfo packet, BufferedWriter out) throws IOException {
		out.write(name + ";" + packet.playersCount + ";" + packet.charactersCount + ";" + packet.playerID + "\n");
		out.flush();
	}
}
