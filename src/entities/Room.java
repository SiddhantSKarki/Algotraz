package entities;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Class: Room
 * @author Alex Aussawalaithong, Siddhant S. Karki
 * @version 1.0
 * Course: CSE201
 * Written: 04/18/2025
 * 
 * Purpose: The Room class represents a game room that contains a set of questions.
 * Each room has a description and a list of questions.
 * The class provides methods to load the room's description and questions from files.
 */
public class Room {

	//private Timer timer;

	final private List<Question> questions;

	final private String description;

	final private int roomNumber;

	/**
	 * Constructs a new Room with the specified path and room number.
	 * The room's description and questions are loaded from the specified path.
	 * 
	 * @param roomPath the path to the room's directory
	 * @param roomNumber the room/level number
	 */	
    public Room(String roomPath, int roomNumber) {
        //this.timer = new Timer(0, 0, 0);
        this.questions = new ArrayList<>();
        this.description = loadDescription(roomPath);
		this.roomNumber = roomNumber;
        loadQuestions(roomPath);
	}

	/**
	 * Loads the room's description from a file.
	 * 
	 * @param path the path to the room's directory
	 * @return the room's description as a string
	 */
	private String loadDescription(String path) {
        File desc = new File(path, "problem_description.txt");
        try {
			// Read the description file and return its content as a string
            return Files.readString(desc.toPath());
        } catch (IOException e) {
            System.err.println("Couldn't read description: " + e.getMessage());
            return "";
        }
    }

	/**
	 * Loads the questions from the specified path.
	 * The questions are loaded from subdirectories within the specified path.
	 * 
	 * @param path the path to the room's directory
	 */
	private void loadQuestions(String path) {
		// Create a File object for the root directory of the room
		File rootDir = new File(path);
		
		// Get a list of all subdirectories within the root directory
		File[] folders = rootDir.listFiles(File::isDirectory);
		
		// If there are no subdirectories, return early
		if (folders == null) return;
		
		// Iterate through each subdirectory
		for (File folder : folders) {
			Question q = null; // Initialize a Question object
			
			// Determine the type of question to create based on the room number
			switch (roomNumber) {
				case 1 -> q = new TrueFalseQuestion(folder.getPath()); // Room 1: True/False questions
				case 2 -> q = new MultipleChoiceQuestion(folder.getPath()); // Room 2: Multiple-choice questions
				case 3 -> q = new CodeQuestion(folder.getPath()); // Room 3: Coding questions
				default -> {
					// Handle unknown room numbers
					System.err.println("Unknown room number: " + roomNumber);
					System.exit(1); // Exit the program with an error
				}
			}

			// If a question was successfully created, add it to the list of questions
			if (q != null) questions.add(q);
		}
	}
    
	/**
	 * Gets the room number.
	 * 
	 * @return the room number
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the list of all questions in the room.
	 * @return the list of questions
	 */
	public List<Question> getQuestions() {
		return questions;
	}

	// public Timer getTimer() {
	// 	return null;
	// }
	// Create a database to read from and fill in attributes
}
