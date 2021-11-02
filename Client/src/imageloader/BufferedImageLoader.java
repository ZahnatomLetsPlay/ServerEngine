package imageloader;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {
	private BufferedImage img;

	public BufferedImage loadImage(String path) {
		try {
			this.img = ImageIO.read(new File(path).toURI().toURL());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	/**
	 * loads the image, given that the path is provided
	 */
	public BufferedImageLoader loadImg(String path) {
		try {
			this.img = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public BufferedImageLoader load_Image(String path) {
		try {
			this.img = ImageIO.read(new File(path).toURI().toURL());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * casts a java.awt.Image into a BufferedImage Object
	 * 
	 * @param image
	 * @return BufferedImage object
	 */
	public BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(image, 0, 0, null);
		bGr.dispose();

		// Return the buffered image

		return bimage;
	}

	/**
	 * returns a scaled image based on the original and desired dimensions of the
	 * image
	 * 
	 * @param width:           desired width
	 * @param height:          desired height
	 * @param original_Width:  original width
	 * @param original_Height: original height
	 * @return
	 */
	public BufferedImage getScaledImage(double width, double height, double original_Width, double original_Height) {
		double xScale = width / original_Width;
		double yScale = height / original_Height;

		BufferedImage scaledImg = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);

		AffineTransform scaler = new AffineTransform();
		scaler.scale(xScale, yScale);

		AffineTransformOp transform = new AffineTransformOp(scaler, AffineTransformOp.TYPE_BILINEAR);

		return transform.filter(img, scaledImg);
	}

	/**
	 * just for a specific type of image, not for general scaling purposes. Scales
	 * the image based on the given Height.
	 * 
	 * @param height
	 * @return BufferedImage
	 */
	public BufferedImage getScaledImage(double height) {
		double scale = height / (0.5 * (double) Toolkit.getDefaultToolkit().getScreenSize().height);

		BufferedImage scaledImg = new BufferedImage((int) (scale * img.getWidth()), (int) (scale * img.getHeight()),
				BufferedImage.TYPE_INT_ARGB);

		AffineTransform scaler = new AffineTransform();
		scaler.scale(scale, scale);

		AffineTransformOp transform = new AffineTransformOp(scaler, AffineTransformOp.TYPE_BILINEAR);

		return transform.filter(img, scaledImg);
	}

	/**
	 * scales the image based on the desired {width}.
	 * 
	 * @param width
	 * @return
	 */
	public BufferedImage getScaledImage_W(double width) {
		double scale = width / 1090;

		BufferedImage scaledImg = new BufferedImage((int) (scale * img.getWidth()), (int) (scale * img.getHeight()),
				BufferedImage.TYPE_INT_ARGB);

		AffineTransform scaler = new AffineTransform();
		scaler.scale(scale, scale);

		AffineTransformOp transform = new AffineTransformOp(scaler, AffineTransformOp.TYPE_BILINEAR);

		return transform.filter(img, scaledImg);
	}

	public BufferedImage getScaledInstance(int width, int height) {
		return toBufferedImage(img.getScaledInstance(width, height, Image.SCALE_DEFAULT));
	}

	public BufferedImageLoader setBufferedImage(BufferedImage image) {
		this.img = image;
		return this;
	}

	public BufferedImage getBufferedImage() {
		return this.img;
	}
}
