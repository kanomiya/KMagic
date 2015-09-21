package com.kanomiya.mcmod.kmagic.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.kanomiya.mcmod.kmagic.KMagic;
import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.item.ItemMagicBase;
import com.kanomiya.mcmod.kmagic.api.magic.material.MagicMaterial;
import com.kanomiya.mcmod.kmagic.api.magic.material.MagicMaterials;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.api.util.IHasModel;
import com.kanomiya.mcmod.kmagic.client.event.GuiHandler;

/**
 * @author Kanomiya
 *
 */
public class ItemMagicStone extends ItemMagicBase implements IHasModel {

	public ItemMagicStone() {
		setCreativeTab(KMagic.tab);
		setHasSubtypes(true);

	}

	@Override public void getMagicMaterials(int metadata, MagicMaterials materials) {

		switch (metadata) {
		case 0: materials.add(MagicMaterial.MAGICSTONE_GRAY); break;
		case 1: materials.add(MagicMaterial.MAGICSTONE_RED); break;
		case 2: materials.add(MagicMaterial.MAGICSTONE_YELLOW); break;
		case 3: materials.add(MagicMaterial.MAGICSTONE_GREEN); break;
		case 4: materials.add(MagicMaterial.MAGICSTONE_BLUE); break;
		}

	}


	@Override public int getMaxMetadata() { return 4; }

	@Override public String getModelNameSuffix(int metadata) {

		switch (metadata) {
		case 0: return "magicStoneGray";
		case 1: return "magicStoneRed";
		case 2: return "magicStoneYellow";
		case 3: return "magicStoneGreen";
		case 4: return "magicStoneBlue";
		}


		return "";
	}


	@Override public String getItemStackDisplayName(ItemStack stack) {
		String ext = "";
		MagicStatus status = KMagicAPI.getMagicStatus(stack);
		String[] names = status.getMagicMaterials().getUnlocalizedNames();

		for (int i=0; i<names.length; i++) {
			if (0 < i) ext += " | ";
			ext += StatCollector.translateToLocal(names[i]);
		}

		return super.getItemStackDisplayName(stack) + " [" + ext + "]";
	}

	@Override @SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
		for (int i=0; i<=4; i++) {
			subItems.add(new ItemStack(itemIn, 1, i));
			subItems.add(KMagicAPI.fullMp(new ItemStack(itemIn, 1, i)));
		}

	}


	@Override public ItemStack onItemRightClick(ItemStack stackIn, World worldIn, EntityPlayer playerIn) {

		playerIn.openGui(KMagic.instance, GuiHandler.GUI_ABILITYHOLDER, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);

		return stackIn;
	}








}
