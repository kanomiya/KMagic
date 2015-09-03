package com.kanomiya.mcmod.kmagic.tileentity.signal;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author Kanomiya
 *
 */
public class SignalUtils {

	public static void sendSignal(SignalData data, World world, IKMSignal sender, BlockPos senderPos) {
		TileEntity target = null;

		target = world.getTileEntity(senderPos.add(0,1,0));
		data.sendTo(sender, target, EnumFacing.DOWN);

		target = world.getTileEntity(senderPos.add(0,-1,0));
		data.sendTo(sender, target, EnumFacing.UP);

		target = world.getTileEntity(senderPos.add(-1,0,0));
		data.sendTo(sender, target, EnumFacing.EAST);

		target = world.getTileEntity(senderPos.add(1,0,0));
		data.sendTo(sender, target, EnumFacing.WEST);

		target = world.getTileEntity(senderPos.add(0,0,-1));
		data.sendTo(sender, target, EnumFacing.SOUTH);

		target = world.getTileEntity(senderPos.add(0,0,1));
		data.sendTo(sender, target, EnumFacing.NORTH);

	}


}
