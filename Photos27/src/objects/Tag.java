package objects;

import java.io.Serializable;

/**
 * @author Joshua Gole
 * @author Nabhanya Neb
 * 
 * The Tag class represents the tags that are used to identify photos
 *
 */
public class Tag implements Serializable{
	/**
	 * the name (or type) of the tag
	 */
	private String name;
	/**
	 * the value for the tag
	 */
	private String value;
	
	/**
	 * Constructor
	 * @param name
	 * @param value
	 */
	public Tag(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * @return the name (or type) of the tag
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
	 * @returns the value for the tag
	 */
	public String getValue() {
	    return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
	    this.value = value;
	}	
	/**
	 * @returns a string to be printed in the display for the photo
	 */
	public String toString() {
		return this.name + "=" + this.value;
	}
}
