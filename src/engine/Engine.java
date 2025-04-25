package engine;

import entities.Player;
import entities.Room;
import entities.Timer;
import entities.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * The {@code Engine} class manages the core game logic, including the player,
 * rooms, questions, and game state. It handles game initialization, progression,
 * and updates based on player input.
 * 
 * <p>Main responsibilities:
 * <ul>
 *   <li>Initialize the game with a player and rooms.</li>
 *   <li>Track the current room and question.</li>
 *   <li>Validate player answers and update the game state.</li>
 * </ul>
 * 
 * @author John Jones
 * @version 1.0
 */
public class Engine {

	private Player player;

	private ArrayList<Room> rooms;

	private ASCII asciiDisplay;

	private Timer gameTimer;

	private int currentRoom;

	private int currentQuestion;

	private boolean gameComplete = false;

	private int playerScore = 0;


	/**
	 * Creates an Engine object to manage the game.
	 *
	 * @param player The player object used to initialize the game.
	 *
	 * This constructor sets up the player, initializes the game state,
	 * and loads the rooms from predefined directories.
	 */
	public Engine(Player player) {
		this.setPlayer(player);
		initializeGame(player.getId(), player.getName());
		this.gameTimer = new Timer();

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
	
	/**
	 * Retrieves the current player instance associated with the engine.
	 *
	 * @return the {@link Player} object representing the current player.
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Sets the player for the engine.
	 *
	 * @param player the Player object to be assigned to the engine
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Retrieves the list of rooms in the engine.
	 *
	 * @return a list of {@link Room} objects representing the rooms.
	 */
	public List<Room> getRooms() {
		return this.rooms;
	}

	/**
	 * Sets the list of rooms for the engine.
	 *
	 * @param rooms the list of Room objects to be set
	 */
	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}

	/**
	 * Retrieves the ASCII display associated with this engine.
	 *
	 * @return the ASCII display object.
	 */
	public ASCII getAsciiDisplay() {
		return this.asciiDisplay;
	}

	/**
	 * Sets the ASCII display for the engine.
	 *
	 * @param asciiDisplay the ASCII display object to be set
	 */
	public void setAsciiDisplay(ASCII asciiDisplay) {
		this.asciiDisplay = asciiDisplay;
	}

	/**
	 * Retrieves the current room based on the specified room index.
	 *
	 * @param roomIndex The 1-based index of the room to retrieve.
	 *                  The index should be greater than 0.
	 * @return The Room object corresponding to the given index.
	 * @throws IndexOutOfBoundsException if the roomIndex is out of range
	 *                                   (less than 1 or greater than the number of rooms).
	 */
	public Room getCurrentRoom(int roomIndex) {
		return this.getRooms().get(roomIndex - 1);
	}

	/**
	 * Retrieves the player's current score.
	 *
	 * @return the player's score as an integer.
	 */
	public int getPlayerScore() {
		return this.playerScore;
	}

	/**
	 * Sets the player's score.
	 *
	 * @param playerScore the score to be assigned to the player.
	 */
	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	/**
	 * Retrieves the current question from the specified room based on the given question index.
	 *
	 * @param quesIndex The index of the question to retrieve (1-based index).
	 * @return The {@code Question} object at the specified index in the current room.
	 * @throws IndexOutOfBoundsException If the question index is out of range.
	 * @throws NullPointerException If the current room or questions list is null.
	 */
	public Question getCurrentQuestion(int quesIndex) {
		return this.getCurrentRoom(currentRoom).getQuestions().get(quesIndex - 1);
	}

	/**
	 * Starts the game by entering the current room and presenting questions to the player.
	 * The method continuously prompts the player for answers to questions and updates
	 * the game state based on the correctness of the answers.
	 * 
	 * <p>Steps performed by this method:
	 * <ul>
	 *   <li>Retrieves and displays the description of the current room.</li>
	 *   <li>Continuously presents questions to the player.</li>
	 *   <li>Processes the player's input and checks the correctness of their answers.</li>
	 *   <li>Updates the game state based on the player's answers.</li>
	 * </ul>
	 */
	public void startGame() {
		// Get current room of the game
		String roomDescription = getCurrentRoom(currentRoom).getDescription();

		// Print to output
		System.out.println("\n\n=== Entering Room " + currentRoom + " ===\n");
		System.out.println(roomDescription + "\n");
		
		Scanner scanner = new Scanner(System.in);

		while (!gameComplete) {	
			Question question = getCurrentQuestion(currentQuestion);		
			
			System.out.println("Problem " + currentQuestion + "\n" + question.getQuestion());
			System.out.print("Your answer: ");

			// Reset and start timer
			gameTimer.resetTime();
			gameTimer.startTime();

			// Get input and validate using processInput
			String playerAnswer = scanner.nextLine().trim();

			// Stop timer
			gameTimer.stopTime();

			// Check if right from Question class
			boolean correct = question.checkPlayerAnswer(playerAnswer);

			// Pass to update game state
			this.updateGameState(correct);
		}

		scanner.close();
	}

	/**
	 * Initializes the game by setting up the player and starting conditions.
	 *
	 * @param playerID   The unique identifier for the player.
	 * @param playerName The name of the player.
	 */
	public void initializeGame(String playerID, String playerName) {
		// Initialize player
		this.setPlayer(new Player(playerID, playerName));

		// Set current room to first room in list
		currentRoom = 1;
		currentQuestion = 1;
	}

	/**
	 * Updates the game state based on the player's answer.
	 *
	 * @param playerCorrect True if the player's answer is correct, false otherwise.
	 *
	 * <p> If correct:
	 * - Moves to the next question or room.
	 * - Completes the game if in the final room and question.
	 * </p><p>
	 * If incorrect:
	 * - Displays an incorrect message.
	 * </p>
	 */
	public void updateGameState(boolean playerCorrect) {
		if (playerCorrect) {
			switch (currentQuestion) {
				case 1:
				
					break;
				case 2:
				
					break;
				case 3:
					// TODO Check if player score meets room threshold
					break;
				default:
					break;
			}
		} else {

		}



		// TODO Switch statement first, then check if theyre right, always go to next question
		// Update points differently 
		
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
						// Game complete
						gameComplete = true;

						// TODO Display total score, average room time, return to start menu
						// TODO Winning ASCII

					} else {
						// TODO Check if player score meets room threshold
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
}
