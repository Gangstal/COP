package com.hamsterfurtif.cop;

public final class Collisions {

	public static boolean isInSegment(int x1, int x2, int w) {
		return x1 >= x2 && x1 < x2 + w;
	}

	public static boolean isInRect(int x1, int y1, int x2, int y2, int w, int h) {
		return isInSegment(x1, x2, w) && isInSegment(y1, y2, h);
	}
}
