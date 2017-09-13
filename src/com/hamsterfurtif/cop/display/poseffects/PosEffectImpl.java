package com.hamsterfurtif.cop.display.poseffects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.map.MapPos;

public abstract class PosEffectImpl implements PosEffect {
	public MapPos pos;
	public Color color;

	public PosEffectImpl(MapPos pos, Color color) {
		this.pos = pos;
		this.color = color;
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, TextureLoader.size, TextureLoader.size);
	}

	public MapPos getPos() {
		return pos;
	}

	public boolean equals(Object o){
		return o instanceof PosEffectImpl && ((PosEffectImpl) o).pos.equals(pos) && ((PosEffectImpl) o).getClass()==this.getClass();
	}
}
