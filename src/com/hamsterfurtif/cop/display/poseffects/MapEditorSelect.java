package com.hamsterfurtif.cop.display.poseffects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.hamsterfurtif.cop.gamestates.Game;
import com.hamsterfurtif.cop.gamestates.GSMapEditor.Edit;
import com.hamsterfurtif.cop.map.MapPos;

public class MapEditorSelect extends PosEffect{

	private Color color;

	public MapEditorSelect(MapPos pos, Edit mode) {
		super(pos);
		switch (mode){
		case tile:
		default:
			color = new Color(Color.green);
			break;
		case line:
			color = new Color(Color.blue);
			break;
		case square:
			color = new Color(Color.red);
			break;
		}
		color.a = 0.125f;
	}
	
	@Override
	public void render(Graphics g, int x, int y){
		g.setColor(color);
		g.fillRect(x, y, 16*Game.scale, 16*Game.scale);
	}

}
