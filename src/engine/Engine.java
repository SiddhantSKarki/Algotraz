package engine;

import entities.Player;
import entities.Room;
import entities.Question;

import java.util.ArrayList;
import java.util.List;


public class Engine {

	private Player player;

	private List<Room> rooms;

	private ASCII asciiDisplay;

	private InputValidator validator;

	private IOHandler iohandler;

	private int currentRoom;

	private int currentQuestion;


	public Engine(Player player) {
		this.setPlayer(player);
		initializeGame(player.getId(), player.getName());

		// Array of room directory names
        String[] roomDirs = { "room1", "room2", "room3" };
		rooms = new ArrayList<Room>();

		// Loop through each room directory
        for (int i = 0; i < roomDirs.length; i++) {
            int roomNumber = i + 1; // Calculate the room number (1-based index)

            // Create a Room object for the current room
            Room room = new Room("src/data/story/" + roomDirs[i], roomNumber);

			// Add to rooms list
			rooms.addLast(room);
		}
	}

	public Engine(Player player, List<Room> rooms, ASCII asciiDisplay, InputValidator validator) {
		this.setPlayer(player);
		this.setRooms(rooms);
		this.setAsciiDisplay(asciiDisplay);
		this.setValidator(validator);
	}
	
	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public List<Room> getRooms() {
		return this.rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public ASCII getAsciiDisplay() {
		return this.asciiDisplay;
	}

	public void setAsciiDisplay(ASCII asciiDisplay) {
		this.asciiDisplay = asciiDisplay;
	}

	public InputValidator getValidator() {
		return this.validator;
	}

	public void setValidator(InputValidator validator) {
		this.validator = validator;
	}

	public Room getCurrentRoom(int roomIndex) {
		return this.getRooms().get(roomIndex - 1);
	}

	public Question getCurrentQuestion(int quesIndex) {
		return this.getCurrentRoom(currentRoom).getQuestions().get(quesIndex - 1);
	}

	public void startGame() {
		// Get current room of the game
		String roomDescription = getCurrentRoom(currentRoom).getDescription();

		// Print to output
		System.out.println("\n\n=== Entering Room " + currentRoom + " ===\n");
		System.out.println(roomDescription + "\n");

		while (true) {	
			Question question = getCurrentQuestion(currentQuestion);		
			
			System.out.println("Problem " + currentQuestion + "\n" + question.getQuestion());
			System.out.print("Your answer: ");

			// Get input and validate using processInput
			String playerAnswer = processInput(iohandler.readInput());

			// Check if right from Question class
			boolean correct = question.checkPlayerAnswer(playerAnswer);

			// Pass to update game state
			this.updateGameState(correct);
		}
	}

	public void initializeGame(String playerID, String playerName) {
		// Initialize player
		this.setPlayer(new Player(playerID, playerName));

		// Other entities
		validator = new InputValidator();
		iohandler = new IOHandler();

		// Set current room to first room in list
		currentRoom = 1;
		currentQuestion = 1;
	}

	public String processInput(String input) {
		String trimmed = input.trim();

		// Validate
		boolean inputIsValid = validator.validateInput(input);

		if (inputIsValid) {
			return trimmed;
		} else {
			return "Invalid input";
		}
	}

	public void updateGameState(boolean playerCorrect) {
		if (playerCorrect) {
			System.out.println("Correct!\n");

			switch (currentQuestion) {
				case 1:
				case 2:
					// Move to next question
					currentQuestion++;
					break;
				case 3:
					// Check if at final room
					if (currentRoom == 3) {
						// Load boss room

					} else {
						// Load next room, first question
						currentRoom++;
						currentQuestion = 1;

						// Print new room to output
						// Get current room of the game
						String roomDescription = getCurrentRoom(currentRoom).getDescription();

						// Print to output
						System.out.println("\n\n=== Entering Room " + currentRoom + " ===\n");
						System.out.println(roomDescription + "\n");
					}

					break;
				default:
					
					break;
			}
		} else {
			System.out.println("Incorrect.\n");
		}
	}

	public void renderASCII() {

	}
}
