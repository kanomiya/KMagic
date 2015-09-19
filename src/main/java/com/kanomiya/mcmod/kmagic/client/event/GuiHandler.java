package com.kanomiya.mcmod.kmagic.client.event;

import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.client.gui.GuiAbilityHolder;
import com.kanomiya.mcmod.kmagic.client.gui.GuiMagicFurnace;
import com.kanomiya.mcmod.kmagic.inventory.ContainerMagicFurnace;
import com.kanomiya.mcmod.kmagic.tileentity.TileEntityMagicFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * @author Kanomiya
 *
 */

public class GuiHandler implements IGuiHandler {
	public static final int GUI_MAGICFURNACE = 0;
	public static final int GUI_ABILITYHOLDER = 1;

	@Override public Object getServerGuiElement(int id, EntityPlayer playerIn, World worldIn, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tileEntity = worldIn.getTileEntity(pos);

		if (id == GUI_MAGICFURNACE) {
			if (tileEntity instanceof TileEntityMagicFurnace) {
				return new ContainerMagicFurnace(playerIn.inventory, (TileEntityMagicFurnace) tileEntity);
			}
		}
		else if (id == GUI_ABILITYHOLDER) {
			return null;
		}


		return null;
	}

	@Override public Object getClientGuiElement(int id, EntityPlayer playerIn, World worldIn, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tileEntity = worldIn.getTileEntity(pos);

		if (id == GUI_MAGICFURNACE) {
			if (tileEntity instanceof TileEntityMagicFurnace) {
				return new GuiMagicFurnace(playerIn.inventory, (TileEntityMagicFurnace) tileEntity);
			}
		}
		else if (id == GUI_ABILITYHOLDER) {
			return new GuiAbilityHolder(KMagicAPI.getMagicStatus(playerIn));
		}

		return null;
	}

}
