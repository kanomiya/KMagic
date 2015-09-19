package com.kanomiya.mcmod.kmagic.api.util;

/**
 * @author Kanomiya
 *
 */
public class Counter {
	private int presentCount;
	private final int interval;

	public Counter(int parInterval) {
		interval = parInterval;
	}


	public boolean update() {
		presentCount ++;

		if (getInterval() <= presentCount) {
			presentCount = 0;
			return true;
		}

		return false;
	}

	public void reset() { presentCount = 0; }

	public int getInterval() { return interval; }


}
