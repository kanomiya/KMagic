package com.kanomiya.mcmod.kmagic;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

/**
 * @author Kanomiya
 *
 */
@SideOnly(Side.CLIENT)
public class KMKeys {
	public static final KeyBinding spellingKey = new KeyBinding("kmagic.key.spellingKey", Keyboard.KEY_BACKSLASH, KMagic.MODID);
	public static final KeyBinding magicKey1 = new KeyBinding("kmagic.key.magicKey1", Keyboard.KEY_C, KMagic.MODID);
	public static final KeyBinding magicKey2 = new KeyBinding("kmagic.key.magicKey2", Keyboard.KEY_V, KMagic.MODID);
	public static final KeyBinding magicKey3 = new KeyBinding("kmagic.key.magicKey3", Keyboard.KEY_F, KMagic.MODID);
	public static final KeyBinding magicKey4 = new KeyBinding("kmagic.key.magicKey4", Keyboard.KEY_X, KMagic.MODID);

	public static void init() {
		ClientRegistry.registerKeyBinding(spellingKey);
		ClientRegistry.registerKeyBinding(magicKey1);
		ClientRegistry.registerKeyBinding(magicKey2);
		ClientRegistry.registerKeyBinding(magicKey3);
		ClientRegistry.registerKeyBinding(magicKey4);

	}

}
