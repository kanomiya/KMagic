package com.kanomiya.mcmod.kmagic.magic.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

/**
 * @author Kanomiya
 *
 */
public class MagicDamageSource {

	public static DamageSource causeUgluCiclu(Entity source) {
		return new EntityDamageSource("kmagic.itemSwordUgluCiclu", source).setDamageBypassesArmor().setMagicDamage();
	}

	public static DamageSource causeAbsorbedAsMp(Entity source) {
		return new EntityDamageSource("kmagic.absorbAsMp", source).setDamageBypassesArmor().setMagicDamage();
	}

}
