package com.hamsterfurtif.cop.display;

import java.util.LinkedList;
import java.util.Queue;

import com.hamsterfurtif.cop.map.Map;
import com.hamsterfurtif.cop.map.MapEventListener;
import com.hamsterfurtif.cop.map.MapPos;

public class MapEventListenerQueued implements MapEventListener {
	public final Queue<MapPos> tileChangedQueue;

	public MapEventListenerQueued() {
		tileChangedQueue = new LinkedList<MapPos>();
	}

	public void onTileChanged(Map map, MapPos pos) {
		synchronized (tileChangedQueue) {
			tileChangedQueue.add(pos);
		}
	}
}
