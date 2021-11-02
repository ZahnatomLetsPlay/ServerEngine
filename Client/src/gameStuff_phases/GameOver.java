package gameStuff_phases;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

@SuppressWarnings("unused")
public class GameOver {
	private Game game;

	private final BufferedImage plant_victory;
	private final BufferedImage water_victory;
	private final BufferedImage fire_victory;

	private Rectangle returnButton;

	public GameOver(Game game) {
		this.game = game;
		this.plant_victory = getScaledImage("/fire_victory.png", this.game.getWidth(), this.game.getHeight());
		this.water_victory = getScaledImage("/water_victory.png", this.game.getWidth(), this.game.getHeight());
		this.fire_victory = getScaledImage("/plant_victory.png", this.game.getWidth(), this.game.getHeight());
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
	}

	//@formatter:off
	/**
	 * scales the image to fit the screen dimensions
	 * @param path
	 * @param desiredWidht
	 * @param desiredHeight
	 * @return
	 */
	public BufferedImage getScaledImage(String path,double desiredWidht, double desiredHeight) {
		return this.game.getImageLoader().loadImg(path).getScaledImage(desiredWidht, desiredHeight, 1920,1080);
	}

}
