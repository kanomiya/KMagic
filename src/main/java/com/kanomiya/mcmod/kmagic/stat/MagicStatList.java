package com.kanomiya.mcmod.kmagic.stat;

import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatBasic;
import net.minecraft.util.ChatComponentTranslation;

// StatList
/**
 *
 * @author Kanomiya
 *
 */
public final class MagicStatList {
	public static StatBase releasedMP = (new StatBasic("kmagic.stat.releasedMP", new ChatComponentTranslation("kmagic.stat.releasedMP", new Object[0])));

	public static void register() {
		releasedMP.registerStat();

	}

}
