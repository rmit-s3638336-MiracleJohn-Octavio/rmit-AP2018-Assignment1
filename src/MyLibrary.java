/**
 * @author Miracle John Octavio jr
 *
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;


public class MyLibrary {

// --- Variables	
	
	// Menu
	private Scanner _objScanner = new Scanner(System.in);
	private MySystem _objMySys = new MySystem();
	private MyString _objMyStr = new MyString();
	private MyNumeric _objMyNum = new MyNumeric();
	private MyMenu _objMyMenu = new MyMenu();
	 
	 // FIXME
	// Enums
	 enum _enmTest {
		Test1,
		Test2,
		Test3;
	}

	 class MySystem {
		 
		 void printIt(String strValue) {
			System.out.println(strValue);
		 }
		 
		 void printIt(String strValue, boolean blnInsertLineFeed) {
		 	if (blnInsertLineFeed) {
		 		System.out.println(strValue);
			} else {
				System.out.print(strValue);
			}
		 }
		 
		 void pressAnyKey() {
			System.out.println("Press <Enter> key to continue!");
	    		try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}    	
		}
		 
		 void pressAnyKey(String strMessage) {
				System.out.println(strMessage);
		    		try {
					System.in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}    	
			}
	 
// ---------- I/O		 
		 
		 /**
		 * Write String to File
		 * 
		 * @param strDelimitedString Delimited string separated by carriage return ('/n')
		 * @param strFileName <Folder (If any)> + Filename
		 * 
		 * @Usage
		 * 
		 * String strDelimited =
		 * 		"P001, Mark, Mark.jpg, Working at KFC, 27\n" +
		 * 		"P002, Elise, Elise.jpg, Working at McDonald, 24\n" +
		 * 		"P003, Andrew, Andrew.jpg, Looking for jobs, 26   \n" +
		 * 		"P004, Mariah, Mariah.jpg, Student at RMIT, 23";
		 *  
		 * _objMySys.writeDataToFile(strDelimited, "src/myData.txt");
		 *  
		 */
		void writeDataToFile(String strDelimitedString, String strFileName) {
			String arrDelimitedString[] = {};
			arrDelimitedString = strDelimitedString.split("/n", -1);
			
			try {
				FileWriter fw = new FileWriter(strFileName);
				PrintWriter pw = new PrintWriter(fw);
				
				for (int i = 0; i < arrDelimitedString.length; i++) {
					pw.println(arrDelimitedString[0]);								
				}
				
				pw.close();
			} catch (IOException e) {
				_objMyMenu.displayMessagePrompt("Error has occured on method 'writeDataToFile'" + e.toString(), true);
			}
		}
			
		/**
		 * Read from File and return as String
		 * 
		 * @param strFileName <Folder (If any)> + Filename
		 * @return Delimited string separated by carriage return ('/n')
		 * 
		 * @Usage
		 * 
		 * String str =  _objMySys.readDataFromFile("src/temp.txt");
		 * _objMySys.printIt(str);
		 * 
		 */		
		String readDataFromFile(String strFileName) {
			String strReturnValue = "";
			
			try {
				FileReader fw = new FileReader(strFileName);
				BufferedReader br = new BufferedReader(fw);
				
				String strData;
				
				while ((strData = br.readLine()) != null) {
					strReturnValue += strData + "\n";
				}
							
				br.close();
			} catch (IOException e) {
				_objMyMenu.displayMessagePrompt("Error has occured on method 'writeDataToFile'" + e.toString(), true);
			}
			
			return strReturnValue;
			
		}
		 
		String getStringInput(String strPrompt) {
			 String strStringInput;				
			// Display prompt	 
			 printIt(strPrompt,false);
			strStringInput = _objScanner.nextLine();				
			return strStringInput;
		 }
	 
	 }
	 	 
	 class MyMenu {
		 
		 // Constants
		 final int _intMENU_WIDTH = 55;				// Menu Width
		 
		 /**
		 * Displays a simple MenuBox with a Title and Options
		 * 
		 * @param strMenuTitle
		 * @param arrMenuOptions
		 */
		void displayMenu(String strMenuTitle, String arrMenuOptions[]) {
	 		// Pattern
	 		// 			 1         2         3         4         5
			//  12345678901234567890123456789012345678901234567890
			// "+------------------------------------------------+");
			// "|                  MiniNet MENU                  |");
			// "+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");
	 		// "| Select option to execute..                     |");
			// "+------------------------------------------------+");
			// "| [ 1 ] - Xxxx                          		    |");
			// "| [ 2 ] - Xxxx                          			|");
			// "| [ 3 ] - Xxxx                          			|");
			// "+------------------------------------------------+");
			// "| [ 0 ] - Exit                                   |");
			// "+------------------------------------------------+");
			// "Type the code of your choice below:";
			
	 		clearScreen();
	 		displayMenuHeader(strMenuTitle);
	 		displayMenuOptions(arrMenuOptions);
	 		displaySeparator();
	 		_objMySys.printIt("Type the code of your choice and press <Enter>: ", false);
		}	
		
		/*
		 * Displays a simple MenuBox with a Title and Options plus allows the user to
		 * press a Return Key to continue
		 * 
		 * @param strMenuTitle
		 * @param arrMenuOptions
		 */
		void displayMenuPrompt(String strMenuTitle, String arrMenuOptions[]) {
			clearScreen();
			displayMenuHeader(strMenuTitle);
			displayMenuOptions(arrMenuOptions);
			displaySeparator();
			
			// Display press <Enter> to continue
	    		_objMySys.pressAnyKey();
		}	
		
		/**
		 * Displays the Menu Window Title
		 */
		void displayMenuTitle() {
			clearScreen();
			displaySeparator();
			_objMySys.printIt("|" + _objMyStr.padCenter("** MiniNet Menu **", _intMENU_WIDTH-2) + "|");
			displaySeparator("~");			
		}
		
		 /**
		 * Displays the Menu Window Header
		 * 
		 * @param strMenuTitle
		 */
		void displayMenuHeader(String strMenuTitle) {
			displayMenuTitle();
			_objMySys.printIt("|" + _objMyStr.padRight(" " + strMenuTitle, _intMENU_WIDTH-2) + "|");
			displaySeparator();
		}
		
		 /**
		 * Displays Window Options
		 * 
		 * @param arrOptionsValue
		 */
		void displayMenuOptions(String arrOptionsValue[]) {
			for (int i = 0; i < arrOptionsValue.length; i++) {
				String value = arrOptionsValue[i];
				String tilde = "~";
				if ( value.equals(tilde)) {
					displaySeparator();
				} else {
					_objMySys.printIt("| " + _objMyStr.padRight(value, _intMENU_WIDTH-4) + " |");	
				}	
			}
		}
		
		 /**
		 * Displays Menu Window Separator
		 */
		void displaySeparator() {
			_objMySys.printIt("+" + _objMyStr.strReplicate("-", _intMENU_WIDTH-2) + "+");
		 }
				
		/**
		 * Displays Menu Window Separator (Can specify width length)
		 * 
		 * @param intWidthLenght
		 */
		void displaySeparator(int intWidthLenght) {
			_objMySys.printIt("+" + _objMyStr.strReplicate("-", intWidthLenght-2) + "+");
		 }
		 
		/**
		 * Displays Menu Window Separator (Can specify the Char)
		 * 
		 * @param strStringToReplicate
		 */
		void displaySeparator(String strStringToReplicate) {
			_objMySys.printIt("+" + _objMyStr.strReplicate(strStringToReplicate, _intMENU_WIDTH-2) + "+");
		 }
		 
		/**
		 * Displays MessageBox
		 * 
		 * @param strMessage
		 */
		void displayMessageBox(String strMessage, boolean blnClearScreen) {		
				// Display the Message
				if(blnClearScreen) {
					clearScreen();
				}
				displaySeparator();
				_objMySys.printIt("|" + _objMyStr.padCenter(strMessage, _intMENU_WIDTH-2) + "|");
				displaySeparator();
		}
		
		/**
		 * Displays MessageBox with Prompt (Press Any Key to Continue)
		 * 
		 * @param strMessage
		 */
		void displayMessagePrompt(String strMessage, boolean blnClearScreen) {		
			// Display the Message
			 displayMessageBox(strMessage, blnClearScreen);
			 
			// Display press <Enter> to continue
			 _objMySys.pressAnyKey();
		}
		
		/**
		 * A simple Menu that prompts the user to select an option
		 * @param strMenuTitle A menu title to prompt the user
		 * @param arrMenuOptions An array of String which contains the Menu Options
		 * @return
		 */
		int getMenuInput(String strMenuTitle, String arrMenuOptions[]) {
			int intChoice;
			
			// Validate User Input if its a string
			while (true) {
		    		// Display the Main Menu	 
		    		displayMenu(strMenuTitle, arrMenuOptions);
					if (_objScanner.hasNextInt()) {
						intChoice = _objScanner.nextInt();
						break;
					} else {
						_objScanner.nextLine();
						displayMessagePrompt("You have typed in an INVALID character!", true);
					}
			}
			
			return intChoice;
		}
		
		void clearScreen() {
			for (int i = 1; i < 150; i++) {
	    			System.out.println("");	
			}		
		 }
		 
	 }
	
	 class MyString {
		 
		 String padRight(String strValue, int intCount) {
		    return String.format("%1$-" + intCount + "s", strValue);
		}
			
		 String padLeft(String strValue, int intCount) {
		    return String.format("%1$" + intCount + "s", strValue);
		}
			
		 String padCenter(String strValue, int intCount) {
			String returnValue = "";
			
			// Get the length of the String
			double dblStringLength = strValue.length();		
			// Get the difference then divide it with 2
			double dblSpace = ((double)intCount - dblStringLength) / 2;
			// Convert it to int
			int intSpace = (int) dblSpace;
					
			// Get the number of space for leading and trailing
			String strSpace = "";
			for (int i = 0; i < intSpace; i++) {
				strSpace += " ";
			}
			
			if (
				(_objMyNum.isEven(intCount) && _objMyNum.isEven((int)dblStringLength)) || 
				(!_objMyNum.isEven(intCount) && !_objMyNum.isEven((int)dblStringLength))) {
				
				// If both Lengths are Odd or Even, then add space equally
				returnValue = strSpace + strValue + strSpace;
				
			} else {
				
				// Otherwise, add one space on leading
				returnValue = " " + strSpace + strValue + strSpace;
				
			}
			
		    return  returnValue;
		}
			
		 String strReplicate(String strValue, int intCount) {
			String strReturnValue = "";
			
			for (int i = 0; i < intCount; i++) {
				strReturnValue += strValue;
			}
		
			return strReturnValue;
		}
	 }
	 
	 class MyNumeric {
		 
		 int getRandomNumber(int intMin, int intMax) {
				
				if (intMin >= intMax) {
					throw new IllegalArgumentException("max must be greater than min");
				}
		
				Random r = new Random();
				return r.nextInt((intMax - intMin) + 1) + intMin;
			}
			
			 boolean isEven(int intValue) {
				// Declare Variable
				boolean returValue = false;
				
				try {
					// Check if the value is odd or even
					if (intValue % 2 == 0) {
						returValue = true;
					}	
				} catch (Exception e) {
					_objMySys.printIt(e.getMessage());
				}
				
				// Return the value
				return returValue;
			}
	 }
	 
}
