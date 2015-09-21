package com.kanomiya.mcmod.kmagic.event;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

import com.kanomiya.mcmod.kmagic.KMagic;
import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.magic.event.MagicStatusInitEvent;
import com.kanomiya.mcmod.kmagic.api.magic.event.MpMoveEvent;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicAbilityHolder;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatusEntity;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatusPlayer;
import com.kanomiya.mcmod.kmagic.api.magic.status.RegistryMSRating;
import com.kanomiya.mcmod.kmagic.network.MessageMagicStatusToClient;
import com.kanomiya.mcmod.kmagic.network.PacketHandler;
import com.kanomiya.mcmod.kmagic.stat.MagicStatList;

/**
 * @author Kanomiya
 *
 */
public class MagicStatusEventHandler {
	public static final MagicStatusEventHandler INSTANCE = new MagicStatusEventHandler();

	// PlayerEvent
	private MagicStatusEventHandler() {  }

	// @SideOnly(Side.SERVER)
	public void sync(MagicStatusEntity status) {
		// TODO: status.evaluate();

		PacketHandler.INSTANCE.sendToAll(new MessageMagicStatusToClient(status));
		status.onSync();
	}

	// @SideOnly(Side.CLIENT)
	public void request(Entity entity) {
		// TODO: PacketHandler.INSTANCE.sendToServer(new MessageMagicStatusRequestToServer(entity));
	}


	public MagicStatusEntity registerProperties(Entity entity) {
		MagicStatusEntity status = KMagicAPI.getMagicStatus(entity);

		if (status == null) {
			status = MagicStatus.newInstance(entity);

			if (entity.registerExtendedProperties(KMagicAPI.STR_DATANAME, status).equals("")) {
				KMagic.logger.error("Failed to register MagicStatus to MagicEntity (id: " + entity.getEntityId() + ")");
			}

		}

		return status;
	}

	@SubscribeEvent public void onEntityConstructing(EntityEvent.EntityConstructing event) {
		if (! KMagicAPI.isMagicObject(event.entity)) return ;

		MagicStatus status = KMagicAPI.getMagicStatus(event.entity);
		if (status == null) registerProperties(event.entity);

	}

	@SubscribeEvent public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		if (! KMagicAPI.isMagicObject(event.entity)) return ;

		MagicStatusEntity status = KMagicAPI.getMagicStatus(event.entity);
		if (status == null) return ;

		status.onUpdate(event.entity.worldObj);

		if (! event.entity.worldObj.isRemote && status.isUpdated()) sync(status);



	}


	@SubscribeEvent public void onEntityItemPickup(EntityItemPickupEvent event) {

	}


	/**
	 *
	 * BlockのInteraction
	 *
	 * @see ForgeEventFactory#onPlayerInteract(EntityPlayer, PlayerInteractEvent.Action, World, BlockPos, EnumFacing)
	 * @see ItemInWorldManager#onBlockClicked(BlockPos, EnumFacing)
	 * @see NetHandlerPlayServer#processPlayerBlockPlacement(C08PacketPlayerBlockPlacement)
	 * @see Minecraft#rightClickMouse()
	 *
	 * @param event
	 */
	@SubscribeEvent public void onPlayerInteractEvent(PlayerInteractEvent event) {
		if (event.pos == null) return ;

		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			TileEntity tile = event.world.getTileEntity(event.pos);

			if (tile != null) {
				MagicStatus status = KMagicAPI.getMagicStatus(tile);

				if (status != null) {
					MagicAbilityHolder holder = status.getAbilityHolder();

					holder.onInteractedWith(event.world, event.entity);

					// TODO:
					tile.markDirty();
					event.world.markBlockForUpdate(event.pos);
				}
			}

		}


	}

	/**
	 *
	 * EntityのInteraction
	 *
	 * @see ForgeEventFactory#canInteractWith(EntityPlayer, Entity)
	 * @see EntityPlayer#interactWith(Entity)
	 *
	 * @param event
	 */
	@SubscribeEvent public void onEntityInteractEvent(EntityInteractEvent event) {
		if (! KMagicAPI.isMagicObject(event.entity)) return ;

		MagicStatus status = KMagicAPI.getMagicStatus(event.target);

		if (status != null) status.getAbilityHolder().onInteractedWith(event.entity.worldObj, event.entity);

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


	@SubscribeEvent public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (! KMagicAPI.isMagicObject(event.entity)) return ;

		if (event.world.isRemote) request(event.entity);

		// if (! event.world.isRemote) sync(KMagicAPI.getMagicStatus(event.entity));

	}

	@SubscribeEvent public void respawnEvent(PlayerRespawnEvent event) {
		if (! event.player.worldObj.isRemote) sync(KMagicAPI.getMagicStatus(event.player));
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
