package com.kanomiya.mcmod.kmagic.magic.spell.instant;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.kanomiya.mcmod.kmagic.api.magic.spell.instant.BaseMagic;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;

/**
 * @author Kanomiya
 *
 */
public class BaseMagicExplosion extends BaseMagic {

	public final boolean protectSpeller, canBreaking, isFire;

	public BaseMagicExplosion(boolean parProtectSpeller, boolean parCanBreaking, boolean parIsFire) {
		protectSpeller = parProtectSpeller;
		canBreaking = parCanBreaking;
		isFire = parIsFire;
	}

	@Override public void execute(World worldIn, Entity speller, double x, double y, double z, MagicStatus status, int power) {
		if (20 < power) power = 20;
		float size = power *1.5f;

		worldIn.newExplosion((protectSpeller) ? speller : null, x, y, z, size, canBreaking, isFire);

	}

	@Override public String getId() {
		String id = "explosion";
		if (protectSpeller) id += "_protect";
		if (canBreaking) id += "_breaking";
		if (isFire) id += "_fire";

		return id;
	}

}
