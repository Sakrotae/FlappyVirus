package de.thu.FlappyVirus.Game.Entitys;


import de.thu.FlappyVirus.Engine.Back.GEntity;
import de.thu.FlappyVirus.Engine.Back.GLocation;
import de.thu.FlappyVirus.Engine.Back.Events.GEventHandler.GEventListener;
import de.thu.FlappyVirus.Engine.Back.Events.GEventHandler.GEventMethod;
import de.thu.FlappyVirus.Engine.Back.Events.Essential.HandleObjectsGEvent;
import de.thu.FlappyVirus.Engine.Front.GTexture;

public class WhiteBloodcell extends GEntity implements GEventListener {
	long ts_created = System.currentTimeMillis();
	double frameStep;
	boolean movingUp;
	int velocity;

	public WhiteBloodcell(GLocation location, double speed, double frameStep, boolean movingUp, int velocity) {
		super(location, GLayor.MAIN3, speed, 3);
		hitRadius = 3;
		texture = new GTexture("/textures/bloodcell_single.png");
		sizeFaktor = 0.4;
		this.frameStep = frameStep;
		this.movingUp = movingUp;
		this.velocity = velocity;
		getLocation().setAngle(-1.57f);
	}
	public WhiteBloodcell(GLocation location, double speed) {
		super(location, GLayor.MAIN3, speed, 3);
		hitRadius = 3;
		texture = new GTexture("/textures/bloodcell_single.png");
		sizeFaktor = 0.4;
		frameStep = -10;
		movingUp = true;
		getLocation().setAngle(-1.57f);
		int velocity = 25;
	}
	
	@Override
	public boolean isMoving() {
		return true;
	}
	
	@GEventMethod
	public void on(HandleObjectsGEvent event) {
		
		if(movingUp && getLocation().getY() < -40 ) {
			getLocation().setAngle(getLocation().getAngle() * (-1));
			movingUp = false;
			frameStep = -frameStep;
		}
		else if(!movingUp && getLocation().getY() > 45) {
			getLocation().setAngle(getLocation().getAngle() * (-1));
			movingUp = true;
			frameStep = -frameStep;
		}
			
		getLocation().addY(frameStep*event.getDiff());
		getLocation().addX(-velocity*event.getDiff());

	}

}
