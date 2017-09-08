package com.hamsterfurtif.cop.packets;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.hamsterfurtif.cop.Conn;
import com.hamsterfurtif.cop.Utils;

public class Packet {
	public final Conn origin;

	public Packet(Conn origin) {
		this.origin = origin;
	}

	public void send(BufferedWriter out) throws IOException {
		try {
			out.write(Utils.getPacketID(this));
			out.write(";");
			getClass().getDeclaredMethod("write", getClass(), BufferedWriter.class).invoke(null, this, out);
			out.write("\n");
			out.flush();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
}
