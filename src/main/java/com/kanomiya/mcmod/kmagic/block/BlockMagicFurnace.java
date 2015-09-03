package com.kanomiya.mcmod.kmagic.block;

import java.util.Random;

import com.kanomiya.mcmod.kmagic.KMBlocks;
import com.kanomiya.mcmod.kmagic.KMagic;
import com.kanomiya.mcmod.kmagic.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.tileentity.TileEntityMagicBase;
import com.kanomiya.mcmod.kmagic.tileentity.TileEntityMagicFurnace;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Kanomiya
 *
 */

public class BlockMagicFurnace extends BlockMagicBase {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool BURNING = PropertyBool.create("burning");
	private static boolean keepInventory;


	public BlockMagicFurnace() {
		super(Material.rock);
		setCreativeTab(KMagic.tab);

		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING, Boolean.FALSE));

		setHardness(3.5F);
		setStepSound(soundTypePiston);

	}

	@Override public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, MagicStatus tileStatus, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {

		TileEntity tileEntity = worldIn.getTileEntity(pos);

		if (tileEntity instanceof TileEntityMagicFurnace) {
			playerIn.openGui(KMagic.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());

			return true;
		}


		return false;
	}



	public static void setState(boolean active, World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		keepInventory = true;

		if (active) {
			worldIn.setBlockState(pos, state.withProperty(BURNING, Boolean.TRUE), 3);
		} else {
			worldIn.setBlockState(pos, state.withProperty(BURNING, Boolean.FALSE), 3);
		}

		keepInventory = false;

		if (tileEntity != null) {
			tileEntity.validate();
			worldIn.setTileEntity(pos, tileEntity);
		}

		/*
			TileEntity tileEntity = world.getTileEntity(pos);
			Boolean isBurning = ((Boolean) state.getValue(BURNING));
			Boolean tileIsBurning = (tileEntity instanceof IInventory && TileEntityMagicFurnace.isBurning((IInventory) tileEntity));

			if (isBurning != tileIsBurning) {

			}
		*/

	}


	@Override public int getLightValue(IBlockAccess world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);

		return ((Boolean) state.getValue(BURNING)) ? 13 : 0;
	}


	@Override public TileEntityMagicBase createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMagicFurnace();
	}











	@Override public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(KMBlocks.blockMagicFurnace);
	}

	@Override public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		setDefaultFacing(worldIn, pos, state);
	}

	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
		if (! worldIn.isRemote) {
			Block block = worldIn.getBlockState(pos.north()).getBlock();
			Block block1 = worldIn.getBlockState(pos.south()).getBlock();
			Block block2 = worldIn.getBlockState(pos.west()).getBlock();
			Block block3 = worldIn.getBlockState(pos.east()).getBlock();
			EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

			if (enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock())
			{
				enumfacing = EnumFacing.SOUTH;
			}
			else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock())
			{
				enumfacing = EnumFacing.NORTH;
			}
			else if (enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock())
			{
				enumfacing = EnumFacing.EAST;
			}
			else if (enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock())
			{
				enumfacing = EnumFacing.WEST;
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}

	@Override @SideOnly(Side.CLIENT)
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if ((Boolean) state.getValue(BURNING)) {
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
			double d0 = pos.getX() + 0.5D;
			double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
			double d2 = pos.getZ() + 0.5D;
			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;

			switch (BlockMagicFurnace.SwitchEnumFacing.FACING_LOOKUP[enumfacing.ordinal()])
			{
				case 1:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
					break;
				case 2:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
					break;
				case 3:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
					break;
				case 4:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}



	@Override public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityMagicFurnace) {
				((TileEntityMagicFurnace)tileentity).setCustomInventoryName(stack.getDisplayName());
			}
		}
	}

	@Override public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (! keepInventory)
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof IInventory)
			{
				InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
				worldIn.updateComparatorOutputLevel(pos, this);
			}
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override public int getComparatorInputOverride(World worldIn, BlockPos pos) {
		return Container.calcRedstone(worldIn.getTileEntity(pos));
	}

	@Override @SideOnly(Side.CLIENT)
	public Item getItem(World worldIn, BlockPos pos) {
		return Item.getItemFromBlock(KMBlocks.blockMagicFurnace);
	}

	@Override public int getRenderType() {
		return 3;
	}

	@Override @SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state) {
		return getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
	}

	@Override public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}

		return getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {FACING, BURNING});
	}

	@SideOnly(Side.CLIENT)
	static final class SwitchEnumFacing
		{
			static final int[] FACING_LOOKUP = new int[EnumFacing.values().length];

			static
			{
				try
				{
					FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 1;
				}
				catch (NoSuchFieldError var4)
				{
					;
				}

				try
				{
					FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 2;
				}
				catch (NoSuchFieldError var3)
				{
					;
				}

				try
				{
					FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 3;
				}
				catch (NoSuchFieldError var2)
				{
					;
				}

				try
				{
					FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 4;
				}
				catch (NoSuchFieldError var1)
				{
					;
				}
			}
		}

}
