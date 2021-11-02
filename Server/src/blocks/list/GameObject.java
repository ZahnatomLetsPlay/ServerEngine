package blocks.list;

import java.awt.Rectangle;

public class GameObject {

	private Location loc;
	private MoveState move = MoveState.none;
	private Type type = Type.none;
	private MoveState lastmove = MoveState.right;
	private Type projectiletype = Type.none;
	private int hp = 3;
	private int id;
	private String name;
	private int speed;
	private int dspeed;
	private final Rectangle rect;
	private int size = 60;
	private boolean firing = false;

	public GameObject(int id) {
		this.loc = new Location(0, 0);
		this.id = id;
		this.speed = 5;
		String speedstring = (Math.ceil((Double.parseDouble(this.speed + "")) / 2) + "");
		this.dspeed = Integer.parseInt(speedstring.substring(0, speedstring.length() - 2));
		this.rect = new Rectangle(this.getLoc().getX()-(this.size/2), this.getLoc().getY()-(this.size/2), this.size, this.size);
	}

	public MoveState getMove() {
		return move;
	}

	public Rectangle getRectangle() {
		return this.rect;
	}

	public GameObject setMove(MoveState move) {
		if (!move.equals(MoveState.none)) {
			this.lastmove = move;
		}
		this.move = move;
		return this;
	}

	public MoveState getLastMove() {
		return this.lastmove;
	}

	public Location getLoc() {
		return loc;
	}

	public GameObject setLoc(Location loc) {
		this.loc = loc;
		this.rect.setBounds(this.getLoc().getX()-(this.size/2), this.getLoc().getY()-(this.size/2), this.size, this.size);
		return this;
	}

	public GameObject setXY(int x, int y) {
		this.setLoc(this.getLoc().setXY(x,y));
		return this;
	}
	
	public int getHp() {
		return hp;
	}

	public GameObject setHp(int hp_new) {
		if (hp_new >= 0) {
			this.hp = hp_new;
		} else {
			this.hp = 0;
		}
		return this;
	}

	public int getId() {
		return id;
	}

	public GameObject setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public GameObject setName(String name) {
		this.name = name;
		return this;
	}

	public Type getType() {
		return type;
	}

	public GameObject setType(Type type) {
		this.type = type;
		if (isProjectile()) {
			this.setSpeed(this.getSpeed() * 2);
		} else if (isPlayer()) {
			this.projectiletype = Type.valueOf(this.getType().toString() + "Projectile");
		}
		return this;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
		String speedstring = (Math.ceil((Double.parseDouble(this.speed + "")) / 2) + "");
		this.dspeed = Integer.parseInt(speedstring.substring(0, speedstring.length() - 2));
	}

	public int getSpeed() {
		return this.speed;
	}

	public int getDspeed() {
		return dspeed;
	}

	public boolean isProjectile() {
		return this.type.name().endsWith("Projectile");
	}

	public boolean isPlayer() {
		return (type.equals(Type.fire) || type.equals(Type.water) || type.equals(Type.plant));
	}

	public Type getProjectileType() {
		return projectiletype;
	}

	public boolean isFiring() {
		return firing;
	}

	public void setFiring(boolean firing) {
		this.firing = firing;
	}

	/*
	 * public GameObject setProjectileType(Type projectileType) {
	 * this.projectiletype = projectileType; return this; }
	 */

}
