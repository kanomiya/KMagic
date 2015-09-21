package com.kanomiya.mcmod.kmagic.api.magic.status;

import net.minecraft.entity.player.EntityPlayer;

import com.kanomiya.mcmod.kmagic.api.magic.status.wrapper.PlayerWrapper;

/**
 * @author Kanomiya
 *
 */
public class MagicStatusPlayer extends MagicStatusEntity {
	protected EntityPlayer player;

	protected MagicStatusPlayer(EntityPlayer parPlayer) {
		super(new PlayerWrapper(parPlayer), parPlayer);

		player = parPlayer;
	}






	// *----------------------------------------------------------------------------------------------*
	//
	// *----------------------------------------------------------------------------------------------*

	public EntityPlayer getPlayer() { return player; }








}
