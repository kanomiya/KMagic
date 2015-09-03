package com.kanomiya.mcmod.kmagic.magic.status.wrapper;

import com.kanomiya.mcmod.kmagic.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.magic.status.MagicStatusEntity;
import com.kanomiya.mcmod.kmagic.magic.status.base.IMagicObject;

import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Kanomiya
 *
 */
public class PlayerWrapper implements IMagicObject {

	protected MagicStatusEntity status;
	protected EntityPlayer player;

	public PlayerWrapper(EntityPlayer parPlayer) {
		player = parPlayer;
	}

	public EntityPlayer getPlayer() { return player; }


	@Override public MagicStatusEntity getMagicStatus() { return status; }

	@Override public void initMagicStatus(MagicStatus status) {

	}


}
