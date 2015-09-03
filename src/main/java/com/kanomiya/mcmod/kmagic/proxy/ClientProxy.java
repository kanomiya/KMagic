package com.kanomiya.mcmod.kmagic.proxy;

import org.lwjgl.input.Keyboard;

import com.kanomiya.mcmod.kmagic.KMagic;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Kanomiya
 *
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	public static final KeyBinding spellingKey = new KeyBinding("kmagic.key.spellingKey", Keyboard.KEY_BACKSLASH, KMagic.MODID);
	public static final KeyBinding magicKey1 = new KeyBinding("kmagic.key.magicKey1", Keyboard.KEY_Z, KMagic.MODID);
	public static final KeyBinding magicKey2 = new KeyBinding("kmagic.key.magicKey2", Keyboard.KEY_X, KMagic.MODID);
	public static final KeyBinding magicKey3 = new KeyBinding("kmagic.key.magicKey3", Keyboard.KEY_C, KMagic.MODID);
	public static final KeyBinding magicKey4 = new KeyBinding("kmagic.key.magicKey4", Keyboard.KEY_V, KMagic.MODID);

	@Override public void preInit() {
		ClientRegistry.registerKeyBinding(spellingKey);
		ClientRegistry.registerKeyBinding(magicKey1);
		ClientRegistry.registerKeyBinding(magicKey2);
		ClientRegistry.registerKeyBinding(magicKey3);
		ClientRegistry.registerKeyBinding(magicKey4);

	}


	@Override public void init() {
		/*
		RenderMagicShot ms = new RenderMagicShot();

		// HAVE TO REGISTER to KMEntities TOO

		RenderingRegistry.registerEntityRenderingHandler(EntityMagicShotFire.class, ms);
		RenderingRegistry.registerEntityRenderingHandler(EntityMagicShotWind.class, ms);
		RenderingRegistry.registerEntityRenderingHandler(EntityMagicShotWater.class, ms);
		RenderingRegistry.registerEntityRenderingHandler(EntityMagicShotLand.class, ms);
		RenderingRegistry.registerEntityRenderingHandler(EntityMagicShotLight.class, ms);
		RenderingRegistry.registerEntityRenderingHandler(EntityMagicShotSpace.class, ms);

		*/

	}


}
