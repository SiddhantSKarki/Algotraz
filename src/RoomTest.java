import entities.Question;
import entities.Room;
import java.util.List;
import java.util.Scanner;

public class RoomTest {
    public static void main(String[] args) {
        // Create a Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Array of room directory names
        String[] roomDirs = { "room1", "room2", "room3" };

        // Loop through each room directory
        for (int i = 0; i < roomDirs.length; i++) {
            int roomNumber = i + 1; // Calculate the room number (1-based index)

            // Print a message indicating the current room
            System.out.println("\n=== Entering Room " + roomNumber + " ===");

            // Create a Room object for the current room
            Room room = new Room("src/data/story/" + roomDirs[i], roomNumber);

            // Print the room's description
            System.out.println("\nRoom Description:\n" + room.getDescription());

            // Get the list of questions for the current room
            List<Question> questions = room.getQuestions();

            // Loop through each question in the room
            for (int j = 0; j < questions.size(); j++) {
                Question q = questions.get(j); // Get the current question
                System.out.println("\nProblem " + (j + 1) + ":"); // Print the problem number
                q.printQuestion(); // Print the question details

                // Prompt the user for their answer
                System.out.print("Your answer: ");
                String answer = scanner.nextLine();

                // Check if the user's answer is correct
                if (q.checkPlayerAnswer(answer)) {
                    System.out.println("✅ Correct!"); // Print success message
                } else {
                    System.out.println("❌ Incorrect."); // Print failure message
                }
            }
        }

        // Close the Scanner object to release resources
        scanner.close();
    }
}
