package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import gameStuff_phases.Game;
import gameobjects_states.Type;
import push_Data_toServer.DataOutputChannel;

public class ServerConnectionSource {

	private Socket connection;

	private BufferedReader inputChannel;

	private PrintWriter outputChannel;

	private ComsManager comsManager;

	private DataOutputChannel dataChannel;

	public ServerConnectionSource(String IP_Address, int port, Game game) {

		try {
			connection = new Socket(IP_Address, port);

			System.out.println("connected!");

			inputChannel = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			outputChannel = new PrintWriter(connection.getOutputStream());

			comsManager = new ComsManager(game);

		} catch (IOException e) {
			System.out.println(e);
		}

	}

	public void sendPlayerType(Type type) {
		if (this.outputChannel != null) {
			sendMsg("type:" + type.toString());
			System.out.println("player type sended " + type.toString());
		}
	}

	public void setDataOutputChannel(DataOutputChannel dataChannel) {
		this.dataChannel = dataChannel;
	}

	public DataOutputChannel getDataOutputChannel() {
		if (this.dataChannel != null) {
			return this.dataChannel;
		} else {
			return null;
		}
	}

	public void sendMsg(String encryptedMsg) {
		if (!encryptedMsg.isBlank()) {
			outputChannel.println(encryptedMsg);
			outputChannel.flush();
		}
	}

	public ServerConnectionSource inititateInputChannelThread() {
		Thread inputChannelThread = new Thread(() -> {
			try {

				while (true) {
					String incoming = inputChannel.readLine().toLowerCase();
					if (incoming.equals("request_data")) {
						if (dataChannel != null && !dataChannel.getProcessedData().equals("noData")) {
							sendMsg(dataChannel.getProcessedData());
						}
					} else if (incoming.startsWith("id:")) {
						for (String proinc : incoming.split("\\|\\|")) {
							comsManager.process(proinc);
						}
					}
				}
			} catch (IOException e) {
				System.out.println(e);
			} finally {
			}
		});
		inputChannelThread.start();
		return this;
	}

	public boolean isConnected() {
		return connection != null && connection.isConnected();
	}

}
