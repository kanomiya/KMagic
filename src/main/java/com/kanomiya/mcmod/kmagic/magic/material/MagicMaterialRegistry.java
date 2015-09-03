package com.kanomiya.mcmod.kmagic.magic.material;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author Kanomiya
 *
 */
public class MagicMaterialRegistry {
	private MagicMaterialRegistry() {  }


	private static Map<String, MagicMaterial> idToMagicMaterial = Maps.newHashMap();
	private static Map<MagicMaterial, String> materialToId = Maps.newHashMap();

	public static void registerMaterial(MagicMaterial material) {
		idToMagicMaterial.put(material.getName(), material);
		materialToId.put(material, material.getName());
	}

	public static MagicMaterial getMagicMaterial(String id) {
		return idToMagicMaterial.get(id);
	}

	public static String getMaterialId(MagicMaterial material) {
		return materialToId.get(material);
	}


	public static Collection values() { return idToMagicMaterial.values(); }








}
