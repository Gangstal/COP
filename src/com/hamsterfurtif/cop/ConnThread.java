package com.hamsterfurtif.cop;

import java.io.IOException;

import com.hamsterfurtif.cop.packets.Packet;
import com.hamsterfurtif.cop.packets.types.PacketTypes;

public class ConnThread extends Thread {
	public Conn conn;

	public ConnThread(Conn conn) {
		this.conn = conn;
	}

	public void run() {
		try {
			Packet packet;
			while (true) {
				if ((packet = PacketTypes.readPacket(conn.in, conn)) != null) {
					synchronized (COP.packets) {
						COP.packets.add(packet);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Connection reset (" + e.getLocalizedMessage() + ")");
			try {
				conn.socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
