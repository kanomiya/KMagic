package com.kanomiya.mcmod.kmagic.api.magic.spell.instant;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;

/**
 * @author Kanomiya
 *
 */
public abstract class BaseMagic {

	public abstract void execute(World worldIn, Entity speller, double x, double y, double z, MagicStatus status, int power);

	public abstract String getId();

}
