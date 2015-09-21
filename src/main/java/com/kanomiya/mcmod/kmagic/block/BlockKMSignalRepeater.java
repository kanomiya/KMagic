package com.kanomiya.mcmod.kmagic.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.kanomiya.mcmod.kmagic.KMagic;
import com.kanomiya.mcmod.kmagic.tileentity.signal.TileEntityKMSignalRepeater;

/**
 * @author Kanomiya
 *
 */

public class BlockKMSignalRepeater extends BlockContainer {


	public BlockKMSignalRepeater() {
		super(Material.rock);

		setHardness(3.5F);
		setStepSound(soundTypePiston);

		setCreativeTab(KMagic.tab);

	}


	@Override public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityKMSignalRepeater();
	}


	@Override public int getRenderType() {
		return 3;
	}




}
