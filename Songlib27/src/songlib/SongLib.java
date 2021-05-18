//Joshua Gole and Nabhanya Neb
package songlib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import data.Songs;
import data.Songs.Song;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.SongLibController;


@SuppressWarnings("unused")
public class SongLib extends Application {
	
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(getClass().getResource("/view/songlib.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		SongLibController listController = loader.getController();
		listController.start(primaryStage);

		Scene scene = new Scene(root, 450, 400);
		primaryStage.setScene(scene);
		primaryStage.show(); 

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	

}
