
package com.kanomiya.mcmod.kmagic;

import com.kanomiya.mcmod.kmagic.block.BlockKMSignalPortal;
import com.kanomiya.mcmod.kmagic.block.BlockKMSignalRepeater;
import com.kanomiya.mcmod.kmagic.block.BlockMagicFurnace;
import com.kanomiya.mcmod.kmagic.block.BlockMagicKMSignalSender;
import com.kanomiya.mcmod.kmagic.block.BlockMagicStoneOre;
import com.kanomiya.mcmod.kmagic.block.itemblock.ItemBlockMagicStoneOre;
import com.kanomiya.mcmod.kmagic.tileentity.TileEntityMagicFurnace;
import com.kanomiya.mcmod.kmagic.tileentity.signal.TileEntityKMSignalPortal;
import com.kanomiya.mcmod.kmagic.tileentity.signal.TileEntityKMSignalRepeater;
import com.kanomiya.mcmod.kmagic.tileentity.signal.TileEntityMagicKMSignalSender;
import com.kanomiya.mcmod.kmagic.util.ModelUtils;

import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author Kanomiya
 *
 */
public class KMBlocks {

	public static BlockMagicStoneOre blockMagicStoneOre;
	public static BlockMagicFurnace blockMagicFurnace;
	public static BlockKMSignalRepeater blockKMSignalRepeater;
	public static BlockKMSignalPortal blockKMSignalPortal;
	public static BlockMagicKMSignalSender blockMagicKMSignalSender;


	public static void init(boolean client) {
		register(blockMagicStoneOre = new BlockMagicStoneOre(), ItemBlockMagicStoneOre.class, "blockMagicStoneOre", ModelUtils.getModelNameSuffixes(blockMagicStoneOre), client);
		register(blockMagicFurnace = new BlockMagicFurnace(), "blockMagicFurnace", client);

		register(blockKMSignalRepeater = new BlockKMSignalRepeater(), "blockKMSignalRepeater", client);
		register(blockKMSignalPortal = new BlockKMSignalPortal(), "blockKMSignalPortal", client);
		register(blockMagicKMSignalSender = new BlockMagicKMSignalSender(), "blockMagicKMSignalSender", client);


		// Blocks

		GameRegistry.registerTileEntity(TileEntityMagicFurnace.class, "kmagic:blockMagicFurnace");
		GameRegistry.registerTileEntity(TileEntityKMSignalRepeater.class, "kmagic:blockKMSignalRepeater");
		GameRegistry.registerTileEntity(TileEntityKMSignalPortal.class, "kmagic:blockKMSignalPortal");
		GameRegistry.registerTileEntity(TileEntityMagicKMSignalSender.class, "kmagic:blockMagicKMSignalSender");

	}



	public static void register(Block block, Class<? extends ItemBlock> itemclass, String name, String infix, String[] metaNames, boolean client) {

		block.setUnlocalizedName(name);
		GameRegistry.registerBlock(block, itemclass, name);

		if (client) {
			String keyName = KMagic.MODID + ":" + name;
			Item itemBlock = Item.getItemFromBlock(block);

			if (metaNames != null && 0 < metaNames.length) {
				for (int index=0; index<metaNames.length; index++) {
					boolean flag = (metaNames[index] != null && ! metaNames[index].equals(""));

					String key = keyName + ((flag) ? infix + metaNames[index] : "");
					ModelResourceLocation location = new ModelResourceLocation(key, "inventory");

					ModelBakery.addVariantName(itemBlock, key);
					ModelLoader.setCustomModelResourceLocation(itemBlock, index, location);

				}
			} else {
				ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(keyName, "inventory"));
			}
		}

	}


	public static void register(Block block, Class<? extends ItemBlock> itemclass, String name, String[] metaNames, boolean client) {
		register(block, itemclass, name, "_", metaNames, client);
	}

	public static void register(Block block, Class<? extends ItemBlock> itemclass, String name, int metaSize, boolean client) {
		register(block, itemclass, name, "_", new String[metaSize], client);
	}

	public static void register(Block block, Class<? extends ItemBlock> itemclass, String name, boolean client) {
		register(block, itemclass, name, null, null, client);
	}



	public static void register(Block block, String name, String[] metaNames, boolean client) {
		register(block, ItemBlock.class, name, "_", metaNames, client);
	}

	public static void register(Block block, String name, int metaSize, boolean client) {
		register(block, ItemBlock.class, name, "_", new String[metaSize], client);
	}

	public static void register(Block block, String name, boolean client) {
		register(block, ItemBlock.class, name, null, null, client);
	}





}
