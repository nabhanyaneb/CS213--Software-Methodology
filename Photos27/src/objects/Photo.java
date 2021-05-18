package objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.image.Image;

/**
 * 
 * @author Joshua Gole
 * @author Nabhanya Neb
 *
 * The Photo class is used to represent each photo
 */
public class Photo implements Serializable{
	/**
	 * holds the url for the image
	 */
	private String img;
	/**
	 * holds the name of the photo
	 */
	private String name;
	/**
	 * holds the caption for the photo
	 */
	private String caption;
	/**
	 * holds a list of albums that the photo is contained in
	 */
	private ArrayList<Album> albums;
	/**
	 * holds a list of all tags for the photo
	 */
	private ArrayList<Tag> tags;
	/**
	 * holds a string versino of the date for the photo
	 */
	private String date_string;
	
	/**
	 * Constructor
	 * @param img
	 * @param name
	 * @param caption
	 */
	public Photo(String img, String name, String caption) {
		this.img = img;
		this.name = name;
		this.caption = caption;
		this.tags = new ArrayList<Tag>();
		this.date_string = LocalDateTime.now().format(
				DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}
	
	  	 /**
	  	 * @return Image with the url
	  	 */
	  	public Image getImage() {
		    return new Image(img);
		  }
	  	  
	  	 /**
	  	 * @return url for the image
	  	 */
	  	public String getImageString() {
		    return img;
		  }

		 /**
		 * @param img
		 */
		public void setImage(String img) {
		    this.img = img;
		  }

		 /**
		 * @return the name for the photo
		 */
		public String getName() {
		    return name;
		  }

		 /**
		 * @param name
		 */
		public void setName(String name) {
		    this.name = name;
		  }

		 /**
		 * @return the caption for the photo 
		 */
		public String getCaption() {
		    return caption;
		  }

		 /**
		 * @param caption
		 */
		public void setCaption(String caption) {
		    this.caption = caption;
		  }

		 /**
		 * @return list of albums that the photo is in
		 */
		public ArrayList<Album> getAlbums() {
		    return albums;
		  }

		 /**
		 * @param albums
		 */
		public void setAlbums(ArrayList<Album> albums) {
		    this.albums = albums;
		  }

			/**
			 * @return list of tags for the photo
			 */
			public ArrayList<Tag> getTags() {
		    return tags;
		  }

		 /**
		 * @param tags
		 */
		public void setTags(ArrayList<Tag> tags) {
		    this.tags = tags;
		  }
		  
		 /**
		 * @return a string version of the photo's date
		 */
		public String getDateString() {
			  return date_string;
		  }

		 /**
		 * @param date
		 */
		public void setDate(LocalDateTime date) {this.date_string = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
		  }
		  
		 /**
		 * @returns a string to be printed in display for the photo
		 */
		public String toString() {
			  String ret = caption + "\n" + date_string.substring(0,10);
			  if (tags.size() > 0) {
				  for (Tag t: tags) {
					  ret += "\n" + t.toString();
				  }
			  }
					  
			  return ret;
		  }
}
