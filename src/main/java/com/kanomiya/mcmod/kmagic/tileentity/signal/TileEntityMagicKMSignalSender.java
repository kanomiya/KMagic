package com.kanomiya.mcmod.kmagic.tileentity.signal;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import com.kanomiya.mcmod.kanomiyacore.util.bit.BitFieldHelper;
import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.magic.material.MagicMaterial;
import com.kanomiya.mcmod.kmagic.api.magic.material.MagicMaterials;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicAbilityHolder;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.api.tileentity.TileEntityMagicBase;
import com.kanomiya.mcmod.kmagic.api.tileentity.signal.IKMSignal;
import com.kanomiya.mcmod.kmagic.api.tileentity.signal.SignalData;
import com.kanomiya.mcmod.kmagic.api.tileentity.signal.SignalUtils;
import com.kanomiya.mcmod.kmagic.block.BlockMagicKMSignalSender;
import com.kanomiya.mcmod.kmagic.magic.ability.MADrainMp;

/**
 * @author Kanomiya
 *
 */
public class TileEntityMagicKMSignalSender extends TileEntityMagicBase implements IKMSignal {
	private int cost;
	private boolean isActivated = false;

	public TileEntityMagicKMSignalSender() { cost = 5; }

	public TileEntityMagicKMSignalSender(int parCost) {
		cost = parCost;

	}


	@Override public void initMagicStatus(MagicStatus status) {
		MagicMaterials materials = status.getMagicMaterials();

		materials.add(MagicMaterial.STONE, 4);

		MagicAbilityHolder holder = status.getAbilityHolder();
		holder.addAbility(new MADrainMp(status));

	}

	public boolean canActivate() {
		return KMagicAPI.isFull(status.getMp(), cost);
	}


	public void activate(SignalData data) {

		if (MagicStatus.dealMp(status, null, cost, false, false)) {
			isActivated = true;

			worldObj.setBlockState(pos, worldObj.getBlockState(pos).withProperty(BlockMagicKMSignalSender.ACTIVATED, Boolean.TRUE));
		}

		if (isActivated) SignalUtils.sendSignal(data, worldObj, this, pos);

	}

	@Override public boolean receiveSignal(SignalData data, EnumFacing from) {
		worldObj.setBlockState(pos, worldObj.getBlockState(pos).withProperty(BlockMagicKMSignalSender.ACTIVATED, BitFieldHelper.intToBool(data.getData())));

		return true;
	}



	@Override public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		NBTTagCompound nbt = getTileData();
		cost = nbt.getInteger("cost");
		isActivated = nbt.getBoolean("isActivated");

	}

	@Override public void writeToNBT(NBTTagCompound compound) {
		NBTTagCompound nbt = getTileData();
		nbt.setInteger("cost", cost);
		nbt.setBoolean("isActivated", isActivated);

		super.writeToNBT(compound);
	}


}
