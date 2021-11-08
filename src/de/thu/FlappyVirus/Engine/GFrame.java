package de.thu.FlappyVirus.Engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import de.thu.FlappyVirus.Engine.Back.GBackground;
import de.thu.FlappyVirus.Engine.Back.GContent;
import de.thu.FlappyVirus.Engine.Front.GTexture;
import de.thu.FlappyVirus.Engine.GInput.GMouseClick;
import de.thu.FlappyVirus.Game.World;

@SuppressWarnings("serial")
public class GFrame extends JFrame implements KeyListener, MouseListener {

	private GPanel panel;

	public GFrame(String title) {
		super(title);

		panel = new GPanel("default");
		panel.setFrame(this);

		setContentPane(panel);
		setResizable(true);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int dimX = (int) dimension.getWidth();
		int dimY = (int) dimension.getHeight();

		setBounds(dimX / 4, dimY / 4, dimX / 2, dimY / 2);
		setMinimumSize(new Dimension(dimX / 4, dimY / 4));
		setBackground(Color.BLACK);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addMouseListener(this);
		addKeyListener(this);

		init();
	}

	private void init() {
		setVisible(true);
		panel.start();
	}

	public GPanel getPanel() {
		return panel;
	}

	public void setBackground(GTexture texture, double offsetX) {
		panel.setBackground(new GBackground(texture, panel.getViewCenter().addX(offsetX)));
	}

	public GContent getContent() {
		return panel.getContent();
	}

	public void setContent(World content) {
		panel.setContent(content);
	}

	/*
	 * input handling...
	 */

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		panel.getInput().add(new GMouseClick(e.getX(), e.getY()));
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		panel.getInput().remove(e.getKeyChar());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		panel.getInput().add(e.getKeyChar());
	}
}
