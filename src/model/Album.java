/**
 * @author Bola Henine
 *
 * @author Roshan Seth
 */
package model;

import java.util.ArrayList;

/**
 * The Album class
 */

public class Album implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = 1891567810783724951L;
	private String name;
	private ArrayList<Photo> photos;

	/**
	 * @param name
	 *            The constructor of the album
	 */

	public Album(String name) {
		this.name = name;
		photos = new ArrayList<Photo>();
	}

	/**
	 * @return name the name of the album
	 */

	public String getName() {
		return this.name;
	}

	/**
	 * @param albumName
	 *            sets the name of the album.
	 */

	public void setName(String albumName) {
		name = albumName;
	}

	/**
	 * @param image
	 *            adds a photo to the album
	 */

	public void addPhoto(Photo image) {
		photos.add(image);
	}

	/**
	 * @return photos a list of the photos in the album.
	 */

	public ArrayList<Photo> getPhotos() {
		return photos;
	}

	/**
	 * @param image
	 *            removes a photo from the album.
	 */

	public void removePhoto(Photo image) {
		photos.remove(image);
	}

}
