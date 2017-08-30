package com.hamsterfurtif.cop.display.poseffects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.map.MapPos;

public class MouseHover extends PosEffect{

	public Color color;
	
	public MouseHover(MapPos pos, Color color) {
		super(pos);
		this.color=color;
	}

	@Override
	public void render(Graphics g, int x, int y){
		g.setColor(color);
		g.drawRect(x, y, 16*Game.scale-1, 16*Game.scale-1);
	}
}
