
package com.kanomiya.mcmod.kmagic;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.kanomiya.mcmod.core.util.GameRegistryUtils;
import com.kanomiya.mcmod.kmagic.item.ItemMagicStone;
import com.kanomiya.mcmod.kmagic.util.ModelUtils;

/**
 * @author Kanomiya
 *
 */
public class KMItems {

	public static ItemMagicStone itemMagicStone;
	// public static Item item;

	public static void preInit(FMLPreInitializationEvent event) {
		final boolean client = event.getSide().isClient();

		GameRegistryUtils.registerItem(itemMagicStone = new ItemMagicStone(), "itemMagicStone", ModelUtils.getModelNameSuffixes(itemMagicStone), client);

		// GameRegistryUtils.registerItem(item = new Item(), "item", client);

	}

	public static void init(FMLInitializationEvent event) {  }
	public static void postInit(FMLPostInitializationEvent event) {  }



}
