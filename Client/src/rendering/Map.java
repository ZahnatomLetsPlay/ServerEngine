package rendering;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gameobjects_states.GameObject;
import gameobjects_states.Type;

public class Map extends GameObject {

	private BufferedImage map;

	public Map(int x, int y, Type type, int id) {
		super(x, y, type, id);
	}

	public void setMap(BufferedImage map) {
		this.map = map;
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(map, x, y, null);
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}
