package com.kanomiya.mcmod.kmagic.world;

import com.kanomiya.mcmod.kmagic.KMConfig;
import com.kanomiya.mcmod.kmagic.world.chunk.ChunkProviderKMagic;
import com.kanomiya.mcmod.kmagic.world.chunk.WorldChunkManagerKMagic;
import com.kanomiya.mcmod.kmagic.world.gen.biome.BiomeRichMEManager;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

/**
 * @author Kanomiya
 *
 */
public class WorldProviderKMagic extends WorldProvider {

	@Override public void registerWorldChunkManager() {
		worldChunkMgr = new WorldChunkManagerKMagic(BiomeRichMEManager.biomeGenRichMEPlains, 0.3F);
		isHellWorld = false;
		hasNoSky = false;
		dimensionId = KMConfig.DIMID_KMAGIC;
	}

	@Override public IChunkProvider createChunkGenerator() {
		return new ChunkProviderKMagic(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled(), worldObj.getWorldInfo().getGeneratorOptions());
	}

	@Override public boolean canRespawnHere() { return true; }

	@Override public String getDimensionName() {
		return "KMagic";
	}

	@Override public String getInternalNameSuffix() {
		return "_kmagic";
	}

}
