package com.kanomiya.mcmod.kmagic;

import java.util.Random;

import com.kanomiya.mcmod.kmagic.magic.material.MagicMaterial;
import com.kanomiya.mcmod.kmagic.magic.material.MagicMaterialRegistry;
import com.kanomiya.mcmod.kmagic.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.magic.status.MagicStatusEntity;
import com.kanomiya.mcmod.kmagic.magic.status.MagicStatusPlayer;
import com.kanomiya.mcmod.kmagic.magic.status.base.IMagicItem;
import com.kanomiya.mcmod.kmagic.magic.status.base.IMagicObject;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.Constants.NBT;

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


	public static boolean isMagicOwner(Entity entity) {
		return (entity instanceof IMagicObject || entity instanceof EntityPlayer);
	}

	public static boolean isMagicOwner(ItemStack stack) {
		if (stack == null) return false;
		return (stack.getItem() instanceof IMagicItem);
	}


	public static boolean isMagicOwner(TileEntity tileEntity) {
		return (tileEntity instanceof IMagicObject);
	}


	public static MagicStatus getMagicStatus(ItemStack stack) {
		return MagicStatus.getInstance(stack, getMagicNBT(stack));
	}

	public static void setMagicStatus(ItemStack stack, MagicStatus status) {
		NBTTagCompound magicNbt = new NBTTagCompound();
		status.writeToNBT(magicNbt);
		setMagicNBT(stack, magicNbt);
	}


	public static MagicStatusPlayer getMagicStatus(EntityPlayer player) {
		return MagicStatus.getInstance(player);
	}

	public static MagicStatusEntity getMagicStatus(Entity entity) {
		return MagicStatus.getInstance(entity);
	}

	public static MagicStatus getMagicStatus(TileEntity tileEntity) {
		return MagicStatus.getInstance(tileEntity);
	}




	protected static NBTTagCompound getNBT(ItemStack stack) {
		if (! stack.hasTagCompound()) return new NBTTagCompound();
		else return stack.getTagCompound();
	}

	public static boolean hasMagicNBT(ItemStack stack) {
		return getNBT(stack).hasKey(KMagicAPI.STR_DATANAME, NBT.TAG_COMPOUND);
	}

	public static NBTTagCompound getMagicNBT(ItemStack stack) {
		boolean initFlag = (! hasMagicNBT(stack) && isMagicOwner(stack));
		NBTTagCompound magicNbt = getNBT(stack).getCompoundTag(KMagicAPI.STR_DATANAME);

		if (initFlag) {
			MagicStatus status = MagicStatus.getInstance(stack); //TODO: , magicNbt);
			status.evaluate();

			status.writeToNBT(magicNbt);
			setMagicStatus(stack, status);
		}

		return magicNbt;
	}

	public static void setMagicNBT(ItemStack stack, NBTTagCompound magicNbt) {
		NBTTagCompound stackNbt = getNBT(stack);
		stackNbt.setTag(STR_DATANAME, magicNbt);

		stack.setTagCompound(stackNbt);
	}


	public static ItemStack fullMp(ItemStack stack) {
		NBTTagCompound nbt = KMagicAPI.getMagicNBT(stack);

		nbt.setInteger("mp", nbt.getInteger("maxMp"));

		KMagicAPI.setMagicNBT(stack, nbt);

		return stack;
	}

	public static ItemStack randomMp(ItemStack stack, Random rand, float minRate, float maxRate) {
		if (1.0f < minRate) minRate = 1.0f;
		if (1.0f < maxRate) maxRate = 1.0f;

		NBTTagCompound nbt = KMagicAPI.getMagicNBT(stack);

		int maxMp = getMaxMp(nbt);
		int value = Math.min(maxMp, MathHelper.getRandomIntegerInRange(rand, (int) Math.floor(minRate *maxMp), (int) Math.floor(maxRate *maxMp)));
		nbt.setInteger("mp", value);

		KMagicAPI.setMagicNBT(stack, nbt);

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
