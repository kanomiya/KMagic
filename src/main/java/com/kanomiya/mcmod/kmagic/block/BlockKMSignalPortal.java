package com.kanomiya.mcmod.kmagic.block;

import java.util.Random;

import com.kanomiya.mcmod.kmagic.KMConfig;
import com.kanomiya.mcmod.kmagic.KMagic;
import com.kanomiya.mcmod.kmagic.tileentity.signal.TileEntityKMSignalPortal;
import com.kanomiya.mcmod.kmagic.world.TeleporterKMagic;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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


	@Override public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		super.onFallenUpon(worldIn, pos, entityIn, fallDistance);

		IBlockState state = worldIn.getBlockState(pos);

		if ((Boolean) state.getValue(ACTIVATED)) {
			int targetDimId = -1;

			if (entityIn.dimension == 0) targetDimId = KMConfig.DIMID_KMAGIC;
			else if (entityIn.dimension == KMConfig.DIMID_KMAGIC) targetDimId = 0;

			if (targetDimId == -1) return ;
			if (0 < entityIn.timeUntilPortal) return ;


			entityIn.timeUntilPortal = entityIn.getPortalCooldown();
			entityIn.setInPortal();

			// BlockPortal

				MinecraftServer mcServer = MinecraftServer.getServer();

				ServerConfigurationManager scmanager = mcServer.getConfigurationManager();
				// WorldServer worldServer = mcServer.worldServerForDimension(entityIn.dimension);
				WorldServer targetWorldServer = mcServer.worldServerForDimension(targetDimId);

				if (! (entityIn instanceof EntityPlayerSP)) {

					if (entityIn instanceof EntityPlayerMP) {
						scmanager.transferPlayerToDimension((EntityPlayerMP) entityIn, targetDimId, new TeleporterKMagic(targetWorldServer));

					} else {
						// TODO: scmanager.transferEntityToWorld(entityIn, targetDimId, worldServer, targetWorldServer, new TeleporterKMagic(targetWorldServer));
						// entityIn.travelToDimension(targetDimId);
					}

				}


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

		// BlockPressurePlate

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
