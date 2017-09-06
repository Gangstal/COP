package com.hamsterfurtif.cop.packets.types;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.map.Map;
import com.hamsterfurtif.cop.map.MapReader;
import com.hamsterfurtif.cop.packets.Packet;
import com.hamsterfurtif.cop.packets.PacketSendSettings;

public class PacketTypeSendSettings extends PacketType {

	public PacketTypeSendSettings() {
		super("send_settings");
	}

	public Packet readPacket(String args, Conn origin) {
		String[] sp = args.split(";");
		if (sp.length != 3) {
			System.out.println("WARNING: Invalid arguments length, should be 3");
			return null;
		}
		int[] vals = new int[2];
		try {
			for (int i = 0; i < 2; i++)
				vals[i] = Integer.parseInt(sp[1 + i]);
		} catch (NumberFormatException e) {
			System.out.println("WARNING: Number expected");
			return null;
		}
		Map map;
		try {
			BufferedReader br = new BufferedReader(new StringReader(sp[0].replaceAll("%2", "\n").replaceAll("%1", ";").replaceAll("%0", "%")));
			map = MapReader.readMap(br);
			br.close();
		} catch (IOException | NumberFormatException e) {
			throw new RuntimeException(e);
		}
		return new PacketSendSettings(map, vals[0], vals[1], origin);
	}

	public void writePacket(Packet packet, BufferedWriter out) throws IOException {
		if (packet instanceof PacketSendSettings) {
			writePacket((PacketSendSettings) packet, out);
		} else {
			System.out.println("WARNING: Packet not sended");
		}
	}

	public void writePacket(PacketSendSettings packet, BufferedWriter out) throws IOException {
		StringWriter sw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(sw);
		MapReader.writeMap(packet.map, bw);
		bw.close();
		out.write(name + ";" + sw.toString().replaceAll("%", "%0").replaceAll(";", "%1").replaceAll("\n", "%2") + ";" + packet.maxHP + ";" + packet.maxSpawn + "\n");
		out.flush();
	}

}
