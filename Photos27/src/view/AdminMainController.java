package view;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;



/**
 * @author Joshua Gole
 * @author Nabhanya Neb
 * 
 * This controls the main screen when the admin logs in, and handles all the buttons
 *
 */
public class AdminMainController {
	
	@FXML Button quit; 
	@FXML Button logout; 
	@FXML Button listUsers;
	@FXML Button createUser;
	@FXML Button deleteUser;
	
	/**
	 * global version of the main stage
	 */
	Stage global;
	
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
	public void adminAction(ActionEvent e) throws Exception{
		
		Button b = (Button) e.getSource();
		String id = b.getId();
		System.out.println(id);
		
		if (id.equals("listUsers")) {
			listUsers();
			Stage stage = (Stage) listUsers.getScene().getWindow();
			stage.close();
		}
		
		if (id.equals("createUser")) {
			createUser();
			Stage stage = (Stage) createUser.getScene().getWindow();
			stage.close();
			
		}
		
		if (id.equals("deleteUser")) {
			deleteUser();
			Stage stage = (Stage) deleteUser.getScene().getWindow();
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
	}
	
	/**
	 * the method used to create a new instance of a controller when the admin wants to list all users
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void listUsers() throws IOException, ClassNotFoundException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/listUsers.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		ListUsersController listUsersController = loader.getController();
		Stage listUsersStage = new Stage();
		
		listUsersController.start(listUsersStage);
		Scene scene = new Scene(root, 400, 200);
		listUsersStage.setScene(scene);
		listUsersStage.show();
		
		
	}
	
	
	
	/**
	 * the method used to create a new instance of a controller when the admin wants to create a new user
	 * @throws IOException
	 */
	public void createUser() throws IOException {
	
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/createUser.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		CreateUserController createUserController = loader.getController();
		Stage createUserStage = new Stage();
		
		createUserController.start(createUserStage);
		Scene scene = new Scene(root, 400, 200);
		createUserStage.setScene(scene);
		createUserStage.show();
		
	}
	
	/**
	 * the method used to create a new instance of a controller when the admin wants to delete a current user
	 * @throws IOException
	 */
	public void deleteUser() throws IOException {
		
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
	
	/**
	 * logs out of the application
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

}
