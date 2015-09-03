package com.kanomiya.mcmod.kmagic.client.gui;

import com.kanomiya.mcmod.kmagic.KMagicAPI;
import com.kanomiya.mcmod.kmagic.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.magic.status.wrapper.StackWrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author Kanomiya
 *
 */
@SideOnly(Side.CLIENT)
public class GuiAbilityHolder extends GuiScreen {

	MagicStatus status;
	GuiMagicSlotList guiList;

	public GuiAbilityHolder(MagicStatus parStatus)
	{
		mc = Minecraft.getMinecraft();
		status = parStatus;

		guiList = new GuiMagicSlotList(status.getAbilityHolder(), this, mc, 300, 300, 20, 20, 20);
		buttonList.add(new GuiButton(0, 10, 10, "↑"));
		buttonList.add(new GuiButton(1, 10, 30, "↓"));

	}

	@Override public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		super.drawDefaultBackground();

		if (0 < guiList.getSize()) {
			guiList.drawScreen(mouseX, mouseY, partialTicks);
		}

		fontRendererObj.drawString("MagicSlot", 18, 12, 0xffffff);


		/*
		boolean flag = mc.thePlayer.capabilities.isCreativeMode;
		int k = container.getLapisAmount();

		for (int l = 0; l < 3; ++l)
		{
			int i1 = container.enchantLevels[l];
			int j1 = container.field_178151_h[l];
			int k1 = l + 1;

			if (isPointInRegion(60, 14 + 19 * l, 108, 17, mouseX, mouseY) && i1 > 0 && j1 >= 0)
			{
				ArrayList arraylist = Lists.newArrayList();
				String s;


				this.drawHoveringText(arraylist, mouseX, mouseY);
				break;
			}
		}
		*/
	}


	@Override public void onGuiClosed() {
		if (status.getMagicObject() instanceof StackWrapper) {
			KMagicAPI.setMagicStatus(((StackWrapper) status.getMagicObject()).stack, status);
		}

	}

}
