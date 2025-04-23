package engine;
import java.util.Scanner;

public class IOHandler {

	private Scanner scanner;

	public IOHandler() {
		scanner = new Scanner(System.in);
	}

	public String readInput() {
		return scanner.nextLine();
	}

	public void writeOutput(String param1) {

	}

}
