package entities;
import java.util.Map;

public class Question {

	private String question;

	private Map<Integer, String> options;

	private int answer;

	public void printQuestion() {
		System.out.println("Question: " + question);
		for (Map.Entry<Integer, String> entry : options.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

	public Boolean checkPlayerAnswer(int param1) {
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

	public boolean isCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(boolean correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	@Override
	public Boolean checkPlayerAnswer(int param1) {
		return param1 == (correctAnswer ? 1 : 0);
	}
}

class MultipleAnswerQuestion extends Question {

	private int[] correctAnswers;

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
