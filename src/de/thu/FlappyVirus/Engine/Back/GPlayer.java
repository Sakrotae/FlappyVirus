package de.thu.FlappyVirus.Engine.Back;

import de.thu.FlappyVirus.Engine.Back.Events.GEventHandler;

public abstract class GPlayer extends GEntity {
	
	GLocation viewCenter;
	public GPlayer(GLocation location, 	GLocation viewCenter, double speed, double hitRadius) {
		super(location, GLayor.MAIN4, speed, hitRadius);
		this.viewCenter = viewCenter;
		GEventHandler.register(this);
	}
	
	
}
