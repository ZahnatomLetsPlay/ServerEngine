package blocks.list;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

public class WallList implements Serializable {

	private static final long serialVersionUID = 2643096838352102846L;

	private ArrayList<Rectangle> walls;

	public WallList() {
		this.walls = new ArrayList<>();
	}

	public void addWall(int x, int y) {
		this.walls.add(new Rectangle(x, y, 60, 60));
	}

	public ArrayList<Rectangle> getWalls() {
		return this.walls;
	}
}