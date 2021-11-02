package de.aeg.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {

	private final Socket socket;
	private PrintWriter outchan;
	private BufferedReader inchan;
	private boolean connected;

	public Connection(Socket socket) {
		this.socket = socket;
		try {
			outchan = new PrintWriter(socket.getOutputStream(), true);
			inchan = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			connected = true;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public boolean isConnected() {
		this.checkConnection();
		return this.connected;
	}

	private void checkConnection() {
		if (!(this.connected = socket.isConnected())) {
			try {
				this.socket.close();
				this.outchan.close();
				this.inchan.close();
			} catch (IOException e) {
			}
		}
	}

	public String read() {
		if (!this.isConnected()) {
			return null;
		}
		try {
//			if (inchan.ready()) {
			this.requestData();
			String read = inchan.readLine();
			
			//socket.getInputStream().skip(socket.getInputStream().available());
			//inchan = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			return read;
//			}
		} catch (IOException ex) {
		}
		return null;
	}

	public void write(String msg) {
		outchan.println(msg);
	}
	
	public void requestData() {
		this.write("request_data");
	}

}
