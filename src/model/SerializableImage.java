/**
 * @author Bola Henine
 *
 * @author Roshan Seth
 */
package model;

import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 *
 * creates a the serialization of the image pixel by pixel
 *
 */

public class SerializableImage implements Serializable {

	private static final long serialVersionUID = -1266447099158556616L;
	private int width, height;
	private int[][] pixels;

	/**
	 *
	 * @param image
	 *            the image that will be serialized
	 */

	public SerializableImage(Image image) {
		width = (int) image.getWidth();
		height = (int) image.getHeight();
		pixels = new int[width][height];

		PixelReader reader = image.getPixelReader();
		for (int currentWidth = 0; currentWidth < width; currentWidth++)
			for (int currentHeight = 0; currentHeight < height; currentHeight++)
				pixels[currentWidth][currentHeight] = reader
						.getArgb(currentWidth, currentHeight);
	}

	/**
	 *
	 * @return the image that was serialized
	 */

	public Image getImage() {
		WritableImage image = new WritableImage(width, height);

		PixelWriter w = image.getPixelWriter();
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				w.setArgb(i, j, pixels[i][j]);

		return image;
	}

	/**
	 *
	 * @return the width if the image
	 */

	public int getImageWidth() {
		return width;
	}

	/**
	 *
	 * @return the height of the image
	 */

	public int getImageHeight() {
		return height;
	}

	/**
	 *
	 * @return the pixels of the image
	 */

	public int[][] getImagePixels() {
		return pixels;
	}

	/**
	 *
	 * @param image
	 *            the image that will be compared
	 * @return true if the two images are the same and false if they are not
	 */

	public boolean isEqual(SerializableImage image) {
		if (width != image.getImageWidth() || height != image.getImageHeight())
			return false;

		for (int currentRow = 0; currentRow < width; currentRow++)
			for (int currentColumn = 0; currentColumn < height; currentColumn++)
				if (pixels[currentRow][currentColumn] != image
						.getImagePixels()[currentRow][currentColumn])
					return false;

		return true;
	}

}