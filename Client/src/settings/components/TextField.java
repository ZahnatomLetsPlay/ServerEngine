package settings.components;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TextField {

	private int x;
	private int y;
	private int width;
	private int height;
	private int lineY;
	private int lineX1;
	private int lineX2;
	private int previousKey;
	private int newKey = 23878924;
	private int capsCounter;

	private final int characterLimit;

	private final char Cursor = '|';

	private long time = System.currentTimeMillis();

	private String text = "";

	private final String font = "arial";
	private String previewText;

	private Point mousePos;

	private Rectangle bounds;

	private boolean focusable;
	private boolean delete = false;
	private boolean hasFocus = false;
	private boolean init = false;
	private boolean capsOn;

	public TextField(boolean focusable, String previewText, int characterLimit) {
		this.focusable = focusable;
		this.previewText = previewText;
		this.characterLimit = characterLimit;

	}

	public TextField(int x, int y, int width, int height, boolean focusable, String previewText, int CharacterLimit) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.lineY = this.y + this.height;
		this.lineX1 = this.x;
		this.lineX2 = this.x + this.width;
		this.bounds = new Rectangle(x, y, width, height);
		this.focusable = focusable;
		this.previewText = previewText;
		this.characterLimit = CharacterLimit;

	}

	public void setText(String text) {
		this.previewText = text;
	}

	public boolean hasBeenEdited() {
		return !previewText.equals(text);
	}

	public void setBounds(int x, int y, int width, int height) {
		if (!init) {
			this.bounds = new Rectangle(x, y, width, height);
			this.lineY = this.bounds.y + this.bounds.height;
			this.lineX1 = this.bounds.x;
			this.lineX2 = this.bounds.x + this.bounds.width;
			init = true;
		}
	}

	private void addCharacter(String str) {
		if (this.bounds.contains(mousePos)) {
			if (text.length() <= this.characterLimit) {
				this.text += capsOn ? str.toUpperCase() : str.toLowerCase();
			}
		}
	}

	private void deleteLastCharacter() {
		if (this.bounds.contains(mousePos)) {
			if (!this.text.isBlank()) {
				this.text = text.substring(0, this.text.length() - 1);
			}
		}
		delete = true;
	}

	public int getCharacterLimit() {
		return this.characterLimit;
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		// test rectangle
		// g2d.draw(bounds);

		if (focusable) {

			if (bounds != null && mousePos != null) {

				if (bounds.contains(mousePos) && hasFocus) {

					g2d.setStroke(new BasicStroke(7f));
					g2d.setFont(new Font(font, Font.BOLD, 30));

					int offset = (int) (this.bounds.getCenterX() - g2d.getFontMetrics().stringWidth(text) / 2)
							+ g2d.getFontMetrics().stringWidth(text) + 1;

					if (System.currentTimeMillis() - time > 400 * 3) {
						g2d.drawString(this.Cursor + "", offset,
								(int) this.bounds.getCenterY() + g2d.getFontMetrics().getHeight() / 4 - 2);

						time = System.currentTimeMillis();
					} else if (System.currentTimeMillis() - time > 2 * 400) {
						g2d.drawString(this.Cursor + "", offset,
								(int) this.bounds.getCenterY() + g2d.getFontMetrics().getHeight() / 4 - 2);

					} else if (System.currentTimeMillis() - time > 400) {
						g2d.drawString(this.Cursor + "", offset,
								(int) this.bounds.getCenterY() + g2d.getFontMetrics().getHeight() / 4 - 2);
					}
				} else {
					g2d.setFont(new Font(font, Font.PLAIN, 30));
					g2d.setStroke(new BasicStroke(4f));

				}

			}
			if (mousePos != null && bounds.contains(mousePos)) {
				g2d.setStroke(new BasicStroke(7f));
				g2d.setFont(new Font(font, Font.BOLD, 30));
			} else {
				g2d.setFont(new Font(font, Font.PLAIN, 30));
				g2d.setStroke(new BasicStroke(4f));
			}
			if (this.text.isBlank() && !hasFocus) {
				g2d.setFont(null);
				g2d.drawString(previewText,
						(int) (this.bounds.getCenterX() - g2d.getFontMetrics().stringWidth(previewText) / 2),
						(int) this.bounds.getCenterY() + g2d.getFontMetrics().getHeight() / 4);
			}

			// sets focus to this component if the mouse is hovering over this component

			g2d.drawLine(lineX1, lineY, lineX2, lineY);
		}
		if (!delete) {
			g2d.drawString(text, (int) (this.bounds.getCenterX() - g2d.getFontMetrics().stringWidth(text) / 2),
					(int) this.bounds.getCenterY() + g2d.getFontMetrics().getHeight() / 4);
		} else {

			g2d.drawString(text, (int) (this.bounds.getCenterX() - g2d.getFontMetrics().stringWidth(text) / 2),
					(int) this.bounds.getCenterY() + g2d.getFontMetrics().getHeight() / 4);
			this.delete = false;
		}

	}

	public void loadUsername(String username) {
		this.text = username;
	}

	public String getText() {
		return this.text.isEmpty() ? this.previewText : this.text.toString();
	}

	public void onKeyPressedEvent(KeyEvent e) {
		if (newKey != 23878924) {
			this.previousKey = this.newKey;
		}
		this.newKey = e.getKeyCode();

		if (KeyEvent.VK_CAPS_LOCK == e.getKeyCode()) {
			capsCounter++;
			if (capsCounter == 1) {
				capsOn = true;
			} else if (capsCounter == 2) {
				capsCounter = 0;
				capsOn = false;
			}
		}

		filterKeys(e);
	}

	public void filterKeys(KeyEvent e) {
		if (previousKey == KeyEvent.VK_SHIFT) {
			if (newKey == KeyEvent.VK_MINUS) {
				addCharacter("_");
			} else if (newKey == KeyEvent.VK_7) {
				addCharacter("/");
			} else if (newKey == KeyEvent.VK_PERIOD) {
				addCharacter(":");
			}
		} else {
			if (e.getKeyCode() == KeyEvent.VK_PERIOD) {
				addCharacter(".");
			}
			if (e.getKeyCode() == KeyEvent.VK_MINUS) {
				addCharacter("-");
			}
			if (bounds != null && hasFocus) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					deleteLastCharacter();
				}
				if (e.getKeyCode() != KeyEvent.VK_SPACE) {
					if (Character.isLetterOrDigit(e.getKeyCode())) {
						addCharacter(Character.toString(e.getKeyCode()));
					}
				}
			}
		}
	}

	public void onMouseClickedEvent(MouseEvent e) {
		this.mousePos = e.getPoint();
		if (bounds != null && this.bounds.contains(this.mousePos)) {
			this.hasFocus = true;
		} else
			this.hasFocus = false;
	}

	public void onMouseMovedEvent(MouseEvent e) {
		this.mousePos = e.getPoint();
		if (bounds != null && hasFocus && !bounds.contains(this.mousePos)) {
			this.hasFocus = false;
		}
	}
}
