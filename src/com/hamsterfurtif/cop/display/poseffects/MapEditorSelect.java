package com.hamsterfurtif.cop.display.poseffects;

import org.newdawn.slick.Color;

import com.hamsterfurtif.cop.gamestates.GSMapEditor.Edit;
import com.hamsterfurtif.cop.map.MapPos;

public class MapEditorSelect extends PosEffectImpl{
	public static final Color green = new Color(Color.green.r, Color.green.g, Color.green.b, 0.125f);
	public static final Color red   = new Color(Color.red.r,   Color.red.g,   Color.red.b,   0.125f);
	public static final Color blue  = new Color(Color.blue.r,  Color.blue.g,  Color.blue.b,  0.125f);

	public static Color colorFromMode(Edit mode) {
		switch (mode){
		case tile:
		default:
			return green;
		case line:
			return blue;
		case square:
			return red;
		}
	}

	public MapEditorSelect(MapPos pos, Edit mode) {
		super(pos, colorFromMode(mode));
	}
}
