package com.kanomiya.mcmod.kmagic.world;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureStart;

import com.kanomiya.mcmod.kmagic.world.gen.structure.PopulateChunkEventHandler;

/**
 * @author Kanomiya
 *
 */
public class TeleporterKMagic extends Teleporter {
	private final WorldServer worldServerInstance;
	private final Random random;

	public TeleporterKMagic(WorldServer worldIn) {
		super(worldIn);

		worldServerInstance = worldIn;
		random = new Random(worldIn.getSeed());

	}

	@Override public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
		return false; // return super.placeInExistingPortal(entityIn, rotationYaw);
	}


	@Override public void placeInPortal(Entity entityIn, float rotationYaw) {
		int portalX = MathHelper.floor_double(entityIn.posX);
		int portalY = MathHelper.floor_double(entityIn.posY) -1;
		int portalZ = MathHelper.floor_double(entityIn.posZ);

		boolean flag = false;
		BlockPos portalPos = new BlockPos(portalX, portalY, portalZ);
		portalPos = worldServerInstance.getTopSolidOrLiquidBlock(portalPos);

		/*
		int searchSize = 16*8;

		for (int i=-searchSize; i<=searchSize; i++) {
			for (int j=-searchSize; j<=searchSize; j++) {
				for (int k=-searchSize; k<=searchSize; k++) {
					BlockPos pos = portalPos.add(i, j, k);
					IBlockState state = worldServerInstance.getBlockState(pos);
					if (state.getBlock() == KMBlocks.blockKMSignalPortal && (Boolean) state.getValue(BlockKMSignalPortal.ACTIVATED)) {
						entityIn.setPosition(pos.getX() +0.5d, pos.getY() +1, pos.getZ() +0.5d);

						flag = true;
					}
				}
			}
		}
		*/

		if (! flag) {
			StructureStart start = PopulateChunkEventHandler.mapGenMagicGate.getStructureStart(portalX /16, portalZ /16);
			StructureBoundingBox sbb = start.getBoundingBox();

			start.generateStructure(worldServerInstance, random, sbb);

			// worldServerInstance.setBlockState(portalPos, KMBlocks.blockKMSignalPortal.getStateFromMeta(1));
			entityIn.setPosition(((sbb.minX + sbb.maxX) /2) +0.5d, sbb.minY +2, ((sbb.minZ + sbb.maxZ) /2) +0.5d);

		}


	}





	@Override public boolean makePortal(Entity entityIn) {

		return false;

		// PopulateChunkEventHandler.mapGenMagicGate.func_175792_a(worldServerInstance.getChunkProvider(), worldServerInstance, entityIn.chunkCoordX, entityIn.chunkCoordZ, null);
		// PopulateChunkEventHandler.mapGenMagicGate.func_175794_a(worldServerInstance, random, new ChunkCoordIntPair(entityIn.chunkCoordX, entityIn.chunkCoordZ)); // generateStructuresInChunk


		/*
		byte portalSiteSize = 16;
		double d0 = -1.0D;
		int portalX = MathHelper.floor_double(entityIn.posX);
		int portalY = MathHelper.floor_double(entityIn.posY);
		int portalZ = MathHelper.floor_double(entityIn.posZ);
		int cachePortalX = portalX;
		int cachePortalY = portalY;
		int cachePortalZ = portalZ;
		int k1 = 0;
		int l1 = random.nextInt(4);
		int i3;
		int j3;
		int k3;
		int l3;
		int i4;
		int j4;
		int k4;
		int l4;
		int i5;
		double d3;
		double d4;

		for (int tempX = portalX - portalSiteSize; tempX <= portalX + portalSiteSize; ++tempX)
		{
			double checkX = tempX + 0.5D - entityIn.posX;

			for (int tempZ = portalZ - portalSiteSize; tempZ <= portalZ + portalSiteSize; ++tempZ)
			{
				double checkZ = tempZ + 0.5D - entityIn.posZ;
				label271:

				for (i3 = worldServerInstance.getActualHeight() - 1; i3 >= 0; --i3)
				{
					if (worldServerInstance.isAirBlock(new BlockPos(tempX, i3, tempZ)))
					{
						while (i3 > 0 && worldServerInstance.isAirBlock(new BlockPos(tempX, i3 - 1, tempZ)))
						{
							--i3;
						}

						for (j3 = l1; j3 < l1 + 4; ++j3)
						{
							k3 = j3 % 2;
							l3 = 1 - k3;

							if (j3 % 4 >= 2)
							{
								k3 = -k3;
								l3 = -l3;
							}

							for (i4 = 0; i4 < 3; ++i4)
							{
								for (j4 = 0; j4 < 4; ++j4)
								{
									for (k4 = -1; k4 < 4; ++k4)
									{
										l4 = tempX + (j4 - 1) * k3 + i4 * l3;
										i5 = i3 + k4;
										int j5 = tempZ + (j4 - 1) * l3 - i4 * k3;

										if (k4 < 0 && !worldServerInstance.getBlockState(new BlockPos(l4, i5, j5)).getBlock().getMaterial().isSolid() || k4 >= 0 && !worldServerInstance.isAirBlock(new BlockPos(l4, i5, j5)))
										{
											continue label271;
										}
									}
								}
							}

							d3 = i3 + 0.5D - entityIn.posY;
							d4 = checkX * checkX + d3 * d3 + checkZ * checkZ;

							if (d0 < 0.0D || d4 < d0)
							{
								d0 = d4;
								cachePortalX = tempX;
								cachePortalY = i3;
								cachePortalZ = tempZ;
								k1 = j3 % 4;
							}
						}
					}
				}
			}
		}

		if (d0 < 0.0D)
		{
			for (int tempX = portalX - portalSiteSize; tempX <= portalX + portalSiteSize; ++tempX)
			{
				double checkX = tempX + 0.5D - entityIn.posX;

				for (int tempZ = portalZ - portalSiteSize; tempZ <= portalZ + portalSiteSize; ++tempZ)
				{
					double checkZ = tempZ + 0.5D - entityIn.posZ;
					label219:

					for (i3 = worldServerInstance.getActualHeight() - 1; i3 >= 0; --i3)
					{
						if (worldServerInstance.isAirBlock(new BlockPos(tempX, i3, tempZ)))
						{
							while (i3 > 0 && worldServerInstance.isAirBlock(new BlockPos(tempX, i3 - 1, tempZ)))
							{
								--i3;
							}

							for (j3 = l1; j3 < l1 + 2; ++j3)
							{
								k3 = j3 % 2;
								l3 = 1 - k3;

								for (i4 = 0; i4 < 4; ++i4)
								{
									for (j4 = -1; j4 < 4; ++j4)
									{
										k4 = tempX + (i4 - 1) * k3;
										l4 = i3 + j4;
										i5 = tempZ + (i4 - 1) * l3;

										if (j4 < 0 && !worldServerInstance.getBlockState(new BlockPos(k4, l4, i5)).getBlock().getMaterial().isSolid() || j4 >= 0 && !worldServerInstance.isAirBlock(new BlockPos(k4, l4, i5)))
										{
											continue label219;
										}
									}
								}

								d3 = i3 + 0.5D - entityIn.posY;
								d4 = checkX * checkX + d3 * d3 + checkZ * checkZ;

								if (d0 < 0.0D || d4 < d0)
								{
									d0 = d4;
									cachePortalX = tempX;
									cachePortalY = i3;
									cachePortalZ = tempZ;
									k1 = j3 % 2;
								}
							}
						}
					}
				}
			}
		}

		int tempX = cachePortalX;
		int tempY = cachePortalY;
		int tempZ = cachePortalZ;
		int l5 = k1 % 2;
		int l2 = 1 - l5;

		if (k1 % 4 >= 2)
		{
			l5 = -l5;
			l2 = -l2;
		}

		if (d0 < 0.0D)
		{
			cachePortalY = MathHelper.clamp_int(cachePortalY, 70, worldServerInstance.getActualHeight() - 10);
			tempY = cachePortalY;

			for (i3 = -1; i3 <= 1; ++i3)
			{
				for (j3 = 1; j3 < 3; ++j3)
				{
					for (k3 = -1; k3 < 3; ++k3)
					{
						l3 = tempX + (j3 - 1) * l5 + i3 * l2;
						i4 = tempY + k3;
						j4 = tempZ + (j3 - 1) * l2 - i3 * l5;
						boolean flag = k3 < 0;
						worldServerInstance.setBlockState(new BlockPos(l3, i4, j4), flag ? Blocks.obsidian.getDefaultState() : Blocks.air.getDefaultState());
					}
				}
			}
		}

		IBlockState iblockstate = Blocks.portal.getDefaultState().withProperty(BlockPortal.AXIS, l5 != 0 ? EnumFacing.Axis.X : EnumFacing.Axis.Z);

		for (j3 = 0; j3 < 4; ++j3)
		{
			for (k3 = 0; k3 < 4; ++k3)
			{
				for (l3 = -1; l3 < 4; ++l3)
				{
					i4 = tempX + (k3 - 1) * l5;
					j4 = tempY + l3;
					k4 = tempZ + (k3 - 1) * l2;
					boolean flag1 = k3 == 0 || k3 == 3 || l3 == -1 || l3 == 3;
					worldServerInstance.setBlockState(new BlockPos(i4, j4, k4), flag1 ? Blocks.obsidian.getDefaultState() : iblockstate, 2);
				}
			}

			for (k3 = 0; k3 < 4; ++k3)
			{
				for (l3 = -1; l3 < 4; ++l3)
				{
					i4 = tempX + (k3 - 1) * l5;
					j4 = tempY + l3;
					k4 = tempZ + (k3 - 1) * l2;
					worldServerInstance.notifyNeighborsOfStateChange(new BlockPos(i4, j4, k4), worldServerInstance.getBlockState(new BlockPos(i4, j4, k4)).getBlock());
				}
			}
		}
		*/
	}



}
