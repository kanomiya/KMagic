package com.kanomiya.mcmod.kmagic.api.tileentity.signal;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.util.EnumFacing;

/**
 * @author Kanomiya
 *
 */
public class SignalData {

	private int data;
	private List<IKMSignal> receivedList = Lists.newArrayList();

	public SignalData(int parData) {
		data = parData;
	}

	public int getData() { return data; }


	public SignalData newCopy() {
		return new SignalData(data);
	}



	public boolean sendTo(IKMSignal sender, Object target, EnumFacing to) {
		if (target == null || (! (target instanceof IKMSignal)) || receivedList.contains(target)) return false;

		if (sender != null) receivedList.add(sender);


		IKMSignal signal = (IKMSignal) target;
		if (signal.receiveSignal(this, to)) {
			receivedList.add(signal);
			return true;
		}

		return false;
	}

}
