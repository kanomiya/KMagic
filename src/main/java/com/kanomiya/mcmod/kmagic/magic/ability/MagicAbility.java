package com.kanomiya.mcmod.kmagic.magic.ability;

import com.kanomiya.mcmod.kmagic.magic.status.MagicStatus;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


/**
 * @author Kanomiya
 *
 */
public abstract class MagicAbility {

	public final MagicStatus status;

	public MagicAbility(MagicStatus parStatus) {
		status = parStatus;
	}



	public abstract boolean shouldExecute(World worldIn);
	public boolean continueExecuting(World worldIn) { return shouldExecute(worldIn); }


	public void setup(World worldIn) {  }
	public void reset(World worldIn) {  }

	public void update(World worldIn) {  }


	public void writeToNBT(NBTTagCompound nbt) {

	}

	public void readFromNBT(NBTTagCompound nbt) {

	}



}