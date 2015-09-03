package com.kanomiya.mcmod.kmagic.magic.spell;

import java.util.Map;

import com.google.common.collect.Maps;
import com.kanomiya.mcmod.kmagic.magic.spell.instant.BaseMagic;

/**
 * @author Kanomiya
 *
 */
public class BaseMagicRegistry {

	private static Map<String, BaseMagic> baseMagics = Maps.newHashMap();

	public static void registerBaseMagic(BaseMagic magic) {
		baseMagics.put(magic.getId(), magic);
	}

	public static BaseMagic getBaseMagic(String id) {
		return baseMagics.get(id);
	}



}
