/*
 * Name:		Zach Graham
 * Class:		CS 341
 * Year:		Fall 2013
 * Assignment:	Final Assignment
 */

package mapquest_emulator;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Collections;

import mapquest_emulator.includes.*;

/**
 * Weighted graph of City objects with connections to other Cities
 * 
 * That is, a list of Cities (vertices), each having a list of Connections (edges) to other Cities
 * 
 * Each Connection has a floating point mileage distance value to the connected City
 * 
 * The minimum distance from one City to another City can be calculated, and the shortest path
 * returned as a 2-dimensional array of source and destination cities and the distances between them
 * 
 * @author Zach Graham
 */
public class MapQuestEmulator {
	
	private CityList cities; // A list of all the cities in this graph
	
	/**
	 * Default constructor that initializes a new empty CityGraph
	 */
	public MapQuestEmulator() {
		
		cities = new CityList();
	}
	
	/**
	 * Add a new city to the graph
	 * 
	 * Specify the city name and a new City will be constructed with that name
	 * 
	 * @param cityName the city name to set
	 * @return true if add successful, false if not
	 */
	public boolean addCity(String cityName) {
		
		if (!cities.contains(cityName)) {
			
			cities.add(new City(cityName));
			return true;
		}
		
		return false;
	}
	
	/**
	 * Removes a city from the graph
	 * 
	 * If the list of cities in this graph contains the parameter City, it is removed from the list
	 * 
	 * If the removal was successful, true is returned, else false is returned
	 * 
	 * @param cityName the name of the city to remove
	 * @return true if the removal was successful, false if not
	 */
	public boolean removeCity(String cityName) {
		
		City city = cities.getCityObjectFromString(cityName);
		
		if (cities.contains(city)) {
			
			//for (Connection cxn : city.getConnectedCities()) {
				
			//	cxn.getCity().delConnection(city);
			//}
			
			cities.remove(cities.getCityObjectFromString(cityName));
			return true;
		}
		
		return false;	
	}
	
	/**
	 * Creates a connection between two cities, with a distance value
	 * 
	 * @param city the source city
	 * @param connection the destination city to connect
	 * @param distance the distance between the two cities
	 * @return true if operation successful, false if unsuccessful
	 */
	public boolean addConnectionToCity (String city, String connection, float distance) {
		
		City city1 = cities.getCityObjectFromString(city);
		City city2 = cities.getCityObjectFromString(connection); // Need to check these if null
		
		if (city1 != null && city2 != null) {
			
			city1.addConnection(city2, distance);
			city2.addConnection(city1, distance);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Removes a connection between two cities
	 * 
	 * @param city the city to remove the connection from
	 * @param connection the connected city to disconnect
	 * @return true if deletion successful, false if unsuccessful
	 */
	public boolean delConnectionFromCity (String city, String connection) {
		
		City city1 = cities.getCityObjectFromString(city);
		City city2 = cities.getCityObjectFromString(connection); // Need to check these if null
		
		if (city1 != null && city2 != null) {
			
			boolean result1 = city1.delConnection(city2);
			boolean result2 = city2.delConnection(city1);	
			
			if (result1 == true  && result2 == true) {
				
				return true;
			}
			
			if (result1 == false  && result2 == false) {
				
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * Searches the given City's list of connections, and returns true if the City has a given connection
	 * 
	 * @param city the City to check
	 * @param connection the connected City 
	 * @return true if cities are connected, false if not
	 */
	public boolean cityHasConnection(String city, String connection) {
		
		City city1 = cities.getCityObjectFromString(city);
		
		return (city1.getConnectedCities().contains(connection));
	}
	
	/**
	 * Calculates the minimum path distance from one city in the graph to another
	 * 
	 * The result is returned as 2D array of Strings, which consists of a list of the path taken and the 
	 * distance required at each step of the path
	 * 
	 * @param city1 the starting City name
	 * @param city2 the destination City name
	 * @return 2D array of minimum distance path
	 */
	public String[][] getMinimumDistanceBetweenCities(String city1, String city2) {
		
		/*
		 * Each row of the returned array consists of a list of source cities, destination cities, and distances between them
		 * 
		 * ***The last row of the 2d array stores the total minimum distance from city1 to city2***
		 * 
		 * For the search "Eyota" to "Byron", the returned array should look like:
		 * 
		 * |city1_name|city2_name|distance_between_two|
		 * --------------------------------------------
		 * |Eyota     |Rochester |10.0                |
		 * |Rochester |Byron     |12.0                |
		 * |Eyota     |Byron     |22.0                |            
		 *
		 */
		City endCity = cities.getCityObjectFromString(city2);
		
		computePaths(city1);
		
		ArrayList<City> shortestPath = getShortestPathTo(city2);
		
		String[][] pathArray = new String[shortestPath.size()][3];
		
		int i;
		
		for (i = 0; i < shortestPath.size(); i++) {
			
			if (shortestPath.get(i) != null && i+1 < shortestPath.size()) {
				
				String distanceFromTo = String.format("%.2f",
										(shortestPath.get(i+1).getMinDistance() - shortestPath.get(i).getMinDistance()));
				
				pathArray[i][0] = shortestPath.get(i).getName();
				pathArray[i][1] = shortestPath.get(i+1).getName();
				pathArray[i][2] = String.valueOf(distanceFromTo);
			}
		}

		pathArray[i-1][0]  = city1;
		pathArray[i-1][1] = city2;
		pathArray[i-1][2] = String.valueOf(String.format("%.2f", endCity.getMinDistance()));
		
		return pathArray;
		
	}
	
	/**
	 * Gets the list of cities in this graph
	 * 
	 * @return list of cities in the graph
	 */
	public CityList getCityList() {
		
		return cities;
	}
	
	
	/**
	 * Helper method, used to calculate the minimum distance path
	 * 
	 * @param startCityName the name of the city we are starting from
	 */
	private void computePaths(String startCityName) {
		
		City startCity = cities.getCityObjectFromString(startCityName);
		
		startCity.setMinDistance(0.0f);
		
		PriorityQueue<City> cityQueue = new PriorityQueue<City>();
		
		cityQueue.add(startCity);
		
		while (!cityQueue.isEmpty()) {
			
			City c = cityQueue.poll();
			
			for (Connection connectedCity : c.getConnectedCities()) {

				City city = connectedCity.getCity();
				
				float distance = connectedCity.getDistance();
				
				float distanceThruC = c.getMinDistance() + distance;
				
				if (distanceThruC < city.getMinDistance()) {
					
					cityQueue.remove(city);
					city.setMinDistance(distanceThruC);
					city.setPrevious(c);
					cityQueue.add(city);
				}
			}
		}
	
	}
	
	/**
	 * Helper method to get the shortest path to a target city
	 * 
	 * Returns a list of the Cities visited in the shortest path
	 * 
	 * @param targetCity the name of the target City
	 * @return List of cities on the shortest path to the destination City
	 */
	private ArrayList<City> getShortestPathTo(String targetCity) {
		
		City target = cities.getCityObjectFromString(targetCity);
		
		ArrayList<City> outputList = new ArrayList<City>();
		
	    for (City vertex = target; vertex != null; vertex = vertex.getPrevious()) {
	    	
	    	outputList.add(vertex);
	    }
	    
	    Collections.reverse(outputList);
	    
	    return outputList;
	    
	}
	
} // End class MapQuestEmulator
