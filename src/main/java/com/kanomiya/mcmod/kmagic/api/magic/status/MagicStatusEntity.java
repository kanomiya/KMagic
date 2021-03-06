package com.kanomiya.mcmod.kmagic.api.magic.status;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.magic.status.base.IMagicObject;

/**
 * @author Kanomiya
 *
 */
public class MagicStatusEntity extends MagicStatus implements IExtendedEntityProperties {
	protected Entity entity;


	protected MagicStatusEntity(IMagicObject parMObj, Entity parEntity) {
		super(parMObj);

		entity = parEntity;
	}

	protected MagicStatusEntity(Entity parEntity) {
		this((IMagicObject) parEntity, parEntity);
	}


	@Override public void init(Entity entity, World world) {

	}





	@Override public void evaluate() {
		super.evaluate();


		/* TODO:
		ItemStack[] inventory = entity.getInventory();
		if (inventory != null) {
			for (ItemStack stack: inventory) {
				if (stack != null && stack.getItem() instanceof IMagicItem) {
					StackWrapper wrapper = new StackWrapper(stack, (IMagicItem) stack.getItem());

					wrapper.initHolderStatus(this);
				}
			}
		}
		*/

	}




	// *----------------------------------------------------------------------------------------------*
	//
	// *----------------------------------------------------------------------------------------------*

	public Entity getEntity() { return entity; }
	public World getWorldObj() { return entity.worldObj; }






	@Override public void saveNBTData(NBTTagCompound nbt) {
		NBTTagCompound magicNbt = new NBTTagCompound();
		writeToNBT(magicNbt);

		nbt.setTag(KMagicAPI.STR_DATANAME, magicNbt);
	}

	@Override public void loadNBTData(NBTTagCompound nbt) {
		readFromNBT(nbt.getCompoundTag(KMagicAPI.STR_DATANAME));
	}







}
