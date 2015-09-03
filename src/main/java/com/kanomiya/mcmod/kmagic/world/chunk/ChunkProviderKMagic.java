package com.kanomiya.mcmod.kmagic.world.chunk;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

/**
 * @author Kanomiya
 *
 */
public class ChunkProviderKMagic implements IChunkProvider {
	private Random rand;
	private NoiseGeneratorOctaves noiseGen1;
	private NoiseGeneratorOctaves noiseGen2;
	private NoiseGeneratorOctaves noiseGen3;
	private NoiseGeneratorPerlin noiseGen4;
	public NoiseGeneratorOctaves noiseGen5;
	public NoiseGeneratorOctaves noiseGen6;
	public NoiseGeneratorOctaves mobSpawnerNoise;
	private World worldObj;
	private final boolean mapFeaturesEnabled;
	private WorldType field_177475_o;
	private final double[] field_147434_q;
	private final float[] parabolicField;
	private ChunkProviderSettings settings;
	private Block seaBlock;
	private double[] stoneNoise;
	private MapGenBase caveGenerator;
	private MapGenStronghold strongholdGenerator;
	private MapGenVillage villageGenerator;
	private MapGenMineshaft mineshaftGenerator;
	private MapGenScatteredFeature scatteredFeatureGenerator;
	private MapGenBase ravineGenerator;
	private StructureOceanMonument oceanMonumentGenerator;
	private BiomeGenBase[] biomesForGeneration;
	double[] field_147427_d;
	double[] field_147428_e;
	double[] field_147425_f;
	double[] field_147426_g;

	public ChunkProviderKMagic(World worldIn, long worldSeed, boolean isMapFeaturesEnabled, String options) {
		seaBlock = Blocks.water;
		stoneNoise = new double[256];
		caveGenerator = new MapGenCaves();
		strongholdGenerator = new MapGenStronghold();
		villageGenerator = new MapGenVillage();
		mineshaftGenerator = new MapGenMineshaft();
		scatteredFeatureGenerator = new MapGenScatteredFeature();
		ravineGenerator = new MapGenRavine();
		oceanMonumentGenerator = new StructureOceanMonument();
		{
			caveGenerator = TerrainGen.getModdedMapGen(caveGenerator, InitMapGenEvent.EventType.CAVE);
			strongholdGenerator = (MapGenStronghold)TerrainGen.getModdedMapGen(strongholdGenerator, InitMapGenEvent.EventType.STRONGHOLD);
			villageGenerator = (MapGenVillage)TerrainGen.getModdedMapGen(villageGenerator, InitMapGenEvent.EventType.VILLAGE);
			mineshaftGenerator = (MapGenMineshaft)TerrainGen.getModdedMapGen(mineshaftGenerator, InitMapGenEvent.EventType.MINESHAFT);
			scatteredFeatureGenerator = (MapGenScatteredFeature)TerrainGen.getModdedMapGen(scatteredFeatureGenerator, InitMapGenEvent.EventType.SCATTERED_FEATURE);
			ravineGenerator = TerrainGen.getModdedMapGen(ravineGenerator, InitMapGenEvent.EventType.RAVINE);
			oceanMonumentGenerator = (StructureOceanMonument)TerrainGen.getModdedMapGen(oceanMonumentGenerator, InitMapGenEvent.EventType.OCEAN_MONUMENT);
		}
		worldObj = worldIn;
		mapFeaturesEnabled = isMapFeaturesEnabled;
		field_177475_o = worldIn.getWorldInfo().getTerrainType();
		rand = new Random(worldSeed);
		noiseGen1 = new NoiseGeneratorOctaves(rand, 16);
		noiseGen2 = new NoiseGeneratorOctaves(rand, 16);
		noiseGen3 = new NoiseGeneratorOctaves(rand, 8);
		noiseGen4 = new NoiseGeneratorPerlin(rand, 4);
		noiseGen5 = new NoiseGeneratorOctaves(rand, 10);
		noiseGen6 = new NoiseGeneratorOctaves(rand, 16);
		mobSpawnerNoise = new NoiseGeneratorOctaves(rand, 8);
		field_147434_q = new double[825];
		parabolicField = new float[25];

		for (int j = -2; j <= 2; ++j)
		{
			for (int k = -2; k <= 2; ++k)
			{
				float f = 10.0F / MathHelper.sqrt_float(j * j + k * k + 0.2F);
				parabolicField[j + 2 + (k + 2) * 5] = f;
			}
		}

		if (options != null)
		{
			settings = ChunkProviderSettings.Factory.func_177865_a(options).func_177864_b();
			seaBlock = settings.useLavaOceans ? Blocks.lava : Blocks.water;
		}

		NoiseGenerator[] noiseGens = {noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5, noiseGen6, mobSpawnerNoise};
		noiseGens = TerrainGen.getModdedNoiseGenerators(worldIn, rand, noiseGens);
		noiseGen1 = (NoiseGeneratorOctaves)noiseGens[0];
		noiseGen2 = (NoiseGeneratorOctaves)noiseGens[1];
		noiseGen3 = (NoiseGeneratorOctaves)noiseGens[2];
		noiseGen4 = (NoiseGeneratorPerlin)noiseGens[3];
		noiseGen5 = (NoiseGeneratorOctaves)noiseGens[4];
		noiseGen6 = (NoiseGeneratorOctaves)noiseGens[5];
		mobSpawnerNoise = (NoiseGeneratorOctaves)noiseGens[6];
	}

	public void setBlocksInChunk(int p_180518_1_, int p_180518_2_, ChunkPrimer p_180518_3_)
	{
		biomesForGeneration = worldObj.getWorldChunkManager().getBiomesForGeneration(biomesForGeneration, p_180518_1_ * 4 - 2, p_180518_2_ * 4 - 2, 10, 10);
		func_147423_a(p_180518_1_ * 4, 0, p_180518_2_ * 4);

		for (int k = 0; k < 4; ++k)
		{
			int l = k * 5;
			int i1 = (k + 1) * 5;

			for (int j1 = 0; j1 < 4; ++j1)
			{
				int k1 = (l + j1) * 33;
				int l1 = (l + j1 + 1) * 33;
				int i2 = (i1 + j1) * 33;
				int j2 = (i1 + j1 + 1) * 33;

				for (int k2 = 0; k2 < 32; ++k2)
				{
					double d0 = 0.125D;
					double d1 = field_147434_q[k1 + k2];
					double d2 = field_147434_q[l1 + k2];
					double d3 = field_147434_q[i2 + k2];
					double d4 = field_147434_q[j2 + k2];
					double d5 = (field_147434_q[k1 + k2 + 1] - d1) * d0;
					double d6 = (field_147434_q[l1 + k2 + 1] - d2) * d0;
					double d7 = (field_147434_q[i2 + k2 + 1] - d3) * d0;
					double d8 = (field_147434_q[j2 + k2 + 1] - d4) * d0;

					for (int l2 = 0; l2 < 8; ++l2)
					{
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int i3 = 0; i3 < 4; ++i3)
						{
							double d14 = 0.25D;
							double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;

							for (int j3 = 0; j3 < 4; ++j3)
							{
								if ((d15 += d16) > 0.0D)
								{
									p_180518_3_.setBlockState(k * 4 + i3, k2 * 8 + l2, j1 * 4 + j3, Blocks.stone.getDefaultState());
								}
								else if (k2 * 8 + l2 < settings.seaLevel)
								{
									p_180518_3_.setBlockState(k * 4 + i3, k2 * 8 + l2, j1 * 4 + j3, seaBlock.getDefaultState());
								}
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}

	public void func_180517_a(int p_180517_1_, int p_180517_2_, ChunkPrimer p_180517_3_, BiomeGenBase[] p_180517_4_)
	{
		ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, p_180517_1_, p_180517_2_, p_180517_3_, worldObj);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY) return;

		double d0 = 0.03125D;
		stoneNoise = noiseGen4.func_151599_a(stoneNoise, p_180517_1_ * 16, p_180517_2_ * 16, 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

		for (int k = 0; k < 16; ++k)
		{
			for (int l = 0; l < 16; ++l)
			{
				BiomeGenBase biomegenbase = p_180517_4_[l + k * 16];
				biomegenbase.genTerrainBlocks(worldObj, rand, p_180517_3_, p_180517_1_ * 16 + k, p_180517_2_ * 16 + l, stoneNoise[l + k * 16]);
			}
		}
	}

	@Override
	public Chunk provideChunk(int x, int z)
	{
		rand.setSeed(x * 341873128712L + z * 132897987541L);
		ChunkPrimer chunkprimer = new ChunkPrimer();
		setBlocksInChunk(x, z, chunkprimer);
		biomesForGeneration = worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, x * 16, z * 16, 16, 16);
		func_180517_a(x, z, chunkprimer, biomesForGeneration);

		if (settings.useCaves)
		{
			caveGenerator.func_175792_a(this, worldObj, x, z, chunkprimer);
		}

		if (settings.useRavines)
		{
			ravineGenerator.func_175792_a(this, worldObj, x, z, chunkprimer);
		}

		if (settings.useMineShafts && mapFeaturesEnabled)
		{
			mineshaftGenerator.func_175792_a(this, worldObj, x, z, chunkprimer);
		}

		if (settings.useVillages && mapFeaturesEnabled)
		{
			villageGenerator.func_175792_a(this, worldObj, x, z, chunkprimer);
		}

		if (settings.useStrongholds && mapFeaturesEnabled)
		{
			strongholdGenerator.func_175792_a(this, worldObj, x, z, chunkprimer);
		}

		if (settings.useTemples && mapFeaturesEnabled)
		{
			scatteredFeatureGenerator.func_175792_a(this, worldObj, x, z, chunkprimer);
		}

		if (settings.useMonuments && mapFeaturesEnabled)
		{
			oceanMonumentGenerator.func_175792_a(this, worldObj, x, z, chunkprimer);
		}

		Chunk chunk = new Chunk(worldObj, chunkprimer, x, z);
		byte[] abyte = chunk.getBiomeArray();

		for (int k = 0; k < abyte.length; ++k)
		{
			abyte[k] = (byte)biomesForGeneration[k].biomeID;
		}

		chunk.generateSkylightMap();
		return chunk;
	}

	private void func_147423_a(int p_147423_1_, int p_147423_2_, int p_147423_3_)
	{
		field_147426_g = noiseGen6.generateNoiseOctaves(field_147426_g, p_147423_1_, p_147423_3_, 5, 5, settings.depthNoiseScaleX, settings.depthNoiseScaleZ, settings.depthNoiseScaleExponent);
		float f = settings.coordinateScale;
		float f1 = settings.heightScale;
		field_147427_d = noiseGen3.generateNoiseOctaves(field_147427_d, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, f / settings.mainNoiseScaleX, f1 / settings.mainNoiseScaleY, f / settings.mainNoiseScaleZ);
		field_147428_e = noiseGen1.generateNoiseOctaves(field_147428_e, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, f, f1, f);
		field_147425_f = noiseGen2.generateNoiseOctaves(field_147425_f, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, f, f1, f);

		int l = 0;
		int i1 = 0;

		for (int j1 = 0; j1 < 5; ++j1)
		{
			for (int k1 = 0; k1 < 5; ++k1)
			{
				float f2 = 0.0F;
				float f3 = 0.0F;
				float f4 = 0.0F;
				byte b0 = 2;
				BiomeGenBase biomegenbase = biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

				for (int l1 = -b0; l1 <= b0; ++l1)
				{
					for (int i2 = -b0; i2 <= b0; ++i2)
					{
						BiomeGenBase biomegenbase1 = biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
						float f5 = settings.biomeDepthOffSet + biomegenbase1.minHeight * settings.biomeDepthWeight;
						float f6 = settings.biomeScaleOffset + biomegenbase1.maxHeight * settings.biomeScaleWeight;

						if (field_177475_o == WorldType.AMPLIFIED && f5 > 0.0F)
						{
							f5 = 1.0F + f5 * 2.0F;
							f6 = 1.0F + f6 * 4.0F;
						}

						float f7 = parabolicField[l1 + 2 + (i2 + 2) * 5] / (f5 + 2.0F);

						if (biomegenbase1.minHeight > biomegenbase.minHeight)
						{
							f7 /= 2.0F;
						}

						f2 += f6 * f7;
						f3 += f5 * f7;
						f4 += f7;
					}
				}

				f2 /= f4;
				f3 /= f4;
				f2 = f2 * 0.9F + 0.1F;
				f3 = (f3 * 4.0F - 1.0F) / 8.0F;
				double d7 = field_147426_g[i1] / 8000.0D;

				if (d7 < 0.0D)
				{
					d7 = -d7 * 0.3D;
				}

				d7 = d7 * 3.0D - 2.0D;

				if (d7 < 0.0D)
				{
					d7 /= 2.0D;

					if (d7 < -1.0D)
					{
						d7 = -1.0D;
					}

					d7 /= 1.4D;
					d7 /= 2.0D;
				}
				else
				{
					if (d7 > 1.0D)
					{
						d7 = 1.0D;
					}

					d7 /= 8.0D;
				}

				++i1;
				double d8 = f3;
				double d9 = f2;
				d8 += d7 * 0.2D;
				d8 = d8 * settings.baseSize / 8.0D;
				double d0 = settings.baseSize + d8 * 4.0D;

				for (int j2 = 0; j2 < 33; ++j2)
				{
					double d1 = (j2 - d0) * settings.stretchY * 128.0D / 256.0D / d9;

					if (d1 < 0.0D)
					{
						d1 *= 4.0D;
					}

					double d2 = field_147428_e[l] / settings.lowerLimitScale;
					double d3 = field_147425_f[l] / settings.upperLimitScale;
					double d4 = (field_147427_d[l] / 10.0D + 1.0D) / 2.0D;
					double d5 = MathHelper.denormalizeClamp(d2, d3, d4) - d1;

					if (j2 > 29)
					{
						double d6 = (j2 - 29) / 3.0F;
						d5 = d5 * (1.0D - d6) + -10.0D * d6;
					}

					field_147434_q[l] = d5;
					++l;
				}
			}
		}
	}

	@Override
	public boolean chunkExists(int x, int z)
	{
		return true;
	}

	@Override
	public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
	{
		BlockFalling.fallInstantly = true;
		int k = p_73153_2_ * 16;
		int l = p_73153_3_ * 16;
		BlockPos blockpos = new BlockPos(k, 0, l);
		BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(blockpos.add(16, 0, 16));
		rand.setSeed(worldObj.getSeed());
		long i1 = rand.nextLong() / 2L * 2L + 1L;
		long j1 = rand.nextLong() / 2L * 2L + 1L;
		rand.setSeed(p_73153_2_ * i1 + p_73153_3_ * j1 ^ worldObj.getSeed());
		boolean flag = false;
		ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(p_73153_2_, p_73153_3_);

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag));

		if (settings.useMineShafts && mapFeaturesEnabled)
		{
			mineshaftGenerator.func_175794_a(worldObj, rand, chunkcoordintpair);
		}

		if (settings.useVillages && mapFeaturesEnabled)
		{
			flag = villageGenerator.func_175794_a(worldObj, rand, chunkcoordintpair);
		}

		if (settings.useStrongholds && mapFeaturesEnabled)
		{
			strongholdGenerator.func_175794_a(worldObj, rand, chunkcoordintpair);
		}

		if (settings.useTemples && mapFeaturesEnabled)
		{
			scatteredFeatureGenerator.func_175794_a(worldObj, rand, chunkcoordintpair);
		}

		if (settings.useMonuments && mapFeaturesEnabled)
		{
			oceanMonumentGenerator.func_175794_a(worldObj, rand, chunkcoordintpair);
		}

		int k1;
		int l1;
		int i2;

		if (biomegenbase != BiomeGenBase.desert && biomegenbase != BiomeGenBase.desertHills && settings.useWaterLakes && !flag && rand.nextInt(settings.waterLakeChance) == 0
			&& TerrainGen.populate(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag, PopulateChunkEvent.Populate.EventType.LAKE))
		{
			k1 = rand.nextInt(16) + 8;
			l1 = rand.nextInt(256);
			i2 = rand.nextInt(16) + 8;
			(new WorldGenLakes(Blocks.water)).generate(worldObj, rand, blockpos.add(k1, l1, i2));
		}

		if (TerrainGen.populate(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag, PopulateChunkEvent.Populate.EventType.LAVA) && !flag && rand.nextInt(settings.lavaLakeChance / 10) == 0 && settings.useLavaLakes)
		{
			k1 = rand.nextInt(16) + 8;
			l1 = rand.nextInt(rand.nextInt(248) + 8);
			i2 = rand.nextInt(16) + 8;

			if (l1 < 63 || rand.nextInt(settings.lavaLakeChance / 8) == 0)
			{
				(new WorldGenLakes(Blocks.lava)).generate(worldObj, rand, blockpos.add(k1, l1, i2));
			}
		}

		if (settings.useDungeons)
		{
			boolean doGen = TerrainGen.populate(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag, PopulateChunkEvent.Populate.EventType.DUNGEON);
			for (k1 = 0; doGen && k1 < settings.dungeonChance; ++k1)
			{
				l1 = rand.nextInt(16) + 8;
				i2 = rand.nextInt(256);
				int j2 = rand.nextInt(16) + 8;
				(new WorldGenDungeons()).generate(worldObj, rand, blockpos.add(l1, i2, j2));
			}
		}

		biomegenbase.decorate(worldObj, rand, new BlockPos(k, 0, l));
		if (TerrainGen.populate(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag, PopulateChunkEvent.Populate.EventType.ANIMALS))
		{
		SpawnerAnimals.performWorldGenSpawning(worldObj, biomegenbase, k + 8, l + 8, 16, 16, rand);
		}
		blockpos = blockpos.add(8, 0, 8);

		boolean doGen = TerrainGen.populate(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag, PopulateChunkEvent.Populate.EventType.ICE);
		for (k1 = 0; doGen && k1 < 16; ++k1)
		{
			for (l1 = 0; l1 < 16; ++l1)
			{
				BlockPos blockpos1 = worldObj.getPrecipitationHeight(blockpos.add(k1, 0, l1));
				BlockPos blockpos2 = blockpos1.down();

				if (worldObj.func_175675_v(blockpos2))
				{
					worldObj.setBlockState(blockpos2, Blocks.ice.getDefaultState(), 2);
				}

				if (worldObj.canSnowAt(blockpos1, true))
				{
					worldObj.setBlockState(blockpos1, Blocks.snow_layer.getDefaultState(), 2);
				}
			}
		}

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag));

		BlockFalling.fallInstantly = false;
	}

	@Override
	public boolean func_177460_a(IChunkProvider p_177460_1_, Chunk p_177460_2_, int p_177460_3_, int p_177460_4_)
	{
		boolean flag = false;

		if (settings.useMonuments && mapFeaturesEnabled && p_177460_2_.getInhabitedTime() < 3600L)
		{
			flag |= oceanMonumentGenerator.func_175794_a(worldObj, rand, new ChunkCoordIntPair(p_177460_3_, p_177460_4_));
		}

		return flag;
	}

	@Override
	public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_)
	{
		return true;
	}

	@Override
	public void saveExtraData() {}

	@Override
	public boolean unloadQueuedChunks()
	{
		return false;
	}

	@Override
	public boolean canSave()
	{
		return true;
	}

	@Override
	public String makeString()
	{
		return "RandomLevelSource";
	}

	@Override
	public List func_177458_a(EnumCreatureType p_177458_1_, BlockPos p_177458_2_)
	{
		BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(p_177458_2_);

		if (mapFeaturesEnabled)
		{
			if (p_177458_1_ == EnumCreatureType.MONSTER && scatteredFeatureGenerator.func_175798_a(p_177458_2_))
			{
				return scatteredFeatureGenerator.getScatteredFeatureSpawnList();
			}

			if (p_177458_1_ == EnumCreatureType.MONSTER && settings.useMonuments && oceanMonumentGenerator.func_175796_a(worldObj, p_177458_2_))
			{
				return oceanMonumentGenerator.func_175799_b();
			}
		}

		return biomegenbase.getSpawnableList(p_177458_1_);
	}

	@Override
	public BlockPos getStrongholdGen(World worldIn, String p_180513_2_, BlockPos p_180513_3_)
	{
		return "Stronghold".equals(p_180513_2_) && strongholdGenerator != null ? strongholdGenerator.getClosestStrongholdPos(worldIn, p_180513_3_) : null;
	}

	@Override
	public int getLoadedChunkCount()
	{
		return 0;
	}

	@Override
	public void recreateStructures(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_)
	{
		if (settings.useMineShafts && mapFeaturesEnabled)
		{
			mineshaftGenerator.func_175792_a(this, worldObj, p_180514_2_, p_180514_3_, (ChunkPrimer)null);
		}

		if (settings.useVillages && mapFeaturesEnabled)
		{
			villageGenerator.func_175792_a(this, worldObj, p_180514_2_, p_180514_3_, (ChunkPrimer)null);
		}

		if (settings.useStrongholds && mapFeaturesEnabled)
		{
			strongholdGenerator.func_175792_a(this, worldObj, p_180514_2_, p_180514_3_, (ChunkPrimer)null);
		}

		if (settings.useTemples && mapFeaturesEnabled)
		{
			scatteredFeatureGenerator.func_175792_a(this, worldObj, p_180514_2_, p_180514_3_, (ChunkPrimer)null);
		}

		if (settings.useMonuments && mapFeaturesEnabled)
		{
			oceanMonumentGenerator.func_175792_a(this, worldObj, p_180514_2_, p_180514_3_, (ChunkPrimer)null);
		}
	}

	@Override
	public Chunk provideChunk(BlockPos blockPosIn)
	{
		return this.provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
	}
}
