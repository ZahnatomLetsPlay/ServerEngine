package blocks.list;

import java.util.ArrayList;

import de.aeg.server.Server.Connection;

public class Player extends GameObject {

	private final Connection con;

	public Player(Connection con, int id) {
		super(id);

		this.con = con;
		con.write(this.getId() + "");
	}

	/**
	 * Receives newest data from client and sends updated data
	 * 
	 * @param ArrayList of GameObjects that changed
	 */

	public void tick(ArrayList<GameObject> changes) {
		if (this.con.isConnected()) {

			// receives, sorts and updates data sent from client

			String readstr = this.con.read();
			if (readstr != null) {
				String[] readarr = readstr.split(":");
				if (readarr.length == 2) {
					String evnt = readarr[0].toLowerCase();
					String data = readarr[1].toLowerCase();
					// System.out.println(evnt + ":" + data);
					switch (evnt) {
					case "move":
						MoveState oldmove = this.getMove();
						this.setMove(MoveState.valueOf(data));
						if (oldmove != this.getMove()) {
							System.out.println(this.getMove().toString());
						}
						break;
					case "type":
						System.out.println(data);
						this.setType(Type.valueOf(data));
						try {
							Thread.sleep(10);
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
						StringBuilder pkg = new StringBuilder();
						pkg.append("id:");
						pkg.append(this.getId());

						pkg.append(",");

						pkg.append("pos:");
						pkg.append(this.getLoc().getX());
						pkg.append(";");
						pkg.append(this.getLoc().getY());

						pkg.append(",");

						pkg.append("type:");
						pkg.append(this.getType());

						pkg.append(",");

						pkg.append("move:");
						pkg.append(this.getLastMove());

						pkg.append(",");

						pkg.append("hp:");
						pkg.append(this.getHp());

						pkg.append("\\||");

						System.out.println(pkg.toString());
						con.write(pkg.toString());
						changes.clear();
						break;
					case "action":
						if (data.equalsIgnoreCase("fire")) {
//								this.setFiring(true);
						}
					}
				}
			}

			// builds and sends new data for every updated GameObject, acquired from the
			// engine

			StringBuilder pkg = new StringBuilder();

			for (GameObject obj : changes) {
				// id, pos(x;y), type, lastmove, hp
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

				pkg.append("\\||");
			}
			if (pkg.length() > 0) {
				pkg.substring(0, (pkg.length() - 2 < 0 ? 0 : pkg.length() - 2));
				con.write(pkg.toString());
			}
		} else {
			this.setMove(MoveState.none);
		}
	}

}
