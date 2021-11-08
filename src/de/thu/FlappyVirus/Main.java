package de.thu.FlappyVirus;

import java.util.HashMap;

import de.thu.FlappyVirus.Engine.GFrame;
import de.thu.FlappyVirus.Engine.Back.GLocation;
import de.thu.FlappyVirus.Engine.Back.Events.GEventHandler;
import de.thu.FlappyVirus.Engine.Back.Events.GEventHandler.GEventListener;
import de.thu.FlappyVirus.Engine.Front.GTexture;
import de.thu.FlappyVirus.Game.World;
import de.thu.FlappyVirus.Game.Entitys.Player;

public class Main implements GEventListener {

	static World world;
	static Player player;

	public static void main(String[] args) {
		GEventHandler.register(new Main());

		Thread mainThread = new Thread("mainThread") {
			public void run() {

				GFrame frame = new GFrame("Flappy Virus");
				world = new World();
				GEventHandler.register(world);
				frame.setContent(world);
				frame.setBackground(new GTexture("/textures/background.png"), 0);
				

				player = new Player(new GLocation(0, 0, world),
						frame.getPanel().getContent().getViewCenter());
				world.setPlayer(player);
				world.place(player);
				

				HashMap<String, String> gameStats = new HashMap<String, String>();
				gameStats.put("Zeit", "0");

				frame.getPanel().setGameStats(gameStats);
				frame.getPanel().showGameStats(true);
				

				frame.update(frame.getGraphics());
				
			}
		};
		mainThread.start();

	}

}
