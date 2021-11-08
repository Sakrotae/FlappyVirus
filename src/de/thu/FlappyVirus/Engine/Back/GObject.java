package de.thu.FlappyVirus.Engine.Back;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Comparator;
import java.util.UUID;

import de.thu.FlappyVirus.Engine.Front.GTexture;
import de.thu.FlappyVirus.Game.Entitys.Player;

public abstract class GObject {

	private String objectID, fieldID;
	private GLayor layor;
	private GLocation location;
	protected GTexture texture;
	protected double sizeFaktor = 1;
	private boolean moving, collidAble;//, isLiving = true;
	protected double hitRadius;

	public GObject(GLocation location, GLayor layor, boolean moving, boolean collidAble, double hitRadius) {
		this.location = location;
		this.layor = layor;
		this.moving = moving;
		this.collidAble = collidAble;
		this.hitRadius = hitRadius;

		objectID = UUID.randomUUID().toString().substring(0, 5);
		fieldID = "";

		texture = new GTexture("/textures/404.png");

	}
	
	
	public GObject(GLocation location, GLayor layor, boolean moving, boolean collidAble, double hitRadius, double sizeFaktor) {
		this.location = location;
		this.layor = layor;
		this.moving = moving;
		this.collidAble = collidAble;
		this.hitRadius = hitRadius;
		this.sizeFaktor = sizeFaktor;

		objectID = UUID.randomUUID().toString().substring(0, 5);
		fieldID = "";

		texture = new GTexture("/textures/404.png");

	}

	/**
	 * @param image      The entire image of the frame
	 * @param observer   The image observer of frame or panel
	 * @param viewCenter Middle of the "camera"
	 * @param diff 
	 */
	public void draw(BufferedImage image, ImageObserver observer, GLocation viewCenter, double screenFactor) {

		GLocation relative = new GLocation((getLocation().getX() - viewCenter.getX()) * screenFactor,
				(getLocation().getY() - viewCenter.getY()) * screenFactor, viewCenter.getWorld());

		relative.addX(image.getWidth() / 2);
		relative.addY(image.getHeight() / 2);

		Image _texture = getTexture();
		

		int width = (int) (_texture.getWidth(observer) * sizeFaktor * screenFactor);
		int height = (int) (_texture.getHeight(observer)* sizeFaktor * screenFactor);
		int x = (int) relative.getX() - width / 2;
		int y = (int) relative.getY() - height / 2;
		
		if( this instanceof Player){
			_texture = rotate(toBufferedImage(_texture),this.getLocation().getAngle());
			
			image.getGraphics().drawImage(_texture, x, y, width, height, null);
			return;
		}

		image.getGraphics().drawImage(_texture, x, y, width, height, null);
	}
	
	public static BufferedImage rotate(BufferedImage bimg, double angle) {

	    int w = bimg.getWidth();    
	    int h = bimg.getHeight();

	    BufferedImage rotated = new BufferedImage(w, h, bimg.getType());  
	    Graphics2D graphic = rotated.createGraphics();
	    graphic.rotate(angle, w/2, h/2);
	    graphic.drawImage(bimg, null, 0, 0);
	    graphic.dispose();
	    return rotated;
	}
	
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
	
	public String getFieldID() {
		return fieldID;
	}

	public void setFieldID(String fieldID) {
		this.fieldID = fieldID;
	}

	public boolean isCollidAble() {
		return collidAble;
	}

	public double getHitRadius() {
		return hitRadius;
	}

	public boolean isMoving() {
		return moving;
	}

	public String getObjectID() {
		return objectID;
	}

	public GLocation getLocation() {
		return location;
	}

	public void setLocation(GLocation location) {
		this.location = location;
	}

//	just an alias xD
	public void teleport(GLocation location) {
		setLocation(location);
	}

	public Image getTexture() {
		return texture.getImage();
	}

	public void setTexture(GTexture texture) {
		this.texture = texture;
	}

	public abstract void handle(double diff, GContent world);

	public enum GLayor {
		BACKGROUND, MAIN1, MAIN2, MAIN3, MAIN4, OVERLAY1, OVERLAY2;
	}

	/**
	 * sorts the objects according to the planes to ensure that, for example, the
	 * background textures are in the background
	 */
	public static class GObjectOrganizer implements Comparator<GObject> {
		@Override
		public int compare(GObject o1, GObject o2) {
			if (o1.getLayor().ordinal() > o2.getLayor().ordinal())
				return 1;
			else if (o1.getLayor().ordinal() < o2.getLayor().ordinal())
				return -1;
			else
				return 0;
		}

	}

	public GLayor getLayor() {
		return layor;
	}

}
