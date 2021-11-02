package rendering;

import java.awt.image.BufferedImage;

import gameStuff_phases.Game;
import gameobjects_states.Type;

public class GameScene {

	private Game game;
	private boolean loaded = false;

	public GameScene(Game game) {
		this.game = game;
		/**
		 * BufferedImage bi = new BufferedImage(this.img.getWidth(),
		 * this.img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		 * 
		 * Graphics g = bi.createGraphics();
		 * 
		 * g.setColor(Color.black); g.fillRect(0, 0, this.img.getWidth(),
		 * this.img.getHeight());
		 * 
		 * for (int i = 0; i < this.img.getWidth(); i += this.xSize) { for (int j = 0; j
		 * < this.img.getHeight(); j += this.ySize) {
		 * 
		 * int pixel = this.img.getRGB(i, j);
		 * 
		 * Color rgb = new Color(pixel);
		 * 
		 * if (rgb.getRed() == 255) { g.setColor(Color.red); g.fillRect(i, j,
		 * this.xSize, this.ySize); continue; } else if (rgb.getBlue() == 255) {
		 * g.setColor(Color.blue); g.fillRect(i, j, this.xSize, this.ySize); continue; }
		 * 
		 * }
		 * 
		 * }
		 * 
		 * g.dispose();
		 * 
		 * try { ImageIO.write(bi, "png", new File("Images/copyyy.png")); } catch
		 * (IOException e) {
		 * 
		 * }
		 */
	}

	public void loadGameScene() {
		if (!loaded) {
			BufferedImage mapImg = this.game.getImageLoader().load_Image("Images//Map.png")
					.getScaledInstance(this.game.getWidth(), this.game.getHeight());
			Map map = new Map(0, 0, Type.none, 0);
			map.setMap(mapImg);
			this.game.getHandler().addGameObject(map);
			loaded = true;
		}
	}

}
