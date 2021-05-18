//Joshua Gole and Nabhanya Neb
package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

import data.Songs;
import data.Songs.Song;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

@SuppressWarnings("unused")
public class SongLibController {

	@FXML ListView<String> listView;   
	@FXML Button add;
	@FXML Button edit;
	@FXML Button delete;
	@FXML Button cancel;
	@FXML TextField name;
	@FXML TextField artist;
	@FXML TextField album;
	@FXML TextField year;
	@FXML TextArea details;

	private ObservableList<String> obsList;   
	private ArrayList<String> arr;
	private ArrayList<String> keys;
	
	Stage global;

	public void start(Stage mainStage) {      
		global = mainStage;
		// create an ObservableList 
		// from an ArrayList  
		importData();
		Songs.sortLibrary();
		
		arr = new ArrayList<String>();
		keys = new ArrayList<String>();
		
		for (Song s: Songs.library) {
			arr.add(s.getName());
			keys.add(s.getKey());
		}	
		
		obsList = FXCollections.observableArrayList(keys); 
		listView.setItems(obsList); 

		// select the first item
		listView.getSelectionModel().selectFirst();

		// set listener for the items
		listView
		.getSelectionModel()
		.selectedItemProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showItem()); 

		exportData();
		mainStage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent e) {
		     Platform.exit();
		     System.exit(0);
		    }
		  });
				

	}
	private void showItem() {                
		int index = listView.getSelectionModel().getSelectedIndex();
		if(index != -1){
			details.setText(Songs.library.get(index).toString());
		}
		if (index == -1)
			details.setText("");
	}
	
	public void button(ActionEvent e) { 
		Button b = (Button)e.getSource();
		if (b == add) {
			
			if (name.getText().isEmpty() && artist.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(global);
				alert.setTitle("Song Library");
				alert.setHeaderText("ERROR!");
				alert.setContentText("Song Name and Artist Required");
				alert.showAndWait();
			}
			else if(name.getText().isEmpty()){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(global);
				alert.setTitle("Song Library");
				alert.setHeaderText("ERROR!");
				alert.setContentText("Song Name Required");
				alert.showAndWait();
			}
			else if(artist.getText().isEmpty()){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(global);
				alert.setTitle("Song Library");
				alert.setHeaderText("ERROR!");
				alert.setContentText("Song Artist Required");
				alert.showAndWait();
			}
			else if(duplicate(name.getText(), artist.getText())) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(global);
				alert.setTitle("Song Library");
				alert.setHeaderText("ERROR!");
				alert.setContentText("Song Already Exists");
				alert.showAndWait();				
			}
			else {
				if (album.getText().isEmpty() && year.getText().isEmpty())
					addSong(name.getText(), artist.getText(), "", -1);
				else if (album.getText().isEmpty()) {
					int y = Integer.parseInt(year.getText());
					if (y<0) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.initOwner(global);
						alert.setTitle("Song Library");
						alert.setHeaderText("ERROR!");
						alert.setContentText("Invalid Year");
						alert.showAndWait();						
					}
					else addSong(name.getText(), artist.getText(), "",y );
				}
				else if (year.getText().isEmpty()) {
					addSong(name.getText(), artist.getText(), album.getText(), -1);
				}
				else {
					
						int y = Integer.parseInt(year.getText());
						if (y<0) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.initOwner(global);
							alert.setTitle("Song Library");
							alert.setHeaderText("ERROR!");
							alert.setContentText("Invalid Year");
							alert.showAndWait();						
						}
						else addSong(name.getText(), artist.getText(), album.getText(),y );					
				}

			}
			
			this.name.clear();
			this.artist.clear();
			this.album.clear();
			this.year.clear();
		}
		if (b == edit)
			editSong();
		if (b == delete) {			
			deleteSong();
		}
	}
	
	private boolean duplicate(String name, String artist) {
		
		for (Song s: Songs.library) {
			if (s.getName().toLowerCase().equals(name.toLowerCase()) && s.getArtist().toLowerCase().equals(artist.toLowerCase()))
				return true;
		}
		
		return false;
	}
		
	public void addSong(String name, String artist, String album, int year) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(global);
		alert.setTitle("Song Library");
		alert.setHeaderText("CONFIRM");
		alert.setContentText("Are you sure you want to add "+name+" ?");
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK) {
			Song s = new Song(name, artist, album, year);
			Songs.library.add(s);
			Songs.sortLibrary();
			
			arr.add(name);
			sortNames(arr);
			keys.add(name+" | "+artist);
			sortKeys(keys);

			
			int index = Songs.library.indexOf(s);
	
			
			obsList = FXCollections.observableArrayList(keys); 
			listView.setItems(obsList);
			listView.getSelectionModel().select(index);

			
			exportData();
		}
		else return;
		
	}
	
	public void editSong() {

		
		String item = listView.getSelectionModel().getSelectedItem();
		int index = listView.getSelectionModel().getSelectedIndex();
		
		Song s = Songs.library.get(index);
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(global);
		alert.setTitle("Song Library");
		alert.setHeaderText("CONFIRM");
		alert.setContentText("Are you sure you want to make these changes to "+item+" ?");
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK) {
			if (!name.getText().isEmpty()) {
				s.setName(name.getText());
				s.setKey(s.getName()+s.getArtist());
				arr.set(index, name.getText());
				keys.set(index, s.getName()+" | "+s.getArtist());
			}
			if (!artist.getText().isEmpty()) {
				s.setArtist(artist.getText());
				s.setKey(s.getName()+s.getArtist());
				keys.set(index, s.getName()+" | "+s.getArtist());
			}
			if (!album.getText().isEmpty()) {
				s.setAlbum(album.getText());
			}
			if (!year.getText().isEmpty()) {
				int y = Integer.parseInt(year.getText());
				s.setYear(y);
			}
			
			this.name.clear();
			this.artist.clear();
			this.album.clear();
			this.year.clear();
			
			Songs.sortLibrary();
			index = Songs.library.indexOf(s);
			sortNames(arr);
			sortKeys(keys);
			
			obsList = FXCollections.observableArrayList(keys); 
			listView.setItems(obsList);
			listView.getSelectionModel().select(index);
			
			exportData();
			
		}
		else return;
		
	}
	
	public void deleteSong() {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(global);
		alert.setTitle("Song Library");
		alert.setHeaderText("CONFIRM");
		alert.setContentText("Are you sure you want to delete "+listView.getSelectionModel().getSelectedItem()+" ?");
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK) {
		
			String item = listView.getSelectionModel().getSelectedItem();
			int index = listView.getSelectionModel().getSelectedIndex();
			
			Songs.library.remove(index);
			Songs.sortLibrary();
			
			arr.remove(index);
			sortNames(arr);
			keys.remove(index);
			sortKeys(keys);
			
			obsList = FXCollections.observableArrayList(keys); 
			listView.setItems(obsList);
			
			if (index >= Songs.library.size())
				index = Songs.library.size() - 1;
			listView.getSelectionModel().select(index);
			
			listView
			.getSelectionModel()
			.selectedItemProperty()
			.addListener(
					(obs, oldVal, newVal) -> 
					showItem()); 	
			
			exportData();
		}
		else return;

	}
	
	public void sortNames(ArrayList<String> arr) {
		arr.clear();
		for (Song s: Songs.library) {
			arr.add(s.getName());
		}
	}
	public void sortKeys(ArrayList<String> keys) {
		Collections.sort(keys, new Comparator<String>() {
			public int compare(String song1, String song2) {
				int ret = song1.compareToIgnoreCase(song2);
				
				return ret;
			}
				
	});
	}
	public void importData() { 
		try {
			BufferedReader br = new BufferedReader(new FileReader("songdata.txt"));
			String currentLine = "";
			while ((currentLine = br.readLine())!=null) {
				String[] info = currentLine.split("\\|");
				
				if (info[2].equals("unknown") && info[3].equals("unknown")) {
					Songs.library.add(new Song(info[0], info[1], "", -1));
				}
				else if (info[2].equals("unknown")) {
					Songs.library.add(new Song(info[0], info[1], "", Integer.parseInt(info[3])));
				}
				else if (info[3].equals("unknown")) {
					Songs.library.add(new Song(info[0], info[1], info[2], -1));
				}
				else {
					Songs.library.add(new Song(info[0], info[1], info[2], Integer.parseInt(info[3])));
				}
			}
			Songs.sortLibrary();
			br.close();
		}
		catch(Exception e) {
			System.out.println(e);
			return;
		}
	}
	
	public void exportData() { 
		try {
			PrintWriter pw = new PrintWriter("songdata.txt");
			for (Song s : Songs.library) {
				if (s.getAlbum().equals("") && s.getYear() == -1) {
					pw.println(s.getName()+"|"+s.getArtist()+"|unknown|unknown");
				}
				else if (s.getAlbum().equals("")) {
					pw.println(s.getName()+"|"+s.getArtist()+"|unknown|"+s.getYear());
				}
				else if (s.getYear() == -1) {
					pw.println(s.getName()+"|"+s.getArtist()+"|"+s.getAlbum()+"|unknown");
				}
				else {
					pw.println(s.getName()+"|"+s.getArtist()+"|"+s.getAlbum()+"|"+s.getYear());
				}
			}
			pw.close();
		}
		catch (Exception e) {
			return;
		}
	}

}
