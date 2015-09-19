package com.kanomiya.mcmod.kmagic.api.magic.status;

import com.kanomiya.mcmod.kmagic.api.magic.status.wrapper.PlayerWrapper;

import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Kanomiya
 *
 */
public class MagicStatusPlayer extends MagicStatusEntity {
	protected EntityPlayer player;

	public MagicStatusPlayer(EntityPlayer parPlayer) {
		super(new PlayerWrapper(parPlayer), parPlayer);

		player = parPlayer;
	}






	// *----------------------------------------------------------------------------------------------*
	//
	// *----------------------------------------------------------------------------------------------*

	public EntityPlayer getPlayer() { return player; }








}
