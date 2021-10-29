package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javafx.scene.image.Image;

public class Photo implements java.io.Serializable {

	private static final long serialVersionUID = 1891567810783724951L;
	private String name;
	private ArrayList<HashMap<String, String>> tag;
	private String tagvalue;
	private String caption;
	private Calendar date;
	private SerializableImage photo;
	public static final String storeDir = "data";
	public static final String storeFile = "datas.dat";

	public Photo(String photoName, Image image, Calendar createdDate) {
		name = photoName;
		photo = new SerializableImage(image);
		date = createdDate;
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

	public ArrayList<HashMap<String, String>> getTag() {
		return tag;
	}

	public void setTag(ArrayList<HashMap<String, String>> Tag) {
		tag = Tag;
	}

}
