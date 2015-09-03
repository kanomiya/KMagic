package com.kanomiya.mcmod.kmagic.world.gen.biome;

import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenPlains;

/**
 * @author Kanomiya
 *
 */
public class BiomeGenRichMEPlains extends BiomeGenPlains {

	public BiomeGenRichMEPlains(int id) {
		super(id);
		setBiomeName(BiomeRichMEManager.INSTANCE.getNewName(BiomeGenBase.plains.biomeName));

	}

	@Override public void decorate(World world, Random rand, BlockPos pos) {
		super.decorate(world, rand, pos);
		BiomeRichMEManager.INSTANCE.decorate(world, rand, pos);
	}

}
