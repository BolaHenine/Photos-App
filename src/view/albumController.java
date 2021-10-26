package view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.User;

public class albumController {
	@FXML
	Button logout;
	@FXML
	Button close;
	@FXML
	Button editName;
	@FXML
	Button openPhoto;
	@FXML
	Button deletePhoto;
	@FXML
	Button addPhoto;
	@FXML
	Button recaption;
	@FXML
	Button caption;
	@FXML
	Button move;
	@FXML
	Label albumName;
	@FXML
	ListView<String> imageList;

	FXMLLoader photoLoader;

	Parent photoParent;

	private ObservableList<User> users;

	private ObservableList<String> imageNames;

	private ArrayList<Image> images = new ArrayList<Image>();

	private int userIndex;
	private int albumIndex;

	private File photoFile;

	private String[] imageNam;

	public void start(int userNumber, int albumNumber) throws ClassNotFoundException, IOException {

		userIndex = userNumber;

		users = User.readApp();

		albumName.setText(users.get(userNumber).getAlbums().get(albumNumber).getName());

		photoFile = new File("/Users/bolahenine/Desktop/image.jpeg");
		Image image = new Image(photoFile.toURI().toString(), 50, 50, false, false);

		String[] arr = { "tree" };

		imageNam = arr;

		imageNames = FXCollections.observableArrayList(imageNam);

		imageList.setItems(imageNames);

		images.add(image);

		imageList.setCellFactory(param -> new ListCell<String>() {
			private ImageView imageView = new ImageView();

			@Override
			public void updateItem(String name, boolean empty) {
				super.updateItem(name, empty);
				if (empty) {
					setText(null);
					setGraphic(null);
				} else {
					imageView.setImage(images.get(getIndex()));
					setText(name);
					setGraphic(imageView);
				}
			}
		});

	}

	public void buttonClick(ActionEvent e) throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/loginPage.fxml"));
		Scene root = (Scene) loader.load();
		root.getRoot().setStyle("-fx-font-family: 'serif'");
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

		photoLoader = new FXMLLoader(getClass().getResource("/view/photoView.fxml"));
		photoParent = (Parent) photoLoader.load();
		Scene photoScene = new Scene(photoParent);
		photoScene.getRoot().setStyle("-fx-font-family: 'serif'");
		photoController photoController = photoLoader.getController();

		Button b = (Button) e.getSource();
		int index = imageList.getSelectionModel().getSelectedIndex();
		if (b == logout) {
			stage.setScene(root);
		}
		if (b == close) {
			stage.close();
		}
		if (b == openPhoto) {
			
			stage.setScene(photoScene);
		}
		if (b == addPhoto) {
			photoFile = new File("/Users/bolahenine/Desktop/pic.jpeg");
			Image image2 = new Image(photoFile.toURI().toString(), 50, 50, false, false);

			images.add(image2);

			String[] tempArr = new String[imageNam.length + 1];

			for (int i = 0; i < imageNam.length; i++) {
				tempArr[i] = imageNam[i];
			}
			tempArr[tempArr.length - 1] = "cool";

			imageNames = FXCollections.observableArrayList(tempArr);

			imageList.setItems(imageNames);
		}
	}

}

// The way this will work is by sending the selected album to the start method
// and then goind throught
// it and adding the photos to the arrayList of photos and the names to the
// arrayList of names
