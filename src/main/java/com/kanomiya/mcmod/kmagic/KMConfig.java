package com.kanomiya.mcmod.kmagic;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

/**
 * @author Kanomiya
 *
 */
public class KMConfig {

	public static int DIMID_KMAGIC = 57;


	public static void init(File configfile) {
		Configuration config = new Configuration(configfile);

		try {
			config.load();

			DIMID_KMAGIC = config.getInt("DimensionId", "DimensionKMagic", 57, 2, Integer.MAX_VALUE, "");


		} catch (Exception e) {
		} finally {
			config.save();
		}

	}

}
