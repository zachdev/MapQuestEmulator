/*
 * Name:		Zach Graham
 * Class:		CS 341
 * Year:		Fall 2013
 * Assignment:	Final Assignment
 */

package mapquest_emulator.includes;

/**
 * City class, represents a city, with a list of connected cities
 * 
 * @author Zach Graham
 *
 */
public class City implements Comparable<City> {
	
	private String cityName;
	
	private ConnectionList connectionList;
	
	private float minDistance = Float.POSITIVE_INFINITY;
	
	private City previous;
	
	/**
	 * Default constructor that initializes with the specified city name and instantiates
	 * a new list of connections
	 * 
	 * @param cityName the city name
	 */
	public City(String cityName) {
		
		this.cityName = cityName;
		connectionList = new ConnectionList();
		
	}
	
	/**
	 * Returns this city's name
	 * 
	 * @return this city's name
	 */
	public String getName() {
		
		return cityName;
	}

	/**
	 * Adds a city with its associated distance value to the list of connections
	 * 
	 * Returns true if the addition was successful, false if not
	 * 
	 * @param newCity the City this City will be connected to
	 * @param distance the distance from this city to the connected city 
	 * @return true if add is successful, false if not
	 */
	public boolean addConnection(City newCity, float distance) {
		
		if (!connectionList.contains(newCity.getName())) {
			
			Connection newConnection = new Connection(newCity, distance);
			
			connectionList.add(newConnection);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Deletes the specified connection from this City
	 * 
	 * Returns true if the operation was successful, false if not
	 * 
	 * @param delCity the City to be removed from the connection list
	 * @return true if removal successful, false if unsuccessful
	 */
	public boolean delConnection(City delCity) {
		
		return connectionList.remove(delCity.getName());
	}
	
	/**
	 * Returns the list of connections this city has
	 * 
	 * @return list of connections to this city
	 */
	public ConnectionList getConnectedCities() {
		
		return connectionList;
		
	}
	
	/**
	 * Returns the minimum distance of this City
	 * 
	 * @return minimum distance of this City
	 */
	public float getMinDistance() {
		
		return minDistance;
	}
	
	/**
	 * Sets the minimum distance of this City
	 * 
	 * @param minDistance the new distance value to set
	 */
	public void setMinDistance(float minDistance) {
		
		this.minDistance = minDistance;
	}
	
	/**
	 * Compares the minDistance of this City and another City
	 * 
	 * @return comparison result of this City and parameter City
	 */
	public int compareTo(City otherCity) {
		
		return Float.compare(minDistance, otherCity.minDistance);
	}
	
	/**
	 * Gets the previous City
	 * 
	 * @return the previous City
	 */
	public City getPrevious() {
		
		return previous;
	}
	
	/**
	 * Sets the previous city
	 * 
	 * @param newPrevious the new previous City
	 */
	public void setPrevious(City newPrevious) {
		
		previous = newPrevious;
	}
	
} // End class City