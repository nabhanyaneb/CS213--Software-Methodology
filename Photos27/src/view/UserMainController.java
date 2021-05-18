package view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
//import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.SerializableController;
import objects.Album;
import objects.Photo;
import objects.Tag;
import objects.User;

/**
 * @author nabhanyaneb
 *
 *This controls the main screen when the user logs in, and handles all the buttons
 */
/**
 * @author nabhanyaneb
 *
 */
public class UserMainController implements Serializable{
	
	@FXML Label label;
	@FXML ListView<String> listView; //will be of Type Album later or something
	@FXML TextArea details;
	@FXML TextField searchBar;
	@FXML TextField renameBar;
	@FXML Button open;
	@FXML Button rename;
	@FXML Button delete;
	@FXML Button createNew;
	@FXML Button byDate;
	@FXML Button byTag;
	@FXML Button createResults;
	@FXML Button quit;
	@FXML Button logout;


	/**
	 * holds the list of all users
	 */
	ArrayList<User> users = new ArrayList<User>();
	/**
	 * the current user
	 */
	User user;
	
	/**
	 * holds the name of all the albums
	 */
	ArrayList<String> names;
	/**
	 * used for the listview
	 */
	ObservableList<String> items;
	
	/**
	 * used when searching 
	 */
	ArrayList<String> temp_names;
	/**
	 * used when searching
	 */
	ObservableList<String> temp_items;
	/**
	 * used when searching
	 */
	ArrayList<Photo> temp_photos;
    

	/**
	 * starts the scene
	 * @param userStage
	 * @param user
	 */
	public void start(Stage userStage, User user) {
		System.out.println(user);
		this.user = user;
		
		names = new ArrayList<String>();
		for (Album a: this.user.getAlbums()) {
			names.add(a.getName());
		}
		
		items =FXCollections.observableArrayList(names);	              
	    listView.setItems(items);
	   
        listView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                	
                    for (int i = 0; i < user.getAlbums().size(); i++) { 
                    	if (user.getAlbums().get(i).getPhotos().isEmpty()) {
                            setText(null);
                            setGraphic(null);
                        } else {
		                    if(name.equals(user.getAlbums().get(i).getName()))
		                        imageView.setImage(user.getAlbums().get(i).getPhotos().get(0).getImage());
                        }
                    }
                   
                    imageView.setFitHeight(10);
                    imageView.setFitWidth(10);
                    setText(name);
                    setGraphic(imageView);
                    
                }
            }
        });
        
		listView.getSelectionModel().selectFirst();
		listView
		.getSelectionModel()
		.selectedItemProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showItem()); 
		
		if (!(user.getAlbums().isEmpty()))
			details.setText(user.getAlbums().get(0).toString());
	
	}
	
	/**
	 * shows the item selected
	 */
	private void showItem() {                
		int index = listView.getSelectionModel().getSelectedIndex();
		if(index != -1){
			details.setText(user.getAlbums().get(index).toString());
		}
		if (index == -1)
			details.setText("");
	}
	
	/**
	 * The method used to take action accordingly anytime a button is clicked
	 * @param e
	 * @throws Exception
	 */
	public void userAction(ActionEvent e) throws Exception{
		Button b = (Button) e.getSource();
		String id = b.getId();
		System.out.println(id);
		
		if (id.equals("open")) {
			if (user.getAlbums().isEmpty()) {
				Alert emptyAlert = new Alert(AlertType.ERROR);
				emptyAlert.setTitle("No Albums Exist");
				emptyAlert.setContentText("User does not have any photo albums to open.");
		        
		        Optional<ButtonType> z = emptyAlert.showAndWait();
		        
		        if (z.get() == ButtonType.OK) {
		        	
		        	emptyAlert.close();
		        }
			} else {
				openAlbum();
				Stage stage = (Stage) logout.getScene().getWindow();
				stage.close();	
			}				
		}
		
		if (id.equals("delete")) {
			users = SerializableController.read();
			int index = listView.getSelectionModel().getSelectedIndex();
			ArrayList<Album> ret = user.getAlbums();
			ret.remove(index);	
			names.remove(index);
			items =FXCollections.observableArrayList(names);	              
		    listView.setItems(items);
		    if (index > 0)
		    	listView.getSelectionModel().select(index-1);
		    else listView.getSelectionModel().selectFirst();
		    for (User stored : users){
				if (stored.getUsername().equals(user.getUsername()))
					stored.setAlbums(ret);
			}
		    try {
				SerializableController.save(users);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		
		if (id.equals("rename")) {
			users = SerializableController.read();
			int index = listView.getSelectionModel().getSelectedIndex();
			ArrayList<Album> ret = user.getAlbums();
			Album rAlbum = user.getAlbums().get(index);
			
			String text = renameBar.getText();
			boolean exists = false;
			for (Album x : ret){
				if (x.getName().equals(text))
					exists = true;
			}
			
			if (!(exists)) {
				names.set(index, text);
				
				items =FXCollections.observableArrayList(names);	              
			    listView.setItems(items);
			    
			    rAlbum.setName(text);
			    listView.getSelectionModel().select(index);
			    
			    ret.set(index, rAlbum);
			    
			    for (User stored : users){
					if (stored.getUsername().equals(user.getUsername()))
						stored.setAlbums(ret);
				}
			    try {
					SerializableController.save(users);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
			else {
				Alert existsAlert = new Alert(AlertType.ERROR);
				existsAlert.setTitle("Album Name Exists Alreafy");
				existsAlert.setContentText("Please type a non-existant album name");
		        
		        Optional<ButtonType> z = existsAlert.showAndWait();
		        
		        if (z.get() == ButtonType.OK) {
		        	
		        	existsAlert.close();
		        }
			}
			
		}
		
		if (id.equals("createNew")) {

			users = SerializableController.read();
			int count = 0;
			for (Album a: user.getAlbums()) {
				if (a.getName().substring(0,5).equals("album"))
					count++;
			}
			Album album = new Album("album"+(count+1));
			
			ArrayList<Album> temp = new ArrayList<Album>();
			temp = user.getAlbums();
			temp.add(album);
			user.setAlbums(temp);
			for (User stored : users){
				if (stored.getUsername().equals(user.getUsername()))
					stored.setAlbums(user.getAlbums());
			}
		    try {
				SerializableController.save(users);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			names = new ArrayList<String>();
			for (Album a: this.user.getAlbums()) {
				names.add(a.getName());
			}
			
			System.out.println("before error");
			items =FXCollections.observableArrayList(names);		
		    listView.setItems(items); //THIS IS THE LINE IT IS ERRORING ON
		    System.out.println("after error"); 
		    
		    
	        listView.setCellFactory(param -> new ListCell<String>() {
	            private ImageView imageView = new ImageView();
	            @Override
	            public void updateItem(String name, boolean empty) {
	                super.updateItem(name, empty);
	                if (empty) {
	                    setText(null);
	                    setGraphic(null);
	                } else {
	                	
	                    for (int i = 0; i < user.getAlbums().size(); i++) {            	
		                    if(name.equals(user.getAlbums().get(i).getName())) {
		                    	if (user.getAlbums().get(i).getPhotos().size() > 0)
		                    		imageView.setImage(user.getAlbums().get(i).getPhotos().get(0).getImage());
		                    }
		                
	                    }
	                   
	                    imageView.setFitHeight(10);
	                    imageView.setFitWidth(10);
	                    setText(name);
	                    setGraphic(imageView);
	                    
	                }
	            }
	        });
	        
			listView.getSelectionModel().selectFirst();
			listView
			.getSelectionModel()
			.selectedItemProperty()
			.addListener(
					(obs, oldVal, newVal) -> 
					showItem()); 	
		}
		
		if (id.equals("createResults")) {
			if ((temp_photos == null) || (temp_photos.isEmpty())){
				Alert nullAlert = new Alert(AlertType.ERROR);
				nullAlert.setTitle("No Results");
				nullAlert.setContentText("Album can't be created from empty results.");
		        
		        Optional<ButtonType> z = nullAlert.showAndWait();
		        
		        if (z.get() == ButtonType.OK) {
		        	
		        	nullAlert.close();
		        }
			}
			else if (temp_photos.size()>0) {
				users = SerializableController.read();
				int count = 0;
				for (Album a: user.getAlbums()) {
					if (a.getName().substring(0,5).equals("album"))
						count++;
				}				
				Album album = new Album("album"+(count+1));
				ArrayList<Album> temp = new ArrayList<Album>();
				temp = user.getAlbums();
				for (Photo p: temp_photos) {
					album.getPhotos().add(p);
				}
				temp.add(album);
				names = new ArrayList<String>();
				for (Album a: temp) {
					names.add(a.getName());
				}
				user.setAlbums(temp);
				for (User stored : users){
					if (stored.getUsername().equals(user.getUsername()))
						stored.setAlbums(user.getAlbums());
				}
			    try {
					SerializableController.save(users);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				items =FXCollections.observableArrayList(names);	              
			    listView.setItems(items);
			   
		        listView.setCellFactory(param -> new ListCell<String>() {
		            private ImageView imageView = new ImageView();
		            @Override
		            public void updateItem(String name, boolean empty) {
		                super.updateItem(name, empty);
		                if (empty) {
		                    setText(null);
		                    setGraphic(null);
		                } else {
		                	
		                    for (int i = 0; i < user.getAlbums().size(); i++) {            	
			                    if(name.equals(user.getAlbums().get(i).getName()))
			                        imageView.setImage(user.getAlbums().get(i).getPhotos().get(0).getImage());
		                    }
		                   
		                    imageView.setFitHeight(10);
		                    imageView.setFitWidth(10);
		                    setText(name);
		                    setGraphic(imageView);
		                    
		                }
		            }
		        });
		        
				listView.getSelectionModel().selectFirst();
				listView
				.getSelectionModel()
				.selectedItemProperty()
				.addListener(
						(obs, oldVal, newVal) -> 
						showItem()); 
			}
		}
		
		if (id.equals("byTag")) {
			String text = searchBar.getText();

			if (!(text.contains("="))) {
				Alert equalsAlert = new Alert(AlertType.ERROR);
				equalsAlert.setTitle("Invalid Tag Input");
				equalsAlert.setContentText("Please separate tag name and tag by '='");
		        
		        Optional<ButtonType> z = equalsAlert.showAndWait();
		        
		        if (z.get() == ButtonType.OK) {
		        	
		        	equalsAlert.close();
		        }
			}
			
			else if (text.length()>0) {
				temp_names = new ArrayList<String>();
				temp_photos = new ArrayList<Photo>();
				ArrayList<Image> temp_images = new ArrayList<Image>();
				
				String[] str = text.split("\\s+");
				if (str.length == 1) {
					String[] tags = str[0].split("=");
					for (Album a: user.getAlbums()) {
						for (Photo p: a.getPhotos()) {
							for (Tag t: p.getTags()) {
								if (t.getName().toLowerCase().equals(tags[0].toLowerCase()) && t.getValue().toLowerCase().equals(tags[1].toLowerCase())) {
									temp_names.add(p.getCaption());
									temp_photos.add(p);
									temp_images.add(p.getImage());									
								}
							}
						}
					}
				}
				
				
				else if (str.length == 3){
					String[] tag1 = str[0].split("=");
					String[] tag2 = str[2].split("=");
					for (Album a: user.getAlbums()) {
						for (Photo p: a.getPhotos()) {
							for (Tag t: p.getTags()) {
								if (str[1].toUpperCase().equals("OR")) {
									if (t.getName().toLowerCase().equals(tag1[0].toLowerCase()) && t.getValue().toLowerCase().equals(tag1[1].toLowerCase()) && !temp_photos.contains(p)) {
										temp_names.add(p.getCaption());
										temp_photos.add(p);
										temp_images.add(p.getImage());									
									}
									else if (t.getName().toLowerCase().equals(tag2[0].toLowerCase()) && t.getValue().toLowerCase().equals(tag2[1].toLowerCase()) && !temp_photos.contains(p)) {
										temp_names.add(p.getCaption());
										temp_photos.add(p);
										temp_images.add(p.getImage());											
									}
								}
								
								if (str[1].toUpperCase().equals("AND")) {
									if (t.getName().toLowerCase().equals(tag1[0].toLowerCase()) && t.getValue().toLowerCase().equals(tag1[1].toLowerCase())) {
										for (Tag tt: p.getTags()) {
											if (tt.getName().toLowerCase().equals(tag2[0].toLowerCase()) && tt.getValue().toLowerCase().equals(tag2[1].toLowerCase()) && !temp_photos.contains(p)) {
												temp_names.add(p.getCaption());
												temp_photos.add(p);
												temp_images.add(p.getImage());	
											}
										}
									}
								}
							}
						}
					}					
					
				}
				
				
				temp_items = FXCollections.observableArrayList(temp_names);
				listView.setItems(temp_items);
				listView.getSelectionModel().selectFirst();
		        listView.setCellFactory(param -> new ListCell<String>() {
		            private ImageView imageView = new ImageView();
		            @Override
		            public void updateItem(String caption, boolean empty) {
		                super.updateItem(caption, empty);
		                if (empty) {
		                    setText(null);
		                    setGraphic(null);
		                } else {
		                	
		                    for (int i = 0; i < temp_images.size(); i++) {            	
			                    if(caption.equals(temp_photos.get(i).getCaption()))
			                        imageView.setImage(temp_images.get(i));
		                    }
		                   
		                    imageView.setFitHeight(10);
		                    imageView.setFitWidth(10);
		                    setText(caption);
		                    setGraphic(imageView);
		                    
		                }
		            }
		        });				
			}
			else {
				temp_names = new ArrayList<String>();
				temp_photos = new ArrayList<Photo>();
				ArrayList<Image> temp_images = new ArrayList<Image>();
				
				listView.setItems(items);
				listView.getSelectionModel().selectFirst();
		        listView.setCellFactory(param -> new ListCell<String>() {
		            private ImageView imageView = new ImageView();
		            @Override
		            public void updateItem(String name, boolean empty) {
		                super.updateItem(name, empty);
		                if (empty) {
		                    setText(null);
		                    setGraphic(null);
		                } else {
		                	
		                    for (int i = 0; i < user.getAlbums().size(); i++) {            	
			                    if(name.equals(user.getAlbums().get(i).getName()))
			                        imageView.setImage(user.getAlbums().get(i).getPhotos().get(0).getImage());
		                    }
		                   
		                    imageView.setFitHeight(10);
		                    imageView.setFitWidth(10);
		                    setText(name);
		                    setGraphic(imageView);
		                    
		                }
		            }
		        });	
		        
				listView.getSelectionModel().selectFirst();
				listView
				.getSelectionModel()
				.selectedItemProperty()
				.addListener(
						(obs, oldVal, newVal) -> 
						showItem()); 
			}
		}
		
		if (id.equals("byDate")) {
			String text = searchBar.getText();
			if (text.length()>0) {
				temp_names = new ArrayList<String>();
				temp_photos = new ArrayList<Photo>();
				ArrayList<Image> temp_images = new ArrayList<Image>();
				
				for (Album a: user.getAlbums()) {
					for (Photo p: a.getPhotos()) {
						if (p.getDateString().substring(0,10).equals(text)) {
							temp_names.add(p.getCaption());
							temp_photos.add(p);
							temp_images.add(p.getImage());
						}
					}
				}
				temp_items = FXCollections.observableArrayList(temp_names);
				listView.setItems(temp_items);
				listView.getSelectionModel().selectFirst();
		        listView.setCellFactory(param -> new ListCell<String>() {
		            private ImageView imageView = new ImageView();
		            @Override
		            public void updateItem(String caption, boolean empty) {
		                super.updateItem(caption, empty);
		                if (empty) {
		                    setText(null);
		                    setGraphic(null);
		                } else {
		                	
		                    for (int i = 0; i < temp_images.size(); i++) {            	
			                    if(caption.equals(temp_photos.get(i).getCaption()))
			                        imageView.setImage(temp_images.get(i));
		                    }
		                   
		                    imageView.setFitHeight(10);
		                    imageView.setFitWidth(10);
		                    setText(caption);
		                    setGraphic(imageView);
		                    
		                }
		            }
		        });
			}
			else {
				temp_names = new ArrayList<String>();
				temp_photos = new ArrayList<Photo>();
				ArrayList<Image> temp_images = new ArrayList<Image>();
				
				listView.setItems(items);
				listView.getSelectionModel().selectFirst();
		        listView.setCellFactory(param -> new ListCell<String>() {
		            private ImageView imageView = new ImageView();
		            @Override
		            public void updateItem(String name, boolean empty) {
		                super.updateItem(name, empty);
		                if (empty) {
		                    setText(null);
		                    setGraphic(null);
		                } else {
		                	
		                    for (int i = 0; i < user.getAlbums().size(); i++) {            	
			                    if(name.equals(user.getAlbums().get(i).getName()))
			                        imageView.setImage(user.getAlbums().get(i).getPhotos().get(0).getImage());
		                    }
		                   
		                    imageView.setFitHeight(10);
		                    imageView.setFitWidth(10);
		                    setText(name);
		                    setGraphic(imageView);
		                    
		                }
		            }
		        });
		        
				listView.getSelectionModel().selectFirst();
				listView
				.getSelectionModel()
				.selectedItemProperty()
				.addListener(
						(obs, oldVal, newVal) -> 
						showItem()); 
			}
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
	 *  the method used to create a new instance of a controller when the user wants to open an album
	 * @throws Exception
	 */
	public void openAlbum() throws Exception{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/openAlbum.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		OpenAlbumController openAlbumController = loader.getController();
		Stage openStage = new Stage();
		
		int index = listView.getSelectionModel().getSelectedIndex();
		openAlbumController.start(openStage, user, user.getAlbums().get(index)); 
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

}
