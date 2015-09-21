package com.kanomiya.mcmod.kmagic.world.gen.structure;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

import com.kanomiya.mcmod.kmagic.KMBlocks;

/**
 * @author Kanomiya
 *
 */
public abstract class StructureMagicGate {

	public static class Start extends StructureStart {

		public Start() {  }

		public Start(World world, Random rand, int chunkX, int chunkZ) {
			super(chunkX, chunkZ);

			int x = (chunkX << 4) + 2;
			int z = (chunkZ << 4) + 2;
			int y = world.getTopSolidOrLiquidBlock(new BlockPos(chunkX, 0, chunkZ)).getY();

			// 構造物の構成パーツを決定する
			Component1 componentMagicGate = new Component1(0, rand, x,y,z);
			components.add(componentMagicGate);

			componentMagicGate.buildComponent(componentMagicGate, components, rand);

			// 構造物全体の占有範囲を更新する
			updateBoundingBox();
		}
	}


	public static class Component1 extends StructureComponent {

		public Component1() {}

		public Component1(int type, Random rand, int x, int y, int z) {

			// 東西南北の方向をランダムに決める

			coordBaseMode = EnumFacing.getHorizontal(rand.nextInt(4));
			switch(coordBaseMode) {
			case NORTH:
			case SOUTH:
			case EAST:
			case WEST:
			default:
				// (x,y,z)の地点から8x3x8ブロックが占有範囲
				boundingBox = new StructureBoundingBox(x, y, z, x + 8, y +3, z + 8);
				break;
			}
		}

		@Override
		public void buildComponent(StructureComponent comp, List list, Random rand) { }


		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox bound) {
			// 建設予定範囲内に液体があった場合は建設中止
			if(isLiquidInStructureBoundingBox(world, bound)) { return false; }

			fillWithAir(world, bound, 0, 0, 0, 8, 3, 8);

			// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
			// 台座
			// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
			func_175804_a(world, bound, 0, 0, 0, 8, 0, 8, Blocks.stone_slab.getStateFromMeta(3), Blocks.air.getDefaultState(), false); // fillWithMetadataBlocks

			fillWithAir(world, bound, 1, 0, 1, 7, 0, 7);
			for (int x=1; x<=7; x++) {
				for (int z=1; z<=7; z++) {
					func_175811_a(world, (rand.nextInt(3) == 0 ? Blocks.cobblestone.getDefaultState() : Blocks.mossy_cobblestone.getDefaultState()), x, 0, z, bound); // placeBlockAtCurrentPosition
				}
			}


			// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
			// 機械
			// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
			fillWithAir(world, bound, 2, 0, 2, 6, 0, 6);
			func_175804_a(world, bound, 2, 0, 2, 6, 0, 6, KMBlocks.blockKMSignalPortal.getDefaultState(), Blocks.air.getDefaultState(), false); // fillWithMetadataBlocks

			func_175811_a(world, KMBlocks.blockKMSignalRepeater.getDefaultState(), 1, 3, 1, bound); // placeBlockAtCurrentPosition
			func_175811_a(world, KMBlocks.blockMagicKMSignalSender.getDefaultState(), 1, 2, 1, bound); // placeBlockAtCurrentPosition
			func_175811_a(world, KMBlocks.blockKMSignalRepeater.getDefaultState(), 1, 1, 1, bound); // placeBlockAtCurrentPosition
			func_175811_a(world, KMBlocks.blockKMSignalRepeater.getDefaultState(), 1, 0, 1, bound); // placeBlockAtCurrentPosition
			func_175811_a(world, KMBlocks.blockKMSignalRepeater.getDefaultState(), 2, 0, 1, bound); // placeBlockAtCurrentPosition


			func_175811_a(world, KMBlocks.blockKMSignalRepeater.getDefaultState(), 7, 3, 1, bound); // placeBlockAtCurrentPosition
			func_175811_a(world, KMBlocks.blockMagicKMSignalSender.getDefaultState(), 7, 2, 1, bound); // placeBlockAtCurrentPosition
			func_175811_a(world, KMBlocks.blockKMSignalRepeater.getDefaultState(), 7, 1, 1, bound); // placeBlockAtCurrentPosition
			func_175811_a(world, KMBlocks.blockKMSignalRepeater.getDefaultState(), 7, 0, 1, bound); // placeBlockAtCurrentPosition
			func_175811_a(world, KMBlocks.blockKMSignalRepeater.getDefaultState(), 7, 0, 2, bound); // placeBlockAtCurrentPosition

			func_175811_a(world, KMBlocks.blockKMSignalRepeater.getDefaultState(), 1, 3, 7, bound); // placeBlockAtCurrentPosition
			func_175811_a(world, KMBlocks.blockMagicKMSignalSender.getDefaultState(), 1, 2, 7, bound); // placeBlockAtCurrentPosition
			func_175811_a(world, KMBlocks.blockKMSignalRepeater.getDefaultState(), 1, 1, 7, bound); // placeBlockAtCurrentPosition
			func_175811_a(world, KMBlocks.blockKMSignalRepeater.getDefaultState(), 1, 0, 7, bound); // placeBlockAtCurrentPosition
			func_175811_a(world, KMBlocks.blockKMSignalRepeater.getDefaultState(), 1, 0, 6, bound); // placeBlockAtCurrentPosition

			func_175811_a(world, KMBlocks.blockKMSignalRepeater.getDefaultState(), 7, 3, 7, bound); // placeBlockAtCurrentPosition
			func_175811_a(world, KMBlocks.blockMagicKMSignalSender.getDefaultState(), 7, 2, 7, bound); // placeBlockAtCurrentPosition
			func_175811_a(world, KMBlocks.blockKMSignalRepeater.getDefaultState(), 7, 1, 7, bound); // placeBlockAtCurrentPosition
			func_175811_a(world, KMBlocks.blockKMSignalRepeater.getDefaultState(), 7, 0, 7, bound); // placeBlockAtCurrentPosition
			func_175811_a(world, KMBlocks.blockKMSignalRepeater.getDefaultState(), 6, 0, 7, bound); // placeBlockAtCurrentPosition

			return true;
		}



		@Override protected void writeStructureToNBT(NBTTagCompound p_143012_1_) {

		}

		@Override protected void readStructureFromNBT(NBTTagCompound p_143011_1_) {

		}

	}

}

