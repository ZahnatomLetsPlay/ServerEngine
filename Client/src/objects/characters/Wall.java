package objects.characters;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import gameStuff_phases.Game;
import gameobjects_states.GameObject;
import gameobjects_states.Type;

public class Wall extends GameObject {

	private int width;
	private int height;

	private Game game;

	private BufferedImage wallImg, scaledWallImg;

	public Wall(int x, int y, Type type, int id, Game game) {
		super(x, y, type, id);
		this.game = game;
		this.wallImg = this.game.getImageLoader().loadImage("/Wall.png");

	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(scaledWallImg, x, y, this.width, this.height, null);
	}

	public void setSize(int width, int height) {

		this.width = width;
		this.height = height;

		BufferedImage scaledImg = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		
		AffineTransform scaler = new AffineTransform();
		scaler.scale(((double) width / 60.0), ((double) height / 60.0));
		
		AffineTransformOp transformer = new AffineTransformOp(scaler, AffineTransformOp.TYPE_BILINEAR);

		this.scaledWallImg = transformer.filter(this.wallImg, scaledImg);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, this.width, this.height);
	}

	//@formatter:off
	@Override
	public void tick() {}
}
