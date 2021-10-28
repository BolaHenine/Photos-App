package view;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

	FXMLLoader photoLoader;

	Parent photoParent;

	private ObservableList<User> users;
	private Photo photo;
	private int userIndex;
	private int photoIndex;
	private int albumIndex;

	public void start(int userNumber, int albumNumber, int photoNumber) throws ClassNotFoundException, IOException {

		userIndex = userNumber;
		photoIndex = photoNumber;
		albumIndex = albumNumber;
		users = User.readApp();
		photoName1.setEditable(false);
		captionName.setEditable(false);
		dateCreated.setEditable(false);

		photo = users.get(userNumber).getAlbums().get(albumNumber).getPhotos().get(photoNumber);
		photoName.setText(photo.getName());
		photoView.setImage(photo.getImage());

		photoName1.setText(photo.getName());
		captionName.setText(photo.getCaption());
		dateCreated.setText(photo.getDate().getTime().toString());
	}

	private void showItemInputDialog() {

	}

	public void buttonClick(ActionEvent e) throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/loginPage.fxml"));
		Scene root = (Scene) loader.load();
		root.getRoot().setStyle("-fx-font-family: 'serif'");
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

		Button b = (Button) e.getSource();

		if (b == logout) {
			stage.setScene(root);
		}
		if (b == close) {
			stage.close();
		}

	}

}
