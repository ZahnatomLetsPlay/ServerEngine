package gameStuff_phases;

import java.io.Serializable;

public class SaveSettingsData implements Serializable {

	private static final long serialVersionUID = 5953957378100760976L;

	private String Username;
	private String Ip;

	private int port;
	private int volumeSliderPosX;

	private int masterVolume;

	public SaveSettingsData(String username, int masterVolume, int volSliderPosX, String IP, int port) {
		this.Username = username;
		this.Ip = IP;
		this.volumeSliderPosX = volSliderPosX;
		this.port = port;
		this.masterVolume = masterVolume;
	}

	public int getMasterVolume() {
		return this.masterVolume;
	}

	public int getPort() {
		return this.port;
	}

	public int getVolumeLevel() {
		return this.masterVolume;
	}

	public String getUsername() {
		return this.Username;
	}

	public String getIPAdress() {
		return this.Ip;
	}

	public int getSliderPosX() {
		return this.volumeSliderPosX;
	}

}
