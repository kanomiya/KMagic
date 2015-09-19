package com.kanomiya.mcmod.kmagic.api.magic.spell;

import com.kanomiya.mcmod.kmagic.api.magic.accessor.IMagicAccessor;

/**
 * @author Kanomiya
 *
 */
public class SpellInterpreter {

	protected String[] codes;

	public SpellInterpreter(String[] parCodes) {
		codes = parCodes;
	}

	public void launch(IMagicAccessor executor) {
		SpellStream stream = new SpellStream(codes);

		String spellName = stream.read();
		double posX = executor.getX();
		double posY = executor.getY();
		double posZ = executor.getZ();

		while(stream.available()) {
			String code = stream.read();

			switch(code) {
			// TODO: set / plus

			case "vile":
				String vile = stream.read();

				switch (vile) {
				case "galnub": break;
				case "pixel": break;
				}

			}

		}

	}




}
