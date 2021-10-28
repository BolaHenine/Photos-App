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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
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
	// @FXML
	// ListView<String> imageList;

	@FXML
	ListView<Photo> imageList;

	FXMLLoader photoLoader;

	Parent photoParent;

	private ObservableList<User> users;

	private ArrayList<Photo> images = new ArrayList<Photo>();

	private int userIndex;
	private int albumIndex;

	private File photoFile;

	private Album selectedAlbum;

	public void start(int userNumber, int albumNumber)
			throws ClassNotFoundException, IOException {

		userIndex = userNumber;

		users = User.readApp();

		selectedAlbum = users.get(userNumber).getAlbums().get(albumNumber);

		albumName.setText(
				users.get(userNumber).getAlbums().get(albumNumber).getName());

		photoFile = new File(
				"/Users/bolahenine/Desktop/cs213/photos17/src/view/image.jpeg");
		Image image = new Image(photoFile.toURI().toString(), 50, 50, false,
				false);

		photoFile = new File(
				"/Users/bolahenine/Desktop/cs213/photos17/src/view/pic.jpeg");
		Image image2 = new Image(photoFile.toURI().toString(), 50, 50, false,
				false);

		Photo test = new Photo("image 1", image);
		Photo test2 = new Photo("image 2", image2);

		selectedAlbum.addPhoto(test);
		selectedAlbum.addPhoto(test2);

		images = selectedAlbum.getPhotos();

		System.out.println(images.size());

		imageList.setItems(
				FXCollections.observableArrayList(selectedAlbum.getPhotos()));

		imageList.setCellFactory(
				new Callback<ListView<Photo>, ListCell<Photo>>() {
					@Override
					public ListCell<Photo> call(ListView<Photo> arg0) {
						ListCell<Photo> cell = new ListCell<Photo>() {
							@Override
							protected void updateItem(Photo photo,
									boolean bt1) {
								super.updateItem(photo, bt1);
								if (photo != null) {
									Image img = images.get(getIndex())
											.getImage();
									ImageView imgview = new ImageView(img);
									setGraphic(imgview);
									setText(images.get(getIndex()).getName());
								} else {
									setGraphic(null);
									setText("");
								}
							}
						};
						return cell;
					};

				});

		// imageList.setItems(FXCollections.observableArrayList(images));

	}

	public void buttonClick(ActionEvent e)
			throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/view/loginPage.fxml"));
		Scene root = (Scene) loader.load();
		root.getRoot().setStyle("-fx-font-family: 'serif'");
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

		photoLoader = new FXMLLoader(
				getClass().getResource("/view/photoView.fxml"));
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
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Choose Image");
			chooser.getExtensionFilters().addAll(
					new ExtensionFilter("Image Files", "*.bmp", "*.BMP",
							"*.gif", "*.GIF", "*.jpg", "*.JPG", "*.png",
							"*.PNG"),
					new ExtensionFilter("Bitmap Files", "*.bmp", "*.BMP"),
					new ExtensionFilter("GIF Files", "*.gif", "*.GIF"),
					new ExtensionFilter("JPEG Files", "*.jpg", "*.JPG"),
					new ExtensionFilter("PNG Files", "*.png", "*.PNG"));
			File selectedFile = chooser.showOpenDialog(stage);

			// photoFile = new File(
			// "/Users/bolahenine/Desktop/cs213/photos17/src/view/167408.jpeg");
			Image image = new Image(selectedFile.toURI().toString(), 50, 50,
					false, false);
			Photo newPhoto = new Photo(selectedFile.getName(), image);
			//
			selectedAlbum.addPhoto(newPhoto);

			imageList.setItems(FXCollections
					.observableArrayList(selectedAlbum.getPhotos()));

		}

		if (b == deletePhoto) {
			imageList.getItems().remove(index);
		}

	}

}

// The way this will work is by sending the selected album to the start method
// and then goind throught
// it and adding the photos to the arrayList of photos and the names to the
// arrayList of names
