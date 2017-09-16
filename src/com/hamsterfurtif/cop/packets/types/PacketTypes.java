package com.hamsterfurtif.cop.packets.types;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.packets.Packet;
import com.hamsterfurtif.cop.packets.PacketAddPos;
import com.hamsterfurtif.cop.packets.PacketCancelMovement;
import com.hamsterfurtif.cop.packets.PacketChangeWeapon;
import com.hamsterfurtif.cop.packets.PacketCharacterReady;
import com.hamsterfurtif.cop.packets.PacketNextCharacter;
import com.hamsterfurtif.cop.packets.PacketPlayerInfo;
import com.hamsterfurtif.cop.packets.PacketReload;
import com.hamsterfurtif.cop.packets.PacketRemoveLastPos;
import com.hamsterfurtif.cop.packets.PacketSendSettings;
import com.hamsterfurtif.cop.packets.PacketServerFull;
import com.hamsterfurtif.cop.packets.PacketShoot;
import com.hamsterfurtif.cop.packets.PacketStartMovement;

public class PacketTypes {
	private static Map<String, Class<? extends Packet>> packetTypes = new HashMap<String, Class<? extends Packet>>();

	public static final Class<? extends Packet> nextCharacter	= putPacket(PacketNextCharacter.class);
	public static final Class<? extends Packet> changeWeapon	= putPacket(PacketChangeWeapon.class);
	public static final Class<? extends Packet> reload			= putPacket(PacketReload.class);
	public static final Class<? extends Packet> startMovement	= putPacket(PacketStartMovement.class);
	public static final Class<? extends Packet> cancelMovement	= putPacket(PacketCancelMovement.class);
	public static final Class<? extends Packet> removeLastPos	= putPacket(PacketRemoveLastPos.class);
	public static final Class<? extends Packet> addPos			= putPacket(PacketAddPos.class);
	public static final Class<? extends Packet> shoot			= putPacket(PacketShoot.class);
	public static final Class<? extends Packet> characterReady	= putPacket(PacketCharacterReady.class);
	public static final Class<? extends Packet> playerInfo		= putPacket(PacketPlayerInfo.class);
	public static final Class<? extends Packet> sendSettings	= putPacket(PacketSendSettings.class);
	public static final Class<? extends Packet> serverFull		= putPacket(PacketServerFull.class);

	private static Class<? extends Packet> putPacket(Class<? extends Packet> pt) {
		try {
			packetTypes.put((String) pt.getDeclaredField("PACKET_ID").get(null), pt);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			throw new RuntimeException(e);
		}
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
		Class<? extends Packet> pt = packetTypes.get(type);
		if (pt == null) {
			System.out.println("WARNING: Received unknown packet \"" + type + "\"");
			return null;
		}
		try {
			return (Packet) pt.getDeclaredMethod("read", String.class, Conn.class).invoke(null, args, origin);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
}
