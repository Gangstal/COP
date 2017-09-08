package com.hamsterfurtif.cop.packets;

import java.io.BufferedWriter;
import java.io.IOException;

import com.hamsterfurtif.cop.Conn;

public class PacketPlayerInfo extends Packet {
	public static final String PACKET_ID = "player_info";

	public static Packet read(String args, Conn origin) {
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

	public static void write(PacketPlayerInfo packet, BufferedWriter out) throws IOException {
		out.write(packet.playersCount + ";" + packet.charactersCount + ";" + packet.playerID);
	}

	public final int playersCount, charactersCount;
	public final int playerID;

	public PacketPlayerInfo(int playersCount, int charactersCount, int playerID) {
		this(playersCount, charactersCount, playerID, null);
	}

	public PacketPlayerInfo(int playersCount, int charactersCount, int playerID, Conn origin) {
		super(origin);
		this.playersCount = playersCount;
		this.charactersCount = charactersCount;
		this.playerID = playerID;
	}
}
