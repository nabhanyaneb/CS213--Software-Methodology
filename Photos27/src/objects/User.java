package objects;

import java.io.Serializable;
import java.util.ArrayList;



/**
 * @author Joshua Gole
 * @author Nabhanya Neb
 *
 * The User class represents each of the users of the application
 */
public class User implements Serializable{
	/**
	 * holds the username
	 */
	private String username;
	/**
	 * holds a list of albums for the user
	 */
	private ArrayList<Album> albums;
	
	/**
	 * Constructor
	 * @param username
	 */
	public User(String username) {
		this.username = username;
		albums = new ArrayList<Album>();
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
	    return username;
	}

	/**
	 * @param username
	 */
	public void setUsername(String username) {
	    this.username = username;
	}

	/**
	 * @return the list of all albums
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
	
}
