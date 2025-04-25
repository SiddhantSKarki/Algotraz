package entities;

public class Timer {

	private long startTime;
	private long elapsedTime;
	private boolean running;

	public Timer() {
		this.startTime = 0;
		this.elapsedTime = 0;
		this.running = false;
	}

	// Start the stopwatch
	public void startTime() {
		if (!running) {
			this.startTime = System.currentTimeMillis() - elapsedTime;
			this.running = true;
		}
	}

	// Stop the stopwatch
	public void stopTime() {
		if (running) {
			this.elapsedTime = System.currentTimeMillis() - startTime;
			this.running = false;
		}
	}

	// Reset the stopwatch
	public void resetTime() {
		this.startTime = 0;
		this.elapsedTime = 0;
		this.running = false;
	}

	// Get the elapsed time in seconds
	public long getElapsedTime() {
		if (running) {
			return (System.currentTimeMillis() - startTime) / 1000;
		} else {
			return elapsedTime / 1000;
		}
	}

	// Set a specific time (in seconds)
	public void setTime(int seconds) {
		this.elapsedTime = seconds * 1000L;
		if (running) {
			this.startTime = System.currentTimeMillis() - elapsedTime;
		}
	}
}
