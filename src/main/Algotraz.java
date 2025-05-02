package main;

import engine.ASCII;
import engine.Engine;
import engine.MenuSound;
import entities.Player;

import java.util.Scanner;

/**
 * The main class for the Algotraz game.
 * This class initializes the game, displays the introduction, and handles the main menu logic.
 */
public class Algotraz {

    /**
     * Displays the introductory ASCII art for the game.
     * The ASCII art is loaded from a file located at "src/data/ascii/welcome.txt".
     */
    private static void displayIntroArt() {
        String introArtPath = "src/data/ascii/welcome.txt";
        ASCII asciiIntro = new ASCII(introArtPath);
        System.out.println(asciiIntro);
    }

    /**
     * Displays the main menu for the game, allowing the player to start the game, view help, or exit.
     * The menu is displayed in a loop until the player chooses to exit or starts the game.
     */
    private static void displayIntroMenu() {
        MenuSound menuSound = new MenuSound("src/data/sound/menuIntro.wav", "src/data/sound/menuLoop.wav");
        menuSound.setLoop(true);
        menuSound.playSound();

        displayStoryLine();
        System.out.println("Please select an option:");
        System.out.println("1) Start Game");
        System.out.println("2) Help");
        System.out.println("3) Exit");

        Scanner scanner = new java.util.Scanner(System.in);
        while (true) {
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                case "start game":
                    // This is where we initialize the following:
                    // Within initializeGame: Game Entities: Player, All Rooms, 
                    Player prisoner = new Player("1", "Pris(O)ner");
                    Engine gameEngine = new Engine(prisoner);

                    menuSound.stopSound(); // Stop the menu sound
                    gameEngine.startGame(scanner);

                    System.out.println("Would you like to play again? (y/n)");
                    String playAgain = scanner.nextLine().trim().toLowerCase(); 
                    if (playAgain.equals("y") || playAgain.equals("yes")) {
                        gameEngine.clearOutput();
                        displayIntroMenu(); // Restart the intro menu
                    } else {
                        System.out.println("Thank you for playing Algotraz! Goodbye!");
                        scanner.close();
                        System.exit(0);
                    }

                    return;
                case "2":
                case "help":
                    displayHelp();
                    break;
                case "3":
                case "exit":
                    scanner.close();
                    System.out.println("Thank you for playing Algotraz! Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays the introductory story line for the game.
     * The story line is loaded from a file located at "src/data/story/intro_description.txt".
     */
    private static void displayStoryLine() {
        String storyPath = "src/data/story/intro_description.txt";
        ASCII asciiStory = new ASCII(storyPath);
        System.out.println(asciiStory);
    }

    /**
     * Displays the help information for the game.
     * This includes a list of available commands and their descriptions.
     */
    private static void displayHelp() {
        System.out.println("Available commands:");
        System.out.println("1) Start Game - Begin your journey in Algotraz.");
        System.out.println("2) Help - View the list of available commands.");
        System.out.println("3) Exit - Exit the game.");
    }

    /**
     * The main entry point of the application.
     * Clears the console, displays the introduction art, and shows the main menu.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");
		System.out.flush();
        displayIntroArt();
        displayIntroMenu();
    }
}
