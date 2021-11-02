package gameStuff_phases;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import saveAndLoad.SaveData;
import settings.components.Slider;
import settings.components.TextField;

public class Settings {

	private int usernameFieldWidth;
	private int stringWidth;
	private int height;
	private int width;
	private int commonPos;
	private int widthMasterVolume;
	private int xx;
	private int ipFieldWidth;
	private int ipX;
	private int ipY;
	private int portX;
	private int porty;
	private int portWidth;
	private boolean init = false;

	private Color saveButtonColor;

	private Game game;

	private final String font = "Arial";

	private TextField usernameField;

	private TextField IPField;

	private TextField portField;

	private Slider masterVolumeSlider;

	private Rectangle masterVolumeBounds;

	private RoundRectangle2D SaveButton;

	private RoundRectangle2D resetButton;

	private Color resetButtonColor;

	private boolean dataChanged = false;
	private boolean dataSaved = true;

	private SaveSettingsData temp_data;

	public Settings(Game game) {
		this.game = game;

		temp_data = this.game.getSavedSettingsData();

		this.usernameField = new TextField(true, temp_data.getUsername(), 15);

		this.IPField = new TextField(true, temp_data.getIPAdress(), 30);
		this.portField = new TextField(true, "" + temp_data.getPort(), 6);

		this.masterVolumeSlider = new Slider(true, temp_data.getMasterVolume());

		this.masterVolumeSlider.setDefaultVolume(temp_data.getMasterVolume());

		this.saveButtonColor = new Color(4, 82, 130);

		this.resetButtonColor = new Color(225, 29, 72);
	}

	@SuppressWarnings("unused")
	private void init_default() {

		this.usernameField = new TextField(true, "faris" + ThreadLocalRandom.current().nextInt(400, 10000), 15);

		this.IPField = new TextField(true, "www.aegame.stevetec.de", 30);
		this.portField = new TextField(true, 32768 + "", 6);

		this.masterVolumeSlider = new Slider(true, 50);

		this.masterVolumeSlider = new Slider(true, 50);

	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.white);
		g2d.setFont(new Font(font, Font.BOLD, 40));

		g2d.drawString("AUDIO", (int) (this.game.getWidth() * 0.05), (int) (this.game.getHeight() * 0.1));

		if (!init) {
			usernameFieldWidth = g2d.getFontMetrics().stringWidth("A".repeat(usernameField.getCharacterLimit() - 1));

			this.ipFieldWidth = g2d.getFontMetrics().stringWidth("A".repeat(IPField.getCharacterLimit() - 1));

			stringWidth = g.getFontMetrics().stringWidth("Master-Volume");

			height = (int) (g.getFontMetrics().getHeight() * 2 + this.game.getHeight() * 0.1);
			width = (int) (this.game.getWidth() * 0.05) + g.getFontMetrics().stringWidth("AUDIO") / 2;

			commonPos = (int) (width + 3 * stringWidth / 2);

			widthMasterVolume = commonPos + (int) (this.game.getWidth() * 0.4) - (width + 3 * stringWidth / 2);

			this.masterVolumeBounds = new Rectangle(width + stringWidth, height - g.getFontMetrics().getHeight() / 2,
					widthMasterVolume, (int) (this.game.getHeight() * 0.03));

			xx = (int) (masterVolumeBounds.getCenterX() - (int) (usernameFieldWidth / 2));

			this.ipX = (int) (this.masterVolumeBounds.getCenterX() - this.ipFieldWidth / 2);
			this.ipY = (int) (height + g.getFontMetrics().getHeight() * 4 + this.game.getHeight() * 0.1) + 20;

			this.portWidth = g2d.getFontMetrics().stringWidth("A".repeat(this.portField.getCharacterLimit()));
			this.portX = (int) (this.masterVolumeBounds.getCenterX() - this.portWidth / 2);
			this.porty = (int) (height + g.getFontMetrics().getHeight() * 6 + this.game.getHeight() * 0.1) + 20;

			int saveWidth = g2d.getFontMetrics().stringWidth("save") + 5;
			int saveHeight = g2d.getFontMetrics().getHeight();
			this.SaveButton = new RoundRectangle2D.Double(this.game.getWidth() / 2 - saveWidth * 1.5,
					this.game.getHeight() * 0.9, saveWidth, saveHeight, 10, 10);

			this.resetButton = new RoundRectangle2D.Double(this.game.getWidth() / 2 + 0.5 * saveWidth,
					this.game.getHeight() * 0.9, saveWidth, saveHeight, 10, 10);

			init = true;
		}

		// volume text
		g2d.setFont(new Font(font, Font.BOLD, 30));
		g2d.drawString("Master-Volume", width, height);

		// masterVolume

		this.masterVolumeSlider.setBounds_specifics(masterVolumeBounds, g2d.getFontMetrics().stringWidth("100"));
		if (dataChanged) {
			this.masterVolumeSlider.setSliderButtonPos(temp_data.getSliderPosX());
			this.masterVolumeSlider.setDefaultVolume(temp_data.getMasterVolume());
			dataChanged = false;
		}

		this.masterVolumeSlider.render(g2d);

		// username-Field
		g2d.setColor(Color.white);
		g2d.setFont(new Font(font, Font.BOLD, 40));
		g2d.drawString("ID", (int) (this.game.getWidth() * 0.05),
				(int) (height + g.getFontMetrics().getHeight() + this.game.getHeight() * 0.05));

		// usernameField bounds
		g2d.setFont(new Font(font, Font.BOLD, 30));
		g2d.drawString("Username", width,
				(int) (height + g2d.getFontMetrics().getHeight() * 1.5 + this.game.getHeight() * 0.1));
		this.usernameField.setBounds(xx,
				(int) (height + g2d.getFontMetrics().getHeight() * 1.5 + this.game.getHeight() * 0.07),
				usernameFieldWidth, g2d.getFontMetrics().getHeight() + 5);
		this.usernameField.render(g2d);

		// Connection-field
		g2d.setColor(Color.white);
		g2d.setFont(new Font(font, Font.BOLD, 40));
		g2d.drawString("Connection", (int) (this.game.getWidth() * 0.05),
				(int) (height + g.getFontMetrics().getHeight() * 4 + this.game.getHeight() * 0.1));

		this.IPField.setBounds(ipX, ipY, this.ipFieldWidth, g2d.getFontMetrics().getHeight() + 5);
		this.IPField.render(g2d);

		this.portField.setBounds(portX, porty, portWidth, g2d.getFontMetrics().getHeight() + 5);
		this.portField.render(g2d);

		g2d.setColor(Color.white);
		g2d.setFont(new Font(font, Font.BOLD, 30));
		g2d.drawString("IP-Address", width, ipY + g2d.getFontMetrics().getHeight() + 10);

		g2d.drawString("Port", width, porty + g2d.getFontMetrics().getHeight() + 10);

		g2d.setColor(saveButtonColor);
		g2d.fill(SaveButton);

		g2d.setColor(resetButtonColor);
		g2d.fill(resetButton);

		g2d.setColor(Color.white);
		g2d.drawString("save", (int) (this.SaveButton.getCenterX() - g2d.getFontMetrics().stringWidth("save") / 2),
				(int) (this.SaveButton.getCenterY() + g2d.getFontMetrics().getHeight() / 4));

		g2d.drawString("reset", (int) (this.resetButton.getCenterX() - g2d.getFontMetrics().stringWidth("reset") / 2),
				(int) (this.resetButton.getCenterY() + g2d.getFontMetrics().getHeight() / 4));

	}

	// various Events to handle the InputFields
	public void onKeyPressedEvent(KeyEvent e) {
		usernameField.onKeyPressedEvent(e);
		IPField.onKeyPressedEvent(e);
		if (Character.isDigit(e.getKeyCode()) || KeyEvent.VK_BACK_SPACE == e.getKeyCode()) {
			this.portField.onKeyPressedEvent(e);
		}
	}

	public void onMouseMovedEvent(MouseEvent e) {
		this.usernameField.onMouseMovedEvent(e);
		this.masterVolumeSlider.onMouseMovedEvent(e);
		this.IPField.onMouseMovedEvent(e);
		this.portField.onMouseMovedEvent(e);

		if (SaveButton.contains(e.getPoint())) {
			setSaveButtonColor(new Color(59, 130, 246));
		} else {
			setSaveButtonColor(new Color(4, 82, 130));
		}
		if (resetButton.contains(e.getPoint())) {
			resetButtonColor = new Color(244, 63, 94);
		} else {
			resetButtonColor = new Color(225, 29, 72);
		}
	}

	public void onMouseClickedEvent(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			this.usernameField.onMouseClickedEvent(e);
			this.IPField.onMouseClickedEvent(e);
			this.portField.onMouseClickedEvent(e);
			// save data as ser
			if (SaveButton != null && SaveButton.contains(e.getPoint())) {
				setSaveButtonColor(new Color(16, 185, 129));
				save();
			} else if (resetButton != null && resetButton.contains(e.getPoint())) {
				if (dataSaved) {
					this.resetButtonColor = new Color(16, 185, 129);
					reset();

					dataChanged = true;

					initialize();
				}
			}
		}
	}

	private void save() {
		SaveSettingsData settingsData = new SaveSettingsData(getUsername(), getVolumeLevel(), getVolSliderPosX(),
				getIPAddress(), getPort());

		SaveData saver = new SaveData();
		saver.saveData(settingsData, "settingsData");
		System.out.println("settings data saved");

		dataSaved = true;
	}

	private void reset() {

		this.game.setSavedSettingsData(
				(SaveSettingsData) this.game.getSavedDataLoader().loadObject("settingsData_default.ser"));

		this.temp_data = this.game.getSavedSettingsData();

		System.out.println("reseted settings back to default");

		save();

	}

	public void initialize() {
		this.usernameField = new TextField(true, temp_data.getUsername(), 15);

		this.IPField = new TextField(true, temp_data.getIPAdress(), 30);
		this.portField = new TextField(true, "" + temp_data.getPort(), 6);

		this.masterVolumeSlider = new Slider(true, temp_data.getMasterVolume());

	}

	public void setSaveButtonColor(Color color) {
		this.saveButtonColor = color;
	}

	public int getVolSliderPosX() {
		return this.masterVolumeSlider.getSliderPosX();
	}

	public void onMouseDraggedEvent(MouseEvent e) {
		this.masterVolumeSlider.onMouseDraggedEvent(e);
		if (this.masterVolumeSlider.isDragged()) {
			Arrays.stream(this.game.getAvailableAudioPlayers()).forEach(i -> {
				if (i != null) {
					i.setVolume(getVolumeLevel());
				}
			});
		}
	}

	public int getVolumeLevel() {
		return this.masterVolumeSlider.getValue();
	}

	public String getUsername() {
		return this.usernameField.getText();
	}

	public String getIPAddress() {
		return this.IPField.getText().toLowerCase();
	}

	public int getPort() {
		return Integer.parseInt(this.portField.getText());
	}
}
