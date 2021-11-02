package window;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Window;

import javax.swing.JFrame;

public class ScreenModifier {

	private GraphicsDevice videoCard;

	public ScreenModifier() {
		GraphicsEnvironment gEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

		videoCard = gEnvironment.getDefaultScreenDevice();
	}

	public void setToFullScreen(DisplayMode displayMode, JFrame window) {

		
		window.setUndecorated(true); // sets all pre-designed components to null
		window.setResizable(false); // window is no longer resizable
		
		if (videoCard.isFullScreenSupported()) {	
			
			videoCard.setFullScreenWindow(window);
			window.setVisible(false);
		}
		if (displayMode != null && videoCard.isDisplayChangeSupported()) {
			try {
				videoCard.setDisplayMode(displayMode);

			} catch (IllegalArgumentException ArgEx) {
				ArgEx.printStackTrace();

			} catch (UnsupportedOperationException UnsupportedEx) {
				UnsupportedEx.printStackTrace();
			}
		}

	}
	
	public Window getFullScreenWindow() {
		return videoCard.getFullScreenWindow();
	}

	// sets the screen size to its original size
	public void restoreScreen() {
		Window w = videoCard.getFullScreenWindow();

		if (w != null) {
			w.dispose();
		}

		videoCard.setFullScreenWindow(null);

	}

	public Point screenDimension() {
		return new Point(getFullScreenWindow().getWidth(), getFullScreenWindow().getHeight());
	}

	// returns the availabe displaymode
	public DisplayMode getAvailableDisplayMode() {
		if (videoCard.isFullScreenSupported()) {
			DisplayMode mode = videoCard.getDisplayMode();
		
			return mode;
		} else {
			return null;
		}
	}
}
