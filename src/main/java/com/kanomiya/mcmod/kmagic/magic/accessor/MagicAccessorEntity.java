package com.kanomiya.mcmod.kmagic.magic.accessor;

import com.kanomiya.mcmod.kmagic.magic.status.MagicStatus;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * @author Kanomiya
 *
 */
public class MagicAccessorEntity extends IMagicAccessor {
	protected Entity entity;

	public MagicAccessorEntity(MagicStatus parStatus, World parWorld, Entity parEntity) {
		super(parStatus, parWorld);

		entity = parEntity;

	}

	public Entity getEntity() { return entity; }



	@Override public double getX() { return entity.posX; }
	@Override public double getY() { return entity.posY; }
	@Override public double getZ() { return entity.posZ; }


}
