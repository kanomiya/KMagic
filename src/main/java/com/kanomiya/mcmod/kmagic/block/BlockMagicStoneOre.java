package com.kanomiya.mcmod.kmagic.block;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.kanomiya.mcmod.kmagic.KMItems;
import com.kanomiya.mcmod.kmagic.KMagic;
import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.magic.material.MagicMaterial;
import com.kanomiya.mcmod.kmagic.api.util.IHasModel;

/**
 * @author Kanomiya
 *
 */

public class BlockMagicStoneOre extends BlockOre implements IHasModel {

	// BlockPlanks
	public static final PropertyEnum TYPE = PropertyEnum.create("type", OreType.class);
	private static final Random rand = new Random();


	public BlockMagicStoneOre() {
		super();

		setCreativeTab(KMagic.tab);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, OreType.MAGICSTONE_GRAY));

		// BlockAnvil
		// BlockOre
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(soundTypePiston);
	}




	@Override public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return KMItems.itemMagicStone;
	}


	@Override public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> ret = super.getDrops(world, pos, state, fortune);

		for (ItemStack stack: ret) {
			float minRate = (fortune < 1) ? 0.667f : 1.0f;
			KMagicAPI.randomMp(stack, rand, minRate, 1.0f);
		}

		return ret;
	}

	@Override public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune) {
		IBlockState state = world.getBlockState(pos);
		Random random = (world instanceof World) ? ((World) world).rand : rand;

		OreType type = (OreType) state.getValue(TYPE);

		if (type != OreType.MAGICSTONE_GRAY) return MathHelper.getRandomIntegerInRange(random, 2, 5);

		return 0;
	}


	@Override public int getMaxMetadata() {
		return 4;
	}

	@Override public String getModelNameSuffix(int metadata) {

		switch (OreType.getByMeta(metadata)) {
		case MAGICSTONE_GRAY: return "magicStoneGray";
		case MAGICSTONE_RED: return "magicStoneRed";
		case MAGICSTONE_YELLOW: return "magicStoneYellow";
		case MAGICSTONE_GREEN: return "magicStoneGreen";
		case MAGICSTONE_BLUE: return "magicStoneBlue";
		}

		return "";
	}


	@Override public int getDamageValue(World worldIn, BlockPos pos) {
		return damageDropped(worldIn.getBlockState(pos));
	}


	@Override public int damageDropped(IBlockState blockState) {
		return getMetaFromState(blockState);
	}

	@Override @SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (OreType localType: (Collection<OreType>) TYPE.getAllowedValues()) {
			list.add(new ItemStack(item, 1, localType.meta));
		}
	}



	@Override @SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState blockState) {
		return getDefaultState().withProperty(TYPE, (blockState.getValue(TYPE)));
	}

	@Override public IBlockState getStateFromMeta(int meta) {
		return getStateFromType(OreType.getByMeta(meta));
	}

	@Override public int getMetaFromState(IBlockState blockState) {
		return ((OreType) blockState.getValue(TYPE)).meta;
	}

	@Override protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { TYPE });
	}

	public IBlockState getStateFromType(OreType oreType) {
		return getDefaultState().withProperty(TYPE, oreType);
	}







	public static enum OreType implements IStringSerializable {
		MAGICSTONE_GRAY(0, MagicMaterial.MAGICSTONE_GRAY),
		MAGICSTONE_RED(1, MagicMaterial.MAGICSTONE_RED),
		MAGICSTONE_YELLOW(2, MagicMaterial.MAGICSTONE_YELLOW),
		MAGICSTONE_GREEN(3, MagicMaterial.MAGICSTONE_GREEN),
		MAGICSTONE_BLUE(4, MagicMaterial.MAGICSTONE_BLUE),

		;

		public static final OreType[] TYPES;

		static {
			OreType[] values = values();
			TYPES = new OreType[values.length];

			for (int i=0; i<values.length; i++) {
				TYPES[values[i].meta] = values[i];
			}

		}

		public final int meta;
		public final MagicMaterial material;

		private OreType(int parMeta, MagicMaterial parMaterial) {
			meta = parMeta;
			material = parMaterial;
		}

		@Override public String getName() { return material.getName(); }

		public static OreType getByMeta(int meta) {
			if (TYPES.length < meta) return MAGICSTONE_GRAY;
			return TYPES[meta];
		}

	}






}
