package objects.characters;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import animation.Animation;
import gameStuff_phases.Game;
import gameobjects_states.GameObject;
import gameobjects_states.Type;

public class Player extends GameObject {

	private Animation playerAnimation;

	private final double xScale;
	private final double yScale;

	private final int width;
	private final int height;

	private BufferedImage[] plant;
	private BufferedImage[] fire;
	private BufferedImage[] water;

	private BufferedImage currentImg;

	private Game game;

	public Player(int x, int y, Type type, int id, Game game) {
		super(x, y, type, id);
		this.game = game;

		this.xScale = this.game.getWidth() / 1920d;
		this.yScale = this.game.getHeight() / 1080d;

		this.width = (int) (60d * this.xScale);
		this.height = (int) (60d * this.yScale);

		if (type.equals(Type.plant)) {
//			this.plant = new BufferedImage[5];
//			for (int i = 0; i < plant.length; i++) {
//				this.plant[i] = (BufferedImage) this.game.getImageLoader()
//						.loadImage("Images//plant//plant" + i + ".jpg")
//						.getScaledInstance(this.width, this.height, Image.SCALE_DEFAULT);
//			}
//			this.playerAnimation = new Animation(4, plant);
//			this.currentImg = (BufferedImage) this.game.getImageLoader().loadImage("Images//plant//plant0.jpg")
//					.getScaledInstance(width, height, Image.SCALE_DEFAULT);

			try {
				this.currentImg = this.game.getImageLoader().toBufferedImage(
						loadImg("Images//plant//plant0.jpg").getScaledInstance(width, height, Image.SCALE_DEFAULT));
			} catch (IOException e) {

				e.printStackTrace();
			}

		} else if (type.equals(Type.fire)) {
//			this.fire = new BufferedImage[3];
//			for (int i = 0; i < this.fire.length; i++) {
//				this.fire[i] = (BufferedImage) this.game.getImageLoader().loadImage("Images//fire//fire" + i + ".jpg")
//						.getScaledInstance(width, height, Image.SCALE_DEFAULT);
//			}
//			this.playerAnimation = new Animation(4, fire);

			try {

				this.currentImg = this.game.getImageLoader().toBufferedImage(
						loadImg("Images//fire//fire0.jpg").getScaledInstance(width, height, Image.SCALE_DEFAULT));
			} catch (IOException e) {
				e.printStackTrace();
			}
//			this.currentImg = (BufferedImage) this.game.getImageLoader().loadImage("Images//fire//fire0.jpg")
//					.getScaledInstance(width, height, Image.SCALE_DEFAULT);

		} else if (type.equals(Type.water)) {
//			this.water = new BufferedImage[4];
//			for (int i = 0; i < water.length; i++) {
//				this.water[i] = (BufferedImage) this.game.getImageLoader()
//						.loadImage("Images//water//water" + i + ".jpg")
//						.getScaledInstance(width, height, Image.SCALE_DEFAULT);
//			}
//			this.playerAnimation = new Animation(4, water);
//			this.currentImg = (BufferedImage) this.game.getImageLoader().loadImage("Images//water//water1.jpg")
//					.getScaledInstance(width, height, Image.SCALE_DEFAULT);

			try {
				this.currentImg = this.game.getImageLoader().toBufferedImage(
						loadImg("Images//water//water0.jpg").getScaledInstance(width, height, Image.SCALE_DEFAULT));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public Image loadImg(String filePath) throws MalformedURLException, IOException {
		return ImageIO.read(new File(filePath).toURI().toURL());
	}

	@Override
	public void tick() {
		// playerAnimation.animate();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(currentImg, x, y, null);
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}
