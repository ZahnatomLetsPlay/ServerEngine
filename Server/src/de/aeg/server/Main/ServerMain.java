package de.aeg.server.Main;

import java.util.ArrayList;
import java.util.Arrays;

import blocks.list.GameObject;
import blocks.list.Location;
import blocks.list.MoveState;
import blocks.list.Type;
import de.aeg.server.Server.Server;

public class ServerMain {

	public static void main(String[] args) {
		Server server = new Server(32768);
		server.start();
//		ArrayList<GameObject> testobjects = new ArrayList<GameObject>();
//		testobjects
//				.add((new GameObject(0)).setLoc(new Location(600, 600)).setMove(MoveState.up_left).setType(Type.water));
//		testobjects.add(
//				(new GameObject(1)).setLoc(new Location(200, 200)).setMove(MoveState.down_right).setType(Type.plant));
//		testobjects.add(
//				(new GameObject(2)).setLoc(new Location(800, 800)).setMove(MoveState.down_right).setType(Type.fire));
//		Engine testEngine = new Engine(testobjects);
//		long mills = System.nanoTime();
//		ArrayList<GameObject> changes = new ArrayList<GameObject>();
//		for (int i = 0; i < 600; i++) {
//			try {
////					System.out.println("Running Tick " + i);
//				testEngine.Tick();
//
//				if (changes.size() == 0) {
//				} else {
////					System.out.println("Changes");
////					for (GameObject obj : changes) {
////						System.out.println(obj.getLoc() + " " + obj.getId());
////					}
////					System.out.println();
//				}
//				if (i == 0 || i == 10 || i == 20) {
//					testEngine.spawnProjectile(testobjects.get(1));
//				}
//				Thread.sleep(17);
//			} catch (InterruptedException ex) {
//				ex.printStackTrace();
//			}
//		}
//		mills = System.nanoTime() - mills;
//		float f = (float) mills / 1000000000;
//		System.out.println(f);
		// startTestSort();
//		for (int i = 0; i < 20; i++) {
//			System.out.println();
//		}
		// startTestCreate();

	}

	@SuppressWarnings("unused")
	private static void startTestCreate() {
		ArrayList<GameObject> testchanges = new ArrayList<GameObject>();
		testchanges.add(new GameObject(testchanges.size()).setLoc(new Location(300, 300)).setMove(MoveState.right)
				.setType(Type.fire));
		testchanges.add(new GameObject(testchanges.size()).setLoc(new Location(300, 300)).setMove(MoveState.up)
				.setType(Type.water));
		testchanges.add(new GameObject(testchanges.size()).setLoc(new Location(300, 300)).setMove(MoveState.down)
				.setType(Type.plant));
		testchanges.add(new GameObject(testchanges.size()).setLoc(new Location(300, 300)).setMove(MoveState.left)
				.setType(Type.none));
		testchanges.add(new GameObject(testchanges.size()).setLoc(new Location(300, 300)).setMove(MoveState.down_left)
				.setType(Type.fireProjectile));
		testchanges.add(new GameObject(testchanges.size()).setLoc(new Location(300, 300)).setMove(MoveState.down_left)
				.setType(Type.fireProjectile));
		testCreate(testchanges);
	}

	@SuppressWarnings("unused")
	private static void startTestSort() {
		StringBuilder strb = new StringBuilder();
		for (MoveState move : MoveState.values()) {
			for (Type type : Type.values()) {
				strb.append("MOVE:");
				strb.append(move.toString());
				strb.append(",");
				strb.append("TyPe:");
				strb.append(type.toString());
				testSort(strb.toString());
				strb.delete(0, strb.length());
			}
		}
	}

	private static void testSort(String input) {
		String[] readarr = input.split(",");
		for (String read : readarr) {
			if (read != null) {
				String[] readp = read.split(":");
				String evnt = readp[0].toLowerCase();
				String data = readp[1].toLowerCase();
				switch (evnt) {
				case "move":
					switch (data) {
					case "w":
						setMove(MoveState.up);
						break;
					case "wd":
						setMove(MoveState.up_right);
						break;
					case "d":
						setMove(MoveState.right);
						break;
					case "sd":
						setMove(MoveState.down_right);
						break;
					case "s":
						setMove(MoveState.down);
						break;
					case "sa":
						setMove(MoveState.down_left);
						break;
					case "a":
						setMove(MoveState.left);
						break;
					case "wa":
						setMove(MoveState.up_left);
						break;
					default:
						setMove(MoveState.none);
						break;
					}
					break;
				case "type":
					setType(Arrays.stream(Type.values()).filter(n -> n.name().equalsIgnoreCase(data)).findAny()
							.orElse(null));
					break;
				}

			}
		}
	}

	private static void testCreate(ArrayList<GameObject> changes) {
		for (GameObject obj : changes) {
			// id, pos(x;y), type, lastmove, hp
			StringBuilder pkg = new StringBuilder();
			pkg.append("id:");
			pkg.append(obj.getId());

			pkg.append(",");

			pkg.append("pos:");
			pkg.append(obj.getLoc().getX());
			pkg.append(";");
			pkg.append(obj.getLoc().getY());

			pkg.append(",");

			pkg.append("type:");
			pkg.append(obj.getType());

			pkg.append(",");

			pkg.append("move:");
			pkg.append(obj.getLastMove());

			pkg.append(",");

			pkg.append("hp:");
			pkg.append(obj.getHp());
			System.out.println(pkg.toString());
			testSort(pkg.toString());
		}
	}

	private static void setType(Type type) {
		System.out.println("Type: " + type);
	}

	private static void setMove(MoveState move) {
		System.out.println("Move: " + move);
	}

}
