package com.kanomiya.mcmod.kmagic.tileentity.signal;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.WorldServer;

import com.kanomiya.mcmod.kanomiyacore.util.bit.BitFieldHelper;
import com.kanomiya.mcmod.kmagic.KMConfig;
import com.kanomiya.mcmod.kmagic.api.tileentity.signal.IKMSignal;
import com.kanomiya.mcmod.kmagic.api.tileentity.signal.SignalData;
import com.kanomiya.mcmod.kmagic.api.tileentity.signal.SignalUtils;
import com.kanomiya.mcmod.kmagic.block.BlockKMSignalPortal;
import com.kanomiya.mcmod.kmagic.world.TeleporterKMagic;

/**
 * @author Kanomiya
 *
 */
public class TileEntityKMSignalPortal extends TileEntityKMSignalRepeater implements IKMSignal {

	public TileEntityKMSignalPortal() {

	}

	@Override public boolean receiveSignal(SignalData data, EnumFacing from) {
		super.receiveSignal(data, from);

		boolean flag = BitFieldHelper.intToBool(data.getData());
		worldObj.setBlockState(pos, worldObj.getBlockState(pos).withProperty(BlockKMSignalPortal.ACTIVATED, flag));

		return true;
	}

	public void action(Entity entityIn) {
		SignalUtils.sendSignal(new SignalData(0), worldObj, this, pos);
		worldObj.setBlockState(pos, worldObj.getBlockState(pos).withProperty(BlockKMSignalPortal.ACTIVATED, Boolean.FALSE));

		int targetDimId = -1;

		if (entityIn.dimension == 0) targetDimId = KMConfig.DIMID_KMAGIC;
		else if (entityIn.dimension == KMConfig.DIMID_KMAGIC) targetDimId = 0;

		if (targetDimId == -1) return ;


		entityIn.timeUntilPortal = entityIn.getPortalCooldown();
		entityIn.setInPortal();

		// BlockPortal
		MinecraftServer mcServer = MinecraftServer.getServer();

		ServerConfigurationManager scmanager = mcServer.getConfigurationManager();
		// WorldServer worldServer = mcServer.worldServerForDimension(entityIn.dimension);
		WorldServer targetWorldServer = mcServer.worldServerForDimension(targetDimId);

		if (! (entityIn instanceof EntityPlayerSP)) {

			if (entityIn instanceof EntityPlayerMP) {
				scmanager.transferPlayerToDimension((EntityPlayerMP) entityIn, targetDimId, new TeleporterKMagic(targetWorldServer));

			} else {

				// worldObj.newExplosion(entityIn, pos.getX(), pos.getY() +1d, pos.getZ(), 3.0f, false, false);

				// 1:
				// TODO: scmanager.transferEntityToWorld(entityIn, targetDimId, mcServer.worldServerForDimension(entityIn.dimension), targetWorldServer, new TeleporterKMagic(targetWorldServer));

				// 2:
				// entityIn.dimension = targetDimId;
				// new TeleporterKMagic(targetWorldServer).placeInPortal(entityIn, entityIn.rotationYaw);

				// 3:
				// entityIn.travelToDimension(targetDimId);
			}

		}

	}

}
