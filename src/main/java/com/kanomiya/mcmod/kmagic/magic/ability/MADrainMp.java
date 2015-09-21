package com.kanomiya.mcmod.kmagic.magic.ability;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.kanomiya.mcmod.kmagic.KMagic;
import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.magic.ability.MagicAbility;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;

/**
 * @author Kanomiya
 *
 */
public class MADrainMp extends MagicAbility {

	public MADrainMp(MagicStatus parStatus) {
		super(parStatus);
	}

	@Override public void onInteractedWith(World worldIn, Entity interactor) {

		MagicStatus intrStatus = KMagicAPI.getMagicStatus(interactor);

		if (intrStatus != null && ! KMagicAPI.mpIsFull(status)) {

			if (! KMagicAPI.mpIsLack(intrStatus)) {
				MagicStatus.dealMp(intrStatus, status, 1, false, false);

				if (status.getMagicObject() instanceof TileEntity) {
					((TileEntity) status.getMagicObject()).markDirty();
					KMagic.logger.info("interact " + status.getMp() + " / " + status.getMaxMp());
				}

			}

		}

	}



}
