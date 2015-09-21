package com.kanomiya.mcmod.kmagic.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.kanomiya.mcmod.kmagic.KMagic;
import com.kanomiya.mcmod.kmagic.api.block.BlockMagicBase;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.api.tileentity.TileEntityMagicBase;
import com.kanomiya.mcmod.kmagic.api.tileentity.signal.SignalData;
import com.kanomiya.mcmod.kmagic.tileentity.signal.TileEntityMagicKMSignalSender;

/**
 * @author Kanomiya
 *
 */

public class BlockMagicKMSignalSender extends BlockMagicBase {

	public static final PropertyBool ACTIVATED = PropertyBool.create("activated");


	public BlockMagicKMSignalSender() {
		super(Material.rock);
		setDefaultState(blockState.getBaseState().withProperty(ACTIVATED, Boolean.FALSE));

		setHardness(3.5F);
		setStepSound(soundTypePiston);

		setCreativeTab(KMagic.tab);

	}

	@Override public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, MagicStatus tileStatus, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {

		if (tileStatus != null && tileStatus.getMagicObject() instanceof TileEntityMagicKMSignalSender) {
			TileEntityMagicKMSignalSender tile = (TileEntityMagicKMSignalSender) tileStatus.getMagicObject();
			if (tile.canActivate()) {
				tile.activate(new SignalData(0));
			}

			return true;
		}

		return false;
	}


	@Override @SideOnly(Side.CLIENT)
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {

		if ((Boolean) state.getValue(ACTIVATED)) {
			double d0 = pos.getX() + 0.5D;
			double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
			double d2 = pos.getZ() + 0.5D;
			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;

			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}





	@Override public TileEntityMagicBase createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMagicKMSignalSender(10);
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
