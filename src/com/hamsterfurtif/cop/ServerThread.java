package com.hamsterfurtif.cop;

import java.io.IOException;

public class ServerThread extends Thread {

	public void run() {
		try {
			while (true) {
				COP.conns.add(new Conn(COP.serverSocket.accept()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
