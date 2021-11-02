package de.aeg.server.Server;

import java.util.ArrayList;

import blocks.list.GameObject;
import blocks.list.Location;
import blocks.list.Player;
import de.aeg.server.engine.Engine;

public class World {

	private final ArrayList<Player> players;
	private final ArrayList<GameObject> objs;
	private final Engine engine;
	private final Location[] spawns = new Location[] { new Location(201, 201), new Location(1001, 201),
			new Location(201, 1001), new Location(1001, 1001) };

	public World(ArrayList<Player> players) {
		this.players = players;
		objs = new ArrayList<>();
		// adds players to objs list
		this.players.forEach(n -> objs.add(n.setLoc(spawns[players.indexOf(n)])));
		engine = new Engine(objs);
	}

	@SuppressWarnings("unchecked")
	public void tick() {
		ArrayList<GameObject> changes = engine.Tick();
		new Thread(() -> {
			for (Player player : players) {
				player.tick((ArrayList<GameObject>) changes.clone());
			}
		}).start();
	}

}
