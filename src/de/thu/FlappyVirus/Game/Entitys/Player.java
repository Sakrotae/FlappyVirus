package de.thu.FlappyVirus.Game.Entitys;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import de.thu.FlappyVirus.Engine.Back.GLocation;
import de.thu.FlappyVirus.Engine.Back.GPlayer;
import de.thu.FlappyVirus.Engine.Back.Events.GEventHandler.GEventMethod;
import de.thu.FlappyVirus.Engine.Back.Events.GEventHandler.GEventMethod.GEventPriority;
import de.thu.FlappyVirus.Engine.Back.Events.Essential.HandleObjectsGEvent;
import de.thu.FlappyVirus.Engine.Back.Events.Essential.KeyPressedGEvent;
import de.thu.FlappyVirus.Engine.Front.GAnimatedTexture;
import de.thu.FlappyVirus.Engine.Front.GTexture;

public class Player extends GPlayer {
	GTexture spritze;
	long lastShot = System.currentTimeMillis();
	

	public Player(GLocation location, GLocation viewCenter) {
		super(location, viewCenter, 3, 8);
		texture = new GAnimatedTexture("/textures/covid-19.png");
		sizeFaktor = 0.4;
	}
	
	@Override
	public void draw(BufferedImage image, ImageObserver observer,
			GLocation viewCenter, double screenFactor) {
		super.draw(image, observer, viewCenter, screenFactor);
		}

	@GEventMethod(ignoreCancelled = true, priority = GEventPriority.HIGHER)
	public void on(KeyPressedGEvent event) {
		switch (event.getKey()) {
		case KeyEvent.VK_SPACE:
			getVelocity().add(0.0, -40);
			break;
		default:
			break;
		}
	}
	
	
	
	@GEventMethod
	public void on(HandleObjectsGEvent event) {
		
		if(this.getVelocity().getPower() < 4) {
			this.addVelocity(0.0, 5.5);
		}
	}
}
