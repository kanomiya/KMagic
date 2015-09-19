package com.kanomiya.mcmod.kmagic.api.magic.event;

import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.magic.spell.InstantParser;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatusEntity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Kanomiya
 *
 */
public class MagicSpellEventHandler {
	public static final MagicSpellEventHandler INSTANCE = new MagicSpellEventHandler();

	private MagicSpellEventHandler() {  }





	@SubscribeEvent public void onServerChat(ServerChatEvent event) {

		if (event.message.startsWith("\\")) {
			event.setCanceled(true);

			String spelling = event.message.substring(1);

			// MagicStatus entityStatus = KMagicAPI.getMagicProperties(event.player).getMagicStatus();

			ItemStack heldStack = event.player.getHeldItem();
			MagicStatus stackStatus = (heldStack != null) ? KMagicAPI.getMagicStatus(heldStack) : null;

			if (stackStatus != null) {
				boolean success = InstantParser.INSTANCE.spell(event.player.worldObj, event.player, stackStatus, spelling);

				if (stackStatus.isUpdated()) KMagicAPI.setMagicStatus(heldStack, stackStatus);


				event.player.addChatMessage(new ChatComponentText("Spelled \" " + spelling + " \"" + ((success) ? "" : " (Miss)")));

			}

		}

	}


	@SubscribeEvent public void onLivingFall(LivingFallEvent event) {

		if (KMagicAPI.isMagicOwner(event.entity)) {
			MagicStatusEntity status = KMagicAPI.getMagicStatus(event.entity);

			// TODO: if (status != null && status.getMagicAttributes().hasMagicAttribute(MAFlight.class)) event.setCanceled(true);


		}

	}

}
