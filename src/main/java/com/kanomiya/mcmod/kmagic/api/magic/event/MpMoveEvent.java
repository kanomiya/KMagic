package com.kanomiya.mcmod.kmagic.api.magic.event;

import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @author Kanomiya
 *
 */
public abstract class MpMoveEvent extends Event {

	public MagicStatus vendor;
	public MagicStatus buyer;
	public int amount;

	public MpMoveEvent(MagicStatus vendor, MagicStatus buyer, int amount) {
		this.vendor = vendor;
		this.buyer = buyer;
		this.amount = amount;
	}

	@Cancelable
	public static class Pre extends MpMoveEvent {
		public Pre(MagicStatus vendor, MagicStatus buyer, int amount) {
			super(vendor, buyer, amount);
		}


	}

	public static class Post extends MpMoveEvent {
		public Post(MagicStatus vendor, MagicStatus buyer, int amount) {
			super(vendor, buyer, amount);
		}

	}

}
