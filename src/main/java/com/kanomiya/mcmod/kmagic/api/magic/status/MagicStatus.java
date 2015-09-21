package com.kanomiya.mcmod.kmagic.api.magic.status;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;

import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.MagicNBTUtils;
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

	protected int prevmp = -1;
	protected int prevmaxMp = -1;

	protected IMagicObject mObj;
	protected final MagicMaterials materials;
	protected final MagicAbilityHolder abilityHolder;


	protected MagicStatus(IMagicObject parMObj) {
		mObj = parMObj;
		materials = new MagicMaterials();
		abilityHolder = new MagicAbilityHolder(this);

		evaluate();
	}


	protected void clear() {
		materials.clear();
		abilityHolder.clear();
	}

	public void evaluate() {
		clear();

		mObj.initMagicStatus(this);

		MagicStatusInitEvent event = new MagicStatusInitEvent(this);
		MinecraftForge.EVENT_BUS.post(event);

		maxMp = materials.getMpCapacity() +event.extraMaxMp;

	}


	public int getMp() { return mp; }
	public int getMaxMp() { return maxMp; }




	public IMagicObject getMagicObject() { return mObj; }
	public MagicMaterials getMagicMaterials() { return materials; }
	public MagicAbilityHolder getAbilityHolder() { return abilityHolder; }






	public boolean isUpdated() {
		return (mpIsUpdated() || maxMpIsUpdated() || abilityHolder.isUpdated);
	}

	private boolean mpIsUpdated() { return (prevmp != mp); }
	private boolean maxMpIsUpdated() { return (prevmaxMp != maxMp); }

	// *----------------------------------------------------------------------------------------------*
	//
	// *----------------------------------------------------------------------------------------------*

	public void onUpdate(World worldIn) {
		abilityHolder.update(worldIn);

	}



	public void onSync() {
		prevmp = mp;
		prevmaxMp = maxMp;

		abilityHolder.isUpdated = false;
	}


	// *----------------------------------------------------------------------------------------------*
	//
	// *----------------------------------------------------------------------------------------------*

	public void writeToNBT(NBTTagCompound nbt) {
		evaluate();

		nbt.setInteger("mp", mp);
		nbt.setInteger("maxMp", maxMp);

		materials.writeToNBT(nbt);
		abilityHolder.writeToNBT(nbt);

		onSync();
	}

	public void readFromNBT(NBTTagCompound nbt) {
		clear();

		mp = nbt.getInteger("mp");
		maxMp = nbt.getInteger("maxMp");

		onSync();

		materials.readFromNBT(nbt);
		abilityHolder.readFromNBT(nbt);

	}







	public static boolean dealMp(MagicStatus vendor, MagicStatus buyer, int amount, boolean adjustable, boolean demo) {
		if (amount == 0 || (vendor == null && buyer == null)) return true;

		int venMp = (vendor == null) ? 0 : vendor.getMp();
		int venMaxMp = (vendor == null) ? 0 : vendor.getMaxMp();
		int buyMp = (buyer == null) ? 0 : buyer.getMp();
		int buyMaxMp = (buyer == null) ? 0 : buyer.getMaxMp();

		int venResult = (vendor == null) ? 0 : venMp -amount;
		int buyResult = (buyer == null) ? 0 : buyMp +amount;

		if (adjustable) {
			if (venResult < 0) {
				buyResult += -venResult;
				amount -= -venResult;
				venResult = 0;
			}

			if (buyResult < 0) {
				venResult += -buyResult;
				amount -= -buyResult;
				buyResult = 0;
			}

			if (vendor != null && venMaxMp < venResult) {
				buyResult -= venMaxMp -venResult;
				amount += venMaxMp -venResult;
				venResult = venMaxMp;
			}

			if (buyer != null && buyMaxMp < buyResult) {
				venResult -= buyMaxMp -buyResult;
				amount += buyMaxMp -buyResult;
				buyResult = buyMaxMp;

			}

		}

		if (amount != 0) {
			boolean flag1 = (vendor == null) || (0 <= venResult && venResult <= venMaxMp);
			boolean flag2 = (buyer == null) || (0 <= buyResult && buyResult <= buyMaxMp);

			if (flag1 && flag2) {

				if (! demo) {
					if (! MinecraftForge.EVENT_BUS.post(new MpMoveEvent.Pre(vendor, buyer, amount))) {

						if (vendor != null) {
							boolean creativeFlag = false;

							if (vendor instanceof MagicStatusPlayer) creativeFlag = ((PlayerWrapper) vendor.mObj).getPlayer().capabilities.isCreativeMode;

							if (! creativeFlag) vendor.mp = venResult;
						}

						if (buyer != null) buyer.mp = buyResult;

						MinecraftForge.EVENT_BUS.post(new MpMoveEvent.Post(vendor, buyer, amount));
					}

				}

				return true;
			}
		}

		return false;
	}



	protected static MagicStatus newInstance(Object obj) {
		if (obj instanceof IMagicObject) {
			if (obj instanceof Entity) return newInstance((Entity) obj);

			return new MagicStatus((IMagicObject) obj);
		}

		if (obj instanceof ItemStack) return newInstance((ItemStack) obj);

		return null;
	}

	public static MagicStatusEntity newInstance(Entity entity) {
		if (entity == null) return null;

		if (entity instanceof EntityPlayer) return new MagicStatusPlayer((EntityPlayer) entity);
		return new MagicStatusEntity(entity);
	}


	public static MagicStatus newInstance(ItemStack stack) {
		if (! (stack.getItem() instanceof IMagicItem)) return null;
		return newInstance(new StackWrapper(stack, (IMagicItem) stack.getItem()));
	}

	public static MagicStatus newInstance(StackWrapper stackWrap) {
		return new MagicStatus(stackWrap);
	}


	/**
	 *
	 *
	 *
	 * @param entity
	 * @return
	 * 	{@link Entity#getExtendedProperties(String)}<br>
	 * 	Propertiesが登録されていない場合はnullを返す
	 */
	public static MagicStatus loadInstance(Entity entity) {
		if (entity == null) return null;

		IExtendedEntityProperties properties = entity.getExtendedProperties(KMagicAPI.STR_DATANAME);

		if (properties instanceof MagicStatus) return (MagicStatus) properties;

		return null;
	}

	protected static MagicStatus loadInstance(IMagicObject obj, NBTTagCompound nbt) {
		MagicStatus status;

		if (obj instanceof StackWrapper) {
			status = newInstance(((StackWrapper) obj).stack);
			status.readFromNBT(nbt);

		} else {
			status = obj.getMagicStatus();

			// 未生成の場合
			if (status == null) {
				status = newInstance(obj);
				status.readFromNBT(nbt);
			}
		}

		return status;
	}

	public static MagicStatus loadInstance(TileEntity tile) {
		if (! (tile instanceof IMagicObject)) return null;
		return loadInstance((IMagicObject) tile, MagicNBTUtils.getMagicNBT(tile));
	}

	public static MagicStatus loadInstance(ItemStack stack) {
		if (stack == null || ! (stack.getItem() instanceof IMagicItem)) return null;

		if (MagicNBTUtils.hasMagicNBT(stack)) {
			return loadInstance(new StackWrapper(stack, (IMagicItem) stack.getItem()), MagicNBTUtils.getMagicNBT(stack));
		}

		return newInstance(stack);
	}


}
