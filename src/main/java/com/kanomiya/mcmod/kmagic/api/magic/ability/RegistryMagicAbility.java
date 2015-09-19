package com.kanomiya.mcmod.kmagic.api.magic.ability;

import java.util.Map;

import com.google.common.collect.Maps;
import com.kanomiya.mcmod.kmagic.KMagic;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;

/**
 * @author Kanomiya
 *
 */
public class RegistryMagicAbility {
	private RegistryMagicAbility() {  }


	private static Map<String, Class<? extends MagicAbility>> idToAbility = Maps.newHashMap();
	private static Map<Class<? extends MagicAbility>, String> abilityToId = Maps.newHashMap();

	public static void registerAbilityClass(String id, Class<? extends MagicAbility> clazz) {
		idToAbility.put(id, clazz);
		abilityToId.put(clazz, id);
	}

	public static Class<? extends MagicAbility> getAbilityClass(String id) {
		return idToAbility.get(id);
	}

	public static MagicAbility getAbilityInstance(String id, MagicStatus status) {
		Class<? extends MagicAbility> clazz = getAbilityClass(id);
		if (clazz != null) {
			try {
				return clazz.getConstructor(MagicStatus.class).newInstance(status);
			} catch (Exception e) {
				KMagic.logger.error(e.getMessage());
			}

		}

		return null;
	}



	public static String getAbilityId(Class<? extends MagicAbility> atClass) {
		return abilityToId.get(atClass);
	}

	public static String getAbilityId(MagicAbility ability) {
		return getAbilityId(ability.getClass());
	}











}
