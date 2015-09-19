package com.kanomiya.mcmod.kmagic.api.magic.material;

/**
 * @author Kanomiya
 *
 */
public interface IHasMagicMaterial {

	public int getMaxMetadata();
	public void getMagicMaterials(int metadata, MagicMaterials materials);

}
