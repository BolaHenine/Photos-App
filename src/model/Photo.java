/**
 * @author Bola Henine
 *
 * @author Roshan Seth
 */
package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.image.Image;

/**
 * The photo class
 */

public class Photo implements java.io.Serializable {

	private static final long serialVersionUID = 1891567810783724951L;
	private String name;
	private HashMap<String, List<String>> tags;
	private String caption;
	private LocalDateTime date;
	private SerializableImage photo;
	public static final String storeDir = "data";
	public static final String storeFile = "datas.dat";

	/**
	 * @param photoName   the name of the photo
	 * @param image       the image itself
	 * @param createdDate the date that the photo was created on
	 * @param captionName the captions of the photo
	 *
	 *                    The photo constructor
	 */

	public Photo(String photoName, Image image, LocalDateTime createdDate, String captionName) {
		name = photoName;
		photo = new SerializableImage(image);
		date = createdDate;
		caption = captionName;
	}

	/**
	 *
	 * @return name the name of the photo
	 */

	public String getName() {
		return name;
	}

	/**
	 *
	 * @return caption the caption of the photo
	 */

	public String getCaption() {
		return caption;
	}

	/**
	 *
	 * @return photo.getImage() the image
	 */

	public Image getImage() {
		return photo.getImage();
	}

	/**
	 *
	 * @return photo the image
	 */

	public SerializableImage getSerImage() {
		return photo;
	}

	/**
	 *
	 * @return date the date of the photo
	 */

	public LocalDateTime getDate() {
		return date;
	}

	/**
	 *
	 * @return tags the tags of the photo
	 */

	public HashMap<String, List<String>> getTag() {
		if (tags == null) {
			tags = new HashMap<>();
		}
		return tags;
	}

	/**
	 *
	 * @param tagName  the name of the tag
	 * @param tagValue the value of the tag
	 */

	public void addTag(String tagName, String tagValue) {
		tags.computeIfAbsent(tagName, k -> new ArrayList<>()).add(tagValue);
	}

	/**
	 *
	 * @param tagName removes the tag
	 */

	public void deleteTag(String tagName) {
		tags.remove(tagName);
	}

	/**
	 *
	 * @param captionName  deletes the caption name of the photo
	 * @param modifiedDate changes the date of the photo
	 */

	public void deleteCaption(String captionName, LocalDateTime modifiedDate) {
		captionName = "";
		caption = captionName;
		date = modifiedDate;
	}

	/**
	 *
	 * @param name         sets the name of the photo
	 * @param modifiedDate changes the date of the photo
	 */

	public void setName(String name, LocalDateTime modifiedDate) {
		this.name = name;
		date = modifiedDate;
	}

	/**
	 *
	 * @param captionName  changes the caption name of the photo
	 * @param modifiedDate changes the date of the photo
	 */

	public void recaption(String captionName, LocalDateTime modifiedDate) {
		caption = captionName;
		date = modifiedDate;
	}

}
