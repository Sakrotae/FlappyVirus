package de.thu.FlappyVirus.Engine.Back;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Collections;

import de.thu.FlappyVirus.Engine.Back.Events.GEventHandler;
import de.thu.FlappyVirus.Engine.Back.Events.Essential.HandleObjectsGEvent;
import de.thu.FlappyVirus.Engine.Back.GObject.GObjectOrganizer;

public abstract class GContent {

	ArrayList<GObject> objects;
	GLocation viewCenter;
	boolean shouldSort;
	
	GCollisionDetection collisions;

	public GContent() {
		objects = new ArrayList<GObject>();
		viewCenter = new GLocation(0, 0, null);
		shouldSort = true;
		collisions = new GCollisionDetection();
	}

	public void handle(double diff) {
		GEventHandler.call(new HandleObjectsGEvent(diff, objects));
		
		for (int i = 0; i < objects.size(); i++) {
			GObject object = objects.get(i);
			try {
				object.handle(diff/2, this);
			}
			catch(NullPointerException e) {e.printStackTrace();}
				
		}
		
		collisions.detect(objects);
	}
	
	public ArrayList<GObject> getObjects() {
		return objects;
	}
	
	public void deleteDeadObjects() {
		// Entferne alle toten Objekte + töte Objekte, die zu weit außerhalb des Sichtfeldes des Spielers ist
		int num=0;
		int gameSize = this.objects.size();
		while(num < gameSize) {
			if(this.objects.get(num) instanceof GEntity && ((GEntity)this.objects.get(num)).isDead() ) {
				objects.remove(num);
				gameSize--;
			}
			else
				num++;
		}
	}


	public void place(GObject object) {
		objects.add(object);
		shouldSort = true;
	}

	
	public void paint(BufferedImage image, ImageObserver observer) {
		if (shouldSort) {
			try {
				Collections.sort(objects, new GObjectOrganizer());
			}
			catch(java.util.ConcurrentModificationException e) {
				e.printStackTrace();
			}
			
			shouldSort = false;
		}

		for (int i = 0; i < objects.size(); i++) {
			GObject object = objects.get(i);
			double screenFactor = image.getHeight() / 100D;
			
			if (object instanceof GEntity && ((GEntity) object).isDead()) {
				objects.remove(object);
				continue;
			} else if (object.getLocation().distance(viewCenter) < image.getWidth() / screenFactor / 1.5) {
				object.draw(image, observer, viewCenter, screenFactor);
			}
				

		}
	}

	
	public static class GDefaultContent extends GContent {

		@Override
		public void handle(double diff) {
		}

		@Override
		public void paint(BufferedImage image, ImageObserver observer) {
			Graphics graphics = image.getGraphics();
			graphics.setColor(Color.RED);
			graphics.fillRect(image.getWidth() / 4, image.getHeight() / 4, image.getWidth() / 2, image.getHeight() / 2);
		}
	}


	public GLocation getViewCenter() {
		return viewCenter;
	}

	public void setViewCenter(GLocation viewCenter) {
		this.viewCenter = viewCenter;
	}
}
