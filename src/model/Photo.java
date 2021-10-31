package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.image.Image;

public class Photo implements java.io.Serializable {

	private static final long serialVersionUID = 1891567810783724951L;
	private String name;
	private HashMap<String, List<String>> tags;
	private String caption;
	private LocalDateTime date;
	private SerializableImage photo;
	public static final String storeDir = "data";
	public static final String storeFile = "datas.dat";

	public Photo(String photoName, Image image, LocalDateTime createdDate,
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

	public LocalDateTime getDate() {
		return date;
	}

	public HashMap<String, List<String>> getTag() {
		if (tags == null) {
			tags = new HashMap<>();
		}
		return tags;
	}

	public void addTag(String tagName, String tagValue) {
		tags.computeIfAbsent(tagName, k -> new ArrayList<>()).add(tagValue);
		// tags.put(tagName, tagValue);
	}

	public void deleteTag(String tagName) {
		tags.remove(tagName);
	}

	public void setName(String name, LocalDateTime modifiedDate) {
		this.name = name;
		date = modifiedDate;
	}

	public void recaption(String captionName, LocalDateTime modifiedDate) {
		caption = captionName;
		date = modifiedDate;
	}

}
