package com.kanomiya.mcmod.kmagic.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import com.kanomiya.mcmod.kmagic.KMagic;

/**
 * @author Kanomiya
 *
 */
public class PacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(KMagic.MODID);

	public static void init() {
		INSTANCE.registerMessage(MessageMagicStatusToClient.class, MessageMagicStatusToClient.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(MessageSettingMagicSlotToServer.class, MessageSettingMagicSlotToServer.class, 1, Side.SERVER);

	}

}
