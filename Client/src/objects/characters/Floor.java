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

public class Floor extends GameObject {

	private int width;
	private int height;

	private final double W0 = 60.0;
	private final double H0 = 60.0;

	private Game game;

	private BufferedImage floorImg, scaledFloorImg;

	public Floor(int x, int y, Type type, int id, Game game) {
		super(x, y, type, id);
		this.game = game;
		this.floorImg = this.game.getImageLoader().loadImage("/floor.png");
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(scaledFloorImg, x, y, this.width, this.height, null);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;

		BufferedImage scaledImg = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);

		AffineTransform scaler = new AffineTransform();
		scaler.scale(((double) width / W0), ((double) height / H0));

		AffineTransformOp transformer = new AffineTransformOp(scaler, AffineTransformOp.TYPE_BILINEAR);

		this.scaledFloorImg = transformer.filter(this.floorImg, scaledImg);

	}

}
