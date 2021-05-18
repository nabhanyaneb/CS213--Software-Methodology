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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
 * This controls the main login screen, and handles all buttons 
 *
 */
public class LoginController implements Serializable{  
	@FXML Button login;
	@FXML Button quit; //no functionality yet
	@FXML TextField username;

	/**
	 * holds the list of all users
	 */
	ArrayList<User> users = new ArrayList<User>();
	/**
	 * a global variable of the stage
	 */
	Stage global;
	/**
	 * the stock User
	 */
	User stock;

	/**
	 * starts the scene
	 * @param primaryStage
	 */
	public void start(Stage primaryStage) {
		// TODO Auto-generated method stub
		global = primaryStage;
	}
	/**
	 * The method used to take action accordingly anytime a button is clicked
	 * @param e
	 * @throws Exception
	 */
	public void loginAction(ActionEvent e) throws Exception {
		
		Button b = (Button) e.getSource();
		String id = b.getId();
		System.out.println(id);
	
		if (id.equals("login")) {
			users = SerializableController.read();
			String entry = username.getText();
			boolean exists = false;
			
			
			if (entry.equals("admin")){
				exists = true;
				startAdmin();
				Stage stage = (Stage) login.getScene().getWindow();
				stage.close();
			}
			else {
				for (User stored : users){
					if (stored.getUsername().equals(entry)){
						exists = true;
						startUser(stored);
						Stage stage = (Stage) login.getScene().getWindow();
						stage.close();
					}
				}
			} if (!(exists)) {

				Alert usernameAlert = new Alert(AlertType.ERROR);
		        usernameAlert.setTitle("Invalid Username");
		        usernameAlert.setContentText("Please enter a valid username.");
		        
		        Optional<ButtonType> z = usernameAlert.showAndWait();
		        
		        if (z.get() == ButtonType.OK) {
		        	
		            usernameAlert.close();
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
	 * the method used to create a new instance of a controller that starts the admin main screen
	 * @throws IOException
	 */
	public void startAdmin() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/adminMain.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		AdminMainController adminMainController = loader.getController();
		Stage adminStage = new Stage();
		
		adminMainController.start(adminStage);
		Scene scene = new Scene(root, 400, 200);
		adminStage.setScene(scene);
		adminStage.show();
	}
	
	/**
	 * the method used to create a new instance of a controller that starts the user main screen
	 * @param user
	 * @throws IOException
	 */
	public void startUser(User user) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/userMain.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		UserMainController userMainController = loader.getController();
		Stage userStage = new Stage();
		
		userMainController.start(userStage, user);
		Scene scene = new Scene(root, 400, 200);
		userStage.setScene(scene);
		userStage.show();		
	}
		
	
}
