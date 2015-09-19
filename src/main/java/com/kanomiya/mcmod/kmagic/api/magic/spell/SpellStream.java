package com.kanomiya.mcmod.kmagic.api.magic.spell;

/**
 * @author Kanomiya
 *
 */
public class SpellStream {
	protected String[] codes;
	protected int nextIndex;
	protected int markedIndex;

	public SpellStream(String[] parCodes) {
		codes = parCodes;
		nextIndex = 0;
		markedIndex = 0;
	}

	public String readPrev() {
		nextIndex --;
		return codes[nextIndex +1];
	}

	public String read() {
		nextIndex ++;
		return codes[nextIndex -1];
	}

	public void mark() {
		markedIndex = nextIndex;
	}

	public void reset() {
		nextIndex = markedIndex;
	}



	public boolean available() {
		return (0 <= nextIndex && nextIndex < codes.length);
	}

}
