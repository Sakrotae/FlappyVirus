package de.thu.FlappyVirus.Engine.Back;

import de.thu.FlappyVirus.Engine.GConsts;
import de.thu.FlappyVirus.Engine.Back.Events.GEventHandler;
import de.thu.FlappyVirus.Engine.Back.Events.Essential.HandleObjectsGEvent;
import de.thu.FlappyVirus.Engine.Back.Events.GEventHandler.GEventListener;
import de.thu.FlappyVirus.Engine.Back.Events.GEventHandler.GEventMethod;
import de.thu.FlappyVirus.Engine.Back.Events.GEventHandler.GEventMethod.GEventPriority;

public abstract class GEntity extends GObject implements GEventListener{
	
	GVelocity velocity;
	protected double speed;
	private boolean isDead;
	
	public GEntity(GLocation location, GLayor layor, double speed, double hitRadius) {
		super(location, layor, true, true, hitRadius);
		
		this.speed = speed;
		isDead = false;
		velocity = new GVelocity();
		GEventHandler.register(this);
	}
	
	@Override
	public void handle(double diff, GContent world) {
		double addX = velocity.getX() * diff * 10;
		double addY = velocity.getY() * diff * 10;
		getLocation().addX(addX);
		getLocation().addY(addY);
	}

	public GVelocity getVelocity() {
		return velocity;
	}
	
	public void addVelocity(double x, double y) {
		velocity.add(x, y);
	}
	
	public void addVelocity(float angle, double power) {
		velocity.add(angle, power);
		
	}

	public void setVelocity(GVelocity velocity) {
		this.velocity = velocity;
	}
	
	@GEventMethod(ignoreCancelled = true, priority = GEventPriority.FOURTWENTY)
	public void on(HandleObjectsGEvent event) {
		velocity.setPower(velocity.getPower() - velocity.getPower() * GConsts.R * event.getDiff());
	}

	public boolean isDead() {
		return isDead;
	}

	public void kill() {
		this.isDead = true;
	}
	
	public void setAlive() {
		this.isDead = false;
	}
	
	@Override
	public boolean isMoving() {
		return getVelocity().getPower() > 0;
	}
}
