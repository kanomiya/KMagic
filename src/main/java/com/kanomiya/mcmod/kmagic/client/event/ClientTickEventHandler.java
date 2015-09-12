package com.kanomiya.mcmod.kmagic.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.kanomiya.mcmod.kmagic.KMKeys;

/**
 *
 * @author Kanomiya
 *
 */
@SideOnly(Side.CLIENT)
public class ClientTickEventHandler {
	public static final ClientTickEventHandler INSTANCE = new ClientTickEventHandler();

	private Minecraft mc = Minecraft.getMinecraft();


	private ClientTickEventHandler() {

	}

	/**
	 *
	 * @param event TickEvent.ClientTickEvent
	 */
	@SubscribeEvent public void onClientTickEvent(TickEvent.ClientTickEvent event) {
		if (event.side.isServer()) return ;

		updateSpelling(event);

	}



	public void updateSpelling(TickEvent.ClientTickEvent event) {

		if (mc.theWorld != null && mc.thePlayer != null) {
			if (! mc.thePlayer.isDead) {
				KeyBinding spellKey = KMKeys.spellingKey;

				if (spellKey.isKeyDown()) {
					mc.displayGuiScreen(new GuiChat("\\"));
				}

			}
		}

	}




}
