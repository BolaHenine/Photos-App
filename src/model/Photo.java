package model;

import java.util.Calendar;

import javafx.scene.image.Image;

public class Photo implements java.io.Serializable {

	private static final long serialVersionUID = 1891567810783724951L;
	private String name;
	private String caption;
	private Calendar date;
	private Image photo;

	public Photo(String photoName, Image image, Calendar createdDate) {
		name = photoName;
		photo = image;
		date = createdDate;
	}

	public String getName() {
		return name;
	}

	public String getCaption() {
		return caption;
	}

	public Image getImage() {
		return photo;
	}

	public Calendar getDate() {
		return date;
	}

}
