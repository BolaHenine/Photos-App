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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class loginPageController {
	@FXML
	Button login;
	@FXML
	Button close;
	@FXML
	TextField username;

	FXMLLoader loader;
	Parent parent;

	FXMLLoader userLoader;
	Parent userParent;

	private ObservableList<User> users;

	private int loggedUserIndex;

	public void buttonClick(ActionEvent e)
			throws IOException, ClassNotFoundException {

		boolean userFound = false;

		users = User.readApp();

		loader = new FXMLLoader(getClass().getResource("/view/adminView.fxml"));
		parent = (Parent) loader.load();
		adminController adminController = loader.getController();
		adminController.start();
		Scene scene = new Scene(parent);
		scene.getRoot().setStyle("-fx-font-family: 'serif'");
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

		Button b = (Button) e.getSource();

		userLoader = new FXMLLoader(
				getClass().getResource("/view/userView.fxml"));
		userParent = (Parent) userLoader.load();
		userController userController = userLoader.getController();
		Scene userScene = new Scene(userParent);
		userScene.getRoot().setStyle("-fx-font-family: 'serif'");
		for (int i = 0; i < users.size(); i++) {
			if (username.getText().equals(users.get(i).getName())) {
				userFound = true;
				loggedUserIndex = i;
			}
		}

		if (b == login) {
			if (username.getText().equals("admin")) {
				stage.setScene(scene);
				stage.show();
			} else if (userFound) {
				userController.start(loggedUserIndex,
						users.get(loggedUserIndex).getName());
				stage.setScene(userScene);
			} else {
				System.out.println("User not Found");
			}
		}
		if (b == close) {
			stage.close();
		}
	}

}
