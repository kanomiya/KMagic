package com.kanomiya.mcmod.kmagic.world.gen.biome;

import java.util.ArrayList;
import java.util.Random;

import com.kanomiya.mcmod.kmagic.KMBlocks;
import com.kanomiya.mcmod.kmagic.block.BlockMagicStoneOre;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

/**
 * @author Kanomiya
 *
 */
public class BiomeRichMEManager {
	public static final BiomeRichMEManager INSTANCE = new BiomeRichMEManager();

	public static WorldGenerator[] oreMagicStoneGen;


	public static ArrayList<BiomeGenBase> biomesRichME = new ArrayList<BiomeGenBase>();
	public static BiomeGenRichMEPlains biomeGenRichMEPlains;
	public static BiomeGenRichMEForest biomeGenRichMEForest;


	public static void init() {
		genInit();
		biomeInit();

	}

	private static void genInit() {
		oreMagicStoneGen = new WorldGenerator[5];

		oreMagicStoneGen[0] = new WorldGenMinable(KMBlocks.blockMagicStoneOre.getStateFromType(BlockMagicStoneOre.OreType.MAGICSTONE_GRAY), 16);
		oreMagicStoneGen[1] = new WorldGenMinable(KMBlocks.blockMagicStoneOre.getStateFromType(BlockMagicStoneOre.OreType.MAGICSTONE_RED), 12);
		oreMagicStoneGen[2] = new WorldGenMinable(KMBlocks.blockMagicStoneOre.getStateFromType(BlockMagicStoneOre.OreType.MAGICSTONE_YELLOW), 10);
		oreMagicStoneGen[3] = new WorldGenMinable(KMBlocks.blockMagicStoneOre.getStateFromType(BlockMagicStoneOre.OreType.MAGICSTONE_GREEN), 8);
		oreMagicStoneGen[4] = new WorldGenMinable(KMBlocks.blockMagicStoneOre.getStateFromType(BlockMagicStoneOre.OreType.MAGICSTONE_BLUE), 6);

	}

	private static void biomeInit() {
		int nextId = 47 -1;
		biomeGenRichMEPlains = new BiomeGenRichMEPlains(nextId ++);
		biomeGenRichMEForest = new BiomeGenRichMEForest(nextId ++);

		// BiomeManager WorldChunkManager
		// BiomeGenBase BiomeGenMutated
		BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(biomeGenRichMEPlains, 6));
		BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(biomeGenRichMEForest, 6));
		BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(biomeGenRichMEPlains, 6));
		BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(biomeGenRichMEForest, 6));

		BiomeManager.addSpawnBiome(biomeGenRichMEPlains);
		BiomeManager.addSpawnBiome(biomeGenRichMEForest);

		biomesRichME.add(biomeGenRichMEPlains);
		biomesRichME.add(biomeGenRichMEForest);

	}


	public String getNewName(String name) {
		return name + " Rich ME";
	}

	public void decorate(World world, Random rand, BlockPos pos) {
		oreGenerate(world, rand, pos);

		// KMagic.logger.info("Decorated BiomeRichME");
	}

	public void oreGenerate(World world, Random rand, BlockPos pos) {

		if (TerrainGen.generateOre(world, rand, oreMagicStoneGen[0], pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
			genStandardOre1(20, oreMagicStoneGen[0], 0, 128, world, rand, pos);

		if (TerrainGen.generateOre(world, rand, oreMagicStoneGen[1], pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
			genStandardOre1(16, oreMagicStoneGen[1], 0, 128, world, rand, pos);

		if (TerrainGen.generateOre(world, rand, oreMagicStoneGen[2], pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
			genStandardOre1(8, oreMagicStoneGen[2], 0, 64, world, rand, pos);

		if (TerrainGen.generateOre(world, rand, oreMagicStoneGen[3], pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
			genStandardOre1(3, oreMagicStoneGen[3], 0, 32, world, rand, pos);

		if (TerrainGen.generateOre(world, rand, oreMagicStoneGen[4], pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
			genStandardOre1(1, oreMagicStoneGen[4], 0, 16, world, rand, pos);

		//

	}

	// BiomeDecorator.genStandardOre1
	protected void genStandardOre1(int number, WorldGenerator gen, int minY, int maxY, World world, Random rand, BlockPos pos) {

		for (int l=0; l<number; l++) {
			gen.generate(world, rand, new BlockPos(pos.getX() +rand.nextInt(16), rand.nextInt(maxY - minY) + minY, pos.getZ() +rand.nextInt(16)));
		}
	}

	public boolean isRichMEBiome(BiomeGenBase biome) {
		if (biome == null) return false;

		for (BiomeGenBase b: biomesRichME) { if (biome.biomeID == b.biomeID) return true; }

		return false;
	}


/*
	@SubscribeEvent public void onGenerateMinable(GenerateMinable event) {


		if (isRichMEBiome(event.world.getBiomeGenForCoords(event.pos))) {
			if (event.type == GenerateMinable.EventType.GOLD) {

			}
		}



	}
*/

}
