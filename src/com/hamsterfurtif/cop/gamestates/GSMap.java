package com.hamsterfurtif.cop.gamestates;

import com.hamsterfurtif.cop.COP;
import com.hamsterfurtif.cop.Collisions;
import com.hamsterfurtif.cop.display.TextureLoader;
import com.hamsterfurtif.cop.map.Map;
import com.hamsterfurtif.cop.map.MapPos;

public abstract class GSMap extends GameStateMenu {
	public static float optimalScale, scale, scalePow, scalePowVel;
	public static float mapDiffx = 0.0f, mapDiffy = 0.0f;
	public static int zoomx = 0, zoomy = 0;
	public static Map map;
	public static String mapname;
	public static int mapx, mapy;
	public static long lastTime = -1;

	public void updateScale() {
		float scale = GSMap.scale;
		long currentTime = System.currentTimeMillis();
		if (lastTime == -1)
			lastTime = currentTime;
		float delta = (float) (currentTime - lastTime) / 1000.0f;
		lastTime = currentTime;
		scalePowVel -= scalePowVel * 9f * delta;
		if (-1.0E-1 <= scalePowVel && scalePowVel <= 1.0E-1)
			scalePowVel = 0.0f;
		scalePow += scalePowVel * delta;
		float min = (float) (Math.log(optimalScale) / Math.log(1.5f));
		float max = (float) (Math.log(10f) / Math.log(1.5f));
		if (scalePow < min) {
			scalePowVel = 0;
			scalePow = min;
		}
		if (scalePow > max) {
			scalePowVel = 0;
			scalePow = max;
		}
		scale = (float) Math.pow(1.5f, scalePow);
		mapDiffx += (float) (zoomx - mapx) * (scale / GSMap.scale - 1.0f);
		mapDiffy += (float) (zoomy - mapy) * (scale / GSMap.scale - 1.0f);
		int iMapDiffx = (int) mapDiffx, iMapDiffy = (int) mapDiffy;
		mapx -= iMapDiffx;
		mapy -= iMapDiffy;
		mapDiffx -= iMapDiffx;
		mapDiffy -= iMapDiffy;
		GSMap.scale = scale;
		correctMapLoc();
	}

	protected MapPos getMapPos(int xpos, int ypos){
		int X = (int) ((float) (xpos - mapx + 168) / (scale * (float) TextureLoader.size));
		int Y = (int) ((float) (ypos - mapy +   0) / (scale * (float) TextureLoader.size));
		return new MapPos(X, Y, 0);
	}

	public void mouseWheelMoved(int offset) {
		scalePowVel += (offset >= 0.0f ? 5f : -5f);
		zoomx = container.getInput().getMouseX();
		zoomy = container.getInput().getMouseY();
	}

	public static boolean isMouseOverMap(int mx, int my){
		float c = TextureLoader.size*GSMap.scale;
		return Collisions.isInRect(mx, my, Math.max(168, mapx), Math.max(0, mapy), Math.min(COP.width - 168, (int) (map.dimX * c)), Math.min(COP.height - 120, (int) (map.dimY * c)));
	}

	public void correctMapLoc() {
		int x = 168, y = 0;
		int borderx = (int) (COP.width-x-TextureLoader.size * GSMap.optimalScale*map.dimX)/2;
		int bordery = (int) (480-TextureLoader.size * GSMap.optimalScale*map.dimY)/2;
		float c = (float) TextureLoader.size * GSMap.scale;
		if(mapx>x+borderx)
			mapx=x+borderx;
		else if(mapx<COP.width-borderx-c*map.dimX)
			mapx=(int) (COP.width-borderx-c*map.dimX);
		if(mapy>y+bordery)
			mapy=y+bordery;
		else if(mapy<480-bordery-c*map.dimY)
			mapy=(int) (480-bordery-c*map.dimY);
	}
}
