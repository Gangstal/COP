package com.hamsterfurtif.cop.display.poseffects;

import org.newdawn.slick.Graphics;

import com.hamsterfurtif.cop.map.MapPos;

public abstract class PosEffect {
	
	public MapPos pos;
	
	public PosEffect(MapPos pos){
		this.pos = pos;
	}
	
	public void render(Graphics g, int x, int y){}

	public boolean equals(PosEffect e){
		return e.pos.equals(pos) && e.getClass()==this.getClass();
	}
}
