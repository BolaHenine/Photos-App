package view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
	@FXML
	TextField photoName;
	@FXML
	TextField captionName;

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

	private Calendar calendar = Calendar.getInstance();

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

		Photo selectedPhoto = imageList.getSelectionModel().getSelectedItem();
		int index = imageList.getSelectionModel().getSelectedIndex();

		if (b == logout) {
			stage.setScene(root);
		}
		if (b == close) {
			stage.close();
		}
		if (b == openPhoto) {
			photoController.start(userIndex, albumIndex, index);
			stage.setScene(photoScene);
		}
		if (b == addPhoto) {
			calendar = Calendar.getInstance();
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
				Image image = new Image(selectedFile.toURI().toString(), 50, 50,
						false, false);
				Photo newPhoto = new Photo(selectedFile.getName(), image,
						calendar, captionName.getText());
				selectedAlbum.addPhoto(newPhoto);
				User.writeApp(users);
				imageList.setItems(FXCollections
						.observableArrayList(selectedAlbum.getPhotos()));
			}

		}

		if (b == deletePhoto) {
			imageList.getItems().remove(index);
			images.remove(index);
			User.writeApp(users);
		}

		if (b == editName) {
			selectedAlbum.getPhotos().get(index).setName(photoName.getText(),
					calendar);
			User.writeApp(users);
			imageList.setItems(FXCollections
					.observableArrayList(selectedAlbum.getPhotos()));
		}

		if (b == recaption) {
			selectedAlbum.getPhotos().get(index)
					.recaption(captionName.getText(), calendar);
			User.writeApp(users);
		}

		if (b == move) {
			Album albumMoveto = null;

			String week_days[] = {"Monday", "Tuesday", "Wednesday", "Thursday",
					"Friday"};

			ArrayList<String> albums = new ArrayList<String>();

			for (int i = 0; i < allAlbums.size(); i++) {
				if (allAlbums.get(i) != allAlbums.get(albumIndex)) {
					albums.add(allAlbums.get(i).getName());
				}
			}

			ComboBox combo_box = new ComboBox(
					FXCollections.observableArrayList(albums));

			combo_box.setPromptText("Please select the Album to move it to");

			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setTitle("Confirmation required");
			dialog.setHeaderText("Are you sure you want to delete the song");
			DialogPane dialogPane = dialog.getDialogPane();
			dialogPane.getButtonTypes().addAll(ButtonType.OK,
					ButtonType.CANCEL);

			dialog.getDialogPane().setContent(combo_box);

			Optional<ButtonType> result = dialog.showAndWait();
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