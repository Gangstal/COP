package com.hamsterfurtif.cop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.hamsterfurtif.cop.packets.Packet;

public class Conn {
	public Player player;
	public final Socket socket;
	public final BufferedReader in;
	public final BufferedWriter out;

	public Conn(Socket socket) {
		player = null;
		try {
			this.socket = socket;
			in  = new BufferedReader(new InputStreamReader (socket.getInputStream ()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			new ConnThread(this).start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void send(Packet packet) throws IOException {
		packet.send(out);
	}
}
