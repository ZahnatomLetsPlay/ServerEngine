package gameStuff_phases;

import java.awt.Graphics;
import java.util.LinkedList;

import gameobjects_states.GameObject;

public class Handler {

	public LinkedList<GameObject> gameObjectList = new LinkedList<>();
	public LinkedList<GameObject> charactersList = new LinkedList<>();

	public void tick() {
		charactersList.forEach(character -> {
			character.tick();
		});
	}

	public void render(Graphics g) {
		gameObjectList.forEach(gameObject -> {
			gameObject.render(g);
		});
		charactersList.forEach(character -> {
			if (character != null) {
				character.render(g);
			}
		});
	}

	public boolean isReady() {
		return !gameObjectList.isEmpty() || !charactersList.isEmpty();
	}

	public void addGameObject(GameObject gameObject) {
		gameObjectList.add(gameObject);
	}

	public void addCharacter(GameObject characterObject) {
		charactersList.add(characterObject);
	}

	public void removeCharacter(GameObject character) {
		if (charactersList.contains(character)) {
			charactersList.remove(character);
		}
	}

	public void removeGameObejct(GameObject gameObject) {
		if (gameObjectList.contains(gameObject)) {
			gameObjectList.remove(gameObject);
		}
	}

}
