package com.kanomiya.mcmod.kmagic.api.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.api.magic.status.base.IMagicObject;

/**
 * @author Kanomiya
 *
 */
public abstract class TileEntityMagicBase extends TileEntity implements IMagicObject {
	public final MagicStatus status = MagicStatus.getInstance(this);



	@Override public MagicStatus getMagicStatus() {
		return status;
	}

	@Override public void initMagicStatus(MagicStatus status) {  }






	@Override public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		NBTTagCompound customTag = getTileData();
		status.readFromNBT(customTag.getCompoundTag(KMagicAPI.STR_DATANAME));

	}

	@Override public void writeToNBT(NBTTagCompound compound) {
		NBTTagCompound magicNbt = new NBTTagCompound();
		status.writeToNBT(magicNbt);
		getTileData().setTag(KMagicAPI.STR_DATANAME, magicNbt);

		super.writeToNBT(compound);
	}


	@Override public Packet getDescriptionPacket() {
		// status.sync(worldObj.isRemote);

		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);

		return new S35PacketUpdateTileEntity(pos, 1, nbt);
	}

	@Override public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

}
