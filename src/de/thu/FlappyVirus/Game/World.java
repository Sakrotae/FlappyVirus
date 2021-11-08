package de.thu.FlappyVirus.Game;

import java.util.Random;

import de.thu.FlappyVirus.Engine.Back.GContent;
import de.thu.FlappyVirus.Engine.Back.GEntity;
import de.thu.FlappyVirus.Engine.Back.GLocation;
import de.thu.FlappyVirus.Engine.Back.GObject;
import de.thu.FlappyVirus.Engine.Back.Events.GEventHandler.GEventListener;
import de.thu.FlappyVirus.Engine.Back.Events.GEventHandler.GEventMethod;
import de.thu.FlappyVirus.Engine.Back.Events.ObjectsCollideEvent;
import de.thu.FlappyVirus.Engine.Back.Events.Essential.HandleObjectsGEvent;
import de.thu.FlappyVirus.Game.Entitys.Player;
import de.thu.FlappyVirus.Game.Entitys.WhiteBloodcell;

public class World extends GContent implements GEventListener {
	
	Random random;
	Player player;
	boolean hit = false;

	long ts_lastCleanObjects;
	
	
	public World() {
		random = new Random();
		ts_lastCleanObjects = System.currentTimeMillis();
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	
	public boolean gothit() {
		return this.hit;
	}
	

	public void sethit(boolean hit) {
		this.hit = hit;
	}
	
	// killt Objekte, die zu weit vom Player entfernt sind
	private void cleanObjects() {
		GLocation locationPlayer = this.getPlayer().getLocation();
		double x = locationPlayer.getX();
		double y = locationPlayer.getY();
		
		GLocation locationObj;
		for(GObject obj : this.getObjects()) {
			locationObj = obj.getLocation();
			double diffX = x - locationObj.getX();
			double diffY = y - locationObj.getY();
			if(diffX > 1000 || diffX < -1000
					|| diffY > 1000 || diffY < -1000) {
				((GEntity) obj).kill();}
		}
	}
	
	@GEventMethod()
	public void on(HandleObjectsGEvent event) {
		long currentTime = System.currentTimeMillis();
		
		if(currentTime -  ts_lastCleanObjects > 1000) {
			if(this.player != null)
				cleanObjects();
			ts_lastCleanObjects = currentTime;
		}
		
	}
	
	// Behandelt alle Berührungen zwischen den Spielobjekten
	@GEventMethod()
	public void on(ObjectsCollideEvent event) {
		
		GObject object = event.getObj1();
		GObject other = event.getObj2();
		
		// Player trifft weißes Blutkörperchen
		if((object instanceof Player && other instanceof WhiteBloodcell)||
			(other instanceof Player && object instanceof WhiteBloodcell)	) {
				System.out.println("Getroffen");
				this.sethit(true);
				
			}
	}

	public void setPlayer(Player player) {
		this.player = player;
		
	}

	
}
