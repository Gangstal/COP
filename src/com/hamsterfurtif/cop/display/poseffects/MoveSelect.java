package com.hamsterfurtif.cop.display.poseffects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.map.MapPos;

public class MoveSelect extends PosEffect {

	public MoveSelect(MapPos pos) {
		super(pos);
	}
	
	@Override
	public void render(Graphics g, int x, int y){
		Color color = new Color(Color.orange);
		color.a = 0.25f;
		g.setColor(color);
		g.fillRect(x, y, 16*Game.scale, 16*Game.scale);
		
	}

}
