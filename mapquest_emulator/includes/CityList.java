/*
 * Name:		Zach Graham
 * Class:		CS 341
 * Year:		Fall 2013
 * Assignment:	Final Assignment
 */

package mapquest_emulator.includes;

import java.util.ArrayList;

/**
 * A list of City objects
 * 
 * Inherits most List management methods/data members from the ArrayList parent class
 * 
 * @author Zach Graham
 *
 */
public class CityList extends ArrayList<City> {

	private static final long serialVersionUID = 1L; 		// Gets rid of Eclipse warning

	/**
	 * Searches this list by String cityName
	 * 
	 * If a City having the same name as the parameter City is found, true is returned
	 * 
	 * Else false is returned
	 * 
	 * @param searchCity the city name to search the list for
	 * @return true if city found, false if not
	 */
	public boolean contains(String searchCity) {
		
		if (size() == 0) {
			
			return false;
		}
		
		else {
			
			for (City city : this) {
				
				if (city.getName().equals(searchCity)) {
					
					return true;
				}
			}
			
			return false;
		}	
	}
	
	/**
	 * Searches the list for a City object having the same name as the parameter
	 * 
	 * If the City is found, a reference to it is returned, else null is returned
	 * 
	 * @param cityName the name of the City to get an object reference to
	 * @return reference to matching City object, or else null
	 */
	public City getCityObjectFromString(String cityName) {
		
		if (size() == 0) {
			
			return null;
		}
		else {
			
			City temp;
			
			for (int i = 0; i < this.size(); i++) {
				
				temp = this.get(i);
				
				if (temp.getName().equals(cityName)) {
					
					return temp;
				}
			}
		}
		
		return null;
	}
	
} // End class CityList
