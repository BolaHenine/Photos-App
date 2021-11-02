package view;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
	Button back;
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
	Button copy;
	@FXML
	Label albumName;
	@FXML
	TextField photoName;
	@FXML
	TextField captionName;

	@FXML
	ListView<Photo> imageList;

	FXMLLoader photoLoader;

	Parent photoParent;

	FXMLLoader loader;
	FXMLLoader userLoader;

	Parent userParent;
	private ObservableList<User> users;

	private ArrayList<Photo> images = new ArrayList<Photo>();

	private int userIndex;
	private int albumIndex;

	private Album selectedAlbum;

	private LocalDateTime calendar = LocalDateTime.now();

	private ArrayList<Album> allAlbums = new ArrayList<Album>();

	public void start(int userNumber, int albumNumber)
			throws ClassNotFoundException, IOException {

		userIndex = userNumber;

		albumIndex = albumNumber;

		users = User.readApp();

		allAlbums = users.get(userIndex).getAlbums();

		selectedAlbum = users.get(userNumber).getAlbums().get(albumNumber);

		albumName.setText(
				users.get(userNumber).getAlbums().get(albumNumber).getName());

		images = selectedAlbum.getPhotos();

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
									imgview.setFitWidth(50);
									imgview.setFitHeight(50);
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

		imageList.getSelectionModel().selectedIndexProperty()
				.addListener((obs) -> showItemInputDialog());

	}

	public void listClick(MouseEvent click)
			throws ClassNotFoundException, IOException {
		int index = imageList.getSelectionModel().getSelectedIndex();
		if (click.getClickCount() == 2) {
			if (index != -1) {
				openPhoto.fire();
			}
		}
	}

	private void showItemInputDialog() {
		Photo selectedPhoto = imageList.getSelectionModel().getSelectedItem();
		int index = imageList.getSelectionModel().getSelectedIndex();

		if (index != -1) {
			photoName.setText(selectedPhoto.getName());
			captionName.setText(selectedPhoto.getCaption());
		}

	}

	public void buttonClick(ActionEvent e)
			throws IOException, ClassNotFoundException {
		loader = new FXMLLoader(getClass().getResource("/view/loginPage.fxml"));
		Scene root = (Scene) loader.load();
		root.getRoot().setStyle("-fx-font-family: 'serif'");
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

		photoLoader = new FXMLLoader(
				getClass().getResource("/view/photoView.fxml"));
		photoParent = (Parent) photoLoader.load();
		Scene photoScene = new Scene(photoParent);
		photoScene.getRoot().setStyle("-fx-font-family: 'serif'");
		photoController photoController = photoLoader.getController();

		userLoader = new FXMLLoader(
				getClass().getResource("/view/userView.fxml"));
		userParent = (Parent) userLoader.load();
		Scene userScene = new Scene(userParent);
		userScene.getRoot().setStyle("-fx-font-family: 'serif'");
		userController usercontroller = userLoader.getController();
		String username = users.get(userIndex).getName();

		Button b = (Button) e.getSource();

		Photo selectedPhoto = imageList.getSelectionModel().getSelectedItem();
		int index = imageList.getSelectionModel().getSelectedIndex();

		Dialog<ButtonType> dialog = new Dialog<>();
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK);

		if (b == logout) {
			stage.setScene(root);
		}
		if (b == back) {
			usercontroller.start(userIndex, username);
			stage.setScene(userScene);
		}
		if (b == close) {
			stage.close();
		}
		if (b == back) {
			usercontroller.start(userIndex, username);
			stage.setScene(userScene);
		}
		if (b == openPhoto) {
			if (index == -1) {
				dialog.setTitle("Nothing is Selected");
				dialog.setHeaderText("Please Select Something to open");
				dialog.show();
			} else {
				photoController.start(userIndex, albumIndex, index);
				stage.setScene(photoScene);
			}

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
			if (selectedFile != null) {
				Image image = new Image(selectedFile.toURI().toString());
				Photo newPhoto = new Photo(selectedFile.getName(), image,
						calendar, captionName.getText());
				selectedAlbum.addPhoto(newPhoto);
				User.writeApp(users);
				imageList.setItems(FXCollections
						.observableArrayList(selectedAlbum.getPhotos()));
			}

		}

		if (b == deletePhoto) {
			if (index == -1) {
				dialog.setTitle("Nothing is Selected");
				dialog.setHeaderText("Please Select Something to delete");
				dialog.show();
			} else {
				imageList.getItems().remove(index);
				images.remove(index);
				User.writeApp(users);
			}

		}

		if (b == editName) {
			if (index == -1) {
				dialog.setTitle("Nothing is Selected");
				dialog.setHeaderText("Please Select Something to edit");
				dialog.show();
			} else if (photoName.getText().trim().equals("")) {
				dialog.setTitle("Empty Photo Name");
				dialog.setHeaderText("Please enter a valid Photo Name");
				dialog.show();
			} else {
				selectedAlbum.getPhotos().get(index)
						.setName(photoName.getText(), calendar);
				User.writeApp(users);
				imageList.setItems(FXCollections
						.observableArrayList(selectedAlbum.getPhotos()));
			}

		}

		if (b == recaption) {
			if (captionName.getText().trim().equals("")) {
				dialog.setTitle("Empty Caption Name");
				dialog.setHeaderText("Please enter a valid Caption Name");
				dialog.show();
			} else {
				selectedAlbum.getPhotos().get(index)
						.recaption(captionName.getText(), calendar);
				User.writeApp(users);
			}
		}
		if (b == move) {
			Album albumMoveto = null;

			ArrayList<String> albums = new ArrayList<String>();

			for (int i = 0; i < allAlbums.size(); i++) {
				if (allAlbums.get(i) != allAlbums.get(albumIndex)) {
					albums.add(allAlbums.get(i).getName());
				}
			}

			ComboBox combo_box = new ComboBox(
					FXCollections.observableArrayList(albums));

			combo_box.setPromptText("Please Select the Album to Move It To");

			Dialog<ButtonType> moveDialog = new Dialog<>();
			moveDialog.getDialogPane().setStyle("-fx-font-family: 'serif'");
			moveDialog.setTitle("Confirmation required");
			moveDialog
					.setHeaderText("Are you sure you want to delete the song");
			DialogPane moveDialogPane = moveDialog.getDialogPane();
			moveDialogPane.getButtonTypes().addAll(ButtonType.OK,
					ButtonType.CANCEL);

			moveDialog.getDialogPane().setContent(combo_box);

			Optional<ButtonType> result = moveDialog.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {

				for (int i = 0; i < allAlbums.size(); i++) {
					if (allAlbums.get(i).getName()
							.equals(combo_box.getValue())) {
						albumMoveto = allAlbums.get(i);
					}
				}
				albumMoveto.addPhoto(selectedPhoto);
				selectedAlbum.removePhoto(selectedPhoto);

			}

			User.writeApp(users);
			imageList.setItems(FXCollections
					.observableArrayList(selectedAlbum.getPhotos()));
		}

	}

}

// The way this will work is by sending the selected album to the start method
// and then goind throught
// it and adding the photos to the arrayList of photos and the names to the
// arrayList of names