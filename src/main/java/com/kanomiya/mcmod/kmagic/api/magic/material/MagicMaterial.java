package com.kanomiya.mcmod.kmagic.api.magic.material;

/**
 * @author Kanomiya
 *
 */
public class MagicMaterial implements IMagicMaterial {

	public static final MagicMaterial WOOD = new MagicMaterial("wood", 2);
	public static final MagicMaterial STONE = new MagicMaterial("stone", 6);
	public static final MagicMaterial IRON = new MagicMaterial("iron", 10);
	public static final MagicMaterial GOLD = new MagicMaterial("gold", 20);
	public static final MagicMaterial DIAMOND = new MagicMaterial("diamond", 15);

	public static final MagicMaterial MAGICSTONE_GRAY = new MagicMaterial("magicStoneGray", 8);
	public static final MagicMaterial MAGICSTONE_RED = new MagicMaterial("magicStoneRed", 20);
	public static final MagicMaterial MAGICSTONE_YELLOW = new MagicMaterial("magicStoneYellow", 70);
	public static final MagicMaterial MAGICSTONE_GREEN = new MagicMaterial("magicStoneGreen", 220);
	public static final MagicMaterial MAGICSTONE_BLUE = new MagicMaterial("magicStoneBlue", 650);

	public static final MagicMaterial PLAYER = new MagicMaterial("player", 300);




	protected String name;
	protected int capacity;


	public MagicMaterial(String parName, int parCapacity) {
		name = parName;
		capacity = parCapacity;

	}


	@Override public int getMpCapacity() { return capacity; }

	@Override public String getName() { return name; }
	public String getUnlocalizedName() { return "magicMaterial." + name; }

	@Override public String toString() { return getName(); }


	public int compareTo(MagicMaterial material) {
		return name.compareTo(material.name);
	}

	@Override public int compareTo(Object paramT) {
		if (paramT instanceof MagicMaterial) return compareTo((MagicMaterial) paramT);
		return 0;
	}







}
