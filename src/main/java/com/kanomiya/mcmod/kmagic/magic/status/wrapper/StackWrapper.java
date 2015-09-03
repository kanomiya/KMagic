package com.kanomiya.mcmod.kmagic.magic.status.wrapper;

import com.kanomiya.mcmod.kmagic.KMagicAPI;
import com.kanomiya.mcmod.kmagic.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.magic.status.MagicStatusEntity;
import com.kanomiya.mcmod.kmagic.magic.status.base.IMagicItem;
import com.kanomiya.mcmod.kmagic.magic.status.base.IMagicObject;

import net.minecraft.item.ItemStack;

/**
 * @author Kanomiya
 *
 */
public class StackWrapper implements IMagicObject {

	public final ItemStack stack;
	public final IMagicItem item;

	public StackWrapper(ItemStack parStack, IMagicItem parItem) {
		stack = parStack;
		item = parItem;
	}

	@Override public void initMagicStatus(MagicStatus status) {
		item.getMagicMaterials(stack.getMetadata(), status.getMagicMaterials());
	}

	@Override public MagicStatus getMagicStatus() {
		return KMagicAPI.getMagicStatus(stack);
	}

	public void initHolderStatus(MagicStatus status) {
		if (status instanceof MagicStatusEntity) item.initHolderStatus(stack, getMagicStatus(), (MagicStatusEntity) status);
	}


}
