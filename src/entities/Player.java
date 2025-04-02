package entities;

/**
 * Class: Player
 * @author Siddhant S. Karki
 * @version 1.0
 * Course: CSE201
 * Written: 03/31/2025
 *
 * Purpose: The Player class represents a player entity with a unique identifier
 * and a name. It provides methods to access and modify the player's attributes,
 * as well as a string representation of the player object.
 *
 * Represents a player with an ID and a name.
 */
public class Player {

	private String id;
	private String name;

	/**
	 * Constructs a new Player with the specified ID and name.
	 *
	 * @param id   the unique identifier for the player
	 * @param name the name of the player
	 */
	public Player(String id, String name) {
		this.setID(id);
		this.setName(name);
	}

	/**
	 * Gets the ID of the player.
	 *
	 * @return the player's ID
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Sets the ID of the player.
	 *
	 * @param id the new ID for the player
	 */
	public void setID(String id) {
		this.id = id;
	}

	/**
	 * Gets the name of the player.
	 *
	 * @return the player's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of the player.
	 *
	 * @param name the new name for the player
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns a string representation of the player.
	 *
	 * @return a string containing the player's ID and name
	 */
	public String toString() {
		return "Player{" +
			"id='" + id + '\'' +
			", name='" + name + '\'' +
			'}';
	}
}
