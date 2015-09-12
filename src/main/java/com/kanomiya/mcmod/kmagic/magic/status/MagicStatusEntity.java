package com.kanomiya.mcmod.kmagic.magic.status;

import com.kanomiya.mcmod.kmagic.KMagicAPI;
import com.kanomiya.mcmod.kmagic.magic.ability.MANaturalMpHealing;
import com.kanomiya.mcmod.kmagic.magic.status.base.IMagicObject;
import com.kanomiya.mcmod.kmagic.network.MessageMagicStatusToClient;
import com.kanomiya.mcmod.kmagic.network.PacketHandler;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

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

	public MagicStatusEntity(Entity parEntity) {
		this((IMagicObject) parEntity, parEntity);
	}


	@Override public void init(Entity entity, World world) {
		evaluate();
		sync(world.isRemote);

	}


	@Override public void onSync(boolean client) {
		if (! client) PacketHandler.INSTANCE.sendToAll(new MessageMagicStatusToClient(this));

	}




	@Override public void evaluate() {
		super.evaluate();

		abilityHolder.addAbility(new MANaturalMpHealing(this));

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
