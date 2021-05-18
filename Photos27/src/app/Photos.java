package app;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.SerializableController;
import objects.Album;
import objects.Photo;
import objects.User;
import view.LoginController;

/**
 * 
 * @author Joshua Gole
 * @author Nabhanya Neb
 * 
 * This is the main class of the Photos Application, it initializes the first user and launches the application.
 *
 */

public class Photos extends Application {
	
	/**
	 * Starts the program
	 */
	
	@Override
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

		ArrayList<User> users = SerializableController.read();
		boolean stockExists = false;
		for (User stored : users){
			if (stored.getUsername().equals("stock")){
				stockExists = true;
			}
		}
		if (!(stockExists))
			setStock(users);
		
		
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(getClass().getResource("/view/login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		LoginController loginController = loader.getController();
		loginController.start(primaryStage);

		Scene scene = new Scene(root, 400, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	/**
	 * Sets the stock user
	 * @param users
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void setStock(ArrayList<User> users) throws IOException, ClassNotFoundException {
		
		User stock = new User("stock");
		ArrayList<Album> stockAlbums = new ArrayList<Album>();
		stockAlbums.add(new Album("stock"));
		ArrayList<Photo>stockPhotos = new ArrayList<Photo>();
		
		stockPhotos.add(new Photo("/data/cares.jpeg", "image1", "stock image1"));
		stockPhotos.add(new Photo("/data/crying.jpeg", "image2", "stock image2"));
		stockPhotos.add(new Photo("/data/heart.jpeg", "image3", "stock image3"));
		stockPhotos.add(new Photo("/data/nerd.jpeg", "image4", "stock image4"));
		stockPhotos.add(new Photo("/data/rolledeye.jpeg", "image5", "stock image5"));
		stockPhotos.add(new Photo("/data/smiling.jpeg", "image6", "stock image6"));
		stockPhotos.add(new Photo("/data/stareye.jpeg", "image7", "stock image7"));
		stockPhotos.add(new Photo("/data/sunglasses.jpeg", "image8", "stock image8"));
		stockPhotos.add(new Photo("/data/tongue.jpeg", "image9", "stock image9"));
		stockPhotos.add(new Photo("/data/wink.jpeg", "image10", "stock image10"));
		stockAlbums.get(0).setPhotos(stockPhotos);
		stock.setAlbums(stockAlbums);
		users.add(stock);
		try {
			SerializableController.save(users);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	/**
	 * The main method which launches the application
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
