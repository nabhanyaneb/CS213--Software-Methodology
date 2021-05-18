package view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import model.SerializableController;
import objects.User;


/**
 * @author Joshua Gole
 * @author Nabhanya Neb
 *
 * This controls the main screen when the admin wants to see a list of all the users, and handles all the buttons
 */
/**
 * @author nabhanyaneb
 *
 */
public class ListUsersController implements Serializable{
	
	@FXML Button quit;
	@FXML Button logout;
	@FXML Button back;
	@FXML TextArea userList;
	ArrayList<User> users = new ArrayList<User>();
	
	Stage global;

	/**
	 * starts the scene
	 * @param listUsersStage
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void start(Stage listUsersStage) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		global = listUsersStage;
		display();
	}
	
	/**
	 * The method used to take action accordingly anytime a button is clicked
	 * @param e
	 * @throws Exception
	 */
	public void listAction (ActionEvent e) throws Exception{

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
	}
	
	
	/**
	 * sends the admin back to the main screen for the admin controller
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
	 * displays the list of users
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void display() throws IOException, ClassNotFoundException {
		

		users = SerializableController.read();
		String display = new String();
		for (User user : users) {
			display =  user.getUsername() + "\n" + display;
		}
		userList.setText(display);
		
	}
	
	/**
	 * logs the user out of the application
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



