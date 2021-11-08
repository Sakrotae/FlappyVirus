package de.thu.FlappyVirus.Engine.Back.Events;

import de.thu.FlappyVirus.Engine.Back.GObject;

public class ObjectsCollideEvent extends GEvent {
	GObject obj1, obj2;

	public ObjectsCollideEvent(double diff, GObject obj1, GObject obj2) {
		super(diff);
		this.obj1 = obj1;
		this.obj2 = obj2;
	}
	
	public GObject getObj1() {
		return this.obj1;
	}
	
	public GObject getObj2() {
		return this.obj2;
	}

}
