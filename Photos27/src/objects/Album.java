package objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * 
 * @author Joshua Gole
 * @author Nabhanya
 *
 * The Album class is used to represent each Album
 */

public class Album implements Serializable{
	/**
	 * Holds the name
	 */
	private String name;
	/**
	 * Holds all the photos in the album
	 */
	private ArrayList<Photo> photos;
	/**
	 * Holds a string version of each of the dates for the photos in the album
	 */
	private ArrayList<String> dates;
	
	
	/**
	 * Constructor
	 * @param name
	 */
	public Album(String name) {
		this.name = name;
		photos = new ArrayList<Photo>();
		dates = new ArrayList<String>();
	}
	
	/**
	 * @return name
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
	 * @return list of photos
	 */
	public ArrayList<Photo> getPhotos() {
	    return photos;
	}

	/**
	 * @param photos
	 */
	public void setPhotos(ArrayList<Photo> photos) {
	    this.photos = photos;
	}
	
	/**
	 * @return list of strings for each of the dates
	 */
	public ArrayList<String> getDateStrings(){
		for (Photo p: this.photos)
			dates.add(p.getDateString());
		return dates;
	}
	
	/**
	 * @return string for earliest date
	 */
	private String getMinDate() {
		ArrayList<String> dates_temp = getDateStrings();
		if (dates_temp.isEmpty())
			return null;
		String min = dates_temp.get(0);
		for (String d: dates_temp) {
			if (d.compareTo(min) < 0)
				min = d;
		}
		return min;
	}
	
	/**
	 * @return string for latest date
	 */
	private String getMaxDate() {
		ArrayList<String> dates_temp = getDateStrings();
		if (dates_temp.isEmpty())
			return null;
		String max = dates_temp.get(0);
		for (String d: dates_temp) {
			if (d.compareTo(max) >= 0)
				max = d;
		}
		return max;
	}	
	
	/**
	 * creates a string to be printed in display for the album
	 * @returns a string to be printed in display for the album
	 */
	public String toString() {
		String min_string = getMinDate();
		String max_string = getMaxDate();
		if ((min_string == null) || (max_string == null)){
			return "Name: "+ this.name + "\nSize: " + photos.size();
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return "Name: "+ this.name + "\nSize: " + photos.size() +"\n" + min_string.substring(0,10) + " -> " + max_string.substring(0,10);
	}
}
