package com.hamsterfurtif.cop.packets.types;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.packets.Packet;

public class PacketTypes {
	private static Map<String, PacketType> packetTypes = new HashMap<String, PacketType>();

	public static final PacketType nextPlayer		= putPacket(new PacketTypeNextPlayer());
	public static final PacketType changeWeapon		= putPacket(new PacketTypeChangeWeapon());
	public static final PacketType reload			= putPacket(new PacketTypeReload());
	public static final PacketType startMovement	= putPacket(new PacketTypeStartMovement());
	public static final PacketType cancelMovement	= putPacket(new PacketTypeCancelMovement());
	public static final PacketType removeLastPos	= putPacket(new PacketTypeRemoveLastPos());
	public static final PacketType addPos			= putPacket(new PacketTypeAddPos());
	public static final PacketType shoot			= putPacket(new PacketTypeShoot());
	public static final PacketType characterReady	= putPacket(new PacketTypeCharacterReady());
	public static final PacketType playerInfo		= putPacket(new PacketTypePlayerInfo());
	public static final PacketType sendSettings		= putPacket(new PacketTypeSendSettings());

	private static PacketType putPacket(PacketType pt) {
		packetTypes.put(pt.name, pt);
		return pt;
	}

	public static Packet readPacket(BufferedReader br) throws IOException {
		return readPacket(br, null);
	}

	public static Packet readPacket(BufferedReader br, Conn origin) throws IOException {
		String line = br.readLine();
		int ind = line.indexOf(";");
		String type, args;
		if (ind >= 0) {
			type = line.substring(0, ind);
			args = line.substring(ind + 1);
		} else {
			type = line;
			args = "";
		}
		PacketType pt = packetTypes.get(type);
		if (pt == null) {
			System.out.println("WARNING: Received unknown packet \"" + type + "\"");
			return null;
		}
		return pt.readPacket(args, origin);
	}
}
