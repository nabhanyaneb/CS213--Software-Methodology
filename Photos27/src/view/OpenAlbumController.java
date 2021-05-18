package view;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

import model.SerializableController;
import objects.Album;
import objects.Photo;
import objects.Tag;
import objects.User;
//import javafx.scene.image.Image;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author Joshua Gole
 * @author Nabhanya Neb
 * 
 * This controls the main screen when the album is opened, and handles all the buttons
 */
public class OpenAlbumController implements Serializable{
	
	@FXML ListView<String> listView;
	@FXML ImageView imageViewFull;
	@FXML Button back;
	@FXML Button logout;
	@FXML Button quit;
	@FXML Button editBtn;
	@FXML Button addBtn;
	@FXML Button deleteBtn;
	@FXML Button remove;
	@FXML Button addPhoto;
	@FXML Button move;
	@FXML Button copy;
	@FXML TextField editBar;
	@FXML TextField addBar;
	@FXML TextField deleteBar;
	@FXML TextArea details;

	/**
	 * The list of all users
	 */
	ArrayList<User> users = new ArrayList<User>();
	
	/**
	 * the list of the captions for the photos
	 */
	ArrayList<String> captions;
	/**
	 * used for the listview
	 */
	ObservableList<String> items;
    
    /**
     * the current user
     */
    private User user;
    /**
     * the current album
     */
    private Album album;
    
    /**
     * starts the scene
     * @param primaryStage
     * @param user
     * @param album
     * @throws Exception
     */
    public void start(Stage primaryStage, User user, Album album) throws Exception {
    	this.user = user;
    	this.album = album;
    	
    	captions = new ArrayList<String>();
    	
    	for (Photo p: this.album.getPhotos()) {
    		captions.add(p.getCaption());
    	}
    	
        items =FXCollections.observableArrayList(captions);
        listView.setItems(items);
        
        listView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String caption, boolean empty) { 
                super.updateItem(caption, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                	
                    for (int i = 0; i < album.getPhotos().size(); i++) {            	
	                    if(caption.equals(album.getPhotos().get(i).getCaption()))
	                        imageView.setImage(album.getPhotos().get(i).getImage());
                    }
                    
                    imageView.setFitWidth(10);
                    imageView.setFitHeight(10);
                    setText(caption);
                    setGraphic(imageView);
                    
                }
            }
        }); 
        
		listView.getSelectionModel().selectFirst();
		if (!(album.getPhotos().isEmpty())) {
			imageViewFull.setImage(album.getPhotos().get(0).getImage());
			details.setText(album.getPhotos().get(0).toString());
		} else {
			imageViewFull.setImage(null);
			details.setText(null);
		}
		
		listView
		.getSelectionModel()
		.selectedItemProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showItem()); 
    }
    
	/**
	 * shows the item selected
	 */
	private void showItem() {                
		int index = listView.getSelectionModel().getSelectedIndex();
		if(index != -1){
			imageViewFull.setImage(album.getPhotos().get(index).getImage());
			details.setText(album.getPhotos().get(index).toString());
		}
	}
	
	/**
	 * The method used to take action accordingly anytime a button is clicked
	 * @param e
	 * @throws Exception
	 */
	public void openAction(ActionEvent e) throws Exception{
		
		Button b = (Button) e.getSource();
		String id = b.getId();
		System.out.println(id);
		
		if (id.equals("editBtn")) {
			users = SerializableController.read();
			
			int index = listView.getSelectionModel().getSelectedIndex();
			
			ArrayList<Photo> tempP = this.album.getPhotos();
			
			String text = editBar.getText();
			tempP.get(index).setCaption(text);
			
			captions.set(index, text);
	        items =FXCollections.observableArrayList(captions);
	        listView.setItems(items);
	        listView.getSelectionModel().select(index);
	        
	        this.album.setPhotos(tempP);
			
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
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		
		if (id.equals("addBtn")) { 
			users = SerializableController.read();
			
			int index = listView.getSelectionModel().getSelectedIndex();
			
			ArrayList<Photo> tempP = this.album.getPhotos();
			ArrayList<Tag> tempT = tempP.get(index).getTags();	
			
			String str = addBar.getText();
			String[] text = str.split("=");
			
			Tag tag = new Tag(text[0], text[1]);
			
			if (tempP.isEmpty()) {
				Alert photoAlert = new Alert(AlertType.ERROR);
				photoAlert.setTitle("No Photos");
				photoAlert.setContentText("Can't add tag, please add photo or change album.");
		        
		        Optional<ButtonType> z = photoAlert.showAndWait();
		        
		        if (z.get() == ButtonType.OK) {
		        	
		        	photoAlert.close();
		        }
			}
			else if (this.album.getPhotos().get(index).getTags().contains(tag)) {
				Alert tagAlert = new Alert(AlertType.ERROR);
				tagAlert.setTitle("Tag Already Exists");
				tagAlert.setContentText("Please add a different tag to this photo.");
		        
		        Optional<ButtonType> z = tagAlert.showAndWait();
		        
		        if (z.get() == ButtonType.OK) {
		        	
		        	tagAlert.close();
		        }
			}
			else {
				tempT.add(tag);
				tempP.get(index).setTags(tempT);
				details.setText(tempP.get(index).toString());
				this.album.setPhotos(tempP);
				
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
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
		
		if (id.equals("deleteBtn")) {
			users = SerializableController.read();
			
			int index = listView.getSelectionModel().getSelectedIndex();

			ArrayList<Photo> tempP = this.album.getPhotos();
			ArrayList<Tag> tempT = new ArrayList<Tag>();		
			
			String str = deleteBar.getText();
			String[] text = str.split("=");
			
			if ((deleteBar.getText() == null)||(deleteBar.getText().isEmpty())) {
				Alert tagAlert = new Alert(AlertType.ERROR);
				tagAlert.setTitle("No Tags");
				tagAlert.setContentText("Invalid Input.");
		        
		        Optional<ButtonType> z = tagAlert.showAndWait();
		        
		        if (z.get() == ButtonType.OK) {
		        	
		        	tagAlert.close();
		        }
			}
			else {
				Tag tag = new Tag(text[0], text[1]);
				if (tempP.isEmpty()) {
					Alert photoAlert = new Alert(AlertType.ERROR);
					photoAlert.setTitle("No Photos");
					photoAlert.setContentText("Can't add tag, please add photo or change album.");
			        
			        Optional<ButtonType> z = photoAlert.showAndWait();
			        
			        if (z.get() == ButtonType.OK) {
			        	
			        	photoAlert.close();
			        }
				} else {
					tempT = tempP.get(index).getTags();	
				}
				if (tempT.isEmpty()) {
					Alert tagAlert = new Alert(AlertType.ERROR);
					tagAlert.setTitle("No Tags");
					tagAlert.setContentText("Photo does not contain any tags.");
			        
			        Optional<ButtonType> z = tagAlert.showAndWait();
			        
			        if (z.get() == ButtonType.OK) {
			        	
			        	tagAlert.close();
			        }
				}
				else {
					tempT.remove(tag);
					tempP.get(index).setTags(tempT);
					details.setText(tempP.get(index).toString());
					this.album.setPhotos(tempP);
					
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
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		}
		if (id.equals("remove")) {
		    if (!(album.getPhotos().isEmpty())) {
				ArrayList<User>users = SerializableController.read();
				int index = listView.getSelectionModel().getSelectedIndex();
				this.album.getPhotos().remove(index);
				captions.remove(index);
				items =FXCollections.observableArrayList(captions);	              
			    listView.setItems(items);
			    if (index > 0)
			    	listView.getSelectionModel().select(index-1);
			    else listView.getSelectionModel().selectFirst();
			    
			    
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
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			    
			    if (album.getPhotos().isEmpty()) {
					imageViewFull.setImage(null);
					details.setText(null);
				} 
		    }
		    else {
		    	Alert photoAlert = new Alert(AlertType.ERROR);
				photoAlert.setTitle("No Photos");
				photoAlert.setContentText("There are no photos available to remove.");
		        
		        Optional<ButtonType> z = photoAlert.showAndWait();
		        
		        if (z.get() == ButtonType.OK) {
		        	
		        	photoAlert.close();
		        }
		    }
		}
		
		if (id.equals("addPhoto")) {
			addPhoto();
			Stage stage = (Stage) addPhoto.getScene().getWindow();
			stage.close();
		}
		
		if (id.equals("move")) {
			int index = listView.getSelectionModel().getSelectedIndex();
			Photo select = this.album.getPhotos().get(index);	
			move(select);
			Stage stage = (Stage) move.getScene().getWindow();
			stage.close();
		}
		
		if (id.equals("copy")) {
			int index = listView.getSelectionModel().getSelectedIndex();
			Photo select = this.album.getPhotos().get(index);	
			copy(select);
			Stage stage = (Stage) copy.getScene().getWindow();
			stage.close();
		}
		
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
	 * takes the user back to the main user page
	 * @throws IOException
	 */
	public void goBack() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/userMain.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		UserMainController userMainController = loader.getController();
		Stage userStage = new Stage();
		
		userMainController.start(userStage, this.user);
		Scene scene = new Scene(root, 400, 200);
		userStage.setScene(scene);
		userStage.show();	
	}
	
	/**
	 * the method used to create a new instance of a controller when the user wants to add a photo to an album
	 * @throws Exception
	 */
	public void addPhoto() throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/addPhoto.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		AddPhotoController addPhotoController = loader.getController();
		Stage userStage = new Stage();
		addPhotoController.start(userStage, user, album);
		
		Scene scene = new Scene(root, 400, 200);
		userStage.setScene(scene);
		userStage.show();	
	}
	
	/**
	 * the method used to create a new instance of a controller when the user wants to move a photo to an album
	 * @param photo
	 * @throws Exception
	 */
	public void move(Photo photo) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/movePhoto.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		MovePhotoController movePhotoController = loader.getController();
		Stage userStage = new Stage();
		movePhotoController.start(userStage, user, album, photo);
		
		Scene scene = new Scene(root, 400, 200);
		userStage.setScene(scene);
		userStage.show();	
	}
	
	/**
	 * the method used to create a new instance of a controller when the user wants to copy a photo to an album
	 * @param photo
	 * @throws Exception
	 */
	public void copy(Photo photo) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/copyPhoto.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		CopyPhotoController copyPhotoController = loader.getController();
		Stage userStage = new Stage();
		copyPhotoController.start(userStage, user, album, photo);
		
		Scene scene = new Scene(root, 400, 200);
		userStage.setScene(scene);
		userStage.show();	
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
