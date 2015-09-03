package com.kanomiya.mcmod.kmagic.magic.status.base;

import com.kanomiya.mcmod.kmagic.magic.material.IHasMagicMaterial;
import com.kanomiya.mcmod.kmagic.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.magic.status.MagicStatusEntity;
import com.kanomiya.mcmod.kmagic.magic.status.wrapper.StackWrapper;

import net.minecraft.item.ItemStack;

/**
 * @author Kanomiya
 *
 */
public interface IMagicItem extends IHasMagicMaterial {

	/**
	 * IMagicItemを実装したItemのItemStackを持つMagicStatusEntityの評価時に呼ばれる
	 *
	 * @param stackIn
	 * @param stackStatus
	 * @param targetStatus
	 * @param targetAbilities
	 *
	 * @see StackWrapper#initMagicStatus(MagicStatus)
	 */
	public void initHolderStatus(ItemStack stackIn, MagicStatus stackStatus, MagicStatusEntity targetStatus);

}