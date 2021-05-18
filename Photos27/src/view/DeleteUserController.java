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
import objects.User;


/**
 * @author Joshua Gole
 * @author Nabhanya Neb
 *
 *This controls the screen when the admin wants to delete a user, and handles all the buttons
 */
public class DeleteUserController implements Serializable{
	
	@FXML Button quit;
	@FXML Button logout;
	@FXML Button back;
	@FXML Button removeUser;
	@FXML TextField username;
	/**
	 * holds a list of current users
	 */
	ArrayList<User> users = new ArrayList<User>();

	/**
	 * starts the scence
	 * @param deleteUserStage
	 */
	public void start(Stage deleteUserStage) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * The method used to take action accordingly anytime a button is clicked
	 * @param e
	 * @throws Exception
	 */
	public void deleteAction (ActionEvent e) throws Exception{
		
		Button b = (Button) e.getSource();
		String id = b.getId();
		
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
		
		if (id.equals("removeUser")) {
			users = SerializableController.read();
			String user = username.getText();
			boolean deletable = false;
			
			if ((user.equals("admin"))||(user.equals("user1"))){  
				//change so that user1 is deletable later
				deletable = false;
			} else {		
				for (User stored : users){
					if (stored.getUsername().equals(user)){
						deletable = true;
					}
				}
			}
			if (deletable) {
				removeUser(user);
				Stage stage = (Stage) removeUser.getScene().getWindow();
				stage.close();
			} else {
				Alert usernameAlert = new Alert(AlertType.ERROR);
		        usernameAlert.setTitle("Username couldn't be deleted.");
		        usernameAlert.setContentText("Please enter a valid username.");
		        
		        Optional<ButtonType> z = usernameAlert.showAndWait();
		        
		        if (z.get() == ButtonType.OK) {
		        	
		            usernameAlert.close();
		        }
			}
		}
	}
	
	/**
	 * takes the admin back to the main screen
	 * @throws IOException
	 */
	public void goBack() throws IOException {
		
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
	 * logs the admin out of the system
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
	
	public void removeUser(String username) throws IOException { 
		for (User stored : users){
			if (stored.getUsername().equals(username)){
				users.remove(stored);
				break;
			}
		}
		try {
			SerializableController.save(users);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/deleteUser.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		DeleteUserController deleteUserController = loader.getController();
		Stage deleteUserStage = new Stage();
		
		deleteUserController.start(deleteUserStage);
		Scene scene = new Scene(root, 400, 200);
		deleteUserStage.setScene(scene);
		deleteUserStage.show();		
	}
}
