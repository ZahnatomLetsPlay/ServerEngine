package gameStuff_phases;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import gameobjects_states.GameState;
import gameobjects_states.Type;

public class PickPhase {

	private Game game;
	private Rectangle Character1;
	private Rectangle Character2;
	private Rectangle Character3;
	private Rectangle Character4;
	private Rectangle cancelButton;

	private RoundRectangle2D proceedButton;
	private RoundRectangle2D returnToPickPhaseButton;

	private Rectangle selectedCharBounds;

	private int rectWidth = 0;
	private int rectHeight;
	private int dist = 80;
	private int xOff;
	private int yOff;

	private float strokeProceed = 0f;
	private float strokeReturn = 0f;

	// private Color cancelCol = Color.white;
	private Color Char1Col = Color.white;
	private Color Char2Col = Color.white;
	private Color Char3Col = Color.white;
	private Color Char4Col = Color.white;
	private Color cancelButtonColor = Color.white;
	private Color proceedButonColor = Color.white;
	private Color returnToPickPhaseButtonColor = Color.white;

	private Stroke char1Str = new BasicStroke(1f);
	private Stroke char2Str = new BasicStroke(1f);
	private Stroke char3Str = new BasicStroke(1f);
	private Stroke char4Str = new BasicStroke(1f);

	private BufferedImage plantBackground, plantBackgroundText;
	private BufferedImage waterBackground, waterBackgroundText;
	private BufferedImage fireBackground, fireBackgroundText;
	private BufferedImage randomBackground, randomBackgroundText;

	private BufferedImage selectedCharacter[];
	private Point mousePos = null;

	private final double originalWidth = (576);
	private final double originalHeight = (1080);

	private BufferedImage[] preScaledRandom;

	private String confirmText = "Do you want to play %s?";

	private Type playerType = Type.unknown;

	private boolean random = false;
	private boolean cancelButtonCreated = false;
	private boolean loaded = false;

	public PickPhase(Game game) {

		this.game = game;

		xOff = (int) (0.12 * game.getWidth());
		yOff = (int) (0.2 * game.getHeight());

		rectHeight = (int) (0.5 * game.getHeight());
		rectWidth = (int) (0.15 * game.getWidth());

		Character1 = new Rectangle(xOff, yOff, rectWidth, rectHeight);
		Character2 = new Rectangle(xOff + rectWidth + dist, yOff, rectWidth, rectHeight);
		Character3 = new Rectangle(xOff + 2 * rectWidth + 2 * dist, yOff, rectWidth, rectHeight);
		Character4 = new Rectangle(xOff + 3 * rectWidth + 3 * dist, yOff, rectWidth, rectHeight);

	}

	public void render(Graphics g) {
		if (!loaded) {

			cancelButton = new Rectangle(game.getWidth() - (int) (this.game.getWidth() * 10.0 / 192.0),
					(int) (game.getHeight() * 19.0 / 1080),
					(int) (this.game.getWidth() * 25.0 / 1920.0),
					(int) (this.game.getHeight() * 33.0 / 1080.0));

			// plant backgrounds
			this.plantBackground = getScaledImage("/char_select/plant_select.png", rectWidth, rectHeight,
					this.originalWidth,
					this.originalHeight);

			this.plantBackgroundText = getScaledImage("/char_select/plant_select_text.png", rectWidth, rectHeight,
					this.originalWidth, this.originalHeight);

			// fire backgrounds
			this.fireBackground = getScaledImage("/char_select/fire_select.png", rectWidth, rectHeight,
					this.originalWidth,
					this.originalHeight);

			this.fireBackgroundText = getScaledImage("/char_select/fire_select_text.png", rectWidth, rectHeight,
					this.originalWidth,
					this.originalHeight);

			// plant backgrounds
			this.waterBackground = getScaledImage("/char_select/water_select.png", rectWidth, rectHeight,
					this.originalWidth,
					this.originalHeight);

			this.waterBackgroundText = getScaledImage("/char_select/water_select_text.png", rectWidth, rectHeight,
					this.originalWidth, this.originalHeight);

			// random Background

			this.randomBackground = getScaledImage("/char_select/random_select.png", rectWidth, rectHeight,
					this.originalWidth,
					this.originalHeight);

			this.randomBackgroundText = getScaledImage("/char_select/random_select_text.png", rectWidth, rectHeight,
					this.originalWidth, this.originalHeight);

			// pickphase_Confirm characters_prescaled for that purpose
			this.preScaledRandom = new BufferedImage[] { getConfirmPhaseScaledImage(plantBackground),
					getConfirmPhaseScaledImage(plantBackgroundText), getConfirmPhaseScaledImage(fireBackground),
					getConfirmPhaseScaledImage(fireBackgroundText), getConfirmPhaseScaledImage(waterBackground),
					getConfirmPhaseScaledImage(waterBackgroundText), getConfirmPhaseScaledImage(randomBackground),
					getConfirmPhaseScaledImage(randomBackgroundText) };

			this.proceedButton = new RoundRectangle2D.Double(
					((this.game.getWidth() + preScaledRandom[0].getWidth()) / 2 - this.game.getWidth() * 0.25),
					(this.game.getHeight() / 2), (this.game.getWidth() * 0.15), (this.game.getHeight() * 0.09), 4, 4);

			this.returnToPickPhaseButton = new RoundRectangle2D.Double(
					((this.game.getWidth() + preScaledRandom[0].getWidth()) / 2 + proceedButton.getWidth() * 0.3),
					(this.game.getHeight() / 2), (this.game.getWidth() * 0.15), (this.game.getHeight() * 0.09), 4, 4);

			this.selectedCharBounds = new Rectangle(2, 0, preScaledRandom[0].getWidth() - 2,
					preScaledRandom[0].getHeight());

			loaded = true;
		}

		Graphics2D g2d = (Graphics2D) g;

		switch (game.getGameState()) {
		case Pickphase:
			// cancel Button

			g2d.setColor(cancelButtonColor);
			g2d.setFont(new Font("arial", Font.PLAIN, 40));
			if (!this.cancelButtonCreated) {
				int width = g2d.getFontMetrics().stringWidth("X");
				int height = g2d.getFontMetrics().getHeight();
				this.cancelButton = new Rectangle(game.getWidth() - 100, height, width, height - 15);
				this.cancelButtonCreated = true;
			}
			// draw CancelButton
			// g2d.draw(cancelButton);
			g2d.drawString("X", (int) (this.cancelButton.getCenterX() - this.cancelButton.width / 2),
					(int) (this.cancelButton.y + this.cancelButton.getCenterY() / 2));

			// title
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Arial", Font.PLAIN, 40));
			g2d.drawString("select a character to proceed",
					(game.getWidth() - g.getFontMetrics().stringWidth("select a character to proceed")) / 2,
					(int) (game.getHeight() * 0.09));

			// characters
			drawImages(g2d, plantBackground, plantBackgroundText, char1Str, Char1Col, Character1);
			drawImages(g2d, fireBackground, fireBackgroundText, char2Str, Char2Col, Character2);
			drawImages(g2d, waterBackground, waterBackgroundText, char3Str, Char3Col, Character3);
			drawImages(g2d, randomBackground, randomBackgroundText, char4Str, Char4Col, Character4);

			break;

		case Pickphase_confirm:
			g2d.setColor(new Color(39, 39, 42));
			g2d.fillRect(0, 0, this.game.getWidth(), this.game.getHeight());

			// centering not optimized (eye-balled)
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Arial", Font.BOLD, 50));
			String tempSelectText = String.format(confirmText,
					random ? "random character" : this.playerType.toString());

			g2d.drawString(tempSelectText, (this.game.getWidth() + preScaledRandom[0].getWidth()) / 2
					- g2d.getFontMetrics().stringWidth(tempSelectText) / 2, (int) (game.getHeight() * 0.09));

			g2d.setColor(new Color(132, 204, 22));
			g2d.fill(proceedButton);
			g2d.setColor(proceedButonColor);
			g2d.setStroke(new BasicStroke(strokeProceed));
			g2d.draw(proceedButton);

			// centering is optimized
			g2d.setColor(Color.WHITE);
			drawText(g2d, "connect", proceedButton.getBounds(), 40, Font.BOLD);

			g2d.setColor(new Color(244, 63, 94));
			g2d.fill(returnToPickPhaseButton);
			g2d.setStroke(new BasicStroke(strokeReturn));
			g2d.setColor(returnToPickPhaseButtonColor);
			g2d.draw(returnToPickPhaseButton);

			g2d.setColor(Color.WHITE);

			drawText(g2d, "cancel", returnToPickPhaseButton.getBounds(), 40, Font.PLAIN);

			drawselectedImage(g2d, getSelectedCharacters()[0], getSelectedCharacters()[1], char1Str, Char1Col);

			break;

		default:
			break;
		}
	}

	private void drawText(Graphics2D g, String text, Rectangle button, int fontSize, int fontStyle) {
		g.setFont(new Font("Arial", Font.PLAIN, fontSize));
		int wo = g.getFontMetrics().stringWidth(text);
		int ho = g.getFontMetrics().getHeight();
		g.drawString(text, (int) (button.getCenterX() - wo / 2), (int) (button.getCenterY() + ho / 4));
	}

	public void drawselectedImage(Graphics2D g2d, BufferedImage img1, BufferedImage img2, Stroke lineStroke,
			Color color) {
		g2d.setStroke(lineStroke);
		g2d.setColor(color);
		// g2d.draw(selectedCharBounds);

		if (mousePos != null && selectedCharBounds.contains(mousePos)) {
			g2d.drawImage(img2, selectedCharBounds.x, selectedCharBounds.y, img2.getWidth(), img2.getHeight(), null);
		} else {
			g2d.drawImage(img1, selectedCharBounds.x, selectedCharBounds.y, img1.getWidth(), img1.getHeight(), null);
		}
	}

	public void drawImages(Graphics2D g2d, BufferedImage image, BufferedImage image_text, Stroke stroke, Color color,
			Rectangle rect) {
		g2d.setStroke(stroke);
		g2d.setColor(color);
		g2d.draw(rect);

		if (mousePos != null && rect.contains(mousePos)) {
			g2d.drawImage(image_text, rect.x, rect.y, image_text.getWidth(), image_text.getHeight(), null);
		} else {
			g2d.drawImage(image, rect.x, rect.y, image.getWidth(), image.getHeight(), null);
		}

	}

	public void setSelectedCharacter(BufferedImage... character) {
		selectedCharacter = new BufferedImage[character.length];

		for (int i = 0; i < character.length; i++) {
			selectedCharacter[i] = character[i];
		}
	}

	public BufferedImage[] getSelectedCharacters() {
		return this.selectedCharacter;
	}

	public void onMouseClickedEvent(MouseEvent e) {

		Point mousePos = e.getPoint();
		if (e.getButton() == MouseEvent.BUTTON1 && loaded) {
			switch (game.getGameState()) {
			case Pickphase:
				if (Character1.getBounds().contains(mousePos)) {
					setSelectedCharacter(preScaledRandom[0], preScaledRandom[1]);

					playerType = Type.plant;
					game.setGameState(GameState.Pickphase_confirm);

				} else if (Character2.getBounds().contains(mousePos)) {
					setSelectedCharacter(preScaledRandom[2], preScaledRandom[3]);
					playerType = Type.fire;

					game.setGameState(GameState.Pickphase_confirm);

				} else if (Character3.getBounds().contains(mousePos)) {
					setSelectedCharacter(preScaledRandom[4], preScaledRandom[5]);

					playerType = Type.water;

					game.setGameState(GameState.Pickphase_confirm);

				} else if (Character4.getBounds().contains(mousePos)) {
					random = true;

					int randomValue = ThreadLocalRandom.current().nextInt(0, 6);

					playerType = randomValue == 0 || randomValue == 1 ? Type.plant
							: randomValue == 2 || randomValue == 3 ? Type.fire : Type.water;

					setSelectedCharacter(preScaledRandom[6], preScaledRandom[7]);

					game.setGameState(GameState.Pickphase_confirm);

				} else if (cancelButton.getBounds().contains(mousePos)) {
					cancelButtonColor = Color.white;
					game.setGameState(GameState.Menu);
				}
				break;

			case Pickphase_confirm:
				if (this.returnToPickPhaseButton.getBounds().contains(mousePos)) {
					this.game.setGameState(GameState.Pickphase);
					returnToPickPhaseButtonColor = Color.white;
					playerType = Type.unknown;
					strokeReturn = 0f;
					random = false;
				} // build a connection
				else if (this.proceedButton.getBounds().contains(mousePos)) {
					this.game.setGameState(GameState.InQueue);

					this.game.getQueue().startQueuing(playerType);

					random = false;
				}
				break;

			default:
				break;
			}
		}
	}

	private BufferedImage getConfirmPhaseScaledImage(BufferedImage img) {
		if (img != null) {
			return this.game.getImageLoader().setBufferedImage(img).getScaledImage(this.game.getHeight());
		} else {
			return null;
		}
	}

	private BufferedImage getScaledImage(String path, double desiredWidth, double desiredHeight, double originalWidth,
			double originalHeight) {
		return this.game.getImageLoader().loadImg(path).getScaledImage(desiredWidth, desiredHeight, originalWidth,
				originalHeight);
	}

	public void onMouseMovedEvent(MouseEvent e) {

		mousePos = e.getPoint();
		if (loaded) {
			switch (this.game.getGameState()) {
			case Pickphase:
				if (Character1.getBounds().contains(e.getPoint())) {
					Char1Col = Color.green;
					char1Str = new BasicStroke(8f);
				} else {
					char1Str = new BasicStroke(0f);
					Char1Col = Color.white;
				}
				if (Character2.getBounds().contains(e.getPoint())) {
					Char2Col = Color.RED;
					char2Str = new BasicStroke(8f);

				} else {
					Char2Col = Color.white;
					char2Str = new BasicStroke(0f);
				}
				if (Character3.getBounds().contains(e.getPoint())) {
					Char3Col = Color.CYAN;
					char3Str = new BasicStroke(8f);

				} else {
					Char3Col = Color.white;
					char3Str = new BasicStroke(0f);
				}
				if (Character4.getBounds().contains(e.getPoint())) {
					Char4Col = Color.WHITE;
					char4Str = new BasicStroke(8f);
				} else {
					char4Str = new BasicStroke(0f);
					Char4Col = Color.white;
				}
				if (cancelButton.getBounds().contains(mousePos)) {
					cancelButtonColor = Color.red;

				} else {
					cancelButtonColor = Color.white;
				}
				break;
			case Pickphase_confirm:
				if (selectedCharBounds.getBounds().contains(mousePos)) {
					Char1Col = Color.CYAN;
					char1Str = new BasicStroke(8f);
				} else {
					Char1Col = Color.white;
					char1Str = new BasicStroke(0f);
				}
				if (proceedButton.getBounds().contains(mousePos)) {
					proceedButonColor = Color.orange;
					strokeProceed = 5f;
				} else {
					proceedButonColor = Color.white;
					strokeProceed = 0f;
				}
				if (this.returnToPickPhaseButton.getBounds().contains(mousePos)) {
					this.returnToPickPhaseButtonColor = Color.orange;
					this.strokeReturn = 5f;
				} else {
					this.returnToPickPhaseButtonColor = Color.white;
					this.strokeReturn = 0f;

				}
				break;
			default:
				break;
			}
		}
	}

}
