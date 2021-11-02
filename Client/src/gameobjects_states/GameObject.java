package gameobjects_states;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class GameObject {
	protected int x;
	protected int y;
	protected int xVel;
	protected int yVel;
	protected Type type;
	protected int uniqueID;

	private int oldX;
	private int oldY;

	private int hp;

	public GameObject(int x, int y, Type type, int id) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.uniqueID = id;
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract Rectangle getBounds();

	public int getUniqueID() {
		return uniqueID;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHp() {
		return hp;
	}

	public GameObject setHp(int hp) {
		this.hp = hp;
		return this;
	}

	public int getxVel() {
		return xVel;
	}

	public int getyVel() {
		return yVel;
	}

	public void setX(int x) {
		this.oldX = this.x;
		this.x = x;
	}

	public void setY(int y) {
		this.oldY = this.y;
		this.y = y;
	}

	public void setxVel(int xVel) {
		this.xVel = xVel;
	}

	public void setyVel(int yVel) {
		this.yVel = yVel;
	}

	public Point getPreviousCoordinates() {
		return new Point(this.oldX, this.oldY);
	}

}
