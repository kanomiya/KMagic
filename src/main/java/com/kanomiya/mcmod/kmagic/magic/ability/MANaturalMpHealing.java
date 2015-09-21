package com.kanomiya.mcmod.kmagic.magic.ability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.magic.ability.MagicAbility;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;

/**
 * @author Kanomiya
 *
 */
public class MANaturalMpHealing extends MagicAbility {
	private static final int tickInterval = 100;

	private int tick;

	public MANaturalMpHealing(MagicStatus parStatus) {
		super(parStatus);
	}

	@Override public void update(World worldIn) {
		tick ++;

		if (tickInterval <= tick) {
			if (! KMagicAPI.mpIsFull(status)) {
				MagicStatus.dealMp(null, status, 1, true, false);
			}

			tick = 0;
		}

	}


	@Override public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("tick", tick);
	}

	@Override public void readFromNBT(NBTTagCompound nbt) {
		tick = nbt.getInteger("tick");
	}



}
