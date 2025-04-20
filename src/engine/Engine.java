package engine;

import entities.Player;
import entities.Room;
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
}
