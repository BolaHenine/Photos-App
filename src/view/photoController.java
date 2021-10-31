package view;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Photo;
import model.User;

public class photoController {
	@FXML
	Button logout;
	@FXML
	Button close;
	@FXML
	Button left;
	@FXML
	Button right;
	@FXML
	Button addTag;
	@FXML
	Button deleteTag;
	@FXML
	Label photoName;
	@FXML
	ListView<String> tagList;
	@FXML
	ImageView photoView;
	@FXML
	TextField photoName1;
	@FXML
	TextField captionName;
	@FXML
	TextField dateCreated;
	@FXML
	TextField tagName;
	@FXML
	TextField tagValue;

	FXMLLoader photoLoader;

	Parent photoParent;

	private ObservableList<User> users;
	private Photo photo;
	private int userIndex;
	private int photoIndex;
	private int albumIndex;

	private HashMap<String, List<String>> tags;

	public void start(int userNumber, int albumNumber, int photoNumber)
			throws ClassNotFoundException, IOException {

		userIndex = userNumber;
		photoIndex = photoNumber;
		albumIndex = albumNumber;
		users = User.readApp();
		photoName1.setEditable(false);
		captionName.setEditable(false);
		dateCreated.setEditable(false);

		photo = users.get(userIndex).getAlbums().get(albumIndex).getPhotos()
				.get(photoIndex);
		photoName.setText(photo.getName());
		photoView.setImage(photo.getImage());
		photoName1.setText(photo.getName());
		captionName.setText(photo.getCaption());
		dateCreated.setText(photo.getDate().getTime().toString());

		tags = photo.getTag();

		if (tags != null) {
			ObservableMap<String, List<String>> observableExtensionToMimeMap = FXCollections
					.observableMap(tags);

			tagList.getItems().setAll(observableExtensionToMimeMap.keySet());

			tagList.setCellFactory(lv -> new ListCell<String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						List<String> valueForFirstKey;
						String key = (String) tags.keySet()
								.toArray()[getIndex()];
						valueForFirstKey = tags.get(key);
						String name = "\"" + key + "\"" + " = " + "\""
								+ valueForFirstKey + "\"" + " ";
						setText(name);
					}
				}
			});
		}

	}

	public void buttonClick(ActionEvent e)
			throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/view/loginPage.fxml"));
		Scene root = (Scene) loader.load();
		root.getRoot().setStyle("-fx-font-family: 'serif'");

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		int index = tagList.getSelectionModel().getSelectedIndex();
		String item = tagList.getSelectionModel().getSelectedItem();
		Button b = (Button) e.getSource();
		int lastPhotoIndex = users.get(userIndex).getAlbums().get(albumIndex).getPhotos().toArray().length;
		if (b == logout) {
			stage.setScene(root);
		}
		if (b == left) {
			if (photoIndex > 0) {
				photoIndex = photoIndex - 1;
			}
			photo = users.get(userIndex).getAlbums().get(albumIndex).getPhotos().get(photoIndex);
			photoName.setText(photo.getName());
			photoView.setImage(photo.getImage());
			photoName1.setText(photo.getName());
			captionName.setText(photo.getCaption());
			dateCreated.setText(photo.getDate().getTime().toString());

		}
		if (b == right) {
			if (photoIndex < lastPhotoIndex - 1) {
				photoIndex = photoIndex + 1;
			}
			photo = users.get(userIndex).getAlbums().get(albumIndex).getPhotos().get(photoIndex);
			photoName.setText(photo.getName());
			photoView.setImage(photo.getImage());
			photoName1.setText(photo.getName());
			captionName.setText(photo.getCaption());
			dateCreated.setText(photo.getDate().getTime().toString());

		}

		if (b == deleteTag) {

			photo.deleteTag(item);
			tags.remove(item);
			ObservableMap<String, List<String>> observableExtensionToMimeMap = FXCollections
					.observableMap(tags);
			tagList.getItems().setAll(observableExtensionToMimeMap.keySet());
			User.writeApp(users);

		}
		if (b == close) {
			stage.close();
		}
		if (b == addTag) {
			photo.addTag(tagName.getText(), tagValue.getText());
			ObservableMap<String, List<String>> observableExtensionToMimeMap = FXCollections
					.observableMap(tags);
			tagList.getItems().setAll(observableExtensionToMimeMap.keySet());

			User.writeApp(users);

		}
	}

}
