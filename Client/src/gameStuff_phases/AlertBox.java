package gameStuff_phases;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class AlertBox {

	private Rectangle bounds;
	private String warning = "";

	private boolean init = false;

	private int x;
	private int y;
	private int width;
	private int height;

	public AlertBox(int x, int y) {
		this.x = x;
		this.y = y;

	}

	public void setWarningAlert(String warning) {
		this.warning = warning;
	}

	public void show(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));

		if (!init) {
			this.width = g2d.getFontMetrics().stringWidth(warning);
			this.height = g2d.getFontMetrics().getHeight() * 2;
			this.bounds = new Rectangle(x, y, this.width, this.height);
			this.init = true;
		}

		g2d.setColor(new Color(2, 132, 199));
		g2d.fill(bounds);

		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
		g2d.setColor(new Color(239, 68, 68));
		g2d.drawString(warning, (int) (this.bounds.getCenterX() - this.width / 2),
				(int) this.bounds.getCenterY() + this.height / 3);
	}

}
