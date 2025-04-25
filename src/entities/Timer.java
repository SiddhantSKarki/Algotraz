package entities;

/**
 * The {@code Timer} class provides functionality to measure elapsed time 
 * and manage its running state. It allows starting, stopping, resetting, 
 * and setting the timer, as well as retrieving the elapsed time in seconds.
 * 
 * <p>This class is useful for tracking time intervals in various applications.
 * The timer maintains its state and ensures accurate timekeeping even when 
 * paused and resumed.
 * 
 * <h2>Features:</h2>
 * <ul>
 *   <li>Start the timer</li>
 *   <li>Stop the timer</li>
 *   <li>Reset the timer</li>
 *   <li>Set elapsed time</li>
 *   <li>Retrieve elapsed time</li>
 * </ul>
 * 
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * Timer timer = new Timer();
 * timer.startTime();
 * // Perform some operations
 * timer.stopTime();
 * System.out.println("Elapsed time: " + timer.getElapsedTime() + " seconds");
 * }</pre>
 * 
 * <h2>Thread Safety:</h2>
 * This class is not thread-safe. If multiple threads access a single instance 
 * of {@code Timer}, external synchronization is required.
 * 
 * <h2>Implementation Notes:</h2>
 * The timer uses the system's current time in milliseconds to calculate 
 * elapsed time. The elapsed time is stored in milliseconds internally but 
 * is returned in seconds for user convenience.
 * 
 * @author John Jones
 * @version 1.0
 */
public class Timer {

	private long startTime;
	private long elapsedTime;
	private boolean running;

	/**
	 * A Timer class that tracks elapsed time and manages its running state.
	 * This class provides functionality to start, stop, and reset the timer.
	 * The timer is initialized with a start time of 0, elapsed time of 0, and is not running.
	 */
	public Timer() {
		this.startTime = 0;
		this.elapsedTime = 0;
		this.running = false;
	}

	/**
	 * Starts the timer if it is not already running. 
	 * The timer's start time is set to the current system time minus the elapsed time,
	 * ensuring that the timer resumes from where it was paused.
	 * 
	 * Preconditions:
	 * - The timer must not already be running.
	 * 
	 * Postconditions:
	 * - The timer's `running` state is set to true.
	 * - The timer's `startTime` is updated to account for any previously elapsed time.
	 */
	public void startTime() {
		if (!running) {
			this.startTime = System.currentTimeMillis() - elapsedTime;
			this.running = true;
		}
	}

	/**
	 * Stops the timer if it is currently running. Calculates and stores the 
	 * elapsed time by subtracting the start time from the current system time.
	 * Sets the running state to false to indicate the timer is no longer active.
	 */
	public void stopTime() {
		if (running) {
			this.elapsedTime = System.currentTimeMillis() - startTime;
			this.running = false;
		}
	}

	/**
	 * Resets the timer by setting the start time and elapsed time to zero.
	 * Also stops the timer from running.
	 */
	public void resetTime() {
		this.startTime = 0;
		this.elapsedTime = 0;
		this.running = false;
	}

	/**
	 * Calculates and returns the elapsed time in seconds.
	 * If the timer is currently running, the elapsed time is calculated
	 * as the difference between the current system time and the start time.
	 * If the timer is not running, the previously recorded elapsed time is returned.
	 *
	 * @return the elapsed time in seconds
	 */
	public long getElapsedTime() {
		if (running) {
			return (System.currentTimeMillis() - startTime) / 1000;
		} else {
			return elapsedTime / 1000;
		}
	}

	/**
	 * Sets the elapsed time for the timer in seconds. If the timer is currently
	 * running, it adjusts the start time accordingly to reflect the new elapsed time.
	 *
	 * @param seconds the elapsed time to set, in seconds
	 */
	public void setTime(int seconds) {
		this.elapsedTime = seconds * 1000L;
		if (running) {
			this.startTime = System.currentTimeMillis() - elapsedTime;
		}
	}
}
