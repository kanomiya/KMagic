package com.kanomiya.mcmod.kmagic.api.magic.spell.instant;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;

/**
 * @author Kanomiya
 *
 */
public abstract class InstantMagicBase {

	public abstract boolean canExecute(Entity speller, MagicStatus status);
	public abstract void execute(World worldIn, Entity speller, MagicStatus status);

}
