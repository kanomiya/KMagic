package com.kanomiya.mcmod.kmagic.api.magic.spell;

import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.google.common.collect.Maps;
import com.kanomiya.mcmod.kmagic.api.magic.spell.instant.InstantMagicBase;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.magic.spell.instant.MagicExplosion;

/**
 *
 * @author Kanomiya
 *
 */
public class InstantParser {

	public static final InstantParser INSTANCE = new InstantParser();

	private Map<String, InstantMagicBase> magics = Maps.newHashMap();

	private InstantParser() {
		magics.put("expl", new MagicExplosion());
	}

	public boolean spell(World worldIn, Entity speller, MagicStatus status, String word) {

		if (magics.containsKey(word)) {
			InstantMagicBase magic = magics.get(word);

			if (magic.canExecute(speller, status)) {
				magic.execute(worldIn, speller, status);

				return true;
			}

		}


		return false;
	}



}
