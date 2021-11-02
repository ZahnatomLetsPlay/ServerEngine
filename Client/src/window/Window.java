package window;

import java.awt.Dimension;

import javax.swing.JFrame;

import gameStuff_phases.Game;

public class Window {

	private ScreenModifier screen;

	private JFrame frame;

	public Window(int width, int height, Game game) {
		this.screen = new ScreenModifier();

		this.frame = new JFrame();

		frame.setPreferredSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));

		frame.add(game);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	

		screen.setToFullScreen(screen.getAvailableDisplayMode(), frame);

	}

	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}
}
