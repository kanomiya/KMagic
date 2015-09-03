package com.kanomiya.mcmod.kmagic.world.gen.structure;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Kanomiya
 *
 */
public class PopulateChunkEventHandler {
	public static final MapGenMagicGate mapGenMagicGate = new MapGenMagicGate();

	@SubscribeEvent public void onPopulateChunkEvent(PopulateChunkEvent.Populate event) {

		// int dimId = event.world.provider.getDimensionId();

		mapGenMagicGate.func_175792_a(event.chunkProvider, event.world, event.chunkX, event.chunkZ, null);
		if (mapGenMagicGate.func_175794_a(event.world, event.rand, new ChunkCoordIntPair(event.chunkX, event.chunkZ))) {
			// generateStructuresInChunk
			// KMagic.logger.info("Built " + event.chunkX *16 + ", " + event.chunkZ *16);
		}


		// Generator

	}

}
