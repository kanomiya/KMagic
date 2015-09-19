package com.kanomiya.mcmod.kmagic.api.util;

/**
 * @author Kanomiya
 *
 */
public abstract class ModelUtils {


	public static String[] getModelNameSuffixes(IHasModel iHasModel) {
		String[] names = new String[iHasModel.getMaxMetadata() +1];

		for (int meta=0; meta<=iHasModel.getMaxMetadata(); meta++) {
			names[meta] = iHasModel.getModelNameSuffix(meta);
			if (names[meta] == null) names[meta] = "";
		}

		return names;
	}



}
