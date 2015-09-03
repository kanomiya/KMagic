package com.kanomiya.mcmod.kmagic.world.chunk;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

/**
 * @author Kanomiya
 *
 */
public class WorldChunkManagerKMagic extends WorldChunkManager
{
	private BiomeGenBase biomeGenerator;
	private float rainfall;

	public WorldChunkManagerKMagic(BiomeGenBase p_i45374_1_, float p_i45374_2_) {
		biomeGenerator = p_i45374_1_;
		rainfall = p_i45374_2_;
	}

	@Override public BiomeGenBase getBiomeGenerator(BlockPos p_180631_1_) {
		return biomeGenerator;
	}

	@Override public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] p_76937_1_, int p_76937_2_, int p_76937_3_, int p_76937_4_, int p_76937_5_) {
		if (p_76937_1_ == null || p_76937_1_.length < p_76937_4_ * p_76937_5_) {
			p_76937_1_ = new BiomeGenBase[p_76937_4_ * p_76937_5_];
		}

		Arrays.fill(p_76937_1_, 0, p_76937_4_ * p_76937_5_, biomeGenerator);
		return p_76937_1_;
	}

	@Override public float[] getRainfall(float[] listToReuse, int x, int z, int width, int length) {
		if (listToReuse == null || listToReuse.length < width * length)
		{
			listToReuse = new float[width * length];
		}

		Arrays.fill(listToReuse, 0, width * length, rainfall);
		return listToReuse;
	}

	@Override public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] oldBiomeList, int x, int z, int width, int depth) {
		if (oldBiomeList == null || oldBiomeList.length < width * depth)
		{
			oldBiomeList = new BiomeGenBase[width * depth];
		}

		Arrays.fill(oldBiomeList, 0, width * depth, biomeGenerator);
		return oldBiomeList;
	}

	@Override public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] listToReuse, int x, int z, int width, int length, boolean cacheFlag) {
		return loadBlockGeneratorData(listToReuse, x, z, width, length);
	}

	@Override public BlockPos findBiomePosition(int x, int z, int range, List biomes, Random random) {
		return biomes.contains(biomeGenerator) ? new BlockPos(x - range + random.nextInt(range * 2 + 1), 0, z - range + random.nextInt(range * 2 + 1)) : null;
	}

	@Override public boolean areBiomesViable(int p_76940_1_, int p_76940_2_, int p_76940_3_, List p_76940_4_) {
		return p_76940_4_.contains(biomeGenerator);
	}
}
