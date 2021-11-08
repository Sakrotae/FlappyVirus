package de.thu.FlappyVirus.Engine.Back.Events.Essential;

import de.thu.FlappyVirus.Engine.Back.GContent;
import de.thu.FlappyVirus.Engine.Back.Events.GEvent;

public class KeyPressedGEvent extends GEvent {

	char key;
	GContent world;
	
	public KeyPressedGEvent(double diff, char key, GContent world) {
		super(diff);
		this.key = key;
		this.world = world;
	}
	
	public char getKey() {
		return key;
	}
	
	public GContent getWorld() {
		return this.world;
	}
}
