package com.kanomiya.mcmod.kmagic.magic.material;

/**
 * @author Kanomiya
 *
 */
public interface IHasMagicMaterial {

	public int getMaxMetadata();
	public void getMagicMaterials(int metadata, MagicMaterials materials);

}
