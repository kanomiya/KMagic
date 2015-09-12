package com.kanomiya.mcmod.core.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

/**
 * @author Kanomiya
 *
 */
public class ItemBlockDamaged extends ItemBlock {

	/**
	 * @param block
	 */
	public ItemBlockDamaged(Block block) {
		super(block);
	}

	@Override public int getMetadata(int damage) {
		return damage;
	}

}
