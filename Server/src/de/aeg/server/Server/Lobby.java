package de.aeg.server.Server;

import java.util.ArrayList;

import blocks.list.Player;

public class Lobby extends Thread {

	private final ArrayList<Player> players;
	private World world;
	
	public Lobby() {
		this.players = new ArrayList<Player>();
	}
	
	/**
	 * Creates new player with connection
	 * @param Takes connection
	 */
	public void addPlayer(Connection connection) {
		if(this.isOpen()) {
			this.players.add(new Player(connection, players.size()));
		}
	}
	
	public boolean isOpen() {
		return this.players.size() < 1; // for testing and debugging purposes
		// return this.players.size() < 4;
	}
	
	@Override
	public void run() {
		world = new World(players);
		while(true) {
			try {
				world.tick();
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
