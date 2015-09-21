package com.kanomiya.mcmod.kmagic.api;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants.NBT;

/**
 * @author Kanomiya
 *
 */
public class MagicNBTUtils {

	protected static NBTTagCompound getNBT(ItemStack stack) {
		if (! stack.hasTagCompound()) return new NBTTagCompound();
		else return stack.getTagCompound();
	}

	public static boolean hasMagicNBT(NBTTagCompound tag) {
		return tag.hasKey(KMagicAPI.STR_DATANAME, NBT.TAG_COMPOUND);
	}

	public static boolean hasMagicNBT(ItemStack stack) {
		return hasMagicNBT(getNBT(stack));
	}

	public static boolean hasMagicNBT(TileEntity tile) {
		return hasMagicNBT(tile.getTileData());
	}

	public static NBTTagCompound getMagicNBT(ItemStack stack) {
		// boolean initFlag = ! hasMagicNBT(stack) && stack.getItem() instanceof IMagicItem;
		NBTTagCompound magicNbt = getNBT(stack).getCompoundTag(KMagicAPI.STR_DATANAME);

		/*
		if (initFlag) {
			MagicStatus status = MagicStatus.getInstance(stack);
			status.evaluate();

			status.writeToNBT(magicNbt);
			KMagicAPI.setMagicStatus(stack, status); // TODO: 必要？
		}
		*/

		return magicNbt;
	}

	public static NBTTagCompound getMagicNBT(TileEntity tile) {
		return tile.getTileData().getCompoundTag(KMagicAPI.STR_DATANAME);
	}

	public static void setMagicNBT(ItemStack stack, NBTTagCompound magicNbt) {
		NBTTagCompound stackNbt = getNBT(stack);
		stackNbt.setTag(KMagicAPI.STR_DATANAME, magicNbt);

		stack.setTagCompound(stackNbt);
	}

}
