package engine;

import entities.Player;
import entities.Room;
import entities.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * The {@code Engine} class serves as the core of the game logic, managing the player,
 * rooms, questions, and game state. It provides methods to initialize the game,
 * retrieve and update game components, and handle the progression of the game.
 * 
 * <p>Key responsibilities of the {@code Engine} class include:
 * <ul>
 *   <li>Initializing the game with a player and predefined rooms.</li>
 *   <li>Managing the current room and question within the game.</li>
 *   <li>Handling player input and validating answers to questions.</li>
 *   <li>Updating the game state based on the player's progress and correctness of answers.</li>
 * </ul>
 * 
 * <p>The game is structured into multiple rooms, each containing a set of questions.
 * The player progresses through the rooms by answering questions correctly. The game
 * ends when the player completes all rooms or chooses to exit.
 * 
 * <p>Usage:
 * <pre>
 * {@code
 * Player player = new Player("playerID", "playerName");
 * Engine engine = new Engine(player);
 * engine.startGame();
 * }
 * </pre>
 * 
 * <p>Dependencies:
 * <ul>
 *   <li>{@link Player} - Represents the player in the game.</li>
 *   <li>{@link Room} - Represents a room containing questions.</li>
 *   <li>{@link Question} - Represents a question within a room.</li>
 *   <li>{@link ASCII} - Handles ASCII-based display for the game.</li>
 * </ul>
 * 
 * <p>Note: The {@code startGame()} method runs in an infinite loop and should be
 * terminated externally when the game ends or the player exits.
 * 
 * @author John Jones
 * @version 1.0
 * @since 2023
 */
public class Engine {

	private Player player;

	private List<Room> rooms;

	private ASCII asciiDisplay;

	private int currentRoom;

	private int currentQuestion;


	/**
	 * Constructs an Engine object for the game.
	 *
	 * @param player The player object representing the current player.
	 *               This is used to initialize the game with the player's ID and name.
	 *
	 * The constructor performs the following:
	 * <ul>
	 *   <li>Sets the player for the engine using {@code setPlayer(player)}.</li>
	 *   <li>Initializes the game with the player's ID and name using {@code initializeGame(player.getId(), player.getName())}.</li>
	 *   <li>Creates a list of rooms by iterating through predefined room directory names.</li>
	 *   <li>For each room directory, creates a {@code Room} object and adds it to the {@code rooms} list.</li>
	 * </ul>
	 *
	 * Note: The {@code rooms} list is populated with {@code Room} objects created
	 * from the directories located in {@code src/data/story/}.
	 */
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
	public void setRooms(List<Room> rooms) {
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
	 * 
	 * <p>This method runs in an infinite loop, so it should be terminated externally
	 * when the game ends or when the player exits.
	 */
	public void startGame() {
		// Get current room of the game
		String roomDescription = getCurrentRoom(currentRoom).getDescription();

		// Print to output
		System.out.println("\n\n=== Entering Room " + currentRoom + " ===\n");
		System.out.println(roomDescription + "\n");

		while (true) {	
			Scanner scanner = new Scanner(System.in);
			Question question = getCurrentQuestion(currentQuestion);		
			
			System.out.println("Problem " + currentQuestion + "\n" + question.getQuestion());
			System.out.print("Your answer: ");

			// Get input and validate using processInput
			String playerAnswer = scanner.nextLine().trim();

			// Check if right from Question class
			boolean correct = question.checkPlayerAnswer(playerAnswer);

			// Pass to update game state
			this.updateGameState(correct);

			scanner.close();
		}
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
	 * Updates the game state based on whether the player's answer is correct.
	 * This method handles the progression of questions and rooms in the game.
	 *
	 * @param playerCorrect A boolean indicating if the player's answer is correct.
	 *                      - If true, the player progresses to the next question or room.
	 *                      - If false, the game acknowledges the incorrect answer.
	 *
	 * <p>Behavior:</p>
	 * <ul>
	 *   <li>If the player's answer is correct:
	 *     <ul>
	 *       <li>For questions 1 and 2, the player moves to the next question.</li>
	 *       <li>For question 3:
	 *         <ul>
	 *           <li>If the player is in the final room (room 3), the game is completed.</li>
	 *           <li>Otherwise, the player's score is checked against the room threshold, and they
	 *               progress to the next room with the first question.</li>
	 *         </ul>
	 *       </li>
	 *     </ul>
	 *   </li>
	 *   <li>If the player's answer is incorrect, a message is displayed.</li>
	 * </ul>
	 *
	 * <p>Additional Notes:</p>
	 * <ul>
	 *   <li>When the game is completed, the total score, average room time, and a winning message
	 *       should be displayed.</li>
	 *   <li>Room descriptions are printed when entering a new room.</li>
	 * </ul>
	 */
	public void updateGameState(boolean playerCorrect) {
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
