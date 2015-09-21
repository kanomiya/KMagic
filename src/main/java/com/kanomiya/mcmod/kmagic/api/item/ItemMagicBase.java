package com.kanomiya.mcmod.kmagic.api.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.MagicNBTUtils;
import com.kanomiya.mcmod.kmagic.api.magic.material.MagicMaterials;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatusEntity;
import com.kanomiya.mcmod.kmagic.api.magic.status.base.IMagicItem;

/**
 *
 * @author Kanomiya
 *
 */
public abstract class ItemMagicBase extends Item implements IMagicItem {


	public boolean syncStatus(ItemStack stackIn, MagicStatus stackStatus) {
		if (stackStatus.isUpdated()) {
			KMagicAPI.setMagicStatus(stackIn, stackStatus);
			return true;
		}

		return false;
	}

	@Override public int getMaxMetadata() { return 0; }

	@Override public void getMagicMaterials(int metadata, MagicMaterials materials) {  }
	@Override public void initHolderStatus(ItemStack stackIn, MagicStatus stackStatus, MagicStatusEntity targetStatus) {  }





	@Override @SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stackIn, EntityPlayer playerIn, List tooltip, boolean advanced) {
		MagicStatus status = KMagicAPI.getMagicStatus(stackIn);

		if (0 < status.getMaxMp()) {
			tooltip.add("MP " + status.getMp() + "/" + status.getMaxMp());
		}



		// TODO: status.getMagicAbilities().addInformation(tooltip);

	}



	@Override public boolean showDurabilityBar(ItemStack stack) {
		if (stack.stackSize != 1) return false;

		NBTTagCompound magicNbt = MagicNBTUtils.getMagicNBT(stack);
		return ! magicNbt.getBoolean("invisibleMpBar"); // KMagicAPI.mpIsFull(MagicNBTUtils.getMagicNBT(stack));
	}

	@Override public double getDurabilityForDisplay(ItemStack stack) {
		NBTTagCompound magicNbt = MagicNBTUtils.getMagicNBT(stack);
		return KMagicAPI.hasMpCapacity(magicNbt) ? 1d -((double) KMagicAPI.getMp(magicNbt) / (double) KMagicAPI.getMaxMp(magicNbt)) : 1d;
	}

	@Override @SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stackIn) {
		return KMagicAPI.mpIsFull(MagicNBTUtils.getMagicNBT(stackIn));
	}





	/*


	public void onUpdate(ItemStack stackIn, MagicStatus stackStatus, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {  }


	public boolean onEntityItemUpdate(EntityItem entityItem, ItemStack stackIn, MagicStatus stackStatus) { return false; }

	public ItemStack onItemRightClick(ItemStack stackIn, MagicStatus stackStatus, World worldIn, EntityPlayer playerIn) { return stackIn; }

	public boolean onItemUse(ItemStack stackIn, MagicStatus stackStatus, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) { return false; }

	public void onUsingTick(ItemStack stackIn, MagicStatus stackStatus, EntityPlayer playerIn, int count) {  }

	public void onPlayerStoppedUsing(ItemStack stackIn, MagicStatus stackStatus, World worldIn, EntityPlayer playerIn, int timeLeft) {  }

	public ItemStack onItemUseFinish(ItemStack stackIn, MagicStatus stackStatus, World worldIn, EntityPlayer playerIn) { return stackIn; }

	public void onArmorTick(World worldIn, EntityPlayer playerIn, ItemStack stackIn, MagicStatus stackStatus) {  }

	public boolean onEntitySwing(EntityLivingBase entityIn, ItemStack stackIn, MagicStatus stackStatus) { return false; }

	public boolean hitEntity(ItemStack stackIn, MagicStatus stackStatus, EntityLivingBase target, EntityLivingBase entityIn) { return false; }

	public boolean onLeftClickEntity(ItemStack stackIn, MagicStatus stackStatus, EntityPlayer playerIn, Entity target) { return false; }

	public boolean itemInteractionForEntity(ItemStack stackIn, MagicStatus stackStatus, EntityPlayer playerIn, EntityLivingBase target) { return false; }

	public void onCreated(ItemStack stackIn, MagicStatus stackStatus, World worldIn, EntityPlayer playerIn) {  }

	public boolean onDroppedByPlayer(ItemStack stackIn, MagicStatus stackStatus, EntityPlayer playerIn) { return true; }














	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
	// FINAL OVERRIDES
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*


	@Override public final void onUpdate(ItemStack stackIn, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		MagicStatus stackStatus = KMagicAPI.getMagicStatus(stackIn);
		stackStatus.onUpdate(worldIn);

		stackStatus.getMagicAbilities().launch(MagicAbilityTrigger.ITEM_UPDATE, KMagicAPI.getMagicStatus(entityIn), new MagicArgEmpty());

		onUpdate(stackIn, stackStatus, worldIn, entityIn, itemSlot, isSelected);

		syncStatus(stackIn, stackStatus);
	}

	@Override public final boolean onEntityItemUpdate(EntityItem entityItem) {
		ItemStack stackIn = entityItem.getEntityItem();
		MagicStatus stackStatus = KMagicAPI.getMagicStatus(stackIn);

		stackStatus.getMagicAbilities().launch(MagicAbilityTrigger.ITEM_ENTITYUPDATE, KMagicAPI.getMagicStatus(entityItem), new MagicArgEmpty());


		boolean flag = onEntityItemUpdate(entityItem, stackIn, stackStatus);

		if (syncStatus(stackIn, stackStatus)) {
			entityItem.setEntityItemStack(stackIn);
		}


		return flag;
	}

	@Override public final ItemStack onItemRightClick(ItemStack stackIn, World worldIn, EntityPlayer playerIn) {

		MagicStatus stackStatus = KMagicAPI.getMagicStatus(stackIn);

		stackStatus.getMagicAbilities().launch(MagicAbilityTrigger.ITEM_ACTIVATED, KMagicAPI.getMagicStatus(playerIn), new MagicArgEmpty());


		ItemStack result = onItemRightClick(stackIn, stackStatus, worldIn, playerIn);

		syncStatus(result, stackStatus);

		return result;
	}


	@Override public final boolean onItemUse(ItemStack stackIn, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {

		MagicStatus stackStatus = KMagicAPI.getMagicStatus(stackIn);

		stackStatus.getMagicAbilities().launch(MagicAbilityTrigger.ITEM_ACTIVATING_BLOCK, KMagicAPI.getMagicStatus(playerIn), new MagicArgActivatedBlock(worldIn, pos, side, hitX, hitY, hitZ));


		boolean flag = onItemUse(stackIn, stackStatus, playerIn, worldIn, pos, side, hitX, hitY, hitZ);

		syncStatus(stackIn, stackStatus);

		return flag;
	}

	@Override public final void onUsingTick(ItemStack stackIn, EntityPlayer playerIn, int count) {
		MagicStatus stackStatus = KMagicAPI.getMagicStatus(stackIn);

		stackStatus.getMagicAbilities().launch(MagicAbilityTrigger.ITEM_USING_TICK, KMagicAPI.getMagicStatus(playerIn), new MagicArgUsing(playerIn.worldObj, playerIn, count));


		onUsingTick(stackIn, stackStatus, playerIn, count);

		syncStatus(stackIn, stackStatus);

	}

	@Override public final void onPlayerStoppedUsing(ItemStack stackIn, World worldIn, EntityPlayer playerIn, int timeLeft) {
		MagicStatus stackStatus = KMagicAPI.getMagicStatus(stackIn);

		stackStatus.getMagicAbilities().launch(MagicAbilityTrigger.ITEM_USING_STOP, KMagicAPI.getMagicStatus(playerIn), new MagicArgUsing(worldIn, playerIn, getMaxItemUseDuration(stackIn) -timeLeft));


		onPlayerStoppedUsing(stackIn, stackStatus, worldIn, playerIn, timeLeft);

		syncStatus(stackIn, stackStatus);

	}

	@Override public final ItemStack onItemUseFinish(ItemStack stackIn, World worldIn, EntityPlayer playerIn) {
		MagicStatus stackStatus = KMagicAPI.getMagicStatus(stackIn);

		stackStatus.getMagicAbilities().launch(MagicAbilityTrigger.ITEM_USE_FINISH, KMagicAPI.getMagicStatus(playerIn), new MagicArgEmpty());


		ItemStack result = onItemUseFinish(stackIn, stackStatus, worldIn, playerIn);

		syncStatus(stackIn, stackStatus);

		return result;
	}

	@Override public final void onArmorTick(World worldIn, EntityPlayer playerIn, ItemStack stackIn) {
		MagicStatus stackStatus = KMagicAPI.getMagicStatus(stackIn);

		stackStatus.getMagicAbilities().launch(MagicAbilityTrigger.ITEM_ARMOR_TICK, KMagicAPI.getMagicStatus(playerIn), new MagicArgEmpty());


		onArmorTick(worldIn, playerIn, stackIn, stackStatus);

		syncStatus(stackIn, stackStatus);
	}

	@Override public final boolean onEntitySwing(EntityLivingBase entityIn, ItemStack stackIn) {
		MagicStatus stackStatus = KMagicAPI.getMagicStatus(stackIn);

		stackStatus.getMagicAbilities().launch(MagicAbilityTrigger.ITEM_ENTITYSWING, KMagicAPI.getMagicStatus(entityIn), new MagicArgEmpty());


		boolean flag = onEntitySwing(entityIn, stackIn, stackStatus);

		syncStatus(stackIn, stackStatus);

		return flag;
	}

	@Override public final boolean hitEntity(ItemStack stackIn, EntityLivingBase target, EntityLivingBase entityIn) {
		MagicStatus stackStatus = KMagicAPI.getMagicStatus(stackIn);

		stackStatus.getMagicAbilities().launch(MagicAbilityTrigger.ITEM_HIT_ENTITY, KMagicAPI.getMagicStatus(entityIn), new MagicArgEntityAttacked(target.worldObj, target));


		boolean flag = hitEntity(stackIn, stackStatus, target, entityIn);

		syncStatus(stackIn, stackStatus);

		return flag;
	}

	@Override public final boolean onLeftClickEntity(ItemStack stackIn, EntityPlayer playerIn, Entity target) {
		MagicStatus stackStatus = KMagicAPI.getMagicStatus(stackIn);

		stackStatus.getMagicAbilities().launch(MagicAbilityTrigger.ITEM_LEFTCLICK_ENTITY, KMagicAPI.getMagicStatus(playerIn), new MagicArgEntityAttacked(target.worldObj, target));


		boolean flag = onLeftClickEntity(stackIn, stackStatus, playerIn, target);

		syncStatus(stackIn, stackStatus);

		return flag;
	}

	@Override public final boolean itemInteractionForEntity(ItemStack stackIn, EntityPlayer playerIn, EntityLivingBase target) {
		MagicStatus stackStatus = KMagicAPI.getMagicStatus(stackIn);

		stackStatus.getMagicAbilities().launch(MagicAbilityTrigger.ITEM_INTERACTENTITY, KMagicAPI.getMagicStatus(playerIn), new MagicArgEntityAttacked(target.worldObj, target));


		boolean flag = itemInteractionForEntity(stackIn, stackStatus, playerIn, target);

		syncStatus(stackIn, stackStatus);

		return flag;
	}

	@Override public final void onCreated(ItemStack stackIn, World worldIn, EntityPlayer playerIn) {
		MagicStatus stackStatus = KMagicAPI.getMagicStatus(stackIn);

		stackStatus.getMagicAbilities().launch(MagicAbilityTrigger.ITEM_CREATED, KMagicAPI.getMagicStatus(playerIn), new MagicArgEmpty());


		onCreated(stackIn, stackStatus, worldIn, playerIn);

		syncStatus(stackIn, stackStatus);


	}

	@Override public final boolean onDroppedByPlayer(ItemStack stackIn, EntityPlayer playerIn) {
		MagicStatus stackStatus = KMagicAPI.getMagicStatus(stackIn);

		stackStatus.getMagicAbilities().launch(MagicAbilityTrigger.ITEM_DROPPED, KMagicAPI.getMagicStatus(playerIn), new MagicArgEmpty());


		boolean flag = onDroppedByPlayer(stackIn, stackStatus, playerIn);

		syncStatus(stackIn, stackStatus);

		return flag;
	}


	*/












}
