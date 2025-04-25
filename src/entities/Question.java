package entities;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class: Question
 * @author Alex Aussawalaithong, Siddhant S. Karki
 * @version 1.0
 * Course: CSE201
 * Written: 04/18/2025
 * 
 * Purpose: This class represents a question in the game. 
 * It can be a True/False question, a Multiple Choice question,
 * or a Code question. Each question has a question text, a set of options,
 * and an answer.
 */
public class Question {

	protected String question;

	protected String answer;

	protected int scoreWeight;


	/**
	 * Prints the question to the console.
	 */
	public void printQuestion() {
		System.out.println("Question: " + question);
	}

	/**
	 * Checks if the player's answer is correct.
	 * 
	 * @param input The player's answer.
	 * @return true if the answer is correct, false otherwise.
	 */
	public boolean checkPlayerAnswer(String input) {
		return answer.equalsIgnoreCase(input);
	}

	/**
	 * Gets the question text.
	 * 
	 * @return The question text.
	 */
	public String getQuestion() {
		return this.question;
	}

	public int getScoreWeight() {
		return this.scoreWeight;
	}

	public void setScoreWeight(int scoreWeight) {
		this.scoreWeight = scoreWeight;
	}
}

/**
 * Class: TrueFalseQuestion
 * @extends Question
 * @author Alex Aussawalaithong, Siddhant S. Karki
 * @version 1.0
 * Course: CSE201
 * Written: 04/18/2025
 * 
 * Purpose: This class represents a True/False question.
 * It extends the Question class and provides
 * functionality specific to True/False questions.
 */
class TrueFalseQuestion extends Question {

    private boolean correctAnswer;

	/**
	 * Constructor for TrueFalseQuestion.
	 * Reads the question and answer from the specified path.
	 * 
	 * @param path The path to the question directory.
	 * @throws IOException If there is an error reading the files.
	 */
    public TrueFalseQuestion(String path) {
		this.scoreWeight = 20; // Set the score weight for True/False questions
        try {
			// get question directory path and answer file path
			String questionPath = path + "/question.txt";
			String answerPath = path + "/answer.txt";

			// Read the question from the file
			this.question = Files.readString(Paths.get(questionPath)).trim();

			// Read the answer from the file
			String ans = Files.readString(Paths.get(answerPath)).trim();
			this.correctAnswer = ans.equals("True");
        } catch (IOException e) {
            System.err.println("Error reading True/False question: " + e.getMessage());
        }
    }


	/**
	 * Checks if the player's answer is correct.
	 * 
	 * @param input The player's answer.
	 * @return true if the answer is correct, false otherwise.
	 */
	@Override
    public boolean checkPlayerAnswer(String input) {
        // Convert input to lowercase for case-insensitive comparison
		String lowerInput = input.toLowerCase();
		return (lowerInput.equals("true") && correctAnswer) ||
			(lowerInput.equals("false") && !correctAnswer);
    }
}

/**
 * Class: MultipleChoiceQuestion
 * @extends Question
 * @author Alex Aussawalaithong
 * @version 1.0
 * Course: CSE201
 * Written: 04/18/2025
 * 
 * Purpose: This class represents a Multiple Choice question.
 * It extends the Question class and provides
 * functionality specific to Multiple Choice questions.
 */
class MultipleChoiceQuestion extends Question {

	private char correctAnswer;

	/**
	 * Constructor for MultipleChoiceQuestion.
	 * Reads the question and answer from the specified path.
	 * 
	 * @param path The path to the question directory.
	 * @throws IOException If there is an error reading the files.
	 */
	public MultipleChoiceQuestion(String path) {
		this.scoreWeight = 30; // Set the score weight for True/False questions
		try {
			// get question directory path and answer file path
			String questionPath = path + "/question.txt";
			String answerPath = path + "/answer.txt";

			// Read the question from the file
			this.question = Files.readString(Paths.get(questionPath)).trim();

			// Read the answer from the file
			String ans = Files.readString(Paths.get(answerPath)).trim();
			this.correctAnswer = ans.charAt(0);
		} catch (IOException e) {
			System.err.println("Error reading Multiple Choice question: "
				+ e.getMessage());
		}
	}

	/**
	 * Checks if the player's answer is correct.
	 * 
	 * @param input The player's answer.
	 * @return true if the answer is correct, false otherwise.
	 */
	@Override
	public boolean checkPlayerAnswer(String input) {
		// Convert input to lowercase for case-insensitive comparison
		String upperInput = input.toUpperCase();
		return upperInput.charAt(0) == correctAnswer;
	}
}

/**
 * Class: CodeQuestion
 * @extends Question
 * @author Alex Aussawalaithong
 * @version 1.0
 * Course: CSE201
 * Written: 04/18/2025
 * 
 * Purpose: This class represents a Code question.
 * It extends the Question class and provides
 * functionality specific to Code questions.
 */
class CodeQuestion extends Question {

	private String correctCode;

	/**
	 * Constructor for CodeQuestion.
	 * Reads the question and answer from the specified path.
	 * 
	 * @param path The path to the question directory.
	 * @throws IOException If there is an error reading the files.
	 */
	public CodeQuestion(String path) {
		this.scoreWeight = 50; // Set the score weight for Code questions
		try {
			// get question directory path and answer file path
			String questionPath = path + "/question.txt";
			String answerPath = path + "/answer.txt";

			// Read the question from the file
			this.question = Files.readString(Paths.get(questionPath)).trim();

			// Read the answer from the file
			this.correctCode = Files.readString(Paths.get(answerPath)).trim();
		} catch (IOException e) {
			System.err.println("Error reading Code question: " + e.getMessage());
		}
	}

	/**
	 * Checks if the player's answer is correct.
	 * 
	 * @param input The player's answer.
	 * @return true if the answer is correct, false otherwise.
	 */
	@Override
	public boolean checkPlayerAnswer(String input) {
		// Compare the input code with the correct code
		return input.trim().equals(correctCode.trim());
	}
}