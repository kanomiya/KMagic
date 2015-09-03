package com.kanomiya.mcmod.kmagic.block;

import com.kanomiya.mcmod.kmagic.KMagicAPI;
import com.kanomiya.mcmod.kmagic.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.tileentity.TileEntityMagicBase;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author Kanomiya
 *
 */

public abstract class BlockMagicBase extends BlockContainer {

	public BlockMagicBase(Material materialIn) {
		super(materialIn);
	}

	@Override public final boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		MagicStatus statusTile = KMagicAPI.getMagicStatus(worldIn.getTileEntity(pos));

		boolean flag = false;

		if (statusTile != null) {
			// TODO: Activate
		}

		flag = flag || onBlockActivated(worldIn, pos, state, statusTile, playerIn, side, hitX, hitY, hitZ);

		return flag;
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, MagicStatus tileStatus, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		return false;
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
