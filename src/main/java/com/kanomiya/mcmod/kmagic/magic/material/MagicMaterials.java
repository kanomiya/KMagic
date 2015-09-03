package com.kanomiya.mcmod.kmagic.magic.material;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.kanomiya.mcmod.kmagic.magic.status.base.IMagicItem;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants.NBT;

/**
 * @author Kanomiya
 *
 */
public class MagicMaterials implements IMagicMaterial {
	protected List<MagicMaterial> materials = Lists.newArrayList();

	public MagicMaterials() {  }

	public MagicMaterials(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}

	public void add(MagicMaterial material) {
		materials.add(material);
		Collections.sort(materials);
	}

	public void add(MagicMaterial material, int amount) {
		for (int i=0; i<amount; i++) add(material);
	}

	public void clear() {
		materials.clear();
	}


	@Override public int getMpCapacity() {
		int capacity = 0;
		for (int i=0; i<materials.size(); i++) capacity += materials.get(i).capacity;

		return capacity;
	}


	@Override public String getName() {
		String names = "";
		for (int i=0; i<materials.size(); i++) {
			if (0 < i) names += "_";
			names += materials.get(i).getName();
		}

		return names;
	}

	public String[] getNames() {
		String[] names = new String[materials.size()];
		for (int i=0; i<materials.size(); i++) {
			names[i] = materials.get(i).getName();
		}

		return names;
	}

	public String[] getUnlocalizedNames() {
		String[] names = new String[materials.size()];
		for (int i=0; i<materials.size(); i++) {
			names[i] = materials.get(i).getUnlocalizedName();
		}

		return names;
	}


	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList list = new NBTTagList();

		for (int i=0; i<materials.size(); i++) {
			list.appendTag(new NBTTagString(materials.get(i).getName()));
		}

		nbt.setTag("materials", list);
	}

	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList list = nbt.getTagList("materials", NBT.TAG_STRING);

		for (int i=0; i<list.tagCount(); i++) {
			MagicMaterial material = MagicMaterialRegistry.getMagicMaterial(list.getStringTagAt(i));

			if (material != null) add(material);
		}

	}




	public int compareTo(MagicMaterials materials) {
		return getName().compareTo(materials.getName());
	}

	@Override public int compareTo(Object paramT) {
		if (paramT instanceof MagicMaterials) return compareTo((MagicMaterials) paramT);
		return 0;
	}








	public static void getAllMaterialNames(MagicMaterials materials, List<String> list) {

		String[] names = materials.getNames();

		for (int i=0; i<names.length; i++) {
			String name = "";

			for (int j=i; j<names.length; j++) {
				if (i < j) name += "_";
				name += names[j];
			}

			// if (! list.contains(names[i])) list.add(names[i]);
			if (! list.contains(name)) list.add(name);
		}

	}


	public static String[] getAllMaterialNames(List<MagicMaterial> materialList) {
		MagicMaterials materials = new MagicMaterials();
		for (MagicMaterial mm: materialList) materials.add(mm);

		List<String> list = Lists.newArrayList();
		getAllMaterialNames(materials, list);

		return list.toArray(new String[list.size()]);
	}

	public static String[] getAllMaterialNames(MagicMaterials materials) {
		List<String> list = Lists.newArrayList();

		getAllMaterialNames(materials, list);

		return list.toArray(new String[list.size()]);
	}

	public static String[] getAllMaterialNames(IMagicItem item) {
		List<String> list = Lists.newArrayList();

		for (int meta=0; meta<=item.getMaxMetadata(); meta++) {
			MagicMaterials materials = new MagicMaterials();
			item.getMagicMaterials(meta, materials);

			getAllMaterialNames(materials, list);
		}

		return list.toArray(new String[list.size()]);
	}



}
