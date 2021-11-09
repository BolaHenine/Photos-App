/**
 * @author Bola Henine
 *
 * @author Roshan Seth
 */
package app;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main driver of the application
 */

public class mainDriver extends Application {

	/**
	 * @param primaryStage
	 *            The main stage of the program
	 */

	@Override
	public void start(Stage primaryStage) throws Exception {
		//
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/loginPage.fxml"));
		Scene root = (Scene) loader.load();
		root.getRoot().setStyle("-fx-font-family: 'serif'");
		primaryStage.setScene(root);
		primaryStage.setTitle("Photos");
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public static void main(String[] args) throws CloneNotSupportedException {
		// TODO Auto-generated method stub

		launch(args);
	}

}
