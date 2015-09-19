
package com.kanomiya.mcmod.kmagic;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.kanomiya.mcmod.kanomiyacore.KanomiyaCore;
import com.kanomiya.mcmod.kanomiyacore.util.GameRegistryUtils;
import com.kanomiya.mcmod.kmagic.item.ItemMagicStone;
import com.kanomiya.mcmod.kmagic.util.ModelUtils;

/**
 * @author Kanomiya
 *
 */
public class KMItems {

	public static ItemMagicStone itemMagicStone;
	// public static Item item;

	public static void preInit(FMLPreInitializationEvent event, KanomiyaCore core) {
		final boolean client = event.getSide().isClient();

		GameRegistryUtils utils = core.getGameRegistryUtils();

		utils.registerItem(itemMagicStone = new ItemMagicStone(), "itemMagicStone", ModelUtils.getModelNameSuffixes(itemMagicStone), client);

		// utils.registerItem(item = new Item(), "item", client);

	}

	public static void init(FMLInitializationEvent event, KanomiyaCore core) {  }
	public static void postInit(FMLPostInitializationEvent event, KanomiyaCore core) {  }



}
