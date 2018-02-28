package org.techm.algorithm;

public class Position {

	public int x;
	public int y;
	public Position prevPos;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position(int x, int y, Position prevPos) {
		this(x, y);
		this.prevPos = prevPos;
	}
}
