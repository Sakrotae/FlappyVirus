package de.thu.FlappyVirus.Engine.Back;

import java.util.Random;

public class GVelocity {

	private float angle;
	private double power;
	private double x, y;

	public GVelocity(float angle, double power) {
		this.angle = angle;
		this.power = power;
		updateXY();
	}

	public GVelocity(double x, double y) {
		this.x = x;
		this.y = y;
		updateAP();
	}

	public GVelocity() {
		this.angle = (float) (new Random().nextFloat() * 2 * Math.PI);
		this.power = 0D;
		updateXY();
	}

	/**
	 * Adjusts the x and y values to the power and the angle
	 */
	private void updateXY() {
		fixAngle();
		x = Math.cos(angle);
		y = Math.sin(angle);
		double c = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		x = x * power / c;
		y = y * power / c;
	}

	/**
	 * Adjusts the power and the angle to the x and y values
	 */
	private void updateAP() {
		angle = (float) Math.atan2(y,+ x);
		power = power - Math.sqrt(power); 
	}

	/*
	 * Adjusts the angle of the usable radian
	 */
	private float fixAngle() {
		angle = (float) Math.atan2(Math.sin(angle), Math.cos(angle));
		return angle;
	}

	/**
	 * lets the velocity tend in a new given direction
	 * 
	 * @param x
	 * @param y
	 */
	public void add(double x, double y) {
		this.x += (x - this.x) / 40;
		this.y += (y - this.y) / 40;
		updateAP();
	}

	/**
	 * lets the velocity tend in a new given direction
	 * 
	 * @param angle
	 * @param power
	 */
	public void add(float angle, double power) {
		double x = Math.cos(angle);
		double y = Math.sin(angle);
		double c = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		x = x * power / c;
		y = y * power / c;
		add(x, y);
	}

	/*
	 * getters & setters...
	 */

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
		updateXY();
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
		updateAP();
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		updateAP();
	}
}
