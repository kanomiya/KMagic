package com.kanomiya.mcmod.kmagic.tileentity.signal;

import com.kanomiya.mcmod.kmagic.block.BlockKMSignalPortal;

import net.minecraft.util.EnumFacing;

/**
 * @author Kanomiya
 *
 */
public class TileEntityKMSignalPortal extends TileEntityKMSignalRepeater implements IKMSignal {

	public TileEntityKMSignalPortal() {

	}

	@Override public boolean receiveSignal(SignalData data, EnumFacing from) {
		super.receiveSignal(data, from);

		worldObj.setBlockState(pos, worldObj.getBlockState(pos).withProperty(BlockKMSignalPortal.ACTIVATED, Boolean.TRUE));

		return true;
	}

}