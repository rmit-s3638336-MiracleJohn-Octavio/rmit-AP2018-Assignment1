/**
 * @author miraclejohnoctaviojr
 *
 * Disclaimer: Java is still new to me and I am still learning
 * more about it. Somehow, I am trying to put functionalities
 * as much as I could to make it efficient. The program specs
 * looks simple but if I dig deeper, the whole process is huge
 * especially if I consider some validations and error traps which
 * would significantly consume my time. Hence, I will stick on
 * the basic requirements to catch up with the deadline.  
 *
 * This simple program is using 2 data files named Adult.txt and 
 * Dependent.txt which is created during "Add a person" operation.
 * 
 * Other methods used are placed on a separate Class named MyLibrary.
 * Inside this class are INNER CLASS that categorized methods 
 * according to its purpose such as MyMenu, MySystem, MyString etc.
 * 
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
	
	private static Map<String, Person> _mapPerson = new HashMap<>();		// This will be loaded at startup using the default Data Text Files
	private static Map<String, Person> _mapFriend = new HashMap<>(); 		// This will only loaded when user have selected a person	
	private static Person _objSelectedPerson;
	
	// Array
	static String _arrMenuOption_Main[];
	static String _arrMenuOption_SelectPerson[]= {
			"[ 0 ] - Back to Main"	
		};
	static String _arrMenuOption_YesNo[]= {
			"[ 1 ] - Yes",			
			"[ 0 ] - No"
			};
	
	// Constants
	static final String DATAPATH_ADULT = "src/Adult.txt";
	static final String DATAPATH_DEPENDENT = "src/Dependent.txt";
	static final int MENU_WIDTH_BIG = 97;
	static final int MENU_WIDTH_SMALL = 55;
	
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
	
// ---------- Main Methods	
	
	/**
	 * Main Method - This is where it all starts
	 * @param args
	 */
	public static void main(String[] args) {
		// Local Variables
	    int intChoice;
	    boolean blnIsExitLoop;
	    String strSelectedPerson = "";
	    
	    // Load File to HashMap
	    loadFileToHashMap();
	    
	    //Begin Infinite Loop
	    while (true) {
		    	// Reset 
		    	blnIsExitLoop  = false;	
		    			    	
		    	// Display the Main Menu
		    	strSelectedPerson = "";
		    	if (_objSelectedPerson != null) {
		    		strSelectedPerson = 
		    			_objSelectedPerson.getId() + " - " + 
					_objSelectedPerson.getName(); 
			}
		    	String arrMenuOption_Main[] = {
		    		"Selected Person: [ " + strSelectedPerson + " ]",
		    		"~",
		    		"[ 1 ] - List all person",
		    		"[ 2 ] - Add a person",
		    		"[ 3 ] - Select a person",
		    		"[ 4 ] - Display profile",
		    		"[ 5 ] - Update profile",
		    		"[ 6 ] - Delete selected person",
		    		"~",
		    		"[ 7 ] - Add a friend",
		    		"[ 8 ] - Add a parent",
		    		"~",
		    		"[ 0 ] - Exit"	
		    	};
		    	_arrMenuOption_Main = arrMenuOption_Main;
		    	intChoice = _myMenu.getMenuInput("Select option to execute..", _arrMenuOption_Main);
		    	
		    	//updateProfile
		    	
		    	// Choices
		    	switch (intChoice) {
		    		case 1:
		    			listAll();		    			
		            break;
		    		case 2:
		    			addPerson();
		            break;
		    		case 3:
		    			selectPerson();
		    			break;
		    		case 4:
		    			displayProfile();
		    			break;
		    		case 5:
		    			updateProfile();
		    			break;
		    		case 6:
		    			deleteSelectedPerson();
		    			break;
		    		case 0:
		    			if (isExitCofirmed()) {
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
	 * Display all person 
	 */
	private static void listAll() {
		// Display records from HashMap
		displayHashMap("List of all person", false);
		// Pause
	    _mySys.pressAnyKey("Press <Enter> key to go back to Main Menu!");
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
		
		// Screen
		_myMenu.displayMenuHeader("Add a person");
		
		// Data Entry
		_mySys.printIt("Enter Id (4 Char Exact): ", false);
		strId = objScanner.nextLine();		
		_mySys.printIt("Enter Name (15 Char Max): ", false);
		strName = objScanner.nextLine();
		strPhoto = strName + ".jpg";
		_mySys.printIt("Enter Status: ", false);
		strStatus = objScanner.nextLine();
		while (true) {
			_mySys.printIt("Enter Age: ", false);		
			if (objScanner.hasNextInt()) {
				intAge = objScanner.nextInt();
				
				// Validate
				if (intAge < 1) {
					_myMenu.displayMessagePrompt("Please enter value greater than 0!", false);	
				} else {
					break;	
				}
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
	 * Select a person from the List
	 */
	private static void selectPerson() {
		
		// Local Variables
		@SuppressWarnings("resource")
		Scanner objScanner = new Scanner(System.in);
		String strSelectedId = "";
		
		// Display records from HashMap
		displayHashMap("Select a person from the list", true);
		// Display Options
		_myMenu.displayMenuOptions(_arrMenuOption_SelectPerson, MENU_WIDTH_BIG);
		_myMenu.displaySeparator(MENU_WIDTH_BIG);
 		
 		// Get User Input
		_mySys.printIt("Type the Person Id of your choice and press <Enter>: ", false);
		strSelectedId = objScanner.nextLine();	
		
		if (strSelectedId == "0") {
			// Do nothing.. Just Exit this method
		} else {
			// Locate the person from the list and put it on 
			// the variable '_objSelectedPerson'
			_objSelectedPerson = getPersonDetails(strSelectedId);
		}
	}
	
	/**
	 * Display the profile of the person selected
	 */
	private static void displayProfile() {
		
		// Local Variables
		String arrMenuOption[];
		
		// Validation
		if (_objSelectedPerson == null) {
			_myMenu.displayMessagePrompt("You need to select a person first!", true);
		} else {
			if (_objSelectedPerson != null) {
		    		arrMenuOption = (
			    		"Id:     " + _objSelectedPerson.getId() + "," + 
			    		"Name:   " + _objSelectedPerson.getName()+ "," +
			    		"Photo:  " + _objSelectedPerson.getPhoto() + "," +
			    		"Status: " + _objSelectedPerson.getStatus() + "," +
			    		"Age:    " + _objSelectedPerson.getAge()).split(",");
			} else {
				arrMenuOption = (
			    		"Id:     ," + 
			    		"Name:   ," +
			    		"Photo:  ," +
			    		"Status: ," +
			    		"Age:    ").split(",");
			}
		    	
		    	// Display the Profile
		    _myMenu.clearScreen();
		    _myMenu.displayMenuHeader("Profile Information");
		    _myMenu.displayMenuOptions(arrMenuOption, MENU_WIDTH_SMALL);
		    _myMenu.displaySeparator();
		    	
		    	// Pause
		    _mySys.pressAnyKey("Press <Enter> key to go back to Main Menu!");
		}
		
	}
	
	/**
	 * Update the profile information of the person selected
	 */
	private static void updateProfile() {

		// Local Variables
		String arrMenuOption[];
		
		@SuppressWarnings("resource")
		Scanner objScanner = new Scanner(System.in);
		
		// -- For Data Entry 
		String strId;
		String strName;
		String strPhoto;
		String strStatus;
		int intAge;
		
		// -- For Storing Original Value 
		String strId_Orig;
		String strName_Orig;
		String strPhoto_Orig;
		String strStatus_Orig;
		int intAge_Orig;


// --- Display current value
		
		// Validation
		if (_objSelectedPerson == null) {
			_myMenu.displayMessagePrompt("You need to select a person first!", true);
		} else {
			
			// Get the ID
			strId = _objSelectedPerson.getId();
			
			// Get the original value
			strId_Orig = _objSelectedPerson.getId();
			strName_Orig = _objSelectedPerson.getName();
			strPhoto_Orig = _objSelectedPerson.getPhoto();
			strStatus_Orig = _objSelectedPerson.getStatus();
			intAge_Orig = _objSelectedPerson.getAge();
						
			arrMenuOption = 
				(
		    		"Id:     " + _objSelectedPerson.getId() + " (Read Only)," + 
		    		"Name:   " + _objSelectedPerson.getName()+ "," +
		    		"Photo:  " + _objSelectedPerson.getPhoto() + "," +
		    		"Status: " + _objSelectedPerson.getStatus() + "," +
		    		"Age:    " + _objSelectedPerson.getAge()  + "," +
		    		"~" + "," +
		    		"Enter new value to change or leave it blank to " + "," +
		    		"remain as is."
		    		).split(",");
		    	
		    	// Display the Profile
		    _myMenu.clearScreen();
		    _myMenu.displayMenuHeader("Current Profile Information");
		    _myMenu.displayMenuOptions(arrMenuOption, MENU_WIDTH_SMALL);
		    _myMenu.displaySeparator();
		    
// --- Data Entry
		    		    		
			_mySys.printIt("Enter Name (15 Char Max): ", false);
			strName = objScanner.nextLine();
			strPhoto = strName + ".jpg";
			_mySys.printIt("Enter Status: ", false);
			strStatus = objScanner.nextLine();
			while (true) {
				_mySys.printIt("Enter Age: ", false);		
				if (objScanner.hasNextInt()) {
					intAge = objScanner.nextInt();
					
					// Validate
					if (intAge < 1) {
						_myMenu.displayMessagePrompt("Please enter value greater than 0!", false);	
					} else {
						break;	
					}
				} else {
					objScanner.nextLine();
					_myMenu.displayMessagePrompt("Please enter numeric value!", false);
				}
			}
			
			// Confirmation to Save
			if (isSavePerson()) {
				
				// Check if value is empty
				if (strName.equals("")) {
					strName = strName_Orig;		
					strPhoto = strName + ".jpg";
				}
				if (strStatus.equals("")) {
					strStatus = strStatus_Orig;
				}
				
				// Put Data to Hashmap
				if (intAge >= 16) {
					_mapPerson.put(strId, new Adult(strId, strName, strPhoto, strStatus, intAge)); 
				} else {
					_mapPerson.put(strId, new Dependent(strId, strName, strPhoto, strStatus, intAge));	
				}
				
				// Update the Text File
				loadHashMapToFile();
				
				// Locate the person from the list and put it on 
				// the variable '_objSelectedPerson'
				_objSelectedPerson = getPersonDetails(strId);
				
				// Inform the user that the person was added
				_myMenu.displayMessagePrompt("The profile was successfully updated!", true);
			};
		}
	}
	
	/**
	 * Delete selected person -- including the data from text file 
	 */
	private static void deleteSelectedPerson() {
		
		// Validation
		if (_objSelectedPerson == null) {
			_myMenu.displayMessagePrompt("You need to select a person first!", true);
		} else {
			if (isDeleteAPersonCofirmed()) {
				// Remove the selected person from HashMap
				_mapPerson.remove(_objSelectedPerson.getId());
				_objSelectedPerson = null;
				
				// Update the Text File using HashMap
				loadHashMapToFile();
			} else {
		    		// Display aborted process
		    		_myMenu.displayMessagePrompt("You have aborted the DELETE operation!", true);	    		

			}
		}
	}
	
	private void displayFriend() {
		
		// Local Variables
		@SuppressWarnings("resource")
		Scanner objScanner = new Scanner(System.in);
		String strSelectedFriend = "";
		
		// Display records from HashMap
		displayHashMap("Select a person from the list", true);
		// Display Options
		_myMenu.displayMenuOptions(_arrMenuOption_SelectPerson, MENU_WIDTH_BIG);
		_myMenu.displaySeparator(MENU_WIDTH_BIG);
		
		// Get User Input
		_mySys.printIt("Type the Person Id of your choice and press <Enter>: ", false);
		strSelectedFriend = objScanner.nextLine();	
		
		if (strSelectedFriend == "0") {
			// Do nothing.. Just Exit this method
		} else {
			// Locate the person from the list and put it on 
			// the variable '_objSelectedPerson'
			_objSelectedPerson = getPersonDetails(strSelectedFriend);
		}
	}
	
	/**
	 * Confirmation to exit the program
	 * 
	 * @return boolean
	 */
	private static boolean isExitCofirmed() {
		
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
	
	/**
	 * Confirmation to delete a person
	 * 
	 * @return boolean
	 */
	private static boolean isDeleteAPersonCofirmed() {
		
		// Local Variables
		boolean blnReturnValue = false;
		int intChoice;
	    
	    while (true) {
	    	
		    	// Display the Main Menu	 
		    	intChoice = _myMenu.getMenuInput("Do you want to DELETE the selected person?", _arrMenuOption_YesNo);
		    	
		    	// Choices	    	
		    	if (intChoice == 1) {
		    		// If the user confirmed to delete the selected person
		    		blnReturnValue = true;
		    		break;
		    	} else if (intChoice == 0) {
		    		// If the user decided not to delete the selected person
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

// ---------- Sub Methods

	/**
	 * Get the person details using the Person Id from the List
	 * 
	 * @param strPersonId
	 * @return
	 */
	private static Person getPersonDetails(String strPersonId) {
		// Local Variables		
		Person objSelectedPerson = null;
		
		// Iterate thru HashMap - Locate the person using the Id
		for (Map.Entry<String, Person> entry : _mapPerson.entrySet()) {
			Person objPerson = entry.getValue();
			if (objPerson.getId().equals(strPersonId)) {
				objSelectedPerson = objPerson;
			}
		}
		
		// Return the value
		return objSelectedPerson;
	}
	
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
			e.printStackTrace();
		}
	}

	/**
	 * Load Data from Hashmap to Text File 
	 */
	private static void loadHashMapToFile() {
		
		String strDelimitedAdult = "";
		String strDelimitedDependent = "";
		
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
		
		// Write to File (Adult and Dependent)
		_mySys.writeDataToFile(strDelimitedAdult, DATAPATH_ADULT);
		_mySys.writeDataToFile(strDelimitedDependent, DATAPATH_DEPENDENT);
	}

	/**
	 * Display records from a HashMap
	 */
	private static void displayHashMap(String strHeader, boolean blnAsMenuOption) {
		
		// Display Column Header		
		_myMenu.clearScreen();
		_myMenu.displaySeparator(MENU_WIDTH_BIG);
		_mySys.printIt("| " + _myStr.padRight(strHeader,MENU_WIDTH_BIG-4) + " |");
		_myMenu.displaySeparator(MENU_WIDTH_BIG);
		_mySys.printIt(
				"| " + 
				_myStr.padRight("Id",8) + "  " +
				_myStr.padRight("Name",15) + "  " +
				_myStr.padRight("Photo",20) + "  " +
				_myStr.padRight("Status",26) + "  " +
				_myStr.padRight("Age",3) + "  " +
				_myStr.padRight("Type",10) + 
				"  |"
				);
		_myMenu.displaySeparator(MENU_WIDTH_BIG);
		
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
 					(blnAsMenuOption == true ? "[ " : "") +		
	    				_myStr.padRight( p.getId(),4) + 
	    				(blnAsMenuOption == true ? " ]  " : "      ") +
	    				
	    				_myStr.padRight(p.getName(),15) + "  " +
	    				_myStr.padRight(p.getPhoto(),20) + "  " +
	    				_myStr.padRight(p.getStatus(),26) + "  " +
	    				_myStr.padRight(Integer.toString(p.getAge()),3) + "  " +
	    				_myStr.padRight(strType,10) + 
	    				"  |"
	    				);
	    }
	    _myMenu.displaySeparator(MENU_WIDTH_BIG);
	    
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
