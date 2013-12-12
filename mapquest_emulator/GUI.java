/*
 * Name:		Zach Graham
 * Class:		CS 341
 * Year:		Fall 2013
 * Assignment:	Final Assignment
 */
package mapquest_emulator;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import mapquest_emulator.includes.*;

/**
 * Simulation of a MapQuest 'minimum distance between cities' calculator
 * 
 * Auto Loads a CSV file, from the filepath specified by the named constant CSV_FILE_PATH
 * 
 * Each city has a connection to another city, and a mileage distance between the two
 * 
 * Then displays a Graphical User Interface, providing the user with selections of source and destination cities
 * 
 * Upon a button click, the shortest path from the source city to the destination city is calculated and displayed
 * 
 * As well as the total minimum distance from the start city to the destination city
 * 
 * @author Zach Graham
 *
 */
public class GUI extends JFrame
{	
	// This line should auto generate the CSV file path, but if for some reason it fails, 
	// Uncomment the next line with a hardcoded String file path and edit to reflect the CSV filepath
	// Then comment this line
	private static final String CSV_FILE_PATH = System.getProperty("user.dir") + "/src/mapquest_emulator/includes/cities.csv";
	//private static final String CSV_FILE_PATH = "C:\eclipse\workspace\CS-341-final\src\mapquest_emulator\includes\cities.csv";
	
	private static final long serialVersionUID = 1L; // Adding this gets rid of the warning Eclipse gives..
	
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 480;
	
	private static final String PADDING = "\t  ";
	
	private ButtonHandler buttonHandler;
	
	private JPanel leftPanel, rightPanel, citySelectionPanel, buttonPanel;
	
	private JLabel sourceLabel, destinationLabel;
	
	private JList sourceCityList, destinationCityList;
	
	private DefaultListModel sourceCityListModel, destinationCityListModel;
	
	private JButton getDistanceButton, exitButton;
	
	private JTextArea distanceOutputArea;
	
	private JScrollPane outputAreaScroller, sourceCityScroller, destinationCityScroller;

	private MapQuestEmulator cityGraph;
	
	/**
	 * Default Constructor
	 * 
	 * Initializes the GUI components and loads the CSV file
	 */
	GUI()
	{
		this.getContentPane();
		this.setTitle("MapQuest Emulator - By Zach");
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
		
		cityGraph = new MapQuestEmulator();
		
		buttonHandler = new ButtonHandler();
		
		distanceOutputArea = new JTextArea();
		distanceOutputArea.setEditable(false);
		distanceOutputArea.setBackground(Color.lightGray);
		distanceOutputArea.setBorder(new EmptyBorder(-15, 5, 0, 0));
		distanceOutputArea.setLineWrap(true);
		distanceOutputArea.setWrapStyleWord(true);
		distanceOutputArea.setText(String.format("%nWelcome to the MapQuest Emulator%n%n" +
												 "Calculate the minimum path distance from one city to another%n%n" +
												 "To begin simply select your source and destination cities and click 'Get Distance'%n%n" +
												 "The result will be then shown in this window"));
		
		outputAreaScroller = new JScrollPane(distanceOutputArea);
		
		sourceCityListModel = new DefaultListModel();
		destinationCityListModel = new DefaultListModel();
		
		sourceCityList = new JList(sourceCityListModel);
		destinationCityList = new JList(destinationCityListModel);
		
		sourceCityList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		sourceCityList.setLayoutOrientation(JList.VERTICAL);
		
		destinationCityList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		destinationCityList.setLayoutOrientation(JList.VERTICAL);
		
		sourceCityScroller = new JScrollPane(sourceCityList);
		destinationCityScroller = new JScrollPane(destinationCityList);

		getDistanceButton = new JButton("Get Distance");
		getDistanceButton.addActionListener(buttonHandler);
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(buttonHandler);
		
		
		leftPanel = new JPanel();	
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setPreferredSize(new Dimension(360, WINDOW_HEIGHT));
		
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		citySelectionPanel = new JPanel(new GridLayout(0,2,0,0));
		
		sourceLabel = new JLabel("Source");
		
		destinationLabel = new JLabel("Destination");
		
		JPanel citySelectionLabelPanel = new JPanel();
		citySelectionLabelPanel.setLayout(new BoxLayout(citySelectionLabelPanel, BoxLayout.X_AXIS));
		
		citySelectionLabelPanel.add(sourceLabel); 
		citySelectionLabelPanel.add(new JLabel("                    ")); 				// For padding...
		citySelectionLabelPanel.add(new JLabel("                    "));
		citySelectionLabelPanel.add(destinationLabel);
		
		citySelectionPanel.add(sourceCityScroller);
		citySelectionPanel.add(destinationCityScroller);
		
		buttonPanel = new JPanel();
		buttonPanel.add(getDistanceButton);
		buttonPanel.add(exitButton);
		
		leftPanel.add(outputAreaScroller);
		rightPanel.add(citySelectionLabelPanel);
		rightPanel.add(citySelectionPanel);
		rightPanel.add(buttonPanel);
		
		
		this.add(leftPanel);
		this.add(rightPanel);
		this.setVisible(true);
		
		this.loadCSV(CSV_FILE_PATH);
		
		for (City city : cityGraph.getCityList()) {
			
			sourceCityListModel.addElement(city.getName());
			
			destinationCityListModel.addElement(city.getName());
			
		}
		
		System.out.println("CSV loaded! Launching GUI...");

	}
	
	/**
	 * Updates the output area with a new entry
	 * 
	 * @param logItem the log entry
	 */
	private void updateLogArea(String logItem)
	{
		distanceOutputArea.setText("\n" + logItem);
	}
	
	/**
	 * Handler for the buttons
	 * @author Zach Graham
	 *
	 */
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == getDistanceButton) {
				
				String sourceCity = (String) sourceCityList.getSelectedValue();
				String destCity = (String) destinationCityList.getSelectedValue();
				
				if (sourceCity != null && destCity != null) {
					
					if (sourceCity.equals(destCity)) {
						
						updateLogArea("Source city is same as destination city\n\nSelect another source or destination city");
						
					}
					
					else {
						
						outputMinimumDistancePath(sourceCity, destCity);
						
						// Reset the city nodes
						for (City city : cityGraph.getCityList()) {
							
							city.setMinDistance(Float.POSITIVE_INFINITY);
							city.setPrevious(null);
						}
					}
					
				}
				else {
					
					updateLogArea("Please select a source and destination city");
				}
			}
			
			if(e.getSource() == exitButton)
			{
				System.exit(1);
			}
			
		}
	}
	
	
	
	/**
	 * Gets the minimum distance path from sourceCity to destCity from the cityGraph, and then
	 * outputs the results to the output area
	 * 
	 * @param sourceCity the source city
	 * @param destCity the destination city
	 */
	public void outputMinimumDistancePath(String sourceCity, String destCity) {
		
		String[][] result = cityGraph.getMinimumDistanceBetweenCities(sourceCity, destCity);
		
		String padding = PADDING;
		
		
		String outputString = String.format("- Shortest Path -\n-----------------------------------\n" + 
							  			    "Source%sDestination%sDistance\n\n", padding, padding);
		
		int cityLength = 0;
		
		for (int rows = 0; rows < result.length-1; rows++) {
			
			for (int columns = 0; columns < 3; columns++) {
				
				cityLength = result[rows][columns].length();
					
				if (columns == 0) {
					
					if (cityLength >= 15) { 
						
						padding = "  ";
					}
					
					outputString += String.format("%s%s", result[rows][columns], padding);
					
					padding = PADDING;
					
				}
				
				if (columns == 1) {
					
					if (cityLength >= 15) { 
						
						padding = "  ";
					}
					
					outputString += String.format("%s%s", result[rows][columns], padding);
					
					padding = PADDING;
				}
				
				if (columns == 2) {
					outputString += String.format("%5s miles%n", result[rows][columns]);
				}
			}
		}
		
		if (result[result.length-1][0].length() >= 15 || result[result.length-1][1].length() >= 15) { 
			
			padding = "  ";
		}
		
		outputString += String.format("%n- Total Minimum Distance -%n-----------------------------------%n%s%s%s%s%s miles", 
									  result[result.length-1][0], padding, result[result.length-1][1], padding, result[result.length-1][2]);
		
		updateLogArea(outputString);
		
		
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
	 
			}
	 
		} catch (FileNotFoundException e) {
			updateLogArea("CSV file could not be loaded. Ensure file path is set properly");
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
	 * Instantiates a new MapQuest Emulator GUI
	 * 
	 * @param args not used
	 */
	public static void main(String[] args)
	{
		GUI mapQuestEmulatorGUI = new GUI();
	}
	
}//end class GUI