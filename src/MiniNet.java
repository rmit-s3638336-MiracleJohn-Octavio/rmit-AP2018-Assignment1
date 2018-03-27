/**
 * @author miraclejohnoctaviojr
 *
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MiniNet {
	
// ---------- Variables
	
	// Object 
	private static MyLibrary _myLib = new MyLibrary();				// User Defined Methods and Functions placed in one Class
	private static MyLibrary.MyMenu _myMenu = _myLib.new MyMenu();
	private static MyLibrary.MySystem _mySys = _myLib.new MySystem();
	private static MyLibrary.MyString _myStr = _myLib.new MyString();
	
	private static Map<String, Person> _mapPerson = new HashMap<>();
	
	// Array
	static String _arrMenuOption_Main[]= {
		"[ 1 ] - List All",
		"[ 2 ] - Add a Person",
		"[ 3 ] - Load Data (For Testing Only)",
		"[ x ] - Select a Person",
		"[ x ] - Select a Friend",
		"~",
		"[ 0 ] - Exit"	
	};
	static String _arrMenuOption_YesNo[]= {
			"[ 1 ] - Yes",			
			"[ 0 ] - No"
			};
	
	// Constants
	static final String DATAPATH_ADULT = "src/Adult_Test.txt";
	static final String DATAPATH_DEPENDENT = "src/Dependent_Test.txt";
	
	// DELETEME
	static final String DATAPATH_ADULT_TEST = "src/Adult_Test.txt";
	static final String DATAPATH_DEPENDENT_TEST = "src/Dependent_Test.txt";
	
// ---------- Data
	
	/*
	 * Assuming we have the following Person Data with the following
	 * format:
	 * ----------------------------
	 * Father
	 * Mother
	 *    Dependent 1	  				
	 *    Dependent 2
	 *    Dependent 3
	 * ----------------------------
	 * Mark
	 * Elise
	 * 		Mitch
	 * ----------------------------
	 * Andrew
	 * Mariah
	 * 		Mike
	 * 		Jenny
	 * 		Cindy (Baby)
	 * ----------------------------
	 * Ron
	 * Sherly
	 * 		Matheo
	 * 		Rita (Baby)
	 * ----------------------------
	 * Greg
	 * Leah
	 * 		Betty
	 * 		Don
	 * 
	 */
	
// ---------- Main Menu	
	
	/**
	 * Main Method - This is where it all starts
	 * @param args
	 */
	public static void main(String[] args) {
		// Local Variables
	    int intChoice;
	    boolean blnIsExitLoop;
	    
	    // Load File to HashMap
	    loadFileToHashMap();
	    
	    //Begin Infinite Loop
	    while (true) {
		    	// Reset 
		    	blnIsExitLoop  = false;	
		    			    	
		    	// Display the Main Menu	 
		    	intChoice = _myMenu.getMenuInput("Select option to execute..", _arrMenuOption_Main);
		    	
		    	// Choices
		    	switch (intChoice) {
		    		case 1:
		    			displayHashMap();
		            break;
		    		case 2:
		    			addPerson();
		            break;
		    		case 3:
		    			loadFileToHashMap();
		    			break;
		    		case 0:
		    			if (isExitApp()) {
			        		// Exit the Loop
			        		blnIsExitLoop = true;
			        	}
			        	break;
		    		default:
			        	// Display invalid key choice
		    			_myMenu.displayMessagePrompt("You have typed in an invalid code!", true);
		    	}
		    	
		    	if (blnIsExitLoop) { 
		    		// Exit the loop
		    		break;	    		    		 	
		    	}
	    }
	}
	
	/**
	 * Add a person in the Network 
	 */
	private static void addPerson() {
		
		// Local Variables
		@SuppressWarnings("resource")
		Scanner objScanner = new Scanner(System.in);
		
		String strId;
		String strName;
		String strPhoto;
		String strStatus;
		int intAge;
		String strType;
		
		// Screen
		_myMenu.displayMenuHeader("Add a person");
		
		// Data Entry
		_mySys.printIt("Enter Id: ", false);
		strId = objScanner.nextLine();
		_mySys.printIt("Enter Name: ", false);
		strName = objScanner.nextLine();
		strPhoto = strName + ".jpg";
		_mySys.printIt("Enter Status: ", false);
		strStatus = objScanner.nextLine();
		while (true) {
			_mySys.printIt("Enter Age: ", false);		
			if (objScanner.hasNextInt()) {
				intAge = objScanner.nextInt();
				break;
			} else {
				objScanner.nextLine();
				_myMenu.displayMessagePrompt("Please enter numeric value!", false);
			}
		}
		
		// Confirmation to Save
		if (isSavePerson()) {
			
			// Put Data to Hashmap
			if (intAge >= 16) {
				_mapPerson.put(strId, new Adult(strId, strName, strPhoto, strStatus, intAge)); 
			} else {
				_mapPerson.put(strId, new Dependent(strId, strName, strPhoto, strStatus, intAge));	
			}
			
			// Update the Text File
			loadHashMapToFile();
			
			// Inform the user that the person was added
			_myMenu.displayMessagePrompt("The person was successfully added!", true);
		};
	}
	
	/**
	 * Confirmation to exit the program
	 * 
	 * @return boolean
	 */
	private static boolean isExitApp() {
		
		// Local Variables
		boolean blnReturnValue = false;
		int intChoice;
	    
	    while (true) {
	    	
		    	// Display the Main Menu	 
		    	intChoice = _myMenu.getMenuInput("Do you want to EXIT MiniNet?", _arrMenuOption_YesNo);
		    	
		    	// Choices	    	
		    	if (intChoice == 1) {
		    		// If the user confirmed to exit (Noo!)
		    		_myMenu.displayMessageBox("Thank you for using MiniNet!!!", true);	    		
		    		blnReturnValue = true;
		    		break;
		    	} else if (intChoice == 0) {
		    		// If the user decided not to exit the program (Yey!)
		    		blnReturnValue = false;
		    		break;
		    	} else {
		    		// Display invalid key choice
		    		_myMenu.displayMessagePrompt("You have selected an invalid choice!", true);	    		
		    	}	    	
	    }
	    
	    // Return the value
		return blnReturnValue;
		
	}

	
// ---------- Methods
	
	/**
	 * Load Data from Text File to Hashmap
	 */
	private static void loadFileToHashMap() {
		
		Scanner objScanner;		
		String strId;		
        String strName;	
        String strPhoto;	
        String strStatus;	
        int intAge;
		
		try {
			
			// Read the file (Must be enclosed with try catch)
			objScanner = new Scanner(new FileReader(DATAPATH_ADULT));

			// Read the lines from Text File
		    while (objScanner.hasNextLine()) {
		    		// Get the line and convert it to array
		        String[] columns = objScanner.nextLine().split(",");
		        	
		        // Put the individual column value to variable
		        strId = columns[0].trim();		
		        strName = columns[1].trim();	
		        strPhoto = columns[2].trim();	
		        strStatus = columns[3].trim();	
		        intAge = Integer.valueOf(columns[4].trim());	

		        // Put Data to Hashmap
		        _mapPerson.put(strId, new Adult(strId, strName, strPhoto, strStatus, intAge));
		    }
		    
		    // Read the file (Must be enclosed with try catch)
 			objScanner = new Scanner(new FileReader(DATAPATH_DEPENDENT));

 			// Read the lines from Text File
 		    while (objScanner.hasNextLine()) {
 		    		// Get the line and convert it to array
 		        String[] columns = objScanner.nextLine().split(",");
 		        	
 		        // Put the individual column value to variable
 		        strId = columns[0].trim();		
 		        strName = columns[1].trim();	
 		        strPhoto = columns[2].trim();	
 		        strStatus = columns[3].trim();	
 		        intAge = Integer.valueOf(columns[4].trim());	

 		        // Put Data to Hashmap
 		        _mapPerson.put(strId, new Dependent(strId, strName, strPhoto, strStatus, intAge));
 		    }
 		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Load Data from Hashmap to Text File 
	 */
	private static void loadHashMapToFile_Old() {
		
		String strDelimitedAdult = "";
		String strDelimitedDependent = "";
		
		// Iterate thru HashMap
		for (Map.Entry<String, Person> entry : _mapPerson.entrySet()) {
			Person obj = entry.getValue();
		    
			// Store Data to Delimited String
			if (obj.getAge() >= 16) {
				strDelimitedAdult +=
					(strDelimitedAdult == "" ? "" : "\n") +
					obj.getId().trim() + ", " +
	    				obj.getName().trim() + ", " +
	    				obj.getPhoto().trim() + ", " +
	    				obj.getStatus().trim() + ", " +
	    				Integer.toString(obj.getAge()).trim();
			} else {
				strDelimitedDependent += 
					(strDelimitedDependent == "" ? "" : "\n") +
					obj.getId().trim() + ", " +
	    				obj.getName().trim() + ", " +
	    				obj.getPhoto().trim() + ", " +
	    				obj.getStatus().trim() + ", " +
	    				Integer.toString(obj.getAge()).trim();
			}
		}
		
		// Write to File
		_mySys.writeDataToFile(strDelimitedAdult, DATAPATH_ADULT_TEST);
		_mySys.writeDataToFile(strDelimitedDependent, DATAPATH_DEPENDENT_TEST);
	}
	
	/**
	 * Load Data from Hashmap to Text File 
	 */
	private static void loadHashMapToFile() {
		
		String strDelimitedAdult = "";
		String strDelimitedDependent = "";
		
		// Iterate thru HashMap
		for (Map.Entry<String, Person> entry : _mapPerson.entrySet()) {
			Person obj = entry.getValue();
		    
			// Store Data to Delimited String
			if (obj.getAge() >= 16) {
				strDelimitedAdult +=
					(strDelimitedAdult == "" ? "" : "\n") +
					obj.getId().trim() + ", " +
	    				obj.getName().trim() + ", " +
	    				obj.getPhoto().trim() + ", " +
	    				obj.getStatus().trim() + ", " +
	    				Integer.toString(obj.getAge()).trim();
			} else {
				strDelimitedDependent += 
					(strDelimitedDependent == "" ? "" : "\n") +
					obj.getId().trim() + ", " +
	    				obj.getName().trim() + ", " +
	    				obj.getPhoto().trim() + ", " +
	    				obj.getStatus().trim() + ", " +
	    				Integer.toString(obj.getAge()).trim();
			}
		}
		
		// ---
		
		// Display the Hashmap (Sorted)
	    Collection<Person> Person = _mapPerson.values();
	    List<Person> list = new ArrayList<>(Person);
	    Collections.sort(list, new comparatorPersonName());

	    for (Iterator<Person> it = list.iterator(); it.hasNext();) {
	    		Person p = (Person) it.next();
	    		
	    		// Store Data to Delimited String
			if (p.getAge() >= 16) {
				strDelimitedAdult +=
					(strDelimitedAdult == "" ? "" : "\n") +
					p.getId().trim() + ", " +
	    				p.getName().trim() + ", " +
	    				p.getPhoto().trim() + ", " +
	    				p.getStatus().trim() + ", " +
	    				Integer.toString(p.getAge()).trim();
			} else {
				strDelimitedDependent += 
					(strDelimitedDependent == "" ? "" : "\n") +
					p.getId().trim() + ", " +
	    				p.getName().trim() + ", " +
	    				p.getPhoto().trim() + ", " +
	    				p.getStatus().trim() + ", " +
	    				Integer.toString(p.getAge()).trim();
			}
	    }
		
		// Write to File
		_mySys.writeDataToFile(strDelimitedAdult, DATAPATH_ADULT_TEST);
		_mySys.writeDataToFile(strDelimitedDependent, DATAPATH_DEPENDENT_TEST);
	}
	
	/**
	 * Display records from a HashMap
	 */
	private static void displayHashMap() {
		
		// Display Column Header
		int intWidthLenght = 97;
		_myMenu.clearScreen();
		_myMenu.displaySeparator(intWidthLenght);
		_mySys.printIt("| " + _myStr.padRight("List of all person",intWidthLenght-4) + " |");
		_myMenu.displaySeparator(intWidthLenght);
		_mySys.printIt(
				"| " + 
				_myStr.padRight("Id",4) + "  " +
				_myStr.padRight("Name",15) + "  " +
				_myStr.padRight("Photo",20) + "  " +
				_myStr.padRight("Status",30) + "  " +
				_myStr.padRight("Age",3) + "  " +
				_myStr.padRight("Type",10) + 
				"  |"
				);
		_myMenu.displaySeparator(intWidthLenght);
		
		// Display the Hashmap (Sorted)
	    Collection<Person> Person = _mapPerson.values();
	    List<Person> list = new ArrayList<>(Person);
	    Collections.sort(list, new comparatorPersonName());

	    for (Iterator<Person> it = list.iterator(); it.hasNext();) {
	    		Person p = (Person) it.next();
	    		
	    		// Check the instance if Adult or Child
	    		String strType = "Dependent";
	    		if (p instanceof Adult) {
	    			strType = "Adult";
			}; 
			
			// Display the Data
	    		_mySys.printIt(
	    				"| " + 
	    				_myStr.padRight(p.getId(),4) + "  " +
	    				_myStr.padRight(p.getName(),15) + "  " +
	    				_myStr.padRight(p.getPhoto(),20) + "  " +
	    				_myStr.padRight(p.getStatus(),30) + "  " +
	    				_myStr.padRight(Integer.toString(p.getAge()),3) + "  " +
	    				_myStr.padRight(strType,10) + 
	    				"  |"
	    				);
	    }
	    _myMenu.displaySeparator(intWidthLenght);
	    
	    // Pause
	    _mySys.pressAnyKey("Press <Enter> key to go back to Main Menu!");
	    
	}
	
	
	/**
	 * Confirm to save the person to text file
	 */
	private static boolean isSavePerson() {
		
		// Local Variables
		boolean blnReturnValue = false;
		int intChoice;
	    
	    while (true) {
	    	
		    	// Display the Main Menu	 
		    	intChoice = _myMenu.getMenuInput("Do you want to SAVE this record?", _arrMenuOption_YesNo);
		    	
		    	// Choices	    	
		    	if (intChoice == 1) {
		    		// If the user confirmed to the record 
		    		blnReturnValue = true;
		    		break;
		    	} else if (intChoice == 0) {
		    		// If the user decided not to save the record -- just exit
		    		break;
		    	} else {
		    		// Display invalid key choice
		    		_myMenu.displayMessagePrompt("You have selected an invalid choice!", true);	    		
		    	}	    	
	    }
	    
	    return blnReturnValue;
	
	}
}

class comparatorPersonName implements Comparator<Person>{
	 
    @Override
    public int compare(Person p1, Person p2) {
        return p1.getName().compareTo(p2.getName());
    }
}
