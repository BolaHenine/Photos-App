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

public class User implements java.io.Serializable {
	private String userName;
	private ArrayList<Album> albums;
	private static final long serialVersionUID = 7676731142065664791L;

	public static final String storeDir = "data";
	public static final String storeFile = "data.dat";

	public String getName() {
		return userName;
	}
	public User(String username) {
		userName = username;
		albums = new ArrayList<Album>();
	}

	public static void writeApp(ObservableList<User> users) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(new ArrayList<User>(users));
		oos.close();
	}

	public static ObservableList<User> readApp()
			throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(storeDir + File.separator + storeFile));
		List<User> list = (List<User>) ois.readObject();
		return FXCollections.observableList(list);
	}
}
