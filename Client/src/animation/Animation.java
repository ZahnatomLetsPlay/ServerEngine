package animation;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {

	private int speed;
	private int frames;

	private int index;
	private int count;

	private BufferedImage[] images;
	private BufferedImage currentImg;

	public Animation(int speed, BufferedImage... args) {
		this.speed = speed;
		this.images = new BufferedImage[args.length];
		for (int i = 0; i < images.length; i++) {
			images[i] = args[i];
		}
		this.frames = args.length;
	}

	public void animate() {
		index++;
		if (index > speed) {
			index = 0;
			nextFrame();
		}
	}

	public void nextFrame() {
		for (int i = 0; i < frames; i++) {
			if (count == i) {
				currentImg = images[i];
			}
		}
		count++;
		
		if (count > frames) {
			count = 0;
		}

	}

	public void drawAnimation(Graphics g, int x, int y) {
		g.drawImage(currentImg, x, y, null);
	}

}
