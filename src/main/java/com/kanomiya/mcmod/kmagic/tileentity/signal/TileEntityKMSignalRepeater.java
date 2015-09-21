package com.kanomiya.mcmod.kmagic.tileentity.signal;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import com.kanomiya.mcmod.kmagic.api.tileentity.signal.IKMSignal;
import com.kanomiya.mcmod.kmagic.api.tileentity.signal.SignalData;
import com.kanomiya.mcmod.kmagic.api.tileentity.signal.SignalUtils;

/**
 * @author Kanomiya
 *
 */
public class TileEntityKMSignalRepeater extends TileEntity implements IKMSignal {

	public TileEntityKMSignalRepeater() {

	}




	@Override public boolean receiveSignal(SignalData data, EnumFacing from) {
		SignalUtils.sendSignal(data, worldObj, this, pos);
		return true;
	}



}
