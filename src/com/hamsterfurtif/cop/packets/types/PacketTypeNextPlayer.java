package com.hamsterfurtif.cop.packets.types;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.packets.Packet;
import com.hamsterfurtif.cop.packets.PacketNextPlayer;

public class PacketTypeNextPlayer extends PacketType {

	public PacketTypeNextPlayer() {
		super("next_player");
	}

	public Packet readPacket(String args, Conn origin) {
		if (args.length() != 0) {
			System.out.println("WARNING: No arguments expected");
			return null;
		}
		return new PacketNextPlayer(origin);
	}

	public void writePacket(Packet packet, BufferedWriter out) throws IOException {
		if (packet instanceof PacketNextPlayer) {
			out.write(name + "\n");
			out.flush();
		} else {
			System.out.println("WARNING: Packet not sended");
		}
	}
}
