/*
 * Name:		Zach Graham
 * Class:		CS 341
 * Year:		Fall 2013
 * Assignment:	Final Assignment
 */

package mapquest_emulator;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import mapquest_emulator.includes.*;

/**
 * A test case for the MapQuestEmulator
 * 
 * @author Zach Graham
 *
 */
public class TestCase {
	
	// This line should auto generate the CSV file path, but if for some reason it fails, 
	// Uncomment the next line with a hardcoded String file path and edit to reflect the CSV filepath
	// Then comment this line
	private static final String CSV_FILE_PATH = System.getProperty("user.dir") + "/src/mapquest_emulator/includes/cities.csv";
	//private static final String CSV_FILE_PATH = "C:\eclipse\workspace\CS-341-final\src\mapquest_emulator\includes\cities.csv";
	
	MapQuestEmulator cityGraph;
	
	ArrayList<String> cities;
	ArrayList<ArrayList<String>> connections;
	
	/**
	 * Default constructor
	 * 
	 * Performs the test case
	 */
	public TestCase() {
		
		cities = new ArrayList<String>();
		connections = new ArrayList<ArrayList<String>>();
		
		
		System.out.println("-- Weighted Graph Test Case --\n");
		
		System.out.println("- Instantiating a new weighted graph -\n");
		
		cityGraph = new MapQuestEmulator();
		
		System.out.printf("- The graph should be empty. Is it? %b -%n%n", cityGraph.getCityList().size() == 0);
		
		System.out.printf("- Now adding cities and connections to the graph while storing a copy of each in an array -%n%n");
		
		this.loadCSV(CSV_FILE_PATH);
		
		System.out.printf("%n- Now verifying that the cities were truly added to the graph by comparing to the array -%n%n");
		
		int i = 0;
		for (City city : cityGraph.getCityList()) {
			
			String cityName = city.getName();
			
			System.out.printf("%s = %s? %b%n", cityName, cities.get(i), cityName.equals(cities.get(i)));
			i++;
			
		}
		
		System.out.printf("%n- Testing the getCityList() method to print each city name in the graph -%n%n");
		
		for (City city : cityGraph.getCityList()) {
			
			System.out.println(city.getName());
		}
		
		System.out.println("\n- Now verifying that all of the connections were added properly with cityHasConnection() method -\n");
		
		for (int j = 0; j < connections.size(); j++) {
			
			String city1 = connections.get(j).get(0);
			String city2 = connections.get(j).get(1);
			
			System.out.printf("%s supposed to have connection to %s, does it? %b%n", 
								city1, city2, cityGraph.cityHasConnection(city1, city2));
		}
		
		if (cityGraph.getCityList().contains("Elgin") && cityGraph.getCityList().contains("Wilson")) {
			
			System.out.printf("%n%nNow testing the getMinimumDistanceBetweenCities() method with Elgin -> Wilson (Should be 41.93)%n");
			
			String[][] minDistanceResultSet = cityGraph.getMinimumDistanceBetweenCities("Elgin", "Wilson");
			
			for (int k = 0; k < minDistanceResultSet.length; k++) {
				
				if (k != minDistanceResultSet.length-1) {
					
					System.out.println(minDistanceResultSet[k][0] + " -> " + minDistanceResultSet[k][1] + " = " + minDistanceResultSet[k][2]);
				}
				
				else {
					
					System.out.printf("%nTotal: " + minDistanceResultSet[k][0] + " -> " + minDistanceResultSet[k][1] + " = " + minDistanceResultSet[k][2]);
				}
				
			}
		}
		
		System.out.printf("%n%n- Now deleting all connections from all cities with the delConnectionFromCity() method -%n%n");
		
		for (int k = 0; k < connections.size(); k++) {
			
			String city1 = connections.get(k).get(0);
			String city2 = connections.get(k).get(1);
			
			String padding = "\t\t\t";
			
			if (city1.length() >= 11 || city2.length() >= 11) {
				
				padding = "\t\t";
			}
			
			System.out.printf("Removing connection '%s' from '%s'%sSuccess?\t%b%n", city1, city2, padding, cityGraph.delConnectionFromCity(city1, city2));
			
		}
		
		System.out.printf("%n- Now deleting all cities from the graph with removeCity() method -%n%n");
		
		for (int k = 0; k < cities.size(); k++) {
			
			String city1 = cities.get(k);
			
			String padding = "\t\t";
			
			if (city1.length() >= 8) {
				
				padding = "\t";
			}
			
			System.out.printf("Removing city '%s' from the graph.%sSuccess?\t%b%n", city1, padding, cityGraph.removeCity(city1));
			
		}
		
		System.out.printf("%n- The graph should now be empty.  Is it? %b -%n%n", cityGraph.getCityList().size() == 0);
		
		System.out.printf("%n- Test case completed -");
	}
	
	/**
	 * Given a String file path, attempts to load a CSV file at the specified file path
	 * 
	 * Then, adds each city and connection in the CSV file to the CityGraph
	 * 
	 * CSV file must be in this format:
	 * 
	 * |sourceCity |destinationCity |(float) distance |
	 * 
	 * @param filePath the full path location of the CSV file
	 */
	public void loadCSV(String filePath) {
		
		BufferedReader br = null;
		String line = "";
		
		try {
			 
			br = new BufferedReader(new FileReader(filePath.toString()));
			
			br.readLine(); 										// Skip the first line (header)
			

			System.out.println("Loading CSV file...");
			
			while ((line = br.readLine()) != null) {
	 
				String[] connection = line.split(",");
	 
				System.out.println("City: " + connection[0] 
	                                 + ", Connection: " + connection[1] + ", Distance: " + connection[2]);
				
				cityGraph.addCity(connection[0]);
				cityGraph.addCity(connection[1]);
				
				cityGraph.addConnectionToCity(connection[0], connection[1], Float.parseFloat(connection[2]));
				
				// Now we add a copy to our ArrayList for verifying
				ArrayList<String> arrayCopy = new ArrayList<String>();
				
				arrayCopy.add(connection[0]);
				arrayCopy.add(connection[1]);
				arrayCopy.add(connection[2]);
				
				connections.add(arrayCopy);
				
	 
			}
			
			for (City city : cityGraph.getCityList()) {
				
				cities.add(city.getName());
			}
	 
		} catch (FileNotFoundException e) {
			//updateLogArea("CSV file could not be loaded. Ensure file path is set properly");
			System.out.println("CSV file could not be loaded. Ensure file path is set properly");	
			
			
		} catch (IOException e) {

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {

				}
			}
		} 
	}

	/**
	 * Launches a new test case
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		
		TestCase testCase = new TestCase();
	}

}
