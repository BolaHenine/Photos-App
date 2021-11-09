/**
 * @author Bola Henine
 *
 * @author Roshan Seth
 */
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * the user class
 *
 */

public class User implements java.io.Serializable {
	private String userName;
	private ArrayList<Album> albums;
	private static final long serialVersionUID = 7676731142065664791L;

	public static final String storeDir = "data";
	public static final String storeFile = "data.dat";

	public static ObjectInputStream ois;

	public static ObjectOutputStream oos;

	/**
	 *
	 * @return the username
	 */

	public String getName() {
		return userName;
	}

	/**
	 *
	 * @param username
	 *            the username that was entered by the user. The constructor
	 *            that sets the username
	 */

	public User(String username) {
		userName = username;
		albums = new ArrayList<Album>();
	}

	/**
	 *
	 * @param newAlbum
	 *            the name of the new album that will be added
	 */

	public void addAlbum(Album newAlbum) {
		albums.add(newAlbum);
	}

	/**
	 *
	 * @return list of the albums
	 */

	public ArrayList<Album> getAlbums() {
		return albums;
	}

	/**
	 * Serializes the data
	 *
	 * @param users
	 *            the list of users
	 * @throws IOException
	 *             throws exception if the serialization fails.
	 */

	public static void writeApp(ObservableList<User> users) throws IOException {
		oos = new ObjectOutputStream(
				new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(new ArrayList<User>(users));
		oos.close();
	}

	/**
	 *
	 * @return the deserialization of the data
	 * @throws IOException
	 *             throws exception if the deserialization fails.
	 * @throws ClassNotFoundException
	 *             throws exception if the class or file name was not found.
	 */

	public static ObservableList<User> readApp()
			throws IOException, ClassNotFoundException {
		ois = new ObjectInputStream(
				new FileInputStream(storeDir + File.separator + storeFile));
		List<User> list = (List<User>) ois.readObject();
		return FXCollections.observableList(list);
	}

}
