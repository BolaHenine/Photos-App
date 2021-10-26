package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainDriver extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/loginPage.fxml"));
		Scene root = (Scene) loader.load();
		primaryStage.setScene(root);
		primaryStage.setTitle("asd");
		primaryStage.setResizable(false);
		primaryStage.show();
		//
		// FXMLLoader loader = new FXMLLoader();
		// loader.setLocation(getClass().getResource("/view/adminView.fxml"));
		// AnchorPane root = (AnchorPane) loader.load();
		// adminController adminController = loader.getController();
		// adminController.start();
		// Scene scene = new Scene(root);
		// primaryStage.setScene(scene);
		// primaryStage.setResizable(false);
		// primaryStage.show();

		// FXMLLoader loader = new FXMLLoader();
		// loader.setLocation(getClass().getResource("/view/userView.fxml"));
		// AnchorPane root = (AnchorPane) loader.load();
		// userController userController = loader.getController();
		//
		// userController.start(Bola, username);
		//
		// Scene scene = new Scene(root);
		// primaryStage.setScene(scene);
		// primaryStage.setResizable(false);
		// primaryStage.show();

		// FXMLLoader loader = new FXMLLoader();
		// loader.setLocation(getClass().getResource("/view/albumView.fxml"));
		// AnchorPane root = (AnchorPane) loader.load();
		//
		// albumController albumController = loader.getController();
		//
		// // albumController.start();
		//
		// Scene scene = new Scene(root);
		// primaryStage.setScene(scene);
		// primaryStage.setResizable(false);
		// primaryStage.show();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

}
