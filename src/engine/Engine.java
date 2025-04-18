package engine;

import entities.Player;
import entities.Question;
import entities.Room;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class Engine {

	private Player player;

	private List<Room> rooms;

	private ASCII asciiDisplay;

	private InputValidator validator;


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

	public void startGame() {
		
	}

	public void initializeGame() {

	}

	public void processInput() {

	}

	public void updateGameState() {

	}

	public void renderASCII() {

	}

	/*
	 * Parse questions database to initialize question list
	 * in Room class
	 */
	public void parseQuestionDatabase() {
		List<Question> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            String type = null, text = null, opts = null, ans = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\|", 2);
                switch (parts[0]) {
                    case "TRUE_FALSE":
                    case "MULTIPLE_CHOICE":
                    case "MULTIPLE_ANSWER":
                    case "CODE":
                        // whenever we hit a new TYPE, flush the previous question
                        if (type != null) {
                            questions.add(buildQuestion(type, text, opts, ans));
                        }
                        type = parts[0];
                        text = parts[1];
                        opts = null;
                        ans = null;
                        break;

                    case "OPTIONS":
                        opts = parts[1];
                        break;

                    case "ANSWER":
                        ans = parts[1];
                        break;

                    default:
                        throw new IllegalArgumentException("Unknown line prefix: " + parts[0]);
                }
            }
            // flush last
            if (type != null) {
                questions.add(buildQuestion(type, text, opts, ans));
            }
        }
        return questions;
	}
}
