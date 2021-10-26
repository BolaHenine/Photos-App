package model;

import java.util.ArrayList;

public class Album implements java.io.Serializable {

	private static final long serialVersionUID = 1891567810783724951L;
	private String name;
	private ArrayList<Photo> photos;

	public Album(String name) {
		this.name = name;
		photos = new ArrayList<Photo>();
	}

	public String getName() {
		return this.name;
	}

	public String getNamess() {
		return this.name;
	}

	public void setName(String albumName) {
		name = albumName;
	}

}
