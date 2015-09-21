package com.kanomiya.mcmod.kmagic.api.magic.status.wrapper;

import net.minecraft.item.ItemStack;

import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatusEntity;
import com.kanomiya.mcmod.kmagic.api.magic.status.base.IMagicItem;
import com.kanomiya.mcmod.kmagic.api.magic.status.base.IMagicObject;

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
