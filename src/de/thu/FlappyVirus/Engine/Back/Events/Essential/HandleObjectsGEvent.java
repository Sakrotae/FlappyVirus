package de.thu.FlappyVirus.Engine.Back.Events.Essential;

import java.util.ArrayList;

import de.thu.FlappyVirus.Engine.Back.GObject;
import de.thu.FlappyVirus.Engine.Back.Events.GEvent;

public class HandleObjectsGEvent extends GEvent {

	ArrayList<GObject> objects;
	
	public HandleObjectsGEvent(double diff, ArrayList<GObject> objects) {
		super(diff);
		this.objects = objects;
	}

	public ArrayList<GObject> getObjects() {
		return objects;
	}
}
