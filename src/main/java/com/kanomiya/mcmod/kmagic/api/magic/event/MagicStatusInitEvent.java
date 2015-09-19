package com.kanomiya.mcmod.kmagic.api.magic.event;

import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;

import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @author Kanomiya
 *
 */
public class MagicStatusInitEvent extends Event {
	public final MagicStatus status;
	public int extraMaxMp = 0;

	public MagicStatusInitEvent(MagicStatus parStatus) {
		status = parStatus;

	}



}
