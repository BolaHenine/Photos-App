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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class adminController {
	@FXML
	Button add;
	@FXML
	Button delete;
	@FXML
	Button logout;
	@FXML
	Button close;
	@FXML
	TextField username;
	@FXML
	ListView<User> usersList;

	FXMLLoader loader;

	Parent parent;

	private ObservableList<User> users;

	public void start() throws ClassNotFoundException, IOException {

		users = User.readApp();

		usersList.setItems(users);
		usersList.setCellFactory(lv -> new ListCell<User>() {
			@Override
			public void updateItem(User item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setText(null);
				} else {
					String name = item.getName();
					setText(name);
				}
			}
		});

	}

	public void buttonClick(ActionEvent e) throws IOException, ClassNotFoundException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/loginPage.fxml"));
		Scene root = (Scene) loader.load();
		root.getRoot().setStyle("-fx-font-family: 'serif'");
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

		Button b = (Button) e.getSource();

		int index = usersList.getSelectionModel().getSelectedIndex();

		if (b == logout) {
			stage.setScene(root);
			stage.show();
		}
		if (b == close) {

			stage.close();

		}
		if (b == add) {
			User newUser = new User(username.getText());

			users.add(newUser);
			username.setText("");

			User.writeApp(users);

		}
		if (b == delete) {
			if (index == -1) {
				System.out.println("nothing is selected");
			} else {
				users.remove(index);
				User.writeApp(users);
			}
		}
	}

}
