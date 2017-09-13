package com.hamsterfurtif.cop.display.poseffects;

import org.newdawn.slick.Color;
import com.hamsterfurtif.cop.map.MapPos;

public class MoveSelect extends PosEffectImpl {
	public static final Color orange = new Color(Color.orange.r, Color.orange.g, Color.orange.b, 0.25f);

	public MoveSelect(MapPos pos) {
		super(pos, orange);
	}
}
