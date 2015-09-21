package com.kanomiya.mcmod.kmagic.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;

import com.kanomiya.mcmod.kmagic.tileentity.TileEntityMagicFurnace;

/**
 * @author Kanomiya
 *
 */
public class SlotMagicFurnaceFuel extends Slot {

	public SlotMagicFurnaceFuel(IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
		super(inventoryIn, slotIndex, xPosition, yPosition);
	}

	@Override public boolean isItemValid(ItemStack stack) {
		return TileEntityMagicFurnace.isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack);
	}

	@Override public int getItemStackLimit(ItemStack stack) {
		return 1;
	}

}
