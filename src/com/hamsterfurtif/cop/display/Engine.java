package com.hamsterfurtif.cop.display;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.function.Predicate;

import org.newdawn.slick.Graphics;

import com.hamsterfurtif.cop.display.poseffects.PosEffect;
import com.hamsterfurtif.cop.map.Map;

public class Engine {
	//RealEngine 1.0 Copyright 1970
	//Designed by Hamster_Furtif, programmed by gaston147

	public static final java.util.Map<Map, MapRenderer> mapRenderers = new WeakHashMap<Map, MapRenderer>();
	public static final List<PosEffect> posEffects = new ArrayList<PosEffect>();

	public static void drawMap(Graphics g, Map map){
		drawMap(g, 1, 0, 0, false, map, false);
	}

	public static void drawMap(Graphics g, float scale, int posX, int posY, boolean squares, Map map, boolean withPlayer){
		if (!mapRenderers.containsKey(map))
			mapRenderers.put(map, new MapRenderer(map));
		MapRenderer mr = mapRenderers.get(map);
		g.pushTransform();
		g.resetTransform();
		g.translate(posX, posY);
		g.scale(scale, scale);
		mr.setSquares(squares);
		mr.setWithPlayer(withPlayer);
		mr.render(g);
		g.popTransform();
	}

	public static boolean addPosEffect(PosEffect effect){
		for(PosEffect e : posEffects)
			if(e.equals(effect))
				return false;
		posEffects.add(effect);
		return true;
	}

	public static void removePosEffect(Class<?> c){
		posEffects.removeIf(new Predicate<Object>() {
			public boolean test(Object o) {
				return o.getClass() == c;
			}
		});
	}

}
