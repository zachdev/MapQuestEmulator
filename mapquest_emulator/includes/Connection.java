/*
 * Name:		Zach Graham
 * Class:		CS 341
 * Year:		Fall 2013
 * Assignment:	Final Assignment
 */

package mapquest_emulator.includes;

/**
 * Represents a connection from one City to another City
 * 
 * Contains a reference to a connected City object, and a floating point distance value
 * 
 * @author Zach Graham
 *
 */
public class Connection {
	
	City connectedCity;
	
	float distance;
	
	Connection previous, next;
	
	/**
	 * Constructor that initializes a new Connection with the given City reference and float distance value
	 * 
	 * @param connectedCity the connected City object
	 * @param distance the floating point distance to the connected City
	 */
	public Connection(City connectedCity, float distance) {
		
		this.connectedCity = connectedCity;
		this.distance = distance;
		previous = null;
		next = null;
		
	}
	
	/**
	 * Gets the next Connection
	 * 
	 * @return a reference to the next Connection
	 */
	public Connection getNext() {
		
		return next;
	}
	
	/**
	 * Sets the next Connection
	 * 
	 * @param next the next Connection after this one
	 */
	public void setNext(Connection next) {
		
		this.next = next;	
	}
	
	/**
	 * Gets the previous Connection of this one
	 * 
	 * @return previous Connection of this one
	 */
	public Connection getPrevious() {
		
		return previous;
	}
	
	/**
	 * Sets the previous Connection of this one
	 * 
	 * @param previous the previous Connection
	 */
	public void setPrevious(Connection previous) {
		
		this.previous = previous;	
	}
	
	/**
	 * Gets the City object associated with this Connection
	 * 
	 * @return reference to the connected City
	 */
	public City getCity() {
		
		return connectedCity;
	}
	
	/**
	 * Sets the connected City
	 * 
	 * @param connectedCity the connected City
	 */
	public void setCity(City connectedCity) {
		
		this.connectedCity = connectedCity;
	}
	
	/**
	 * Gets the distance associated with this Connection
	 * 
	 * @return distance to the connected City
	 */
	public float getDistance() {
		
		return distance;
	}
	
} // End class Connection