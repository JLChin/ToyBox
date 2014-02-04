package com.jameschin.java.algorithms;

/**
 * Timer
 * @author: James Chin <jameslchin@gmail.com>
 */
public final class Timer {
	private long startTime = 0;
	private long endTime = 0;
	private boolean running = false;
	
	/**
	 * Start or resume tracking time until timer is reset.
	 */
	public void start() {
		if (running == false) {
			startTime = System.currentTimeMillis() - (endTime - startTime);
			running = true;
		}
	}
	
	/**
	 * Stops tracking time.
	 */
	public void stop() {
		if (running == true) {
			endTime = System.currentTimeMillis();
			running = false;
		}
	}
	
	/**
	 * Get total tracked time between starts and stops since last reset, or total time up to current if still running.
	 * @return time between start() and stop() or between start() and current time if still running, plus any prior tracked time since last reset().
	 */
	public String getTime() {
		if (running == true)
			endTime = System.currentTimeMillis();
		
		long elapsed = endTime - startTime;
		String time = "";
		
		// hours
		time += (elapsed / 3600000); 
		time += ":";
		
		// minutes
		int min = (int) ((elapsed / 60000) % 60);
		if (min / 10 == 0)
			time += "0";
		time += min;
		time += ":";
		
		// seconds
		int sec = (int) ((elapsed / 1000) % 60);
		if (sec / 10 == 0)
			time += "0";
		time += sec;
		time += ":";
		
		// milliseconds
		int msec = (int) (elapsed % 1000);
		if (msec / 100 == 0) {
			time += "0";
			if (msec / 10 == 0)
				time += "0";
		}
		time += msec;
		
		return time;
	}
	
	/**
	 * Reset tracked time.
	 */
	public void reset() {
		running = false;
		startTime = 0;
		endTime = 0;
	}
}