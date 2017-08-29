package com.hamsterfurtif.cop.display.poseffects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.hamsterfurtif.cop.gamestates.GSGame;
import com.hamsterfurtif.cop.map.MapPos;

public class MouseHover extends PosEffect{

	public MouseHover(MapPos pos) {
		super(pos);
	}

	@Override
	public void render(Graphics g, int x, int y){
		g.setColor(Color.black);
		g.drawRect(x, y, 16*GSGame.scale-1, 16*GSGame.scale-1);
		
	}
}
