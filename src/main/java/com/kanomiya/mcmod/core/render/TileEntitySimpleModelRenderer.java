package com.kanomiya.mcmod.core.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntitySimpleModelRenderer extends IExtendedTileEntitySpecialRenderer {
	public final ISimpleModel model;
	public final ResourceLocation resource;
	public final boolean rotateFlag;

	public TileEntitySimpleModelRenderer(ISimpleModel parModel, ResourceLocation parResource, boolean parRotateFlag) {
		model = parModel;
		resource = parResource;
		rotateFlag = parRotateFlag;
	}

	@Override public void renderTileEntityAt(TileEntity te, double posX, double posZ, double posY, float rot_rot, int p_180535_9_) {
		GlStateManager.pushMatrix(); // 座標保存

		GlStateManager.translate((float)posX +0.5f, (float)posY -0.5f, (float)posZ +0.5f);
		GlStateManager.scale(0.0625f, 0.0625f, 0.0625f);
		if (rotateFlag) GlStateManager.rotate(metaToRotate(te.getBlockMetadata()) *90f, 0f, 1f, 0f);
		GlStateManager.enableCull(); // 背面描画の省略?


		bindTexture(resource);
		model.renderModel(1f);

		GlStateManager.translate(5f, 17f, -7f);

		GlStateManager.popMatrix(); // 座標展開
	}

}
