package com.kanomiya.mcmod.kmagic;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author Kanomiya
 *
 */
public class KMConfig {

	public static int DIMID_KMAGIC = 57;


	public static void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		try {
			config.load();

			DIMID_KMAGIC = config.getInt("DimensionId", "DimensionKMagic", 57, 2, Integer.MAX_VALUE, "");


		} catch (Exception e) {
		} finally {
			config.save();
		}

	}

}
