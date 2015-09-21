package com.kanomiya.mcmod.kmagic.api.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

import com.kanomiya.mcmod.kmagic.api.tileentity.TileEntityMagicBase;

/**
 * @author Kanomiya
 *
 */

public abstract class BlockMagicBase extends BlockContainer {

	public BlockMagicBase(Material materialIn) {
		super(materialIn);
	}


	/*
	@Override public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {

	}

	@Override public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {

	}

	@Override public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

	}

	@Override public void onLanded(World worldIn, Entity entityIn) {

	}

	@Override public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {

	}

	@Override public void fillWithRain(World worldIn, BlockPos pos) {

	}
	*/

	@Override public abstract TileEntityMagicBase createNewTileEntity(World worldIn, int meta);



}
