package view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import model.SerializableController;
import objects.Album;
import objects.Photo;
import objects.User;

/**
 * @author Joshua Gole
 * @author Nabhanya Neb
 * 
 * This is the controller for all the buttons and textfields when the user is copying a photo to another album
 *
 */
public class CopyPhotoController implements Serializable{
	
	@FXML Button quit;
	@FXML Button logout;
	@FXML Button back;
	@FXML Button copy;
	@FXML TextField album;
	
	/**
	 * holds a list of users to be updated
	 */
	ArrayList<User> users = new ArrayList<User>();

    /**
     * the current user
     */
    private User user;
    /**
     * the source album of the photo
     */
    private Album source;
    /**
     * the photo being copied
     */
    private Photo photo;
    
	/**
	 * Starts the scene
	 * @param createUserStage
	 * @param user
	 * @param source
	 * @param photo
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void start(Stage createUserStage, User user, Album source, Photo photo) 
			throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		this.user = user;
		this.source = source;
		this.photo = photo;
		users = SerializableController.read();
	}
	/**
	 * The method used to take action accordingly anytime a button is clicked
	 * @param e
	 * @throws Exception
	 */
	public void createAction (ActionEvent e) throws Exception{
		
		Button b = (Button) e.getSource();
		String id = b.getId();
		System.out.print(id);
		
		if (id.equals("back")) {
			goBack();
			Stage stage = (Stage) back.getScene().getWindow();
			stage.close();
		}
		
		if (id.equals("logout")) {
			logout();
			Stage stage = (Stage) logout.getScene().getWindow();
			stage.close();
		}
		
		if (id.equals("copy")) {
			
			String dest = album.getText();
			boolean exists = false;
			
			for (Album stored : user.getAlbums()){
				if (stored.getName().equals(dest)){
					exists = true;
				}
			}
			if (exists) {
				copy(dest);
				Stage stage = (Stage) copy.getScene().getWindow();
				stage.close();
			} else {
				Alert albumAlert = new Alert(AlertType.ERROR);
				albumAlert.setTitle("Invalid Album Name");
				albumAlert.setContentText("Please enter a valid album name.");
		        
		        Optional<ButtonType> z = albumAlert.showAndWait();
		        
		        if (z.get() == ButtonType.OK) {
		        	
		            albumAlert.close();
		        }
			}
		}
		
		if (id.equals("quit")) {
		    Stage stage = (Stage) quit.getScene().getWindow();
		    stage.close();
		    
			stage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent e) {
			     Platform.exit();
			     System.exit(0);
			    }
			  });
		}
	}
	
	/**
	 * takes the user back to the main screen where the album is opened
	 * @throws Exception
	 */
	public void goBack() throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/openAlbum.fxml"));
		System.out.println(loader.getLocation().toString());
		AnchorPane root = (AnchorPane)loader.load();
		
		OpenAlbumController openAlbumController = loader.getController();
		Stage openStage = new Stage();
		
		openAlbumController.start(openStage, user, source); 
		Scene scene = new Scene(root, 400, 200);
		openStage.setScene(scene);
		openStage.show();
	}
	
	/**
	 * logs the user out
	 * @throws IOException
	 */
	public void logout() throws IOException { 
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(getClass().getResource("/view/login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		LoginController loginController = loader.getController();
		Stage primaryStage = new Stage();
		loginController.start(primaryStage);

		Scene scene = new Scene(root, 400, 200);
		primaryStage.setScene(scene);
		primaryStage.show();		
	}
	
	/**
	 * does the actual copying 
	 * @param dest
	 * @throws Exception
	 */
	public void copy(String dest) throws Exception { 

		User retU = user;
		Album retA = source;
        
	    for (User u : users) {
	    	if (u.getUsername().equals(this.user.getUsername())){
	    		for (Album a : u.getAlbums()) {
	    			if (a.getName().equals(source.getName())){
			            retU = u;
			            retA = a;
			    	} else if (a.getName().equals(dest)){
			    		ArrayList<Photo> temp2 = a.getPhotos();
			            temp2.add(photo);
			            a.setPhotos(temp2);
			    	}
			    }
	    	}
	    }
        
		try {
			SerializableController.save(users);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/openAlbum.fxml"));
		System.out.println(loader.getLocation().toString());
		AnchorPane root = (AnchorPane)loader.load();
		
		OpenAlbumController openAlbumController = loader.getController();
		Stage openStage = new Stage();
		
		openAlbumController.start(openStage, retU, retA); 
		Scene scene = new Scene(root, 400, 200);
		openStage.setScene(scene);
		openStage.show();	
	}
}
