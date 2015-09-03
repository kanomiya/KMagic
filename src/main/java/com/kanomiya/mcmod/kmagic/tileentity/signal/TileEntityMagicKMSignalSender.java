package com.kanomiya.mcmod.kmagic.tileentity.signal;

import com.kanomiya.mcmod.kmagic.KMagicAPI;
import com.kanomiya.mcmod.kmagic.block.BlockMagicKMSignalSender;
import com.kanomiya.mcmod.kmagic.magic.material.MagicMaterial;
import com.kanomiya.mcmod.kmagic.magic.material.MagicMaterials;
import com.kanomiya.mcmod.kmagic.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.tileentity.TileEntityMagicBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

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

		// TODO: abilities.addAbility(new MagicAbilitySendMp(status), new MARTrigger(MATActivate.class));

	}

	public boolean canActivate() {
		return KMagicAPI.isFull(KMagicAPI.getMagicStatus(this).getMp(), cost);
	}


	public void activate(SignalData data) {

		if (MagicStatus.dealMp(KMagicAPI.getMagicStatus(this), null, cost, false, false)) {
			isActivated = true;

			worldObj.setBlockState(pos, worldObj.getBlockState(pos).withProperty(BlockMagicKMSignalSender.ACTIVATED, Boolean.TRUE));
		}

		if (isActivated) SignalUtils.sendSignal(data, worldObj, this, pos);

	}

	@Override public boolean receiveSignal(SignalData data, EnumFacing from) {
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