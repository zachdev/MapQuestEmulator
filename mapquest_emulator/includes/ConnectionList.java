/*
 * Name:		Zach Graham
 * Class:		CS 341
 * Year:		Fall 2013
 * Assignment:	Final Assignment
 */

package mapquest_emulator.includes;

import java.util.ArrayList;

/**
 * Represents the list of connections a city has to it
 * 
 * Inherits most List management methods/data members from the ArrayList parent class
 * 
 * @author Zach Graham
 */
public class ConnectionList extends ArrayList<Connection> {

	private static final long serialVersionUID = 1L;			// Gets rid of Eclipse warning

	/**
	 * Searches this list of connections for a City having a name matching the parameter
	 * 
	 * If found, true is returned, else false is returned
	 * 
	 * @param searchCity the name of the City to search the list for
	 * @return true if the list contains the City, else false
	 */
	public boolean contains(String searchCity) {
		
		if (size() == 0) {
			
			return false;
		}
		
		else {
			
			for (Connection connectedCity : this) {
				
				if (connectedCity.getCity().getName().equals(searchCity)) {
					
					return true;
				}
			}
			
			return false;
		}
		
	}
	
	/**
	 * Searches the list of connections by City object
	 * 
	 * If a City having the same name as the parameter City is found, true is returned, else false returned
	 * 
	 * @param searchCity the City object to search the list for
	 * @return true if the list contains the parameter City, else false
	 */
	public boolean contains(City searchCity) {
		
		if (size() == 0) {
			
			return false;
		}
		
		else {
			
			for (Connection connectedCity : this) {
				
				if (connectedCity.getCity().getName().equals(searchCity.getName())) {
					
					return true;
				}
			}
			
			return false;
		}
	}
	
	/**
	 * Removes a Connection from this list by City name
	 * 
	 * @param searchCity the name of the connected City to remove
	 * @return true if removal successful, false if not
	 */
	public boolean remove(String searchCity) {
		
		if (size() == 0) {
			
			return false;
		}
		
		else {
			
			for (int i = 0; i < this.size(); i++) {
				
				if (this.get(i).getCity().getName().equals(searchCity)) {
					
					this.remove(i);
					return true;
					
				}
			}
			
			return false;
		}
	}
	
} // End class ConnectionList
	