/*
 * Author: Wes Golliher
 * ClueGame
 * Custom exception if the file formatting is off
 */

package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;

public class BadConfigFormatException extends RuntimeException{
	//Default constructor
	public BadConfigFormatException() {
		super("Error with the formatting.");
	}
	
	//Parameterized constructor that prints to log file
	public BadConfigFormatException(String file) {
		super("There is an error with formatting in the file: " + file);
		try {
			PrintWriter out = new PrintWriter("errorLog.txt");
			out.println("There is an error with formatting in the file: " + file);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
