package entities;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Question {

	protected String question;

	protected Map<Integer, String> options;

	protected int answer;

	public void printQuestion() {
		System.out.println("Question: " + question);
		for (Map.Entry<Integer, String> entry : options.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

	public boolean checkPlayerAnswer(int param1) {
		return answer == param1;
	}

	public String getQuestion() {
		return this.question;
	}

	public Map<Integer, String> getOptions() {
		return this.options;
	}


}

class TrueFalseQuestion extends Question {

    private boolean correctAnswer;

    public TrueFalseQuestion(String path) {
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

    @Override
    public boolean checkPlayerAnswer(int input) {
        return input == (correctAnswer ? 1 : 0);
    }
}

class MultipleChoiceQuestion extends Question {

	private char correctAnswer;

	public MultipleChoiceQuestion(String folderPath) {
		super("", null); // Call the parent constructor with empty values
		parseMultipleChoiceQuestion(folderPath);
	}

	public int[] getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(int[] correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	@Override
	public Boolean checkPlayerAnswer(int param1) {
		for (int answer : correctAnswers) {
			if (answer == param1) {
				return true;
			}
		}
		return false;
	}
}

class CodeQuestion extends Question {

	private String correctCode;

	public String getCorrectCode() {
		return correctCode;
	}

	public void setCorrectCode(String correctCode) {
		this.correctCode = correctCode;
	}

	@Override
	public Boolean checkPlayerAnswer(int param1) {
		// This method might need to be overridden to handle code-specific logic
		return null;
	}
}