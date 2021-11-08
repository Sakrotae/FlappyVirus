package de.thu.FlappyVirus.Engine;

import java.util.ArrayList;

public class GInput {

	private ArrayList<Character> pressedKeys;
	private ArrayList<GMouseClick> mouseClicks;
	private boolean cursorInFrame;

	public GInput() {
		pressedKeys = new ArrayList<Character>();
		mouseClicks = new ArrayList<GMouseClick>();
	}

	public void add(Character key) {
		if (!pressedKeys.contains(key))
			pressedKeys.add(key);
	}

	public void remove(Character key) {
		if (pressedKeys.contains(key))
			pressedKeys.remove(key);
	}

	public void add(GMouseClick click) {
		mouseClicks.add(click);
	}

	public ArrayList<Character> getPressedKeys() {
		return pressedKeys;
	}

	public ArrayList<GMouseClick> getMouseClicks() {
		return mouseClicks;
	}

	public void clearPressedKeys() {
		pressedKeys.clear();
	}

	public void clearMouseClicks() {
		mouseClicks.clear();
	}

	public boolean isCursorInFrame() {
		return cursorInFrame;
	}

	public void setCursorInFrame(boolean cursorInFrame) {
		this.cursorInFrame = cursorInFrame;
	}

	public static class GMouseClick {
		
		int mousePressedX;
		int mousePressedY;

		public GMouseClick(int mousePressedX, int mousePressedY) {
			this.mousePressedX = mousePressedX;
			this.mousePressedY = mousePressedY;
		}

		public int getMousePressedX() {
			return mousePressedX;
		}

		public int getMousePressedY() {
			return mousePressedY;
		}
	}
}
