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

}