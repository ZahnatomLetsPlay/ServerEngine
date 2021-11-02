package de.aeg.server.engine;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.ListIterator;

import blocks.list.GameObject;
import blocks.list.Location;
import blocks.list.MoveState;
import blocks.list.ReadData;
import blocks.list.Type;
import blocks.list.WallList;

public class Engine {
	ArrayList<GameObject> players;
	WallList walls;
	ArrayList<GameObject> newObj;

	int Unit_Size;
	int Unit_Speed;
	int Unit_Diagonalspeed;
	int Fly_Speed;
	int Fly_Diagonalspeed;
	final String[][] DmgLookup = { new String[] { "fire", "water", "plant" },
			// hurts î î î the most
			new String[] { "water", "plant", "fire" } };
	int[][] SpawnOffsets;

	public Engine(ArrayList<GameObject> objects) {
		this.players = objects;
		this.newObj = new ArrayList<GameObject>();
//		Unit_Size = Unit_Square;
//		Unit_Speed = Speed_Base;
		Unit_Size = 60;
		Unit_Speed = 5;
		Fly_Speed = 2 * Unit_Speed;
		Unit_Diagonalspeed = Unit_Speed / 2;
		Fly_Diagonalspeed = 2 * Unit_Diagonalspeed;
		SpawnOffsets = new int[][] { new int[] { 0, 65, 65, 65, 0, -65, -65, -65, 0 },
				new int[] { -65, -65, 0, 65, 65, 65, 0, -65, 0 } };
//		bounds = new int[][] { new int[] { 0 + Unit_Size, 0 + Unit_Size },
//				new int[] { 1920 - Unit_Size, 1080 - Unit_Size } };
		ReadData read = new ReadData();
		this.walls = read.getWallsList();

	}

	public ArrayList<GameObject> Tick() {
		ArrayList<GameObject> changes = new ArrayList<>();
//		ArrayList<GameObject> delete = new ArrayList<>();
//		ListIterator<GameObject> i2 = this.newObj.listIterator();
//		while (i2.hasNext()) {
//			this.players.listIterator().add(i2.next());
//			System.out.println("Added Object to Stack");
//		}
//		this.newObj = new ArrayList<GameObject>();

		// Main Loop
		for (ListIterator<GameObject> it = this.players.listIterator(); it.hasNext();) {
			GameObject p = it.next();
			if (p.getHp() <= 0) {
				it.remove();
				continue;
			}
			if (p.isPlayer() && p.isFiring()) {
				spawnProjectile(p);
				p.setFiring(false);
			}
//			if(p.getMove().equals(MoveState.none)) {
//				//System.out.println("movestate is none, skipping");
//				continue;
//			}
			int ox = p.getLoc().getX();
			int oy = p.getLoc().getY();
			int[] newloc = calculateMove(p);
			int px = newloc[0];
			int py = newloc[1];
			// sets potential new position to check collisions with other objects
//			p.getLoc().setX(px);
//			p.getLoc().setY(py);
			p.setXY(px, py);
			boolean collide = false;
			// checks collision with walls
			if (check_map(p)) {
				collide = true;
				break;
			}
			// checks collision with players (and Arrow)
			for (GameObject o : this.players) {
				if (p.getId() != o.getId()
				/*
				 * && o.getHp() > 0 && !o.getType().equals(Type.none) && check_collision(p, o)
				 */) {
					if (o.getHp() > 0) {
						if (!o.getType().equals(Type.none)) {
							if (check_collision(p, o)) {
								// Collision between two Arrow
								if (p.isProjectile() && o.isProjectile()) {

								}
								// Collision between two Players
								else if (!p.isProjectile() && !o.isProjectile()) {
									// System.out.println(p.getType() + " " + o.getType());
								}
								// When p is Player and o is Arrow
								else if (!p.isProjectile() && o.isProjectile()) {
									System.out.println("hit");
									p.setHp(p.getHp() - calculate_damage(p, o));
									o.setHp(0);
//						delete.add(o);
									if (p.getHp() <= 0) {
//							delete.add(p);
										System.out.println("Player " + p.getId() + " has died");
									}
								} else {

									continue;
								}
								collide = true;
								// System.out.println("Collided with: " + o.getId() + ", Who's at " + o.getLoc()
								// + " and is type " + o.getType() + " and has health " + o.getHp());
								break;
							}
						}
					}
				}
			}

			// checks if object is moving out of screen bounds
			if (check_map(p)) {
				collide = true;
			}

			if (collide) {
				// System.out.println("Collision at: " + p.getLoc() + ", by " + p.getId() + ",
				// type " + p.getType() + ", health " + p.getHp());
//				p.getLoc().setX(ox);
//				p.getLoc().setY(oy);
				p.setXY(ox, oy);
				if (p.isProjectile()) {
					p.setMove(MoveState.none);
					p.setType(Type.none);
					p.setHp(0);
					changes.add(p);
//					delete.add(p);
				}
			} else {
				if (!p.getMove().equals(MoveState.none)) {
					changes.add(p);
				}
			}

			if (p.getHp() <= 0) {
				it.remove();
			}

		}

		// Remove deleted from List
//		while (i.hasPrevious()) {
//			i.previous();
//		}
//		while (i.hasNext()) {
//			if (delete.contains(i.next())) {
//				i.remove();
//				System.out.println("Removed Object from Stack");
//			}
//		}
		return changes;
	}

	public boolean check_map(GameObject obj) {
		boolean bounds = false;
		for (Rectangle n : this.walls.getWalls()) {
			bounds = n.intersects(obj.getRectangle());
			if (bounds) {
				// System.out.println("WALLLLL");
				break;
			}
		}
		return bounds;
	}

//	public boolean check_collision(GameObject a, GameObject b) {
//		int dx = Math.abs(a.getLoc().getX() - b.getLoc().getX());
//		int dy = Math.abs(a.getLoc().getY() - b.getLoc().getY());
//		double ds = Unit_Size;
//		if (dx < ds && dy < ds) {
//			return true;
//		} else {
//			return false;
//		}
//
//	}

	public boolean check_collision(GameObject a, GameObject b) {
		// System.out.println(a.getRectangle().getLocation());
		return a.getRectangle().intersects(b.getRectangle()) || b.getRectangle().intersects(a.getRectangle());
	}

	public int calculate_damage(GameObject Player, GameObject Projectile) {
		String p = Player.getType().toString().toLowerCase();
		String a = Projectile.getType().toString().replace("Projectile", "").toLowerCase();
		System.out.println(a + " " + p);
		if (a.equals(DmgLookup[1][this.indexOf(DmgLookup[0], p)])) {
			System.out.println("collsion of Player-Type " + p + " with Projectile-Type " + a + ": critical hit");
			return 2;
		} else {
			System.out.println("collsion of Player-Type " + p + " with Projectile-Type " + a + ": basic hit");
			return 1;
		}
	}

	private int indexOf(Object[] arr, Object val) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(val)) {
				return i;
			}
		}
		return -1;
	}

	public int[] calculateMove(GameObject p) {
		int speed, dspeed;
		if (p.isProjectile()) {
			speed = Fly_Speed;
			dspeed = Fly_Diagonalspeed;
		} else {
			speed = Unit_Speed;
			dspeed = Unit_Diagonalspeed;
		}
		int px = p.getLoc().getX();
		int py = p.getLoc().getY();
		MoveState m = p.getMove();
		// adjusts potential new position based on MoveState
		switch (m) {
		case up:
			py -= speed;
			break;
		case up_left:
			py -= dspeed;
			px -= dspeed;
			break;
		case up_right:
			py -= dspeed;
			px += dspeed;
			break;
		case down:
			py += speed;
			break;
		case down_left:
			py += dspeed;
			px -= dspeed;
			break;
		case down_right:
			py += dspeed;
			px += dspeed;
			break;
		case left:
			px -= speed;
			break;
		case right:
			px += speed;
			break;
		case none:
			break;
		}
		return new int[] { px, py };
	}

	public void spawnProjectile(GameObject spawner) {
		if (spawner.isPlayer()) {
			GameObject proj = new GameObject(spawner.getId());
			proj.setMove(spawner.getLastMove());
			int[] SpawnLoc = spawner.getLoc().getXY();
			SpawnLoc[0] += SpawnOffsets[0][spawner.getLastMove().ordinal()];
			SpawnLoc[1] += SpawnOffsets[1][spawner.getLastMove().ordinal()];
			Location l = new Location(SpawnLoc[0], SpawnLoc[1]);
			proj.setLoc(l);
			proj.setType(spawner.getProjectileType());
			System.out.println("Spawning new Projectile " + proj.getType() + " " + l + " " + proj.getSpeed());
			// this.newObj.add(p);
			this.players.listIterator(this.players.size()).add(proj);
		}
	}
}
