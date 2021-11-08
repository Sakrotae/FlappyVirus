package de.thu.FlappyVirus.Engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JPanel;

import de.thu.FlappyVirus.Engine.GInput.GMouseClick;
import de.thu.FlappyVirus.Engine.Back.GBackground;
import de.thu.FlappyVirus.Engine.Back.GContent;
import de.thu.FlappyVirus.Engine.Back.GLocation;
import de.thu.FlappyVirus.Engine.Back.Events.GEventHandler;
import de.thu.FlappyVirus.Engine.Back.Events.Essential.KeyPressedGEvent;
import de.thu.FlappyVirus.Engine.Back.Events.Essential.MouseClickGEvent;
import de.thu.FlappyVirus.Engine.Front.GTexture;
import de.thu.FlappyVirus.Game.World;
import de.thu.FlappyVirus.Game.Entitys.WhiteBloodcell;

@SuppressWarnings("serial")
public class GPanel extends JPanel {

	private static final GraphicsConfiguration graphicsConf = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getDefaultScreenDevice()
			.getDefaultConfiguration();
	private int fps = 60;
	private int millSecsSpawnBloodcell = 2000;

	private String title;
	private String[] infoText;
	private boolean showInfoText = true;

	private BufferedImage imageBuffer;
	private Graphics graphics;

	private GInput input;
	private World content;

	private GFrame frame;
	private static GPanel instance;

	private HashMap<String, String> gameStats;
	private boolean showGameStats = false;

	private long ts_started = System.currentTimeMillis();
	private long ts_last_planted = System.currentTimeMillis();
	
	public GPanel(String title) {
		this.title = title;
		infoText = new String[0];

		input = new GInput();
		content = new World();

		frame = null;

		imageBuffer = graphicsConf.createCompatibleImage(420, 69);
		graphics = imageBuffer.getGraphics();
		((Graphics2D) graphics).setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		instance = this;
		init();
	}

	public void setGameStats(HashMap<String, String> gameStats) {
		this.gameStats = gameStats;
	}

	public void showGameStats(boolean show) {
		this.showGameStats = show;
	}

	public static GPanel getInstance() {
		return instance;
	}

	private void init() {
		setBackground(Color.BLACK);
	}

	public void setFrame(GFrame frame) {
		this.frame = frame;
	}

	public String[] getInfoText() {
		return infoText;
	}

	public void updateInfoText() {
		updateInfoText("FPS: " + getFPS(),
				"Window: x:" + getWidth() + ", y:" + getHeight());
	}

	public void updateInfoText(String... infoText) {
		this.infoText = infoText;
	}

	Thread panelThread;

	public void start() {
		panelThread = new Thread(title + "Thread") {

			public void run() {
				double last = System.currentTimeMillis();
				double diffAvg = 0.0166666;

				freeze(800); // startup anderer threads

				//showStartInfos(diffAvg);
				last = System.currentTimeMillis();
				while (true) {

					long current = System.currentTimeMillis();
					double diff = (current - last) / 1000.0;
					diffAvg = 0.98 * diffAvg + 0.02 * diff;
					last = current;
					fps = (int) (1D / diffAvg);

					if (showInfoText)
						updateInfoText();

					// Gameloop:
					if (!content.gothit()) {
						handleInput(diff);
						content.handle(diff);

						
						if ((System.currentTimeMillis()
								- ts_last_planted) > millSecsSpawnBloodcell) {
							Random rand = new Random();
							
							int randNum = rand.nextInt(10);
							content.place(new WhiteBloodcell(new GLocation(getWidth()/10, -30+rand.nextInt(70), content), 20,
									randNum > 5 ? -40 : 45,
									randNum > 5 ? true : false, 30+rand.nextInt(25)));
							
							ts_last_planted = System.currentTimeMillis();
						
					}
					} else {
						drawGameOverText();
					}

					clear();
					content.deleteDeadObjects();

					content.paint(imageBuffer, frame.getPanel());

					if (showInfoText && infoText != null)
						drawInfoText();

					if (content.gothit()) {
						drawGameOverText();
					}

					if (showGameStats && gameStats != null) {
						if (content != null && !content.gothit()) {
							gameStats.put("Zeit",
									Double.toString((double)((System.currentTimeMillis()-ts_started)/10)/100.0));
						}

						drawGameStats();
					}
					
					// Mit laufender Spieldauer werden immer mehr Viren
					// platziert 
					frame.getGraphics().drawImage(imageBuffer, 0, 0, frame);
					if((System.currentTimeMillis()-ts_started) > 50000)
						millSecsSpawnBloodcell = 700;
					else if((System.currentTimeMillis()-ts_started) > 40000)
						millSecsSpawnBloodcell = 900;
					else if((System.currentTimeMillis()-ts_started) > 30000)
						millSecsSpawnBloodcell = 1100;
					else if((System.currentTimeMillis()-ts_started) > 20000)
						millSecsSpawnBloodcell = 1400;
					else if((System.currentTimeMillis()-ts_started) > 10000)
						millSecsSpawnBloodcell = 1600;
				}
			}

		};
		panelThread.start();

	}


	/*
	 * freeze Frame content
	 */
	public synchronized void freeze(long time) {
		if (time > 0)
			try {
				synchronized (panelThread) {
					wait(time);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	public GLocation getViewCenter() {
		return content.getViewCenter();
	}

	public void setBackground(GBackground gBackground) {
		content.place(gBackground);
	}

	private void handleInput(double diff) {
		for (int i = 0; i < input.getPressedKeys().size(); i++) {
			char key = input.getPressedKeys().get(i);
			GEventHandler.call(new KeyPressedGEvent(diff, key, this.content));
		}
		

		for (int i = 0; i < input.getMouseClicks().size(); i++) {
			GMouseClick click = input.getMouseClicks().get(i);
			GEventHandler.call(new MouseClickGEvent(diff,
					click.getMousePressedX(), click.getMousePressedY()));
		}

		input.clearMouseClicks();
	}

	long start = System.currentTimeMillis();

	private void clear() {
		if (frame.getWidth() != imageBuffer.getWidth()
				|| frame.getHeight() != imageBuffer.getHeight()
				|| System.currentTimeMillis() - start < 2000) {
			setSize(frame.getWidth(), frame.getHeight());
			imageBuffer = graphicsConf.createCompatibleImage(getWidth(),
					getHeight());
			graphics = imageBuffer.getGraphics();
		}

		// clear frame:
		graphics.setColor(Color.DARK_GRAY);
		graphics.fillRect(0, 0, getWidth(), getHeight());
	}

	private void drawInfoText() {
		Graphics graphics = imageBuffer.getGraphics();
		graphics.setFont(new Font("Arial", 0, getHeight() / 50));
		graphics.setColor(Color.WHITE);
		for (int i = 0; i < infoText.length; i++) {
			if (infoText[i] != null) {

				try {
					graphics.drawString(infoText[i], 10, 20 * i + 40);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void drawGameOverText() {
		Graphics graphics = imageBuffer.getGraphics();
		Font font = new Font("Arial", 0, getHeight() / 10);
		FontMetrics f_metr = graphics.getFontMetrics(font);

		String s_gameOver = "GAME OVER";

		graphics.setFont(font);
		graphics.setColor(Color.WHITE);
		graphics.drawString(s_gameOver,
				(getWidth() - f_metr.stringWidth(s_gameOver)) / 2,
				(getHeight() - f_metr.getHeight()) / 2);


	}

	private void drawGameStats() {
		Graphics graphics = imageBuffer.getGraphics();
		graphics.setFont(new Font("Arial", 0, getHeight() / 20));
		graphics.setColor(Color.LIGHT_GRAY);
		int offset = getHeight() / 9;
		String sGameState = "";
		for (String key : gameStats.keySet()) {
			try {
				if (key.toLowerCase().equals("zeit")) {
					Image pic_ui = new GTexture("/ui/clock_32.png").getImage();
					graphics.drawImage(pic_ui, getWidth() - (int) ( getWidth() / 7),
							offset - (pic_ui.getHeight(null) / 2),
							getHeight() / 20, getHeight() / 20, null);
				} 
				if (gameStats.get(key) != null) {
					sGameState = gameStats.get(key);
				}
				graphics.drawString(sGameState, getWidth() - (int) (getWidth() / 9.5),
						offset + getHeight() / 70);

				offset += getHeight() / 15;

			} catch (NullPointerException e) {
				e.printStackTrace();
			}

		}
		
	}

	public void pause() {
		try {
			panelThread.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getFPS() {
		return fps;
	}

	public GContent getContent() {
		return content;
	}

	public void setContent(World content) {
		this.content = content;
	}

	public double getSizeFactor() {
		return getHeight() / 100;
	}

	public static GraphicsConfiguration getGraphicsconf() {
		return graphicsConf;
	}

	public String getTitle() {
		return title;
	}

	public BufferedImage getImageBuffer() {
		return imageBuffer;
	}

	public Graphics getGraphics() {
		return graphics;
	}

	public GInput getInput() {
		return input;
	}

	public Thread getPanelThread() {
		return panelThread;
	}
}
