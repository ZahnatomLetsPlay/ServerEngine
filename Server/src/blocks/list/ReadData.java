package blocks.list;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadData {

	private FileInputStream inputStream;
	private ObjectInputStream input;

	private WallList walls;

	public ReadData() {
		try {
			this.inputStream = new FileInputStream(new File("WallsData//wallsList.ser"));
			this.input = new ObjectInputStream(inputStream);
			this.walls = (WallList) this.input.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

//		printBounds();

	}

	public void printBounds() {
		// int counter = 1;
		this.walls.getWalls().forEach(i -> {
			System.out.println("wall\t" + i.getBounds());
		});
	}

	public WallList getWallsList() {
		return walls;
	}
}