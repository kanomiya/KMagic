
package com.kanomiya.mcmod.kmagic;

import com.kanomiya.mcmod.kmagic.item.ItemMagicStone;
import com.kanomiya.mcmod.kmagic.util.ModelUtils;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author Kanomiya
 *
 */
public class KMItems {

	public static ItemMagicStone itemMagicStone;

	public static void init(boolean client) {
		register(itemMagicStone = new ItemMagicStone(), "itemMagicStone", ModelUtils.getModelNameSuffixes(itemMagicStone), client);

		// Items
	}



	public static void register(Item item, String name, String infix, String[] metaNames, boolean client) {

		item.setUnlocalizedName(name);
		GameRegistry.registerItem(item, name);

		if (client) {
			String keyName = KMagic.MODID + ":" + name;

			if (metaNames != null && 0 < metaNames.length) {
				for (int index=0; index<metaNames.length; index++) {
					boolean flag = (metaNames[index] != null && ! metaNames[index].equals(""));

					String key = keyName + ((flag) ? infix + metaNames[index] : "");

					ModelBakery.addVariantName(item, key);
					ModelLoader.setCustomModelResourceLocation(item, index, new ModelResourceLocation(key, "inventory"));
				}
			} else {
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(keyName, "inventory"));
			}
		}

	}

	public static void register(Item item, String name, String[] metaNames, boolean client) {
		register(item, name, "_", metaNames, client);
	}

	public static void register(Item item, String name, int metaSize, boolean client) {
		register(item, name, "_", new String[metaSize], client);
	}

	public static void register(Item item, String name, boolean client) {
		register(item, name, null, null, client);
	}





}
