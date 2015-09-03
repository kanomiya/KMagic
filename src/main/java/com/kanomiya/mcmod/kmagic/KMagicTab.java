package com.kanomiya.mcmod.kmagic;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Kanomiya
 *
 */
public class KMagicTab extends CreativeTabs {
	public KMagicTab() {
		super("KMagic");
	}

	ItemStack iconItemStack;

	@Override @SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack() {
		if (iconItemStack == null) {
			iconItemStack = new ItemStack(getTabIconItem(), 1, 4);

			NBTTagCompound nbt = KMagicAPI.getMagicNBT(iconItemStack);
			nbt.setBoolean("invisibleMpBar", true);

			KMagicAPI.setMagicNBT(iconItemStack, nbt);
		}

		return iconItemStack;
	}

	@Override public Item getTabIconItem() {
		return KMItems.itemMagicStone;
	}

	@Override @SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return "KMagic";
	}


}
