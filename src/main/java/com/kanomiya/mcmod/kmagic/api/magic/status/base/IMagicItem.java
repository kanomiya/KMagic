package com.kanomiya.mcmod.kmagic.api.magic.status.base;

import net.minecraft.item.ItemStack;

import com.kanomiya.mcmod.kmagic.api.magic.material.IHasMagicMaterial;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatusEntity;
import com.kanomiya.mcmod.kmagic.api.magic.status.wrapper.StackWrapper;

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
