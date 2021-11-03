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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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

	public void start(int loggedUserIndex, String userName) throws ClassNotFoundException, IOException {

		selectedUserIndex = loggedUserIndex;

		usersList = User.readApp();

		albums = FXCollections.observableList(usersList.get(loggedUserIndex).getAlbums());

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

		albumList.getSelectionModel().selectedIndexProperty().addListener((obs) -> showItemInputDialog());

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

	public void listClick(MouseEvent click) throws ClassNotFoundException, IOException {
		int index = albumList.getSelectionModel().getSelectedIndex();
		if (click.getClickCount() == 2) {
			if (index != -1) {
				openAlbum.fire();
			}
		}
	}

	public void buttonClick(ActionEvent e) throws IOException, ClassNotFoundException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/loginPage.fxml"));
		Scene root = (Scene) loader.load();
		root.getRoot().setStyle("-fx-font-family: 'serif'");
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

		albumLoader = new FXMLLoader(getClass().getResource("/view/albumView.fxml"));
		albumParent = (Parent) albumLoader.load();
		Scene albumScene = new Scene(albumParent);
		albumScene.getRoot().setStyle("-fx-font-family: 'serif'");
		albumController albumController = albumLoader.getController();

		searchLoader = new FXMLLoader(getClass().getResource("/view/searchView.fxml"));
		searchParent = (Parent) searchLoader.load();
		searchController searchController = searchLoader.getController();
		Scene searchScene = new Scene(searchParent);
		searchScene.getRoot().setStyle("-fx-font-family: 'serif'");
		Button b = (Button) e.getSource();

		Dialog<ButtonType> errorDialog = new Dialog<>();
		errorDialog.getDialogPane().setStyle("-fx-font-family: 'serif'");
		DialogPane errorDialogPane = errorDialog.getDialogPane();
		errorDialogPane.getButtonTypes().addAll(ButtonType.OK);

		boolean alreadyExist = false;

		int index = albumList.getSelectionModel().getSelectedIndex();

		if (b == close) {
			stage.close();
		}

		if (b == addAlbum) {
			if (albumName.getText().trim().equals("")) {
				errorDialog.setTitle("Empty Album Name");
				errorDialog.setHeaderText("Please enter a valid Album Name");
				errorDialog.show();
			} else {

				for (int i = 0; i < usersList.get(selectedUserIndex).getAlbums().size(); i++) {
					if (usersList.get(selectedUserIndex).getAlbums().get(i).getName()
							.equals(albumName.getText().trim())) {
						alreadyExist = true;
					}
				}

				if (alreadyExist) {
					errorDialog.setTitle("Album Name already exists");
					errorDialog.setHeaderText("Please enter a different name");
					errorDialog.show();
				} else {
					Album newAlbum = new Album(albumName.getText());
					usersList.get(selectedUserIndex).addAlbum(newAlbum);
					albumName.setText("");
					User.writeApp(usersList);
					albums = FXCollections.observableList(usersList.get(selectedUserIndex).getAlbums());
					albumList.setItems(albums);
				}

			}

		}

		if (b == logout) {
			stage.setScene(root);
		}
		if (b == openAlbum) {
			if (index == -1) {
				errorDialog.setTitle("Nothing is Selected");
				errorDialog.setHeaderText("Please Select Something to open");
				errorDialog.show();
			} else {
				albumController.start(selectedUserIndex, index);
				stage.setScene(albumScene);
			}
		}
		if (b == deleteAlbum) {
			if (index == -1) {
				errorDialog.setTitle("Nothing is Selected");
				errorDialog.setHeaderText("Please Select Something to delete");
				errorDialog.show();
			} else {
				usersList.get(selectedUserIndex).getAlbums().remove(index);
				albumList.refresh();
				User.writeApp(usersList);
				albumName.setText("");
			}

		}
		if (b == editAlbum) {
			if (index == -1) {
				errorDialog.setTitle("Nothing is Selected");
				errorDialog.setHeaderText("Please Select Something to edit");
				errorDialog.show();
			} else if (albumName.getText().trim().equals("")) {
				errorDialog.setTitle("Empty Album Name");
				errorDialog.setHeaderText("Please enter a valid Album Name");
				errorDialog.show();
			} else {

				for (int i = 0; i < usersList.get(selectedUserIndex).getAlbums().size(); i++) {
					if (usersList.get(selectedUserIndex).getAlbums().get(i).getName()
							.equals(albumName.getText().trim())) {
						alreadyExist = true;
					}
				}

				if (alreadyExist) {
					errorDialog.setTitle("Album Name already exists");
					errorDialog.setHeaderText("Please enter a different name");
					errorDialog.show();
				} else {
					usersList.get(selectedUserIndex).getAlbums().get(index).setName(albumName.getText());
					albums = FXCollections.observableList(usersList.get(selectedUserIndex).getAlbums());
					albumList.setItems(albums);
					albumName.setText("");
					User.writeApp(usersList);
				}
			}

		}
		if (b == search) {
			searchController.start(selectedUserIndex);
			stage.setScene(searchScene);
		}

	}

}