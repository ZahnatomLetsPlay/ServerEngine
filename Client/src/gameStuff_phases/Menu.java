package gameStuff_phases;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;

import gameobjects_states.GameState;
import gameobjects_states.MenuState;

@SuppressWarnings("unused")
public class Menu {
	// "Defeats: The dev-team \nArch enemy: The design team\n\n"
	private final String[] opsDescription = {
			"Arch enemy: The design team\nDefeats: The dev-team\n\n\nAEGame is a project of the IT-course at the AEG. The game\n"
					+ "in the genre Run&Chase combines Rock-Paper-Scissors \n"
					+ "strategizing with reaction time and map strategy.",
			"Arch enemy: Plant\n" + "Defeats: Fire\n" + "\n\n"
					+ "The Water character is chilled out even if things get flaming hot,\n"
					+ "with a cool head they erase any burnmarks of the map.",
			"Arch enemy: Water\nDefeats: Plant\n" + "\n\n"
					+ "Nothing says 'bring the heat' more than a literal walking inferno.\n"
					+ "This class will devastate the local ecosystem and leave nothing \n" + "but ashes in it's way.",
			"Arch enemy: Fire\nDefeats: Water\n\n\n"
					+ "On the wasteland of the map this class will do anything to stay hydrated,\n"
					+ "that the water seems strangely alive is of no relevance to that goal." };

	private final String[] text = { "Aegame", "Water", "Plant", "Fire" };

	private final int Width_Buttons = 100;
	private final int Height_Buttons = 80;
	private final int offsetX;
	private final int offsetY;
	private final int offsetYY;
	private final int XSIZED_SCALED;
	private final int YSIZED_SCALED;

	private Rectangle playButton;
	private Rectangle characterButton;
	private Rectangle settingsButton;
	private Rectangle quitButton;
	private Rectangle cancelButton;
	private Rectangle helpButton;
	private Rectangle volumeButton;
	private Rectangle opsRect;
	private Rectangle fireButton;
	private Rectangle marijuanaButton;
	private Rectangle waterButton;
	private Rectangle rc;

	private Arc2D arcFire;
	private Arc2D arcPlant;
	private Arc2D arcWater;

	private Color playButtonColor = Color.white;
	private Color helpButtonColor = Color.white;
	private Color CharaterButtonColor = Color.white;
	private Color settingsButtonColor = Color.white;
	private Color quitButtonColor = Color.white;
	private Color cancelButtonColor = Color.white;

	private MenuState menuState = MenuState.NONE;

	private float pingAlpha_D = 0.5f;

	private String title = "Chase & Kill";

	private int muteClickcounter;
	private int pX = 30;
	private int currentDesc = 0;

	private float alpha = 40;

	private BufferedImage mute;
	private BufferedImage unmute;
	private BufferedImage ops;

	private Game game;

	private boolean cancelButtoncreated = false;
	private boolean muted = false;
	private boolean waterHover = false, plantHover = false, fireHover = false;

	public Menu(Game game) {

		this.game = game;

		this.offsetX = (int) ((double) game.getWidth() * 13.0 / 192.0);
		this.offsetY = (int) ((double) game.getHeight() * 15.0 / 108.0);

		this.XSIZED_SCALED = (int) ((double) this.game.getWidth() * 27.0 / 192.0);
		this.YSIZED_SCALED = (int) ((double) this.game.getHeight() * 8.0 / 108.0);

		this.offsetYY = (int) ((double) game.getHeight() * 10.0 / 108.0);

		playButton = new Rectangle(game.getWidth() / 2 - this.offsetX, this.offsetY + this.offsetYY, XSIZED_SCALED,
				YSIZED_SCALED);

		characterButton = new Rectangle(game.getWidth() / 2 - this.offsetX, 2 * this.offsetY + this.offsetYY,
				XSIZED_SCALED, YSIZED_SCALED);

		settingsButton = new Rectangle(game.getWidth() / 2 - this.offsetX, 3 * this.offsetY + this.offsetYY,
				XSIZED_SCALED, YSIZED_SCALED);

		quitButton = new Rectangle(game.getWidth() / 2 - this.offsetX, 4 * this.offsetY + this.offsetYY, XSIZED_SCALED,
				YSIZED_SCALED);

		cancelButton = new Rectangle(game.getWidth() - (int) ((double) this.game.getWidth() * 10.0 / 192.0),
				(int) ((double) game.getHeight() * 19.0 / 1080), (int) ((double) this.game.getWidth() * 25.0 / 1920.0),
				(int) ((double) this.game.getHeight() * 33.0 / 1080.0));

		this.volumeButton = new Rectangle(this.game.getWidth() - 120, this.game.getHeight() - 120, 80, 80);

		this.mute = this.game.getImageLoader().loadImg("/mute.png").getBufferedImage();
		this.unmute = this.game.getImageLoader().loadImg("/unmute.png").getBufferedImage();

		this.ops = this.game.getImageLoader().loadImg("/opsPreview.png").getScaledImage_W(0.3d * this.game.getWidth());

		this.opsRect = new Rectangle(0, 0, this.ops.getWidth() + pX, this.game.getHeight());

		this.rc = new Rectangle(pX / 2, (int) (opsRect.getCenterY() - ops.getHeight() / 2), this.ops.getWidth(),
				this.ops.getHeight());

		int width_height = (int) (0.3d * this.ops.getHeight());
		int y = (int) (rc.y + rc.getHeight()) - width_height;

		this.fireButton = new Rectangle(pX / 2, y, width_height, width_height);

		this.arcFire = new Arc2D.Double(fireButton, 0, 360, Arc2D.PIE);

		// in honor of snoop dog
		this.marijuanaButton = new Rectangle(rc.x + rc.width - width_height, y, width_height, width_height);

		this.waterButton = new Rectangle((int) (rc.getCenterX() - width_height / 2), rc.y, width_height, width_height);

		this.arcPlant = new Arc2D.Double(marijuanaButton, 0, 360, Arc2D.PIE);

		this.arcWater = new Arc2D.Double(waterButton, 0, 360, Arc2D.PIE);

	}

	public void render(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		if (menuState.equals(MenuState.NONE)) {

			g.setFont(new Font("Arial", Font.PLAIN + Font.BOLD, 70));
			g.setColor(setColor(0, 0, 205));
			int textWidth = g.getFontMetrics().stringWidth(title);
			// g.drawString(title, , this.stringOff + this.titOFF);
			g.drawString(title, (int) (this.playButton.getCenterX() - textWidth / 2),
					this.playButton.y / 2 - this.playButton.y / 16);

			// playButton text
			g.setColor(this.playButtonColor);
			drawText(g2d, "Play", this.playButton);

			// CharacterButton text
			g.setColor(this.CharaterButtonColor);
			drawText(g2d, "Description", characterButton);

			// SettingsButton text
			g.setColor(this.settingsButtonColor);
			drawText(g2d, "Settings", settingsButton);

			// quitButton text
			g.setColor(this.quitButtonColor);
			drawText(g2d, "Quit", quitButton);

			// playButton
			g2d.setColor(this.playButtonColor);
			g2d.draw(playButton);

			// characterButton
			g2d.setColor(this.CharaterButtonColor);
			g2d.draw(characterButton);

			// settingsButton
			g2d.setColor(this.settingsButtonColor);
			g2d.draw(settingsButton);

			// quitButton
			g2d.setColor(this.quitButtonColor);
			g2d.draw(quitButton);

			g2d.drawImage(muted ? mute : unmute, volumeButton.x, volumeButton.y, null);

		} else { // CancelButton text
			g2d.setColor(this.cancelButtonColor);
			g2d.setFont(new Font("Arial", Font.PLAIN, 40));
			createCancelButton(g2d);
			// drawText(g2d, "X", cancelButton);
			g2d.drawString("X", (int) (this.cancelButton.getCenterX() - this.cancelButton.getWidth() / 2),
					(int) (cancelButton.y + cancelButton.getCenterY() / 2));

			// g2d.draw(cancelButton); // cancelButton

			// Characterpreview state
			if (menuState.equals(MenuState.CHARACTERBUTTON)) {

				g2d.setColor(new Color(17, 17, 19));
				g2d.fill(opsRect);

				g2d.setColor(new Color(32, 204, 22, !plantHover ? (int) (this.alpha) : 200));
				g2d.fillOval((int) (this.marijuanaButton.x - this.marijuanaButton.width * 0.05),
						(int) (this.marijuanaButton.y - this.marijuanaButton.height * 0.05),
						(int) (this.marijuanaButton.width * 1.1), (int) (this.marijuanaButton.getHeight() * 1.1));

				g2d.setColor(new Color(14, 165, 233, !waterHover ? (int) (this.alpha) : 200));
				g2d.fillOval((int) (this.waterButton.x - this.waterButton.getWidth() * 0.07),
						(int) (this.waterButton.y - this.waterButton.getHeight() * 0.05),
						(int) (this.waterButton.getWidth() * 1.1), (int) (this.waterButton.getHeight() * 1.1));

				g2d.setColor(new Color(239, 68, 68, !fireHover ? (int) (this.alpha) : 200));
				g2d.fillOval((int) (this.fireButton.x - this.fireButton.getWidth() * 0.05),
						(int) (this.fireButton.y - this.fireButton.getHeight() * 0.05),
						(int) (this.fireButton.getWidth() * 1.1), (int) (this.fireButton.getHeight() * 1.1));

				g2d.drawImage(this.ops, pX / 2, (int) (opsRect.getCenterY() - ops.getHeight() / 2), null);

				// g2d.draw(fireButton);
				// g2d.draw(marijuanaButton);
				// g2d.draw(waterButton);

				g2d.setColor(Color.white);
				int fontSize = (int) ((70.0 / 1920.0) * this.game.getWidth());
				g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, fontSize));

				new Color(fontSize, fontSize, fontSize, fontSize);

				g2d.drawString(text[currentDesc],
						this.opsRect.width + (this.game.getWidth() - this.opsRect.width) / 2
								- g2d.getFontMetrics().stringWidth(text[currentDesc]) / 2,
						g2d.getFontMetrics().getHeight() * 2);

				int Height = 4 * g2d.getFontMetrics().getHeight();
				int fontSize1 = (int) ((30.0 / 1920.0) * this.game.getWidth());
				g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize1));

				int scale1 = (int) (this.opsRect.width + (this.game.getWidth() - this.opsRect.width) / 2 * 0.1);

				drawString(g2d, opsDescription[currentDesc], scale1, Height);

				incrementAlpha();

			} // settings
			else if (menuState.equals(MenuState.SETTINGSBUTTON)) {
				this.game.getSettingsMenu().render(g2d);
			}
		}

	}

	private void incrementAlpha() {
		this.alpha += pingAlpha_D;
		if (this.alpha >= 120) {
			this.pingAlpha_D *= -1;
		} else if (this.alpha <= 40) {
			this.pingAlpha_D *= -1;
		}
	}

	/**
	 * "Arch enemy: The design team\nDefeats: The dev-team\n\n\nAEGame is a project
	 * of the IT-course at the AEG. The game\n" + "in the genre Run&Chase combines
	 * Rock-Paper-Scissors \n" + "strategizing with reaction time and map
	 * strategy.",
	 * 
	 */

	private void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n")) {
			if (line.startsWith("Arch enemy: ")) {
				String s = line.substring(0, line.indexOf(":") + 1);
				g.setColor(new Color(239, 68, 68));
				g.drawString(s, x, y);
				g.setColor(new Color(211, 211, 219));
				g.drawString(line.substring(line.indexOf(":") + 1), x + g.getFontMetrics().stringWidth(s), y);
				y += g.getFontMetrics().getHeight();
			} else if (line.startsWith("Defeats: ")) {
				String s = line.substring(0, line.indexOf(":") + 1);
				g.setColor(new Color(163, 230, 53));
				g.drawString(s, x, y);
				g.setColor(new Color(211, 211, 219));
				g.drawString(line.substring(line.indexOf(":") + 1), x + g.getFontMetrics().stringWidth(s), y);
				y += g.getFontMetrics().getHeight();
			} else {
				g.setColor(new Color(211, 211, 219));
				g.drawString(line, x, y += g.getFontMetrics().getHeight());
			}
		}
	}

	public void createCancelButton(Graphics2D g) {
		if (!cancelButtoncreated) {
			int width = g.getFontMetrics().charWidth('X');
			int height = g.getFontMetrics().getHeight();
			cancelButton = new Rectangle(game.getWidth() - 100, height, width, height - 15);
			cancelButtoncreated = true;
		}
	}

	private void drawText(Graphics g, String text, Rectangle button) {
		g.setFont(new Font("Arial", Font.PLAIN, 35));
		int wo = g.getFontMetrics().stringWidth(text);
		int ho = g.getFontMetrics().getHeight();
		g.drawString(text, (int) (button.getCenterX() - wo / 2), (int) (button.getCenterY() + ho / 4));
	}

	public boolean isMuted() {
		return this.muted;
	}

	public void onMouseClickedEvent(MouseEvent e) {

		if (e.getButton() == MouseEvent.BUTTON1) {
			if (game.getGameState().equals(GameState.Menu)) {
				switch (getMenuState()) {
				case NONE:
					if (playButton.contains(e.getPoint())) {
						// play button pressed
						game.setGameState(GameState.Pickphase);
						return;
					} else if (characterButton.contains(e.getPoint())) {
						// character preview
						setMenuState(MenuState.CHARACTERBUTTON);
						return;

					} else if (settingsButton.contains(e.getPoint())) {
						// setting menu
						setMenuState(MenuState.SETTINGSBUTTON);
						return;
						// quitButton
					} else if (this.volumeButton.contains(e.getPoint())) {
						this.muteClickcounter++;
						if (this.muteClickcounter == 1) {
							this.muted = true;
							this.game.muteMainMenuMusic(muted);
						} else if (this.muteClickcounter == 2) {
							this.muted = false;
							this.game.muteMainMenuMusic(muted);
							this.muteClickcounter = 0;
						}

					}
					// sheesh
					else if (quitButton.contains(e.getPoint())) {
						this.game.EndGame();

					}
					break;
				// 1 = water , 2 = Fire , 3= Plant
				case CHARACTERBUTTON:
					// fire Button
					if (this.arcFire.contains(e.getPoint())) {
						this.currentDesc = 3;
						// Plant Button
					} else if (this.arcPlant.contains(e.getPoint())) {
						this.currentDesc = 2;
						// Water button
					} else if (this.arcWater.contains(e.getPoint())) {
						this.currentDesc = 1;
					} else if (cancelButton.contains(e.getPoint())) {
						setMenuState(MenuState.NONE);
						setCancelButtonColor(Color.white);
						this.currentDesc = 0;
					}
					break;
				case SETTINGSBUTTON:
					this.game.getSettingsMenu().onMouseClickedEvent(e);
					if (cancelButton.contains(e.getPoint())) {
						setMenuState(MenuState.NONE);
						setCancelButtonColor(Color.white);
					}
					break;
				default:
					break;
				}
			}
		}
	}

	//@formatter:off
	
	public void onMouseDraggedEvent(MouseEvent e) {
		switch(menuState) {
		case SETTINGSBUTTON:
			this.game.getSettingsMenu().onMouseDraggedEvent(e);
		default:
			break;
		}
	}
	
	public void onMouseMovedEvent(MouseEvent e) {	

		if (game.getGameState().equals(GameState.Menu)) {
			switch(getMenuState()) {
			case NONE:
				if (playButton.getBounds().contains(e.getPoint())) {
					setPlayButtonColor(Color.blue);
				} else setPlayButtonColor(Color.white);
				if (settingsButton.contains(e.getPoint())) {
					setSettingsButtonColor(Color.green);
				} else setSettingsButtonColor(Color.white);
					
				if (quitButton.contains(e.getPoint())) {
					setQuitButtonColor(Color.red);
				} else setQuitButtonColor(Color.white);
					
				if (characterButton.contains(e.getPoint())) {
					setCharaterButtonColor(Color.yellow);
				} else setCharaterButtonColor(Color.white);
				break;
				
			case CHARACTERBUTTON:
				if(cancelButton.contains(e.getPoint())) {
							setCancelButtonColor(Color.red);
				} else setCancelButtonColor(Color.white);
					
				// fire Button
				if (this.arcFire.contains(e.getPoint())) {
					this.fireHover = true;
				}else fireHover = false;
					
					// Plant Button
				if (this.arcPlant.contains(e.getPoint())) {
					this.plantHover= true;
				}else plantHover = false;
				// Water button
				if (this.arcWater.contains(e.getPoint())) {
					this.waterHover = true;
				} else waterHover = false;
			break;
			
			case SETTINGSBUTTON:
				this.game.getSettingsMenu().onMouseMovedEvent(e);
				if(cancelButton.contains(e.getPoint())) {
					setCancelButtonColor(Color.red);
				} else setCancelButtonColor(Color.white);
				break;
			default:
				break;
			}
		}
		// @formatter:on
	}

	public void onkeyPressedEvent(KeyEvent e) {
		switch (this.menuState) {
		case SETTINGSBUTTON:
			this.game.getSettingsMenu().onKeyPressedEvent(e);
			break;

		default:
			break;
		}
	}

	public Color setColor(int r, int g, int b) {
		return new Color(r, g, b);
	}

	public MenuState getMenuState() {
		return menuState;
	}

	public void setMenuState(MenuState menuState) {
		this.menuState = menuState;
	}

	public void setPlayButtonColor(Color playButtonColor) {
		this.playButtonColor = playButtonColor;
	}

	public void setHelpButtonColor(Color helpButtonColor) {
		this.helpButtonColor = helpButtonColor;
	}

	public void setCharaterButtonColor(Color charaterButtonColor) {
		CharaterButtonColor = charaterButtonColor;
	}

	public void setSettingsButtonColor(Color settingsButtonColor) {
		this.settingsButtonColor = settingsButtonColor;
	}

	public void setQuitButtonColor(Color quitButtonColor) {
		this.quitButtonColor = quitButtonColor;
	}

	public void setCancelButtonColor(Color cancelButtonColor) {
		this.cancelButtonColor = cancelButtonColor;
	}

}
