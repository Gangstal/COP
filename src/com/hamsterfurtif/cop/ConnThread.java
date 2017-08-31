package com.hamsterfurtif.cop;

public class ConnThread extends Thread {
	public Conn conn;

	public ConnThread(Conn conn) {
		this.conn = conn;
	}

	public void run() {
		try {
			while (true) {
				String packet = conn.in.readLine();
				System.out.println("Packet received: [" + packet + "]");
				synchronized (COP.packets) {
					COP.packets.add(packet);
				}
				COP.sendPacket(packet, conn);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
