package com.kanomiya.mcmod.kmagic.world.gen.biome;

import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenForest;

/**
 * @author Kanomiya
 *
 */
public class BiomeGenRichMEForest extends BiomeGenForest {

	public BiomeGenRichMEForest(int id) {
		super(id, 0);
		setBiomeName(BiomeRichMEManager.INSTANCE.getNewName(BiomeGenBase.forest.biomeName));

	}

	@Override public void decorate(World world, Random rand, BlockPos pos) {
		super.decorate(world, rand, pos);
		BiomeRichMEManager.INSTANCE.decorate(world, rand, pos);
	}

}
