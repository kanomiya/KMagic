package com.kanomiya.mcmod.kmagic.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.kanomiya.mcmod.kmagic.KMagic;
import com.kanomiya.mcmod.kmagic.tileentity.signal.TileEntityKMSignalPortal;

/**
 * @author Kanomiya
 *
 */
public class BlockKMSignalPortal extends BlockContainer {

	public static final PropertyBool ACTIVATED = BlockMagicKMSignalSender.ACTIVATED;


	public BlockKMSignalPortal() {
		super(Material.rock);
		setDefaultState(blockState.getBaseState().withProperty(ACTIVATED, Boolean.FALSE));

		setHardness(3.5F);
		setStepSound(soundTypePiston);

		setCreativeTab(KMagic.tab);

	}


	@Override public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
	{
		float f = (((Boolean) state.getValue(ACTIVATED))) ? 0.125f : 0f;
		return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() +1, pos.getY() +1 -f, pos.getZ() +1);
	}

	// @Override public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
	@Override public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{

		if (entityIn instanceof EntityFX) return;

		if ((Boolean) state.getValue(ACTIVATED)) {
			((TileEntityKMSignalPortal) worldIn.getTileEntity(pos)).action(entityIn);


			/*
			if (entityIn instanceof EntityPlayerMP) {
				EntityPlayerMP playerMp = (EntityPlayerMP) entityIn;
				ServerConfigurationManager scmanager = playerMp.mcServer.getConfigurationManager();
				WorldServer worldServer = playerMp.mcServer.worldServerForDimension(targetDimId);

				scmanager.transferPlayerToDimension(playerMp, targetDimId, new TeleporterKMagic(worldServer));

			} else {

				entityIn.travelToDimension(targetDimId);

			}
			*/

		}


	}


	@Override public int quantityDropped(Random random) { return 0; }


	@Override public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityKMSignalPortal();
	}


	@Override public int getRenderType() {
		return 3;
	}


	@Override @SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state) {
		return getDefaultState().withProperty(ACTIVATED, Boolean.FALSE);
	}

	@Override public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(ACTIVATED, (meta == 1));
	}

	@Override public int getMetaFromState(IBlockState state) {
		return ((Boolean) state.getValue(ACTIVATED)) ? 1 : 0;
	}

	@Override protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {ACTIVATED});
	}



}
