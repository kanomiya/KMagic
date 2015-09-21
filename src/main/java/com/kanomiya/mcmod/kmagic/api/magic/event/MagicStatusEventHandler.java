package com.kanomiya.mcmod.kmagic.api.magic.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.kanomiya.mcmod.kmagic.KMagic;
import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatusEntity;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatusPlayer;
import com.kanomiya.mcmod.kmagic.api.magic.status.RegistryMSRating;
import com.kanomiya.mcmod.kmagic.stat.MagicStatList;

/**
 * @author Kanomiya
 *
 */
public class MagicStatusEventHandler {
	public static final MagicStatusEventHandler INSTANCE = new MagicStatusEventHandler();

	// PlayerEvent
	private MagicStatusEventHandler() {  }


	public MagicStatusEntity registerProperties(Entity entity) {

		if (KMagicAPI.isMagicOwner(entity)) {
			MagicStatusEntity status = KMagicAPI.getMagicStatus(entity);

			if (status == null) {
				if (entity instanceof EntityPlayer) {
					status = new MagicStatusPlayer((EntityPlayer) entity);
				} else {
					status = new MagicStatusEntity(entity);
				}

				if (entity.registerExtendedProperties(KMagicAPI.STR_DATANAME, status).equals("")) {
					KMagic.logger.error("Failed to register MagicStatus to MagicEntity (id: " + entity.getEntityId() + ")");
				}

			}

			return status;
		}

		return null;
	}

	@SubscribeEvent public void onEntityConstructing(EntityEvent.EntityConstructing event) {

		if (KMagicAPI.isMagicOwner(event.entity)) {
			MagicStatusEntity status = KMagicAPI.getMagicStatus(event.entity);
			if (status == null) {
				registerProperties(event.entity);
			}
		}
	}

	@SubscribeEvent public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		if (KMagicAPI.isMagicOwner(event.entity)) {
			MagicStatusEntity status = KMagicAPI.getMagicStatus(event.entity);
			if (status == null) {
				status = registerProperties(event.entity);
			}

			if (status != null) {
				status.onUpdate(event.entity.worldObj);
			}
		}

	}


	@SubscribeEvent public void onEntityItemPickup(EntityItemPickupEvent event) {

	}



	@SubscribeEvent public void onAchievementEvent(AchievementEvent event) {
		KMagicAPI.getMagicStatus(event.entity).evaluate();
	}

	@SubscribeEvent public void onPlayerPickupXpEvent(PlayerPickupXpEvent event) {
		KMagicAPI.getMagicStatus(event.entity).evaluate();
	}



	@SubscribeEvent public void onPlayerClone(PlayerEvent.Clone event) {
		if (event.wasDeath) {
			EntityPlayer oldPlayer = event.original;

			if (oldPlayer.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
				MagicStatusEntity oldProp = KMagicAPI.getMagicStatus(oldPlayer);
				MagicStatusEntity newProp = KMagicAPI.getMagicStatus(event.entityPlayer);

				NBTTagCompound nbt = new NBTTagCompound();
				oldProp.saveNBTData(nbt);
				newProp.loadNBTData(nbt);

			}

		}
	}





	@SubscribeEvent public void onMagicStatusInit(MagicStatusInitEvent event) {

		if (event.status instanceof MagicStatusPlayer) {
			EntityPlayer player = ((MagicStatusPlayer) event.status).getPlayer();

			if (player instanceof EntityPlayerMP) {
				EntityPlayerMP playerMp = (EntityPlayerMP) player;

				event.extraMaxMp += playerMp.experienceLevel *10;

				event.extraMaxMp += RegistryMSRating.evaluate(playerMp.getStatFile());

			}

		}

	}

	@SubscribeEvent public void onMpMove(MpMoveEvent.Post event) {

		MagicStatus status = null;
		// if (0 > event.amount && event.buyer != null) status = event.buyer.getMagicStatus();
		if (0 < event.amount && event.vendor != null) status = event.vendor;

		if (status != null && status instanceof MagicStatusPlayer) {
			((MagicStatusPlayer) status).getPlayer().addStat(MagicStatList.releasedMP, Math.abs(event.amount));;
		}

	}



}
