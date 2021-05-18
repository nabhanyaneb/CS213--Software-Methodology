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
import javafx.scene.image.Image;

import model.SerializableController;
import objects.Album;
import objects.Photo;
import objects.User;

/**
 * 
 * @author Joshua Gole
 * @author Nabhanya Neb
 * 
 * This is the controller for all the buttons and textfields when the user is adding a photo to an album
 *
 */
public class AddPhotoController implements Serializable{
	
	@FXML Button quit;
	@FXML Button logout;
	@FXML Button back;
	@FXML Button addPhoto;
	@FXML TextField filename;
	
	/**
	 * holds a list of users to be updated
	 */
	ArrayList<User> users = new ArrayList<User>();
	
    /**
     * the current user
     */
    private User user;
    /**
     * The current album being edited
     */
    private Album album;
	
	/**
	 * Starts the scene
	 * @param createUserStage
	 * @param user
	 * @param album
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void start(Stage createUserStage, User user, Album album) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
    	this.user = user;
    	this.album = album;
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
		
		if (id.equals("addPhoto")) {
//			Example Photo Input Format: "file:///C:/Users/joshg/OneDrive/Documents/mickey.jpg"
//			Input must start with "file://"
			String file = filename.getText();
			boolean existingName = false;
					
			for (Photo stored : this.album.getPhotos()){
				if (stored.getImageString().equals(file)){
					existingName = true;
				}
			}
			if (!(existingName)) {
				try{
					new Image(file);
					addPhoto(file);
					Stage stage = (Stage) addPhoto.getScene().getWindow();
					stage.close();	
				} catch (Exception fileNotFound){ 
					Alert photoAlert = new Alert(AlertType.ERROR);
			        photoAlert.setTitle("Photo doesn't exist.");
			        photoAlert.setContentText("Please insert an image using format\n"
			        		+ "file://insertPathHere/imageName");
			        
			        Optional<ButtonType> z = photoAlert.showAndWait();
			        
			        if (z.get() == ButtonType.OK) {
			        	
			            photoAlert.close();
			        }
				}
			}
			else {
				Alert photoAlert = new Alert(AlertType.ERROR);
		        photoAlert.setTitle("Photo already exists.");
		        photoAlert.setContentText("Please enter a new Image.");
		        
		        Optional<ButtonType> z = photoAlert.showAndWait();
		        
		        if (z.get() == ButtonType.OK) {
		        	
		            photoAlert.close();
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
	 * takes the user back to the scene where the album is opened and displayed
	 * @throws Exception
	 */
	public void goBack() throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/openAlbum.fxml"));
		System.out.println(loader.getLocation().toString());
		AnchorPane root = (AnchorPane)loader.load();
		
		OpenAlbumController openAlbumController = loader.getController();
		Stage openStage = new Stage();
		
		openAlbumController.start(openStage, user, album); 
		Scene scene = new Scene(root, 400, 200);
		openStage.setScene(scene);
		openStage.show();
	}
	
	/**
	 * logs the user out of the applcaition
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
	 * adds the photo
	 * @param filename
	 * @throws Exception
	 */
	public void addPhoto(String filename) throws Exception { 
        Photo photo = new Photo(filename, "image"+album.getPhotos().size()+1, "");
        ArrayList<Photo> temp = this.album.getPhotos();
        temp.add(photo);
        this.album.setPhotos(temp);
	    for (User u : users) {
	    	if (u.getUsername().equals(this.user.getUsername())){
	    		for (Album a : u.getAlbums()) {
			    	if (a.getName().equals(this.album.getName())){
			    		a.setPhotos(this.album.getPhotos());;
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
		AnchorPane root = (AnchorPane)loader.load();
		
		OpenAlbumController openAlbumController = loader.getController();
		Stage openStage = new Stage();
		
		openAlbumController.start(openStage, user, album); 
		Scene scene = new Scene(root, 400, 200);
		openStage.setScene(scene);
		openStage.show();			
	}
}
