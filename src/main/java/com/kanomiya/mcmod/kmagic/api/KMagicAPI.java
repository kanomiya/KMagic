package com.kanomiya.mcmod.kmagic.api;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

import com.kanomiya.mcmod.kmagic.api.magic.material.MagicMaterial;
import com.kanomiya.mcmod.kmagic.api.magic.material.MagicMaterialRegistry;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatusEntity;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatusPlayer;
import com.kanomiya.mcmod.kmagic.api.magic.status.base.IMagicItem;
import com.kanomiya.mcmod.kmagic.api.magic.status.base.IMagicObject;

/**
*
* @author Kanomiya
*
*/
public class KMagicAPI {

	public static final String STR_DATANAME = "KMagicData";

	// *----------------------------------------------------------------------------------------------*
	//
	// *----------------------------------------------------------------------------------------------*

	public static boolean isMagicObject(Object obj) {
		if (obj instanceof ItemStack) return (((ItemStack) obj).getItem() instanceof IMagicItem);
		return (obj instanceof IMagicObject || obj instanceof EntityPlayer);
	}




	public static void setMagicStatus(ItemStack stack, MagicStatus status) {
		NBTTagCompound magicNbt = new NBTTagCompound();
		status.writeToNBT(magicNbt);
		MagicNBTUtils.setMagicNBT(stack, magicNbt);
	}

	public static MagicStatusPlayer getMagicStatus(EntityPlayer player) {
		return (MagicStatusPlayer) MagicStatus.loadInstance(player);
	}

	public static MagicStatusEntity getMagicStatus(Entity entity) {
		// if (entity instanceof EntityPlayer) return getMagicStatus((EntityPlayer) entity);
		return (MagicStatusEntity) MagicStatus.loadInstance(entity);
	}

	public static MagicStatus getMagicStatus(ItemStack stack) {
		return MagicStatus.loadInstance(stack);
	}

	public static MagicStatus getMagicStatus(TileEntity tile) {
		return MagicStatus.loadInstance(tile);
	}





	public static ItemStack fullMp(ItemStack stack) {
		NBTTagCompound nbt = MagicNBTUtils.getMagicNBT(stack);

		nbt.setInteger("mp", nbt.getInteger("maxMp"));

		MagicNBTUtils.setMagicNBT(stack, nbt);

		return stack;
	}

	public static ItemStack randomMp(ItemStack stack, Random rand, float minRate, float maxRate) {
		if (1.0f < minRate) minRate = 1.0f;
		if (1.0f < maxRate) maxRate = 1.0f;

		NBTTagCompound nbt = MagicNBTUtils.getMagicNBT(stack);

		int maxMp = getMaxMp(nbt);
		int value = Math.min(maxMp, MathHelper.getRandomIntegerInRange(rand, (int) Math.floor(minRate *maxMp), (int) Math.floor(maxRate *maxMp)));
		nbt.setInteger("mp", value);

		MagicNBTUtils.setMagicNBT(stack, nbt);

		return stack;
	}



	// *----------------------------------------------------------------------------------------------*
	// Mp is Full
	// *----------------------------------------------------------------------------------------------*

	public static boolean isFull(int amount, int capacity, int capaThreshold) {
		return (capacity +capaThreshold <= amount);
	}

	public static boolean isFull(int amount, int capacity) {
		return isFull(amount, capacity, 0);
	}


	public static boolean mpIsFull(NBTTagCompound magicNbt, int capaThreshold) {
		return isFull(getMp(magicNbt), getMaxMp(magicNbt), capaThreshold);
	}

	public static boolean mpIsFull(NBTTagCompound magicNbt) {
		return mpIsFull(magicNbt, 0);
	}

	public static boolean mpIsFull(MagicStatus status, int capaThreshold) {
		return isFull(status.getMp(), status.getMaxMp(), capaThreshold);
	}

	public static boolean mpIsFull(MagicStatus status) {
		return mpIsFull(status, 0);
	}


	public static boolean mpIsLack(MagicStatus status, int capaThreshold) {
		return (status.getMp() <= capaThreshold);
	}

	public static boolean mpIsLack(MagicStatus status) {
		return mpIsLack(status, 0);
	}

	// *----------------------------------------------------------------------------------------------*
	// Mp
	// *----------------------------------------------------------------------------------------------*

	public static int getMp(NBTTagCompound magicNbt) {
		return magicNbt.getInteger("mp");
	}

	public static int getMaxMp(NBTTagCompound magicNbt) {
		return magicNbt.getInteger("maxMp");
	}

	public static boolean hasMp(NBTTagCompound magicNbt) {
		return (0 < getMp(magicNbt));
	}

	public static boolean hasMpCapacity(NBTTagCompound magicNbt) {
		return (0 < getMaxMp(magicNbt));
	}





	public static void registerMagicMaterial(MagicMaterial material) {
		MagicMaterialRegistry.registerMaterial(material);
	}





}
