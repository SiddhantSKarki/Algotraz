package engine;

import entities.Player;
import entities.Room;
import entities.Timer;
import entities.Question;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code Engine} class manages the core game logic, including the player,
 * rooms, questions, and game state. It handles game initialization,
 * progression,
 * and updates based on player input.
 * 
 * <p>
 * Main responsibilities:
 * <ul>
 * <li>Initialize the game with a player and rooms.</li>
 * <li>Track the current room and question.</li>
 * <li>Validate player answers and update the game state.</li>
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

	private int[] playerPoints;

	private ArrayList<Integer> questionTimes;

	/**
	 * Creates an Engine object to manage the game.
	 *
	 * @param player The player object used to initialize the game.
	 *
	 *               This constructor sets up the player, initializes the game
	 *               state,
	 *               and loads the rooms from predefined directories.
	 */
	public Engine(Player player) {
		this.setPlayer(player);
		initializeGame(player.getId(), player.getName());
		this.gameTimer = new Timer();
		this.asciiDisplay = new ASCII("src/data/ascii/welcome.txt");

		// Array of room directory names
		String[] roomDirs = { "room1", "room2", "room3" };
		int[] roomThresholds = { 250, 200, 100 };
		rooms = new ArrayList<Room>();
		questionTimes = new ArrayList<Integer>();
		playerPoints = new int[3];

		// Loop through each room directory
		for (int i = 0; i < roomDirs.length; i++) {
			int roomNumber = i + 1; // Calculate the room number (1-based index)

			// Create a Room object for the current room
			Room room = new Room("src/data/story/" + roomDirs[i], roomNumber, roomThresholds[i]);

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
	 *                                   (less than 1 or greater than the number of
	 *                                   rooms).
	 */
	public Room getCurrentRoom(int roomIndex) {
		return this.getRooms().get(roomIndex - 1);
	}

	/**
	 * Retrieves the player's current score.
	 *
	 * @param roomNumber The room number for which to retrieve the score.
	 * @return the player's score as an integer.
	 */
	public int getPlayerScore(int roomNumber) {
		return this.playerPoints[roomNumber - 1];
	}

	/**
	 * Retrieves the total score of the player.
	 *
	 * @return the total score as an integer.
	 */
	public int totalPlayerScore() {
		return getPlayerScore(1) + getPlayerScore(2) + getPlayerScore(3);
	}

	/**
	 * Retrieves the current question from the specified room based on the given
	 * question index.
	 *
	 * @param quesIndex The index of the question to retrieve (1-based index).
	 * @return The {@code Question} object at the specified index in the current
	 *         room.
	 * @throws IndexOutOfBoundsException If the question index is out of range.
	 * @throws NullPointerException      If the current room or questions list is
	 *                                   null.
	 */
	public Question getCurrentQuestion(int quesIndex) {
		return this.getCurrentRoom(currentRoom).getQuestions().get(quesIndex - 1);
	}

	/**
	 * Starts the game by entering the current room and presenting questions to the
	 * player.
	 * The method continuously prompts the player for answers to questions and
	 * updates
	 * the game state based on the correctness of the answers.
	 * 
	 * <p>
	 * Steps performed by this method:
	 * <ul>
	 * <li>Retrieves and displays the description of the current room.</li>
	 * <li>Continuously presents questions to the player.</li>
	 * <li>Processes the player's input and checks the correctness of their
	 * answers.</li>
	 * <li>Updates the game state based on the player's answers.</li>
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

			// Add time to question times
			questionTimes.add((int) gameTimer.getElapsedTime());

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
	 * Updates the game state based on the player's answer to the current question.
	 * 
	 * <p>
	 * This method checks if the player's answer is correct and updates the player's
	 * score accordingly.
	 * It also handles room transitions and game completion logic.
	 *
	 * @param playerCorrect A boolean indicating whether the player's answer was
	 *                      correct.
	 */
	public void updateGameState(boolean playerCorrect) {
		int points = 0; // Initialize points

		if (!playerCorrect) {
			System.out.println("\nIncorrect.");
		}

		if (playerCorrect) {
			System.out.println("\nCorrect!");

			// Calculate points
			points = (int) (this.getCurrentQuestion(currentQuestion).getScoreWeight() * 20
					/ (gameTimer.getElapsedTime() + 1));
			playerPoints[currentRoom - 1] += points;
		}

		switch (currentQuestion) {
			case 1:
			case 2:
				currentQuestion++;
				System.out.println("Points: " + points + "\n");
				break;
			case 3:
				System.out.println("Points: " + points + "\n");

				// Check if player score meets room threshold
				if (getPlayerScore(currentRoom) >= getCurrentRoom(currentRoom).getRoomThreshold()) {
					// Check if at final room
					if (currentRoom == 3) {
						// Game complete
						gameComplete = true;

						// Print winning or losing ASCII and message
						try {
							Scanner fileScanner = new Scanner("invalidFile.txt");

							// Check if player score is greater than total points of all rooms
							if (totalPlayerScore() > rooms.stream()
									.mapToInt(Room::getRoomThreshold).sum() + 200) {
								fileScanner = new Scanner(new java.io.File("src/data/story/youWin.txt"));
								asciiDisplay.readFile("src/data/ascii/youWinASCII.txt");
							} else {
								fileScanner = new Scanner(new java.io.File("src/data/story/gameOver.txt"));
								asciiDisplay.readFile("src/data/ascii/gameOverASCII.txt");
							}

							System.out.println(asciiDisplay.toString());

							while (fileScanner.hasNextLine()) {
								System.out.println(fileScanner.nextLine());
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}

						// Calculate averge time per question and room times
						int totalTime = questionTimes.stream().mapToInt(Integer::intValue).sum();
						int averageQuestionTime = totalTime / questionTimes.size();
						System.out.println("\n\nAverage time per question: " + averageQuestionTime + " seconds");
						System.out.println("Average time per room: " + (totalTime / 3) + " seconds");
						System.out.println("Total time: " + totalTime + " seconds\n");
						System.out.println("Total points: " + totalPlayerScore() + "\n");
						System.out.println("Points required to win: "
								+ (rooms.stream().mapToInt(Room::getRoomThreshold).sum() + 200) + "\n");
						System.out.println("Game complete! Thanks for playing!\n");

					} else {
						// Move to next room
						System.out.println(
								"Room threshold met! Moving to next room.\nRoom points: " + getPlayerScore(currentRoom)
										+ "\nPlayer points: " + totalPlayerScore() + "\n");
						currentRoom++;
						currentQuestion = 1;

						// Print new room to output
						// Get current room of the game
						String roomDescription = getCurrentRoom(currentRoom).getDescription();

						// Print to output
						System.out.println("\n\n=== Entering Room " + currentRoom + " ===\n");
						System.out.println(roomDescription + "\n");
					}
				} else {
					System.out.println("Not enough points to move to next room. Starting room over\n");
					currentQuestion = 1;
					playerPoints[currentRoom - 1] = 0;
				}

				break;
			default:
				break;
		}
	}
}
