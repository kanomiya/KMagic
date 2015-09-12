package com.kanomiya.mcmod.kmagic.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.kanomiya.mcmod.core.item.ItemBlockDamaged;
import com.kanomiya.mcmod.kmagic.block.BlockMagicStoneOre;

/**
 * @author Kanomiya
 *
 */

public class ItemBlockMagicStoneOre extends ItemBlockDamaged {

	public ItemBlockMagicStoneOre(Block block) {
		super(block);
	}

	@Override public String getItemStackDisplayName(ItemStack stack) {

		BlockMagicStoneOre.OreType type = BlockMagicStoneOre.OreType.getByMeta(stack.getMetadata());

		return super.getItemStackDisplayName(stack) + " [" + StatCollector.translateToLocal(type.material.getUnlocalizedName()) + "]";
	}



}
