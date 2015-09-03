package com.kanomiya.mcmod.kmagic.inventory;

import com.kanomiya.mcmod.kmagic.tileentity.TileEntityMagicFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Kanomiya
 *
 */
public class ContainerMagicFurnace extends Container
{
	private final IInventory tileFurnace;
	private int field_178152_f;
	private int field_178153_g;
	private int field_178154_h;
	private int field_178155_i;
	private int mpCache;
	private int maxMpCache;

	public ContainerMagicFurnace(InventoryPlayer p_i45794_1_, IInventory furnaceInventory) {
		tileFurnace = furnaceInventory;
		addSlotToContainer(new Slot(furnaceInventory, 0, 56, 17 +5));
		addSlotToContainer(new SlotMagicFurnaceFuel(furnaceInventory, 1, 56 -37, 53 -39));
		addSlotToContainer(new SlotFurnaceOutput(p_i45794_1_.player, furnaceInventory, 2, 116, 35 +5));
		int i;

		for (i=0; i<3; i++) {
			for (int j=0; j<9; j++) {
				addSlotToContainer(new Slot(p_i45794_1_, j + i * 9 + 9, 8 + j * 18, 84 +13 + i * 18));
			}
		}

		for (i=0; i<9; i++) {
			addSlotToContainer(new Slot(p_i45794_1_, i, 8 + i * 18, 142 +13));
		}
	}

	@Override public void addCraftingToCrafters(ICrafting listener) {
		super.addCraftingToCrafters(listener);
		listener.func_175173_a(this, tileFurnace);
	}

	@Override public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i=0; i<crafters.size(); i++) {
			ICrafting icrafting = (ICrafting)crafters.get(i);

			if (field_178152_f != tileFurnace.getField(2)) {
				icrafting.sendProgressBarUpdate(this, 2, tileFurnace.getField(2));
			}

			if (field_178154_h != tileFurnace.getField(0)) {
				icrafting.sendProgressBarUpdate(this, 0, tileFurnace.getField(0));
			}

			if (field_178155_i != tileFurnace.getField(1)) {
				icrafting.sendProgressBarUpdate(this, 1, tileFurnace.getField(1));
			}

			if (field_178153_g != tileFurnace.getField(3)) {
				icrafting.sendProgressBarUpdate(this, 3, tileFurnace.getField(3));
			}

			if (mpCache != tileFurnace.getField(4)) {
				icrafting.sendProgressBarUpdate(this, 4, tileFurnace.getField(4));
			}

			if (maxMpCache != tileFurnace.getField(5)) {
				icrafting.sendProgressBarUpdate(this, 5, tileFurnace.getField(5));
			}


		}

		field_178152_f = tileFurnace.getField(2);
		field_178154_h = tileFurnace.getField(0);
		field_178155_i = tileFurnace.getField(1);
		field_178153_g = tileFurnace.getField(3);
		mpCache = tileFurnace.getField(4);
		maxMpCache = tileFurnace.getField(5);

	}

	@Override @SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		tileFurnace.setField(id, data);
	}

	@Override public boolean canInteractWith(EntityPlayer playerIn) {
		return tileFurnace.isUseableByPlayer(playerIn);
	}

	@Override public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 2) {
				if (! mergeItemStack(itemstack1, 3, 39, true))  return null;

				slot.onSlotChange(itemstack1, itemstack);

			} else if (index != 1 && index != 0) {
				if (FurnaceRecipes.instance().getSmeltingResult(itemstack1) != null) {
					if (! mergeItemStack(itemstack1, 0, 1, false)) return null;

				} else if (TileEntityMagicFurnace.isItemFuel(itemstack1)) {
					if (! mergeItemStack(itemstack1, 1, 2, false)) return null;

				} else if (index >= 3 && index < 30) {
					if (! mergeItemStack(itemstack1, 30, 39, false)) return null;

				} else if (index >= 30 && index < 39) {
					if (! mergeItemStack(itemstack1, 3, 30, false)) return null;
				}

			} else if (! mergeItemStack(itemstack1, 3, 39, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) slot.putStack((ItemStack) null);
			else slot.onSlotChanged();

			if (itemstack1.stackSize == itemstack.stackSize)  return null;

			slot.onPickupFromSlot(playerIn, itemstack1);
		}

		return itemstack;
	}
}
