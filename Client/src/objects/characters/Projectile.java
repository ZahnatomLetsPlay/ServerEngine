package objects.characters;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gameobjects_states.GameObject;
import gameobjects_states.Type;

@SuppressWarnings("unused")
public class Projectile extends GameObject {

	private BufferedImage fireProjectile;
	private BufferedImage plantProjectile;
	private BufferedImage waterProjectile;

	public Projectile(int x, int y, Type type, int id) {
		super(x, y, type, id);
		if (type.equals(Type.fireProjectile)) {
		}
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {

	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}
