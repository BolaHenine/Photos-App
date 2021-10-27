package view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
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

	FXMLLoader photoLoader;

	Parent photoParent;

	private ObservableList<User> users;

	private ObservableList<String> photoNames;

	private ArrayList<Image> images = new ArrayList<Image>();

	private int userIndex;
	private int photoIndex;

	private File photoFile;

	private String[] photoNam;

	public void start(int userNumber, int photoNumber) throws ClassNotFoundException, IOException {

		userIndex = userNumber;

		users = User.readApp();

		photoFile = new File("/Users/roshanseth/Desktop/wallpaper.jpeg");
		Image image = new Image(photoFile.toURI().toString(), 50, 50, true, false);

//		String[] arr = { "tree" };
//
//		photoNam = arr;
//
//		photoNames = FXCollections.observableArrayList(photoNam);

		photoView.setImage(image);

//		images.add(image);

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
