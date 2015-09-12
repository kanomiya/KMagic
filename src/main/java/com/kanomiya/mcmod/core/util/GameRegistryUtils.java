package com.kanomiya.mcmod.core.util;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.kanomiya.mcmod.kmagic.KMagic;

/**
 * @author Kanomiya
 *
 */
public class GameRegistryUtils {
	public static final String MODID = KMagic.MODID;
	public static final Object MOD = KMagic.instance;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*



	public static final String DEFAULT_INFIX = "_";

	public static void registerModel(Item item, String name, String infix, String[] metaNames) {
		String keyName = MODID + ":" + name;

		if (metaNames != null && 0 < metaNames.length) {
			for (int index=0; index<metaNames.length; index++) {
				boolean flag = (metaNames[index] != null && ! metaNames[index].equals(""));

				String key;

				if (! metaNames[index].contains(":")) key = keyName + ((flag) ? infix + metaNames[index] : "");
				else key = metaNames[index];

				ModelResourceLocation location = new ModelResourceLocation(key, "inventory");

				ModelBakery.addVariantName(item, key);
				ModelLoader.setCustomModelResourceLocation(item, index, location);

			}
		} else {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(keyName, "inventory"));
		}

	}



	public static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name, String infix, String[] metaNames, boolean registerModel) {
		block.setUnlocalizedName(name);
		GameRegistry.registerBlock(block, itemclass, name);

		if (registerModel) registerModel(Item.getItemFromBlock(block), name, infix, metaNames);

	}


	public static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name, String[] metaNames, boolean registerModel) {
		registerBlock(block, itemclass, name, DEFAULT_INFIX, metaNames, registerModel);
	}

	public static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name, int metaSize, boolean registerModel) {
		registerBlock(block, itemclass, name, DEFAULT_INFIX, new String[metaSize], registerModel);
	}

	public static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name, boolean registerModel) {
		registerBlock(block, itemclass, name, null, null, registerModel);
	}



	public static void registerBlock(Block block, String name, String[] metaNames, boolean registerModel) {
		registerBlock(block, ItemBlock.class, name, DEFAULT_INFIX, metaNames, registerModel);
	}

	public static void registerBlock(Block block, String name, int metaSize, boolean registerModel) {
		registerBlock(block, ItemBlock.class, name, DEFAULT_INFIX, new String[metaSize], registerModel);
	}

	public static void registerBlock(Block block, String name, boolean registerModel) {
		registerBlock(block, ItemBlock.class, name, null, null, registerModel);
	}


	public static void registerItem(Item item, String name, String infix, String[] metaNames, boolean registerModel) {
		item.setUnlocalizedName(name);
		GameRegistry.registerItem(item, name);

		if (registerModel) registerModel(item, name, infix, metaNames);
	}

	public static void registerItem(Item item, String name, String[] metaNames, boolean registerModel) {
		registerItem(item, name, DEFAULT_INFIX, metaNames, registerModel);
	}

	public static void registerItem(Item item, String name, boolean registerModel) {
		registerItem(item, name, null, registerModel);
	}






	public static int registerEntity(Class entityClass, String name, int entityId) {
		// EntityRegistry.registerGlobalEntityID(entityClass, name, entityId);
		EntityRegistry.registerModEntity(entityClass, name, entityId, MOD, 64, 1, true);

		return entityId;
	}

	public static int registerEntity(Class entityClass, String name) {
		return registerEntity(entityClass, name, EntityRegistry.findGlobalUniqueEntityId());
	}

	public static int registerEntity(Class entityClass, String name, int primaryColor, int secondaryColor) {
		int entityId = registerEntity(entityClass, name);

		EntityRegistry.registerGlobalEntityID(entityClass, name, entityId, primaryColor, secondaryColor);
		// EntityList.addMapping(entityClass, name, entityID, primaryColor, secondaryColor);

		return entityId;
	}

	public static int registerEntity(Class entityClass, String name, boolean addEgg) {

		if (addEgg) {
			long seed = name.hashCode();
			Random rand = new Random(seed);

			return registerEntity(entityClass, name, rand.nextInt() * 16777215, rand.nextInt() * 16777215);
		} else {
			return registerEntity(entityClass, name);
		}
	}


}
