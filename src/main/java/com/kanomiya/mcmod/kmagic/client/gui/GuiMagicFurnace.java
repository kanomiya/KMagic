package com.kanomiya.mcmod.kmagic.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.kanomiya.mcmod.kmagic.inventory.ContainerMagicFurnace;
import com.kanomiya.mcmod.kmagic.tileentity.TileEntityMagicFurnace;

/**
 * @author Kanomiya
 *
 */
@SideOnly(Side.CLIENT)
public class GuiMagicFurnace extends GuiContainer
{
	private static final ResourceLocation furnaceGuiTextures = new ResourceLocation("kmagic:textures/gui/container/magicFurnace.png");
	private final InventoryPlayer playerInventory;
	private TileEntityMagicFurnace tileFurnace;

	public GuiMagicFurnace(InventoryPlayer playerInv, TileEntityMagicFurnace furnace) {
		super(new ContainerMagicFurnace(playerInv, furnace));
		playerInventory = playerInv;
		tileFurnace = furnace;

		xSize = 176;
		ySize = 179;

	}

	@Override protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = tileFurnace.getDisplayName().getUnformattedText();
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		fontRendererObj.drawString(playerInventory.getDisplayName().getUnformattedText(), 8, ySize - 96 + 3, 4210752);
	}

	@Override protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(furnaceGuiTextures);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		int i1;

		if (TileEntityMagicFurnace.isBurning(tileFurnace)) {
			i1 = func_175382_i(13);
			this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1 +5, 176, 12 - i1, 14, i1 + 1);
		}

		i1 = func_175381_h(24);
		this.drawTexturedModalRect(k + 79, l + 34 +5, 176, 14, i1 + 1, 16);


		if (0 < tileFurnace.getField(5)) {
			i1 = tileFurnace.getField(4) * 50 / tileFurnace.getField(5);

			this.drawTexturedModalRect(k + 18, l + 32, 200, -50 +i1, 18, 50);

			if (0 < i1) this.drawTexturedModalRect(k + 36, l + 32 +26, 206, 67, 34, 24);
			if (4 < i1) this.drawTexturedModalRect(k + 36, l + 32 +22, 176, 67, 30, 24);
			if (8 < i1) this.drawTexturedModalRect(k + 36, l + 32 +26, 198, 50, 26, 16);
			if (12 < i1) this.drawTexturedModalRect(k + 36, l + 32 +22, 176, 50, 22, 16);

		}

	}

	private int func_175381_h(int p_175381_1_)
	{
		int j = tileFurnace.getField(2);
		int k = tileFurnace.getField(3);
		return k != 0 && j != 0 ? j * p_175381_1_ / k : 0;
	}

	private int func_175382_i(int p_175382_1_)
	{
		int j = tileFurnace.getField(1);

		if (j == 0)
		{
			j = 200;
		}

		return tileFurnace.getField(0) * p_175382_1_ / j;
	}
}
