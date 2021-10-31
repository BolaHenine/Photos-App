package view;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.User;

public class searchController {
	@FXML
	Button search;
	@FXML
	Button logout;
	@FXML
	Button close;
	@FXML
	ListView<Photo> searchResults;
	@FXML
	TextField tagName;
	@FXML
	TextField tagValue;
	@FXML
	DatePicker fromDate;
	@FXML
	DatePicker toDate;

	private ObservableList<User> users;

	private ArrayList<Photo> allImages = new ArrayList<Photo>();

	private ArrayList<Photo> searchedImages = new ArrayList<Photo>();

	private ArrayList<Album> allAlbums = new ArrayList<Album>();

	public void start(int userIndex)
			throws ClassNotFoundException, IOException {
		users = User.readApp();

		allAlbums = users.get(userIndex).getAlbums();

		for (int i = 0; i < allAlbums.size(); i++) {
			for (int j = 0; j < allAlbums.get(i).getPhotos().size(); j++) {
				allImages.add(allAlbums.get(i).getPhotos().get(j));
			}
		}

		searchResults.setItems(FXCollections.observableArrayList(allImages));

		searchResults.setCellFactory(
				new Callback<ListView<Photo>, ListCell<Photo>>() {
					@Override
					public ListCell<Photo> call(ListView<Photo> arg0) {
						ListCell<Photo> cell = new ListCell<Photo>() {
							@Override
							protected void updateItem(Photo photo,
									boolean bt1) {
								super.updateItem(photo, bt1);
								if (photo != null) {
									Image img = allImages.get(getIndex())
											.getImage();
									ImageView imgview = new ImageView(img);
									setGraphic(imgview);
									setText(allImages.get(getIndex())
											.getName());
								} else {
									setGraphic(null);
									setText("");
								}
							}
						};
						return cell;
					};

				});

	}

	public void buttonClick(ActionEvent e) throws IOException {
		Button b = (Button) e.getSource();
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/view/loginPage.fxml"));
		Scene root = (Scene) loader.load();

		if (b == close) {
			stage.close();
		}
		if (b == logout) {
			stage.setScene(root);
		}

		if (b == search) {
			if (tagName.getText() != null && tagValue.getText() != null) {
				searchedImages.clear();
				for (int i = 0; i < allImages.size(); i++) {
					HashMap<String, List<String>> tagMap = allImages.get(i)
							.getTag();
					for (int j = 0; j < tagMap.size(); j++) {
						String key = (String) tagMap.keySet().toArray()[j];
						if (key.equals(tagName.getText()) && tagMap.get(key)
								.contains(tagValue.getText())) {
							searchedImages.add(allImages.get(i));
						}
					}

				}
			}

			if (fromDate.getValue() != null && toDate.getValue() != null) {
				searchedImages.clear();
				for (int i = 0; i < allImages.size(); i++) {
					LocalDateTime imageTime = allImages.get(i).getDate();
					if (imageTime.isAfter(fromDate.getValue().atStartOfDay())
							&& imageTime.isBefore(
									toDate.getValue().atStartOfDay())) {
						searchedImages.add(allImages.get(i));
					}
				}

			}

			searchResults.setItems(
					FXCollections.observableArrayList(searchedImages));

			searchResults.setCellFactory(
					new Callback<ListView<Photo>, ListCell<Photo>>() {
						@Override
						public ListCell<Photo> call(ListView<Photo> arg0) {
							ListCell<Photo> cell = new ListCell<Photo>() {
								@Override
								protected void updateItem(Photo photo,
										boolean bt1) {
									super.updateItem(photo, bt1);
									if (photo != null) {
										Image img = searchedImages
												.get(getIndex()).getImage();
										ImageView imgview = new ImageView(img);
										setGraphic(imgview);
										setText(searchedImages.get(getIndex())
												.getName());
									} else {
										setGraphic(null);
										setText("");
									}
								}
							};
							return cell;
						};

					});
		}

	}
}
