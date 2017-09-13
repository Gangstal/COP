package com.hamsterfurtif.cop.display.poseffects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.gamestates.GSMap;
import com.hamsterfurtif.cop.map.MapPos;

public class MouseHover extends PosEffectImpl{

	public MouseHover(MapPos pos, Color color) {
		super(pos, color);
	}

	public void render(Graphics g){
		g.setColor(color);
		g.setLineWidth(1.0f);
		g.drawRect(0.0f, 0.0f, TextureLoader.size - 1.0f / GSMap.scale, TextureLoader.size - 1.0f / GSMap.scale);
	}
}
