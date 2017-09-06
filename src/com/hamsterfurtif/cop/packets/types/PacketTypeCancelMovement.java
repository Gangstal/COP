package com.hamsterfurtif.cop.packets.types;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.packets.Packet;
import com.hamsterfurtif.cop.packets.PacketCancelMovement;

public class PacketTypeCancelMovement extends PacketType {

	public PacketTypeCancelMovement() {
		super("cancel_movement");
	}

	public Packet readPacket(String args, Conn origin) {
		if (args.length() != 0) {
			System.out.println("WARNING: No arguments expected");
			return null;
		}
		return new PacketCancelMovement(origin);
	}

	public void writePacket(Packet packet, BufferedWriter out) throws IOException {
		if (packet instanceof PacketCancelMovement) {
			writePacket((PacketCancelMovement) packet, out);
		} else {
			System.out.println("WARNING: Packet not sended");
		}
	}

	public void writePacket(PacketCancelMovement packet, BufferedWriter out) throws IOException {
		out.write(name + "\n");
		out.flush();
	}
}
