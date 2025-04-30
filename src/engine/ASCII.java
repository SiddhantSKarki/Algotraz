package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class: ASCII
 * @author Siddhant S. Karki
 * @version 1.0
 * Course: CSE201
 * Written: 03/31/2025
 *
 * Purpose: The ASCII class is designed to read the contents of a file and store
 * it as a string representation. This functionality can be utilized for effective
 * ASCII-based printing or processing.
 */
public class ASCII {

	private String asciiString; // Added this attribute for effictive ascii printing

	/**
	 * Constructs an ASCII object and initializes the ASCII string by reading the
	 * contents of the specified file.
	 *
	 * @param fileName The name of the file to be read.
	 */
	public ASCII(String fileName) {
		this.asciiString = "";
		try {
			this.readFile(fileName);
		} catch (FileNotFoundException e) {
			System.out.printf("%s file not found!", fileName);
		}

	}

	/**
	 * Reads the contents of a file specified by the given file path and appends
	 * its content to the `asciiString` field, line by line.
	 *
	 * @param param1 The file path of the file to be read.
	 * @throws FileNotFoundException If the specified file does not exist or cannot be opened.
	 */
	public void readFile(String param1) throws FileNotFoundException {
		Scanner file = new Scanner(new File(param1));

		this.asciiString = "";

		while (file.hasNextLine()) {
			this.asciiString += file.nextLine() + "\n";
		}
		file.close();
	}

	/**
	 * Returns the string representation of the ASCII object.
	 * 
	 * @return The ASCII string stored in this object.
	 */
	public String toString() {
		return this.asciiString;
	}

}
