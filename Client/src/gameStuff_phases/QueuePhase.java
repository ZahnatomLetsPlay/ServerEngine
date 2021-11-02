package gameStuff_phases;

import java.awt.Font;
import java.awt.Graphics;
import java.net.InetAddress;

import connection.ServerConnectionSource;
import gameobjects_states.GameState;
import gameobjects_states.Type;

public class QueuePhase {

	private Game game;

	private ServerConnectionSource connection;

	private int defaultPort = 32768;
	private int port;

	private String IP = "";
	private String dots = "";

	private long Time = System.currentTimeMillis();

	//@formatter:off
	public QueuePhase(Game game) {

		this.game = game;
	}
	//@formatter:on

	public void setIPAddress(String IP) {
		this.IP = IP;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void Render(Graphics g) {

		g.setFont(new Font("Arial", Font.PLAIN, 40));
		int width = g.getFontMetrics().stringWidth("connecting to server");
		g.drawString("connecting to server" + dots, (game.getWidth() - width) / 2,
				game.getHeight() / 2 - g.getFontMetrics().getHeight() / 4);

		if (System.currentTimeMillis() - Time > 333 * 4) {
			dots = "...";
			Time = System.currentTimeMillis();
		} else if (System.currentTimeMillis() - Time > 333 * 2) {
			dots = "..";

		} else if (System.currentTimeMillis() - Time > 333) {
			dots = ".";
		}

	}

	public void startQueuing(Type playerType) {
		Long Time = System.currentTimeMillis();
		new Thread(() -> {
			setIPAddress(this.game.getSettingsMenu().getIPAddress());
			System.out.println(this.IP);
			this.port = this.game.getSettingsMenu().getPort();
			while (true) {
				if (System.currentTimeMillis() - Time > 2000l) {
					try {

						if (!this.game.getIPVerifier().setIp(IP).isValidIpv4OrIpv6Address()) {
							System.out.println(this.IP + ": " + InetAddress.getByName(IP).getHostAddress());
							this.connection = new ServerConnectionSource(InetAddress.getByName(IP).getHostAddress(),
									this.port, game);
							if (this.connection != null && this.connection.isConnected()) {
								this.connection.sendPlayerType(playerType);
								this.connection.inititateInputChannelThread();
								this.game.setServerConnectionSource(connection);
								this.game.setGameState(GameState.InGame);
							}
						} else {
							System.out.println("IP: " + this.IP);
							connection = new ServerConnectionSource(this.IP, defaultPort, game);
							connection.inititateInputChannelThread();
							if (this.connection != null && this.connection.isConnected()) {
								this.connection.sendPlayerType(playerType);
								this.connection.inititateInputChannelThread();
								this.game.setServerConnectionSource(connection);
								this.game.setGameState(GameState.InGame);
							}

						}
					} catch (Exception e) {
						System.out.println(e);
						this.game.setGameState(GameState.Pickphase_confirm);
					}
					break;
				}
			}
		}).start();

	}

}
