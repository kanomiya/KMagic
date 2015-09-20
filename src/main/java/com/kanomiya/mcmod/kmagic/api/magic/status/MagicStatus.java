package com.kanomiya.mcmod.kmagic.api.magic.status;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;

import com.kanomiya.mcmod.kmagic.KMagic;
import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.magic.event.MagicStatusInitEvent;
import com.kanomiya.mcmod.kmagic.api.magic.event.MpMoveEvent;
import com.kanomiya.mcmod.kmagic.api.magic.material.MagicMaterials;
import com.kanomiya.mcmod.kmagic.api.magic.status.base.IMagicItem;
import com.kanomiya.mcmod.kmagic.api.magic.status.base.IMagicObject;
import com.kanomiya.mcmod.kmagic.api.magic.status.wrapper.PlayerWrapper;
import com.kanomiya.mcmod.kmagic.api.magic.status.wrapper.StackWrapper;

/**
 * @author Kanomiya
 *
 */
public class MagicStatus {
	protected int mp = 0;
	protected int maxMp = 0;

	protected int prevmp = 0;
	protected int prevmaxMp = 0;

	protected IMagicObject mObj;
	protected final MagicMaterials materials;
	protected final MagicAbilityHolder abilityHolder;


	protected MagicStatus(IMagicObject parMObj) {
		mObj = parMObj;
		materials = new MagicMaterials();
		abilityHolder = new MagicAbilityHolder(this);

		evaluate();
	}

	protected MagicStatus(IMagicObject parMObj, NBTTagCompound nbt) {
		this(parMObj);

		readFromNBT(nbt);
	}


	protected void clear() {
		materials.clear();
		abilityHolder.clear();
	}

	public void evaluate() {
		KMagic.logger.info("is");
		clear();

		mObj.initMagicStatus(this);

		MagicStatusInitEvent event = new MagicStatusInitEvent(this);
		MinecraftForge.EVENT_BUS.post(event);

		maxMp =  materials.getMpCapacity() +event.extraMaxMp;

	}


	public int getMp() { return mp; }
	public int getMaxMp() { return maxMp; }




	public IMagicObject getMagicObject() { return mObj; }
	public MagicMaterials getMagicMaterials() { return materials; }
	public MagicAbilityHolder getAbilityHolder() { return abilityHolder; }






	public boolean isUpdated() {
		return (mpIsUpdated() || maxMpIsUpdated());
	}

	private boolean mpIsUpdated() { return (prevmp != mp); }
	private boolean maxMpIsUpdated() { return (prevmaxMp != maxMp); }

	// *----------------------------------------------------------------------------------------------*
	//
	// *----------------------------------------------------------------------------------------------*

	public void onUpdate(World worldIn) {
		abilityHolder.onUpdate(worldIn);

		if (isUpdated()) { normalize(); sync(worldIn.isRemote); }
	}


	public void normalize() {
		if (maxMp < mp) { mp = maxMp; }

	}

	public void sync(boolean client) {
		if (! client) evaluate();

		onSync(client);

		prevmp = mp;
		prevmaxMp = maxMp;
	}

	public void onSync(boolean client) {  }


	// *----------------------------------------------------------------------------------------------*
	//
	// *----------------------------------------------------------------------------------------------*

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("mp", mp);
		nbt.setInteger("maxMp", maxMp);

		materials.writeToNBT(nbt);
		abilityHolder.writeToNBT(nbt);

	}

	public void readFromNBT(NBTTagCompound nbt) {
		mp = nbt.getInteger("mp");
		maxMp = nbt.getInteger("maxMp");

		prevmp = mp;
		prevmaxMp = maxMp;

		materials.readFromNBT(nbt);
		abilityHolder.readFromNBT(nbt);

	}







	public static boolean dealMp(MagicStatus vendor, MagicStatus buyer, int amount, boolean adjustable, boolean demo) {
		if (amount == 0 || (vendor == null && buyer == null)) return true;

		int venMp = (vendor == null) ? 0 : vendor.getMp();
		int venMaxMp = (vendor == null) ? 0 : vendor.getMaxMp();
		int buyMp = (buyer == null) ? 0 : buyer.getMp();
		int buyMaxMp = (buyer == null) ? 0 : buyer.getMaxMp();

		int result1 = (vendor == null) ? 0 : venMp -amount;
		int result2 = (buyer == null) ? 0 : buyMp +amount;

		if (adjustable) {
			if (result1 < 0) {
				result2 += -result1;
				amount -= -result1;
				result1 = 0;
			}

			if (result2 < 0) {
				result1 += -result2;
				amount -= -result2;
				result2 = 0;
			}

			if (vendor != null && venMaxMp < result1) {
				result2 -= venMaxMp -result1;
				amount += venMaxMp -result1;
				result1 = venMaxMp;
			}

			if (buyer != null && buyMaxMp < result2) {
				result1 -= buyMaxMp -result2;
				amount += buyMaxMp -result2;
				result2 = buyMaxMp;

			}

		}

		if (amount != 0) {
			boolean flag1 = (vendor == null) || (0 <= result1 && result1 <= venMaxMp);
			boolean flag2 = (buyer == null) || (0 <= result2 && result2 <= buyMaxMp);

			if (flag1 && flag2) {

				if (! demo) {
					if (! MinecraftForge.EVENT_BUS.post(new MpMoveEvent.Pre(vendor, buyer, amount))) {

						if (vendor != null) {
							boolean creativeFlag = false;

							if (vendor instanceof MagicStatusPlayer) creativeFlag = ((PlayerWrapper) vendor.mObj).getPlayer().capabilities.isCreativeMode;

							if (! creativeFlag) vendor.mp = result1;
						}

						if (buyer != null) buyer.mp = result2;

						MinecraftForge.EVENT_BUS.post(new MpMoveEvent.Post(vendor, buyer, amount));
					}

				}

				return true;
			}
		}

		return false;
	}



	public static MagicStatusPlayer getInstance(EntityPlayer player) {
		return new MagicStatusPlayer(player);
	}

	public static MagicStatusEntity getInstance(Entity entity) {
		if (! KMagicAPI.isMagicOwner(entity)) return null;

		IExtendedEntityProperties extProp = entity.getExtendedProperties(KMagicAPI.STR_DATANAME);

		if (extProp != null && extProp instanceof MagicStatusEntity) {
			return ((MagicStatusEntity) extProp);
		}

		return null;
	}

	public static MagicStatus getInstance(TileEntity tileEntity) {
		if (! KMagicAPI.isMagicOwner(tileEntity)) return null;

		IMagicObject mObj = ((IMagicObject) tileEntity);

		if (mObj.getMagicStatus() == null) {
			return new MagicStatus(mObj);
		}

		return mObj.getMagicStatus();
	}

	public static MagicStatus getInstance(ItemStack stack) {
		if (! KMagicAPI.isMagicOwner(stack)) return null;
		return new MagicStatus(new StackWrapper(stack, (IMagicItem) stack.getItem()));
	}

	public static MagicStatus getInstance(ItemStack stack, NBTTagCompound nbt) {
		if (! KMagicAPI.isMagicOwner(stack)) return null;
		return new MagicStatus(new StackWrapper(stack, (IMagicItem) stack.getItem()), nbt);
	}


}
