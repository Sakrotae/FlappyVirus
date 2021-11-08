package de.thu.FlappyVirus.Engine.Front;

import java.awt.Image;

public class GAnimatedTexture extends GTexture {

	public GAnimatedTexture(String path) {
		super(path);
		init();
	}

	Image[] frames;

	private void init() {
		int max = img.getHeight() / img.getWidth();
		frames = new Image[max];
		for(int i = 0; i < max; i++) {
			frames[i] = img.getSubimage(0, img.getWidth()*i, img.getWidth(), img.getWidth());
		}
	}

	int frame = 0;
	
	@Override
	public Image getImage() {
		return frames[Math.abs((int)(System.currentTimeMillis()/100)%frames.length)];
	}
}
