package com.kanomiya.mcmod.kmagic.tileentity.signal;

import net.minecraft.util.EnumFacing;

/**
 * @author Kanomiya
 *
 */
public interface IKMSignal {

	public boolean receiveSignal(SignalData data, EnumFacing from);



}
