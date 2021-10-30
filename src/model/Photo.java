package model;

import java.util.Calendar;
import java.util.HashMap;

import javafx.scene.image.Image;

public class Photo implements java.io.Serializable {

	private static final long serialVersionUID = 1891567810783724951L;
	private String name;
	private HashMap<String, String> tags;
	private String caption;
	private Calendar date;
	private SerializableImage photo;
	public static final String storeDir = "data";
	public static final String storeFile = "datas.dat";

	public Photo(String photoName, Image image, Calendar createdDate,
			String captionName) {
		name = photoName;
		photo = new SerializableImage(image);
		date = createdDate;
		caption = captionName;
	}

	public String getName() {
		return name;
	}

	public String getCaption() {
		return caption;
	}

	public Image getImage() {
		return photo.getImage();
	}

	public Calendar getDate() {
		return date;
	}

	public HashMap<String, String> getTag() {
		if (tags == null) {
			tags = new HashMap<String, String>();
		}
		return tags;
	}

	public void addTag(String tagName, String tagValue) {
		tags.put(tagName, tagValue);
	}

}
