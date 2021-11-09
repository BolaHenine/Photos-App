/**
 * @author Bola Henine
 *
 * @author Roshan Seth
 */
package view;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Photo;
import model.User;

/**
 *
 * photoController is the class that controls the Photo View. It creates the
 * functionality for the buttons and Text Fields for the Photo View to function
 * properly.
 */

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
	Button back;
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

	FXMLLoader albumLoader;
	FXMLLoader loader;
	Parent photoParent;
	Parent albumParent;

	private ObservableList<User> users;
	private Photo photo;
	private int userIndex;
	private int photoIndex;
	private int albumIndex;

	private HashMap<String, List<String>> tags;

	private DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH:mm:ss");

	/**
	 *
	 * @param userNumber
	 *            the index of the selected user
	 * @param albumNumber
	 *            the index of the selected album
	 * @param photoNumber
	 *            the index of the selected photo
	 * @throws ClassNotFoundException
	 *             throws an Exception if class is not found
	 * @throws IOException
	 *             throws an Exception if deseriliazation can't be performed
	 */

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
		photoView.setFitHeight(199);
		photoView.setFitWidth(357);
		photoView.setImage(photo.getImage());
		photoView.setPreserveRatio(false);
		photoView.setSmooth(false);

		photoName1.setText(photo.getName());
		captionName.setText(photo.getCaption());
		dateCreated.setText(formatter.format(photo.getDate()));

		tags = photo.getTag();
		if (tags != null) {
			ObservableMap<String, List<String>> observableExtensionToMimeMap = FXCollections
					.observableMap(tags);

			tagList.getItems().setAll(observableExtensionToMimeMap.keySet());

			tagList.setCellFactory(lv -> new ListCell<String>() {

				/**
				 * Method that fetches the tag key,value pair in the hash map
				 * and adds the value to a list of strings and the key to a
				 * String called key. How the key,value pair will be displayed
				 * in the List View, tagList, as a String is also created.
				 * Method will also update the key,value pair if it is changed
				 * in any way.
				 * <p>
				 *
				 * @param item
				 *            String value that denotes the string of a
				 *            tag,value pair in tagList
				 * @param empty
				 *            Boolean value that returns <code>true</code> if
				 *            tagList is empty or <code>false</code> if tagList
				 *            is not empty
				 */

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

	/**
	 *
	 * @param e
	 *            Event Listener that indicates that a component-defined action
	 *            occurred.
	 * @throws IOException
	 *             throws an Exception if deseriliazation can't be performed
	 * @throws ClassNotFoundException
	 *             throws an Exception if class is not found
	 */
	public void buttonClick(ActionEvent e)
			throws IOException, ClassNotFoundException {
		loader = new FXMLLoader(getClass().getResource("/view/loginPage.fxml"));
		Scene root = (Scene) loader.load();
		root.getRoot().setStyle("-fx-font-family: 'serif'");

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		String item = tagList.getSelectionModel().getSelectedItem();
		Button b = (Button) e.getSource();
		int lastPhotoIndex = users.get(userIndex).getAlbums().get(albumIndex)
				.getPhotos().toArray().length;

		albumLoader = new FXMLLoader(
				getClass().getResource("/view/albumView.fxml"));
		albumParent = (Parent) albumLoader.load();
		Scene albumScene = new Scene(albumParent);
		albumScene.getRoot().setStyle("-fx-font-family: 'serif'");
		albumController albumcontroller = albumLoader.getController();

		if (b == logout) {
			stage.setScene(root);
		}
		if (b == back) {
			albumcontroller.start(userIndex, albumIndex);
			stage.setScene(albumScene);
		}
		if (b == left) {
			if (photoIndex > 0) {
				photoIndex = photoIndex - 1;
			}
			photo = users.get(userIndex).getAlbums().get(albumIndex).getPhotos()
					.get(photoIndex);
			photoName.setText(photo.getName());
			photoView.setImage(photo.getImage());
			photoName1.setText(photo.getName());
			captionName.setText(photo.getCaption());
			dateCreated.setText(formatter.format(photo.getDate()));
			tags = photo.getTag();
			ObservableMap<String, List<String>> observableExtensionToMimeMap = FXCollections
					.observableMap(tags);
			tagList.getItems().setAll(observableExtensionToMimeMap.keySet());

		}
		if (b == right) {
			if (photoIndex < lastPhotoIndex - 1) {
				photoIndex = photoIndex + 1;
			}
			photo = users.get(userIndex).getAlbums().get(albumIndex).getPhotos()
					.get(photoIndex);
			photoName.setText(photo.getName());
			photoView.setImage(photo.getImage());
			photoName1.setText(photo.getName());
			captionName.setText(photo.getCaption());
			dateCreated.setText(formatter.format(photo.getDate()));
			tags = photo.getTag();
			ObservableMap<String, List<String>> observableExtensionToMimeMap = FXCollections
					.observableMap(tags);
			tagList.getItems().setAll(observableExtensionToMimeMap.keySet());

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
			if (tagName.getText().isBlank() && tagValue.getText().isBlank()) {
				Dialog<ButtonType> dialog = new Dialog<>();
				dialog.getDialogPane().setStyle("-fx-font-family: 'serif'");
				dialog.setTitle("Error");
				dialog.setHeaderText(
						"Tag Name and Tag Value are empty. Please enter a valid Tag Name and Tag Value.");
				DialogPane dialogPane = dialog.getDialogPane();
				dialogPane.getButtonTypes().addAll(ButtonType.OK);
				dialog.show();
				tagName.clear();
				tagValue.clear();
			} else {
				photo.addTag(tagName.getText(), tagValue.getText());
				ObservableMap<String, List<String>> observableExtensionToMimeMap = FXCollections
						.observableMap(tags);
				tagList.getItems()
						.setAll(observableExtensionToMimeMap.keySet());
				User.writeApp(users);
				tagName.clear();
				tagValue.clear();
			}
		}
	}

}
