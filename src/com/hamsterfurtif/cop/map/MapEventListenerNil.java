package com.hamsterfurtif.cop.map;

public class MapEventListenerNil implements MapEventListener {
	public static MapEventListener nil = new MapEventListenerNil();

	public void onTileChanged(Map map, MapPos pos) {
	}
}
