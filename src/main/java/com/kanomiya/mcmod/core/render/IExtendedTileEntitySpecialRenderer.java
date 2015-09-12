package com.kanomiya.mcmod.core.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

/**
 * @author Kanomiya
 *
 */
public abstract class IExtendedTileEntitySpecialRenderer extends TileEntitySpecialRenderer {

	public float metaToRotate(int meta) {
		switch (meta) {
		case 0: return 2;
		case 1: return 1;
		case 2: return 0;
		case 3: return 3;
		default: return 0;

		}
	}
}
