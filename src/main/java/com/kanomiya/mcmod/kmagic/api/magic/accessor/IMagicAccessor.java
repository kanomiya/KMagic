package com.kanomiya.mcmod.kmagic.api.magic.accessor;

import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;

import net.minecraft.world.World;

/**
 * @author Kanomiya
 *
 */
public abstract class IMagicAccessor {

	public final MagicStatus status;
	public final World worldObj;

	public IMagicAccessor(MagicStatus parStatus, World parWorld) {
		status = parStatus;
		worldObj = parWorld;

	}

	public abstract double getX();
	public abstract double getY();
	public abstract double getZ();


}
