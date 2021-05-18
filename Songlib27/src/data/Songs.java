//Joshua Gole and Nabhanya Neb
package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Songs {
	public static ArrayList<Song> library = new ArrayList<Song>();;
	
	public Songs() {
	
	}

	public static class Song {
		
		private String name;
		private String artist;
		private String album;
		private int year;
		private String key;
		
		public Song(String name, String artist, String album, int year) {
			this.name = name;
			this.artist = artist;
			this.album = album; //can be empty string to represent no album
			this.year = year; //can be -1 to represent no entry
			this.key = name+" | "+ artist;
		}
		
		public String getName(){
			return name;
		}

		public String getArtist(){
			return artist;
		}

		public String getAlbum(){
			return album;
		}

		public int getYear(){
			return year;
		}
		public String getKey() {
			return key;
		}
		public void setName(String name){
			this.name = name;
		}

		public void setArtist(String artist){
			this.artist = artist;
		}

		public void setAlbum(String album){
			this.album = album;
		}

		public void setYear(int year){
			this.year = year;
		}	
		public void setKey(String key) {
			this.key = key;
		}
		public void printSong(){
		    System.out.println("Name: " + name +" | Artist: " + artist + " | Album: " + album + " | Year: "+year);
		}
		public String toString() {
			String str="";
			
			if (this.getAlbum().equals("") && this.getYear() == -1) {
				str = "Name: "+this.getName()+"\n"+"Artist: "+this.getArtist()+"\nAlbum: unknown \nYear: unknown";
			}
			else if (this.getAlbum().equals("")) {
				str = "Name: "+this.getName()+"\n"+"Artist: "+this.getArtist()+"\nAlbum: unknown \nYear: "+this.getYear();
			}
			else if (this.getYear() == -1) {
				str = "Name: "+this.getName()+"\nArtist: "+this.getArtist()+"\nAlbum: "+this.getAlbum()+"\nYear: unknown";
			}
			else {
				str = "Name: "+this.getName()+"\nArtist: "+this.getArtist()+"\nAlbum: "+this.getAlbum()+"\nYear: "+this.getYear();
			}
			
			return str;
		}
	}
	
	public static void sortLibrary() {
		Collections.sort(library, new Comparator<Song>() {
				public int compare(Song song1, Song song2) {
					int ret = song1.getName().compareToIgnoreCase(song2.getName());
					
					if (ret == 0) {
						ret = song1.getArtist().compareToIgnoreCase(song2.getArtist());
					}
					return ret;
				}
					
		});
	}
	
	public static void printLibrary() {
		for (Song s: library) {
			s.printSong();
		}
	}
	
}
