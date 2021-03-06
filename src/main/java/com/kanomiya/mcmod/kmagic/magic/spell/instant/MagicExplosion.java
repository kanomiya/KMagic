package com.kanomiya.mcmod.kmagic.magic.spell.instant;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.magic.spell.instant.InstantMagicBase;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;

/**
 * @author Kanomiya
 *
 */
public class MagicExplosion extends InstantMagicBase {

	@Override public boolean canExecute(Entity speller, MagicStatus status) {
		return KMagicAPI.isFull(status.getMp(), 10);
	}

	@Override public void execute(World worldIn, Entity speller, MagicStatus status) {
		MagicStatus.dealMp(status, null, 10, false, false);
		worldIn.createExplosion(speller, speller.posX, speller.posY, speller.posZ, 2.0f, true);
	}

}
