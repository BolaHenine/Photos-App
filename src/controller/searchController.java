/**
 * @author Bola Henine
 *
 * @author Roshan Seth
 */
package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
	Button back;
	@FXML
	Button close;
	@FXML
	ListView<Photo> searchResults;
	@FXML
	DatePicker fromDate;
	@FXML
	DatePicker toDate;
	@FXML
	Button addToAlbum;
	@FXML
	Button tagSearch;
	@FXML
	TextField secondTagName;
	@FXML
	TextField secondTagValue;
	@FXML
	TextField firstTagName;
	@FXML
	TextField firstTagValue;
	@FXML
	ComboBox searchComboBox;
	@FXML
	GridPane dialogGrid;
	@FXML
	Label secondTagNameLabel;
	@FXML
	Label secondTagValueLabel;
	@FXML
	Label firstTagNameLabel;
	@FXML
	Label firstTagValueLabel;

	private Parent userParent;
	private FXMLLoader userLoader;
	private ObservableList<User> users;

	private ArrayList<Photo> allImages = new ArrayList<Photo>();

	private ArrayList<Photo> searchedImages = new ArrayList<Photo>();

	private ArrayList<Album> allAlbums = new ArrayList<Album>();

	private int userNumber;

	private Boolean singleSearch = false;
	private Boolean conjunctiveSearch = false;
	private Boolean disjunctiveSearch = false;

	/**
	 *
	 * @param userIndex
	 *            the index of the selected user
	 * @throws ClassNotFoundException
	 *             throws exception if the class or file name was not found.
	 * @throws IOException
	 *             throws exception if the deserialization fails.
	 */

	public void start(int userIndex)
			throws ClassNotFoundException, IOException {

		dialogGrid.setVisible(false);
		firstTagName.setVisible(false);
		firstTagValue.setVisible(false);
		secondTagValue.setVisible(false);
		secondTagName.setVisible(false);

		firstTagNameLabel.setVisible(false);
		firstTagValueLabel.setVisible(false);
		secondTagValueLabel.setVisible(false);
		secondTagNameLabel.setVisible(false);

		String options[] = {"Single Tag Value Search",
				"Conjunctive combination", "Disjunctive combination"};

		searchComboBox.setItems(FXCollections.observableArrayList(options));

		users = User.readApp();

		userNumber = userIndex;

		allAlbums = users.get(userIndex).getAlbums();

		for (int i = 0; i < allAlbums.size(); i++) {
			for (int j = 0; j < allAlbums.get(i).getPhotos().size(); j++) {
				allImages.add(allAlbums.get(i).getPhotos().get(j));
			}
		}

	}

	/**
	 *
	 * @param e
	 *            the drop down menu action event
	 */

	public void dropDownSelected(ActionEvent e) {
		if (searchComboBox.getValue().equals("Single Tag Value Search")) {
			firstTagName.setVisible(true);
			firstTagValue.setVisible(true);

			secondTagValue.setVisible(false);
			secondTagName.setVisible(false);

			firstTagNameLabel.setVisible(true);
			firstTagValueLabel.setVisible(true);

			secondTagValueLabel.setVisible(false);
			secondTagNameLabel.setVisible(false);

			singleSearch = true;
			conjunctiveSearch = false;
			disjunctiveSearch = false;

		} else if (searchComboBox.getValue()
				.equals("Conjunctive combination")) {
			singleSearch = false;
			conjunctiveSearch = true;
			disjunctiveSearch = false;
			firstTagName.setVisible(true);
			firstTagValue.setVisible(true);
			secondTagValue.setVisible(true);
			secondTagName.setVisible(true);
			firstTagNameLabel.setVisible(true);
			firstTagValueLabel.setVisible(true);
			secondTagValueLabel.setVisible(true);
			secondTagNameLabel.setVisible(true);
		} else {
			singleSearch = false;
			conjunctiveSearch = false;
			disjunctiveSearch = true;
			firstTagName.setVisible(true);
			firstTagValue.setVisible(true);
			secondTagValue.setVisible(true);
			secondTagName.setVisible(true);
			firstTagNameLabel.setVisible(true);
			firstTagValueLabel.setVisible(true);
			secondTagValueLabel.setVisible(true);
			secondTagNameLabel.setVisible(true);
		}
	}

	/**
	 *
	 * @param e
	 *            the action event that triggered the method
	 * @throws IOException
	 *             throws exception if the deserialization fails.
	 */

	public void buttonClick(ActionEvent e) throws IOException {
		Button b = (Button) e.getSource();
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/view/loginPage.fxml"));
		Scene root = (Scene) loader.load();
		root.getRoot().setStyle("-fx-font-family: 'serif'");
		TextArea albumName = new TextArea();
		albumName.setPromptText("Enter the album name");
		albumName.setPrefWidth(10);
		albumName.setPrefHeight(25);

		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.getDialogPane().setStyle("-fx-font-family: 'serif'");
		dialog.setTitle("Enter Album Name");
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialog.getDialogPane().setContent(albumName);

		Dialog<ButtonType> tagSearchDialog = new Dialog<>();
		tagSearchDialog.getDialogPane().setStyle("-fx-font-family: 'serif'");
		tagSearchDialog.setTitle("Confirmation required");
		tagSearchDialog.setHeaderText("Select Search Type");
		DialogPane tagSearchDialogPane = tagSearchDialog.getDialogPane();
		tagSearchDialogPane.getButtonTypes().addAll(ButtonType.OK,
				ButtonType.CANCEL);

		tagSearchDialogPane.setContent(dialogGrid);

		userLoader = new FXMLLoader(
				getClass().getResource("/view/userView.fxml"));
		userParent = (Parent) userLoader.load();
		Scene userScene = new Scene(userParent);
		userScene.getRoot().setStyle("-fx-font-family: 'serif'");
		userController usercontroller = userLoader.getController();
		String username = users.get(userNumber).getName();

		Dialog<ButtonType> errorDialog = new Dialog<>();
		errorDialog.getDialogPane().setStyle("-fx-font-family: 'serif'");
		DialogPane errorDialogPane = errorDialog.getDialogPane();
		errorDialogPane.getButtonTypes().addAll(ButtonType.OK);

		Dialog<ButtonType> dateDialog = new Dialog<>();
		dateDialog.getDialogPane().setStyle("-fx-font-family: 'serif'");
		DialogPane dateDialogPane = dateDialog.getDialogPane();
		dateDialogPane.getButtonTypes().addAll(ButtonType.OK);

		Dialog<ButtonType> dateDialog1 = new Dialog<>();
		dateDialog1.getDialogPane().setStyle("-fx-font-family: 'serif'");
		DialogPane dateDialogPane1 = dateDialog1.getDialogPane();
		dateDialogPane1.getButtonTypes().addAll(ButtonType.OK);

		if (b == close) {
			stage.close();
		}
		if (b == logout) {
			stage.setScene(root);
		}
		if (b == back) {
			try {
				usercontroller.start(userNumber, username);
				stage.setScene(userScene);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		if (b == search) {
			fromDate.getEditor().setStyle("-fx-font-family: 'serif'");
			toDate.getEditor().setStyle("-fx-font-family: 'serif'");

			if (fromDate.getValue() != null && toDate.getValue() != null) {
				searchedImages.clear();
				for (int i = 0; i < allImages.size(); i++) {
					LocalDateTime imageTime = allImages.get(i).getDate();
					if (imageTime.isAfter(fromDate.getValue().atStartOfDay())
							&& imageTime.isBefore(
									toDate.getValue().atStartOfDay())) {
						searchedImages.add(allImages.get(i));
					} else {
						dateDialog.setTitle("No Search Results");
						dateDialog.setHeaderText(
								"Did not find any results for these days.");
						dateDialog.show();
					}
				}

			} else if (fromDate.getValue() == null
					&& toDate.getValue() == null) {
				dateDialog1.setTitle("Empty Dates");
				dateDialog1.setHeaderText("Dates are empty");
				dateDialog1.show();
				fromDate.getEditor().clear();
				toDate.getEditor().clear();

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
										imgview.setFitWidth(50);
										imgview.setFitHeight(50);
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
		if (b == addToAlbum) {
			if (searchedImages.size() == 0) {
				errorDialog.setTitle("Empty Search Result");
				errorDialog.setHeaderText("Empty Search Result");
				errorDialog.show();
			} else {
				Optional<ButtonType> result = dialog.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					int albumLength = users.get(userNumber).getAlbums().size();
					users.get(userNumber)
							.addAlbum(new Album(albumName.getText()));
					for (int i = 0; i < searchedImages.size(); i++) {
						Photo temp = searchedImages.get(i);
						users.get(userNumber).getAlbums().get(albumLength)
								.addPhoto(temp);
					}
				}
				User.writeApp(users);
			}
		}
		if (b == tagSearch) {
			dialogGrid.setVisible(true);
			Optional<ButtonType> result = tagSearchDialog.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				if (singleSearch) {
					if (firstTagName.getText() != null
							&& firstTagValue.getText() != null) {
						searchedImages.clear();
						for (int i = 0; i < allImages.size(); i++) {
							HashMap<String, List<String>> tagMap = allImages
									.get(i).getTag();
							for (int j = 0; j < tagMap.size(); j++) {
								String key = (String) tagMap.keySet()
										.toArray()[j];
								if (key.equals(firstTagName.getText())
										&& tagMap.get(key).contains(
												firstTagValue.getText())) {
									if (!searchedImages
											.contains(allImages.get(i))) {
										searchedImages.add(allImages.get(i));
									}
								}
							}

						}
					}
				} else if (disjunctiveSearch) {
					if (firstTagName.getText() != null
							&& firstTagValue.getText() != null) {
						searchedImages.clear();
						for (int i = 0; i < allImages.size(); i++) {
							HashMap<String, List<String>> tagMap = allImages
									.get(i).getTag();
							for (int j = 0; j < tagMap.size(); j++) {
								String key = (String) tagMap.keySet()
										.toArray()[j];
								if ((key.equals(firstTagName.getText())
										&& tagMap.get(key).contains(
												firstTagValue.getText()))
										|| (key.equals(secondTagName.getText())
												&& tagMap.get(key)
														.contains(secondTagValue
																.getText()))) {
									if (!searchedImages
											.contains(allImages.get(i))) {
										searchedImages.add(allImages.get(i));
									}
								}
							}

						}
					}
				} else if (conjunctiveSearch) {

					if (firstTagName.getText() != null
							&& firstTagValue.getText() != null) {
						searchedImages.clear();
						for (int i = 0; i < allImages.size(); i++) {
							boolean foundFirst = false;
							boolean foundsecond = false;
							HashMap<String, List<String>> tagMap = allImages
									.get(i).getTag();
							if (tagMap.containsKey(firstTagName.getText())
									&& tagMap.containsKey(
											secondTagName.getText())) {
								for (int j = 0; j < tagMap.size(); j++) {
									String key = (String) tagMap.keySet()
											.toArray()[j];
									if ((key.equals(firstTagName.getText())
											&& tagMap.get(key).contains(
													firstTagValue.getText()))) {
										foundFirst = true;
									}
									if (foundFirst && ((key
											.equals(secondTagName.getText())
											&& tagMap.get(key)
													.contains(secondTagValue
															.getText())))) {
										foundsecond = true;
										if (foundsecond && !searchedImages
												.contains(allImages.get(i))) {
											searchedImages
													.add(allImages.get(i));
										}
									}
								}
							}
						}
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
										imgview.setFitWidth(50);
										imgview.setFitHeight(50);
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
