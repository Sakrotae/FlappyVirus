package de.thu.FlappyVirus.Engine.Back.Events;


public abstract class GEvent {

	protected boolean isCancelled;
	double diff;

	public GEvent(double diff) {
		isCancelled = false;
		this.diff = diff;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public double getDiff() {
		return diff;
	}

	public void setDiff(double diff) {
		this.diff = diff;
	}
}
