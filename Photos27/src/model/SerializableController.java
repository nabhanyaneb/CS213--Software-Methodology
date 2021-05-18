package model;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import objects.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Joshua Gole
 * @author Nabhanya Neb
 * 
 * This is the Serializable controller we use to help us store data
 */

 public class SerializableController implements Serializable{
	static String storedUsersDir = "src";
	static String storedUsers = "users.dat";
	

	/**
	 * Saves data
	 * @param users
	 * @throws IOException
	 */
	public static void save(ArrayList<User> users) throws IOException {
		
		ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream(storedUsersDir + File.separator + storedUsers));
			out.writeObject(users);
			out.close();
	}
	
	
	/**
	 * Reads data
	 * @return list of users 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static ArrayList<User> read() throws IOException, ClassNotFoundException {
		
		ObjectInputStream in = new ObjectInputStream(
				new FileInputStream(storedUsersDir + File.separator + storedUsers));
		ArrayList<User> users = (ArrayList<User>)in.readObject();
		in.close();
		return users;
	}
}
