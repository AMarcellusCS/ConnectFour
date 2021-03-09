/**
 * @author Adrian Marcellus 
 * @version 12/17/2019
 * Final Project Driver
 * This is the driver used for playing connect four. 
 */

import javafx.application.Application; 
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ConnectFourDriver extends Application{
	public static void main(String[] args) {
		Application.launch(args);
	}
	public void start(Stage primaryStage) {
		ConnectFourPane game = new ConnectFourPane();
		BorderPane fullGame = new BorderPane();
		fullGame.setCenter(game);
		Scene scene = new Scene(fullGame);
		primaryStage.setTitle("Connect Four"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
}
