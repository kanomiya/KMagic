package com.kanomiya.mcmod.kmagic.world.gen.structure;

import java.util.Random;

import com.kanomiya.mcmod.kmagic.world.gen.biome.BiomeRichMEManager;

import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

// MapGenScatteredFeature

/**
 * @author Kanomiya
 *
 */
public class MapGenMagicGate extends MapGenStructure {

	// private List spawnList;
	private int minDistance, maxDistance;

	public MapGenMagicGate() {
		super();
		// spawnList = new ArrayList();
		// spawnList.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, 1, 1, 1));

		minDistance = 8;
		maxDistance = 32;
	}

	@Override public String getStructureName() { return "kmagic:magicGate"; }

	@Override protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8));

		if (BiomeRichMEManager.INSTANCE.isRichMEBiome(biome)) {
			int k = chunkX;
			int l = chunkZ;

			if (chunkX < 0) {  chunkX -= maxDistance - 1; }
			if (chunkZ < 0) { chunkZ -= maxDistance - 1; }

			int i1 = chunkX / maxDistance;
			int j1 = chunkZ / maxDistance;
			Random random = worldObj.setRandomSeed(i1, j1, 74281592);
			i1 *= maxDistance;
			j1 *= maxDistance;
			i1 += random.nextInt(maxDistance - minDistance);
			j1 += random.nextInt(maxDistance - minDistance);
			if (k == i1 && l == j1) {
				return true;
			}
		}

		return false;
	}

	@Override public StructureStart getStructureStart(int chunkX, int chunkZ) {
		return new StructureMagicGate.Start(worldObj, rand, chunkX, chunkZ);
	}


}
