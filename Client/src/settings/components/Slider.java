package settings.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class Slider {

	private int buttonWidth;
	private int value;
	private int defaultVol;

	private boolean isFocusable;

	private boolean init = false;

	private boolean isDragged = false;

	private Point mousePos;

	private Rectangle bounds;

	private Color sliderButtonColor = new Color(22, 78, 99);

	private Rectangle sliderButton;
	private Rectangle filledRectangle;
	private RoundRectangle2D valueBox;

	//@formatter:off
	public Slider(boolean isFocusable, int value) {
		this.isFocusable = isFocusable;
		this.value = value;
	}
	
	public boolean hasBeenEdited() {
		return defaultVol != value; 
	}
	public int getSliderPosX() {
		return sliderButton.x;
	}
	
	public void setDefaultVolume(int Volume) {
		this.defaultVol = Volume;
	}
	
	
	//@formatter:on
	public Slider(Rectangle bounds, int buttonWidth, boolean isFocusable) {
		this.bounds = bounds;

		this.sliderButton = new Rectangle((int) this.bounds.getCenterX() - buttonWidth / 2,
				this.bounds.y - this.bounds.height / 2, buttonWidth, this.bounds.height * 2);
		this.buttonWidth = buttonWidth;
		this.value = (int) (this.sliderButton.getCenterX() / this.bounds.width * 100);
		this.isFocusable = isFocusable;
		this.filledRectangle = new Rectangle(bounds.x, bounds.y,
				(int) (this.sliderButton.getCenterX() - this.bounds.getX()), bounds.height);
		this.valueBox = new RoundRectangle2D.Double(sliderButton.x - this.buttonWidth / 2,
				this.sliderButton.getCenterY() - this.bounds.height * 1.5 / 2, this.buttonWidth,
				this.bounds.height * 1.5, 10, 10);

	
	}

	public void setBounds_specifics(Rectangle bounds, int buttonWidth) {
		if (!init) {
			this.bounds = bounds;
			this.sliderButton = new Rectangle((int) this.bounds.getCenterX() - this.buttonWidth / 2,
					this.bounds.y - this.bounds.height / 2, this.buttonWidth, this.bounds.height * 2);
			this.value = (int) ((this.sliderButton.getCenterX() - this.bounds.getX()) / this.bounds.getWidth() * 100);
			this.buttonWidth = buttonWidth;
			init = true;
			this.filledRectangle = new Rectangle(bounds.x, bounds.y,
					(int) (this.sliderButton.getCenterX() - this.bounds.getX()), bounds.height);
			this.valueBox = new RoundRectangle2D.Double(sliderButton.x - this.buttonWidth / 2,
					this.sliderButton.getCenterY() - this.bounds.height * 1.5 / 2, this.buttonWidth,
					this.bounds.height * 1.5, 10, 10);
		}
	
	}

	public void setSliderButtonPos(int x) {
		this.sliderButton.setLocation(new Point(x, this.bounds.y - this.bounds.height / 2));
		this.valueBox.setFrame(new Rectangle(sliderButton.x - this.buttonWidth / 2,
				(int) (this.sliderButton.getCenterY() - this.bounds.height * 1.5 / 2), this.buttonWidth,
				(int) (this.bounds.height * 1.5)));
		this.filledRectangle = new Rectangle(bounds.x, bounds.y,
				(int) (this.sliderButton.getCenterX() - this.bounds.getX()), bounds.height);
		this.value = (int) ((this.sliderButton.getCenterX() - this.bounds.getX()) / this.bounds.getWidth() * 100);

	}

	public int getValue() {
		return this.value;
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		if (isFocusable) {

			g2d.setColor(Color.white);
			g2d.fill(bounds);

			g2d.setColor(new Color(156, 83, 5));
			g2d.fill(filledRectangle);

			if (getValue() != 0) {
				g2d.setColor(sliderButtonColor);
			} else {
				g2d.setColor(new Color(102, 3, 1));
			}
			g2d.fill(valueBox);

			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("arial", Font.PLAIN, 20));
			g2d.drawString(this.value + "%",
					(int) (this.sliderButton.getCenterX() - g2d.getFontMetrics().stringWidth(this.value + "%") / 2),
					(int) (this.sliderButton.getCenterY() + g2d.getFontMetrics().getHeight() / 4));
		}

	}

	public void onMouseDraggedEvent(MouseEvent e) {
		this.mousePos = e.getPoint();

		if (sliderButton != null && this.mousePos.getX() >= this.bounds.getX()
				&& this.mousePos.getX() <= this.bounds.getX() + this.bounds.getWidth()
				&& this.valueBox.contains(mousePos) || this.bounds.contains(mousePos)) {

			setSliderButtonColor(new Color(3, 105, 161));
			setSliderButtonPos(this.mousePos.x);
			isDragged = true;
		} else {
			isDragged = false;
		}
	}

	public boolean isDragged() {
		return this.isDragged;
	}

	private void setSliderButtonColor(Color color) {
		this.sliderButtonColor = color;
	}

	public void onMouseMovedEvent(MouseEvent e) {
		this.mousePos = e.getPoint();
		if (valueBox != null) {
			if (this.valueBox.contains(mousePos)) {
				setSliderButtonColor(new Color(3, 105, 161));
			} else {
				setSliderButtonColor(new Color(22, 78, 99));
			}
		}
	}

}
