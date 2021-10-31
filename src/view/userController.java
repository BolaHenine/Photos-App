package view;

import java.io.IOException;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Album;
import model.User;

public class userController {
	@FXML
	Button addAlbum;
	@FXML
	Button editAlbum;
	@FXML
	Button openAlbum;
	@FXML
	Button close;
	@FXML
	Button deleteAlbum;
	@FXML
	Button logout;
	@FXML
	Button search;
	@FXML
	TextField albumName;
	@FXML
	Label userNameLabel;
	@FXML
	ListView<Album> albumList;

	FXMLLoader albumLoader;

	Parent albumParent;

	FXMLLoader searchLoader;

	Parent searchParent;

	private ObservableList<Album> albums;

	private int selectedUserIndex;

	private ObservableList<User> usersList;

	public void start(int loggedUserIndex, String userName)
			throws ClassNotFoundException, IOException {

		selectedUserIndex = loggedUserIndex;

		usersList = User.readApp();

		albums = FXCollections
				.observableList(usersList.get(loggedUserIndex).getAlbums());

		albumList.setItems(albums);

		albumList.setCellFactory(lv -> new ListCell<Album>() {
			@Override
			public void updateItem(Album item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setText(null);
				} else {
					String name = item.getName();
					setText(name);
				}
			}
		});

		albumList.getSelectionModel().selectedIndexProperty()
				.addListener((obs) -> showItemInputDialog());

		userNameLabel.setText(userName);
	}

	private void showItemInputDialog() {
		Album selectedAlbum = albumList.getSelectionModel().getSelectedItem();
		int index = albumList.getSelectionModel().getSelectedIndex();
		if (albums.size() == 0) {
			albumName.setText("");
		} else if (selectedAlbum == null) {

		} else {
			albumName.setText(selectedAlbum.getName());
		}

	}

	public void buttonClick(ActionEvent e)
			throws IOException, ClassNotFoundException {

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/view/loginPage.fxml"));
		Scene root = (Scene) loader.load();
		root.getRoot().setStyle("-fx-font-family: 'serif'");
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

		albumLoader = new FXMLLoader(
				getClass().getResource("/view/albumView.fxml"));
		albumParent = (Parent) albumLoader.load();
		Scene albumScene = new Scene(albumParent);
		albumScene.getRoot().setStyle("-fx-font-family: 'serif'");
		albumController albumController = albumLoader.getController();

		searchLoader = new FXMLLoader(
				getClass().getResource("/view/searchView.fxml"));
		searchParent = (Parent) searchLoader.load();
		searchController searchController = searchLoader.getController();
		Scene searchScene = new Scene(searchParent);

		Button b = (Button) e.getSource();

		int index = albumList.getSelectionModel().getSelectedIndex();

		if (b == close) {
			stage.close();
		}

		if (b == addAlbum) {

			Album newAlbum = new Album(albumName.getText());

			// selectedUser.addAlbum(albumName.getText());

			usersList.get(selectedUserIndex).addAlbum(newAlbum);
			albumName.setText("");
			User.writeApp(usersList);

			albums = FXCollections.observableList(
					usersList.get(selectedUserIndex).getAlbums());
			albumList.setItems(albums);

		}

		if (b == logout) {
			stage.setScene(root);
		}
		if (b == openAlbum) {
			albumController.start(selectedUserIndex, index);
			stage.setScene(albumScene);
		}
		if (b == deleteAlbum) {
			usersList.get(selectedUserIndex).getAlbums().remove(index);
			albumList.refresh();
			User.writeApp(usersList);
			albumName.setText("");
		}
		if (b == editAlbum) {

			usersList.get(selectedUserIndex).getAlbums().get(index)
					.setName(albumName.getText());

			albums = FXCollections.observableList(
					usersList.get(selectedUserIndex).getAlbums());
			albumList.setItems(albums);

			albumName.setText("");

			User.writeApp(usersList);

		}
		if (b == search) {
			searchController.start(selectedUserIndex);
			stage.setScene(searchScene);
		}

	}

}