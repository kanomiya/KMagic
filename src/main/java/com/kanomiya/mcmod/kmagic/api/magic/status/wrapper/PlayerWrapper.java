package com.kanomiya.mcmod.kmagic.api.magic.status.wrapper;

import net.minecraft.entity.player.EntityPlayer;

import com.kanomiya.mcmod.kmagic.api.magic.material.MagicMaterial;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatusEntity;
import com.kanomiya.mcmod.kmagic.api.magic.status.base.IMagicObject;
import com.kanomiya.mcmod.kmagic.magic.ability.MANaturalMpHealing;

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
		status.getMagicMaterials().add(MagicMaterial.PLAYER);
		status.getAbilityHolder().addAbility(new MANaturalMpHealing(status));

	}


}
