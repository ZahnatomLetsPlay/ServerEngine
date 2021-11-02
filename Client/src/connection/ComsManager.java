package connection;

import gameStuff_phases.Game;
import gameobjects_states.GameObject;
import gameobjects_states.Type;
import objects.characters.Player;
import objects.characters.Projectile;

public class ComsManager {

	private Game game;

	private final double yScaleFactor;
	private final double xScaleFactor;
	private final double sizeX;
	private final double sizeY;

	public ComsManager(Game game) {
		this.game = game;
		this.xScaleFactor = (double) (this.game.getWidth() / 1920.0);
		this.yScaleFactor = (double) (this.game.getHeight() / 1080.0);
		this.sizeX = 30 * this.xScaleFactor;
		this.sizeY = 30 * this.yScaleFactor;
	}

	// id:int,pos:x;y,type:str,move:dir,hp:3
	public void process(String msg) {
		String[] arr = null;
		int id = 0;
		double x = 0;
		double y = 0;
		int hp = 0;

		try {
			arr = msg.split(",");
			id = Integer.parseInt(arr[0].split(":")[1]);
			String pos = arr[1].split(":")[1];
			x = (Integer.parseInt(pos.split(";")[0])) * this.xScaleFactor - this.sizeX;
			y = (Integer.parseInt(pos.split(";")[1])) * this.yScaleFactor - this.sizeY;

			hp = Integer.parseInt(arr[arr.length - 1].split(":")[1]);

		} catch (NumberFormatException e) {
		}
		Type type = Type.valueOf(arr[2].split(":")[1]);

		// MoveState direction = MoveState.valueOf(arr[3].split(":")[1]);

		boolean found = false;

		for (GameObject obj : game.getHandler().charactersList) {

			if (obj.getUniqueID() == id) {
				obj.setX((int) x);
				obj.setY((int) y);
				if (obj.getType() != type) {
					obj.setType(type);
				}
				obj.setHp(hp);
				found = true;
			}
		}
		if (!found) {
			if (isPlayer(type)) {
				game.getHandler().addCharacter(new Player((int) x, (int) y, type, id, this.game));
			} else if (isProjectile(type)) {
				game.getHandler().charactersList.add(new Projectile((int) x, (int) y, type, id));
			}
		}
	}

	public boolean isPlayer(Type type) {
		return type.equals(Type.plant) || type.equals(Type.fire) || type.equals(Type.water);
	}

	public boolean isProjectile(Type type) {
		return type.equals(Type.plantProjectile) || type.equals(Type.fireProjectile)
				|| type.equals(Type.waterProjectile);
	}

}
