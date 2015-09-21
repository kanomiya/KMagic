package com.kanomiya.mcmod.kmagic.api.magic.ability;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import com.kanomiya.mcmod.kmagic.api.magic.status.MagicAbilityHolder;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;


/**
 *
 * @see RegistryMagicAbility
 * @author Kanomiya
 *
 */
public abstract class MagicAbility {

	public final MagicStatus status;

	public MagicAbility(MagicStatus parStatus) {
		status = parStatus;
	}


	public boolean shouldRemove(World worldIn) { return false; }

	public void update(World worldIn) {  }

	/**
	 *
	 * @see MagicAbilityHolder#onSpawn(World)
	 *
	 * @param worldIn
	 */
	public void onSpawn(World worldIn) {  }

	/**
	 *
	 * TileEntityの右クリック時<br>
	 * Entityの右クリック時
	 *
	 * @see MagicAbilityHolder#onInteractedWith(World, Entity)
	 *
	 * @param worldIn
	 * @param interactor
	 */
	public void onInteractedWith(World worldIn, Entity interactor) {  }

	/**
	 *
	 * @see MagicAbilityHolder#onFall(World, LivingFallEvent)
	 *
	 * @param worldIn
	 * @param event
	 */
	public void onFall(World worldIn, LivingFallEvent event) {  }

	/**
	 *
	 * @see MagicAbilityHolder#onDeath(World)
	 *
	 * @param worldIn
	 */
	public void onDeath(World worldIn) {  }

	public void writeToNBT(NBTTagCompound nbt) {

	}

	public void readFromNBT(NBTTagCompound nbt) {

	}



}
