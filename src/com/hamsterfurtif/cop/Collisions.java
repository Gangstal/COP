package com.hamsterfurtif.cop;

public final class Collisions {

	public static boolean isInSegment(int x1, int x2, int w) {
		return x1 >= x2 && x1 < x2 + w;
	}

	public static boolean isInRect(int x1, int y1, int x2, int y2, int w, int h) {
		return isInSegment(x1, x2, w) && isInSegment(y1, y2, h);
	}

	public static boolean segment(int x1, int w1, int x2, int w2) {
		return x1 + w1 > x2 && x2 + w2 > x1;
	}

	public static boolean AABB(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
		return segment(x1, w1, x2, w2) && segment(y1, h1, y2, h2);
	}
}
