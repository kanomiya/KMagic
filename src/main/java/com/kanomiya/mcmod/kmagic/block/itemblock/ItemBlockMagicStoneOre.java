package com.kanomiya.mcmod.kmagic.block.itemblock;

import com.kanomiya.mcmod.kmagic.block.BlockMagicStoneOre;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

/**
 * @author Kanomiya
 *
 */

public class ItemBlockMagicStoneOre extends ItemBlock {

	public ItemBlockMagicStoneOre(Block block) {
		super(block);
	}

	@Override public String getItemStackDisplayName(ItemStack stack) {

		BlockMagicStoneOre.OreType type = BlockMagicStoneOre.OreType.getByMeta(stack.getMetadata());

		return super.getItemStackDisplayName(stack) + " [" + StatCollector.translateToLocal(type.material.getUnlocalizedName()) + "]";
	}

	@Override public int getMetadata(int damage) {
		return damage;
	}


}
