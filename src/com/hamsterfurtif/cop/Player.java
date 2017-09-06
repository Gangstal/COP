package com.hamsterfurtif.cop;

import com.hamsterfurtif.cop.entities.EntityCharacter;

public class Player {
	public final int id;
	public final String name;
	public final EntityCharacter[] characters;
	public Conn conn;
	public boolean used;

	public Player(int id, String name, EntityCharacter[] characters) {
		this.id = id;
		this.name = name;
		this.characters = characters;
		conn = null;
	}
}
