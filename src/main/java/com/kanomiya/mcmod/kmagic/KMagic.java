package com.kanomiya.mcmod.kmagic;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.Logger;

import com.kanomiya.mcmod.kanomiyacore.KanomiyaCore;
import com.kanomiya.mcmod.kmagic.client.event.ClientTickEventHandler;
import com.kanomiya.mcmod.kmagic.client.event.GuiHandler;
import com.kanomiya.mcmod.kmagic.client.gui.GuiIngameHandler;
import com.kanomiya.mcmod.kmagic.command.CommandKMagic;
import com.kanomiya.mcmod.kmagic.magic.ability.MAFlight;
import com.kanomiya.mcmod.kmagic.magic.ability.MANaturalMpHealing;
import com.kanomiya.mcmod.kmagic.magic.ability.RegistryMagicAbility;
import com.kanomiya.mcmod.kmagic.magic.event.MagicSpellEventHandler;
import com.kanomiya.mcmod.kmagic.magic.event.MagicStatusEventHandler;
import com.kanomiya.mcmod.kmagic.magic.material.MagicMaterial;
import com.kanomiya.mcmod.kmagic.magic.status.RegistryMSRating;
import com.kanomiya.mcmod.kmagic.network.PacketHandler;
import com.kanomiya.mcmod.kmagic.stat.MagicStatList;
import com.kanomiya.mcmod.kmagic.world.WorldProviderKMagic;
import com.kanomiya.mcmod.kmagic.world.gen.biome.BiomeRichMEManager;
import com.kanomiya.mcmod.kmagic.world.gen.structure.PopulateChunkEventHandler;
import com.kanomiya.mcmod.kmagic.world.gen.structure.StructureMagicGate;

/**
 *
 * @author Kanomiya
 *
 */
@Mod(modid = KMagic.MODID, name = KMagic.MODID, version = KMagic.VERSION)
public class KMagic {
	public static final String MODID = "kmagic";
	public static final String VERSION = "alpha0.4.0";

	@Mod.Instance(MODID)
	public static KMagic instance;

	public static Logger logger;

	public static CreativeTabs tab = new CreativeTabs(MODID) {
		ItemStack iconItemStack;

		@Override @SideOnly(Side.CLIENT)
		public ItemStack getIconItemStack() {
			if (iconItemStack == null) {
				iconItemStack = new ItemStack(getTabIconItem(), 1, 4);

				NBTTagCompound nbt = KMagicAPI.getMagicNBT(iconItemStack);
				nbt.setBoolean("invisibleMpBar", true);

				KMagicAPI.setMagicNBT(iconItemStack, nbt);
			}

			return iconItemStack;
		}

		@Override public Item getTabIconItem() {
			return KMItems.itemMagicStone;
		}
	};

	public static KanomiyaCore core;

	@EventHandler public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		core = new KanomiyaCore(MODID, instance);

		KMKeys.init();
		KMConfig.preInit(event, core);

		boolean client = event.getSide().isClient();

		if (client) {
			MinecraftForge.EVENT_BUS.register(new GuiIngameHandler());
			FMLCommonHandler.instance().bus().register(ClientTickEventHandler.INSTANCE);
		}

		NetworkRegistry.INSTANCE.registerGuiHandler(MODID, new GuiHandler());


		MagicStatList.register();

		registerAbility();
		registerRatingStat();
		registerMagicMaterial();

		KMItems.preInit(event, core);
		KMBlocks.preInit(event, core);

		DimensionManager.registerProviderType(KMConfig.DIMID_KMAGIC, WorldProviderKMagic.class, false);
		DimensionManager.registerDimension(KMConfig.DIMID_KMAGIC, KMConfig.DIMID_KMAGIC);

	}

	@EventHandler public void init(FMLInitializationEvent event) {

		PacketHandler.init();
		MinecraftForge.EVENT_BUS.register(MagicStatusEventHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(MagicSpellEventHandler.INSTANCE);

		KMItems.init(event, core);
		KMBlocks.init(event, core);


		MinecraftForge.TERRAIN_GEN_BUS.register(new PopulateChunkEventHandler());

		MapGenStructureIO.registerStructure(StructureMagicGate.Start.class, "kmagic:magicGate");
		MapGenStructureIO.registerStructureComponent(StructureMagicGate.Component1.class, "kmagic:magicGate1");

		BiomeRichMEManager.init();
		MinecraftForge.EVENT_BUS.register(BiomeRichMEManager.INSTANCE);


	}


	@EventHandler public void postInit(FMLPostInitializationEvent event) {

		KMItems.postInit(event, core);
		KMBlocks.postInit(event, core);

		// if (Loader.isModLoaded("NotEnoughItems")) { codechicken.nei.api.API. }

	}


	@EventHandler public void serverStarting(FMLServerStartingEvent event){
		event.registerServerCommand(new CommandKMagic());

	}









	private void registerAbility() {
		RegistryMagicAbility.registerAbilityClass("naturalMpHealing", MANaturalMpHealing.class);
		RegistryMagicAbility.registerAbilityClass("flight", MAFlight.class);

	}

	private void registerRatingStat() {
		// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
		// Achievement
		// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
		RegistryMSRating.registerAchievement(AchievementList.snipeSkeleton, 10); // snipe Skeleton from 50m
		RegistryMSRating.registerAchievement(AchievementList.ghast, 10); // kill Ghast
		RegistryMSRating.registerAchievement(AchievementList.onARail, 10); // go on 1km rails

		RegistryMSRating.registerAchievement(AchievementList.diamonds, 20); // get Diamond
		RegistryMSRating.registerAchievement(AchievementList.overkill, 20); // overkill
		RegistryMSRating.registerAchievement(AchievementList.blazeRod, 20); // kill Blaze & get Blaze rod
		RegistryMSRating.registerAchievement(AchievementList.bookcase, 20); // make Bookshelf

		RegistryMSRating.registerAchievement(AchievementList.potion, 30); // make Potion
		RegistryMSRating.registerAchievement(AchievementList.enchantments, 30); // make EnchantmentTable
		RegistryMSRating.registerAchievement(AchievementList.overpowered, 30); // overpowered

		RegistryMSRating.registerAchievement(AchievementList.portal, 40); // go to Nether
		RegistryMSRating.registerAchievement(AchievementList.theEnd, 40); // go to The End
		RegistryMSRating.registerAchievement(AchievementList.spawnWither, 40); // spawnWither
		RegistryMSRating.registerAchievement(AchievementList.exploreAllBiomes, 40); // exploreAllBiomes
		RegistryMSRating.registerAchievement(AchievementList.theEnd2, 50); // killEnderDragon
		RegistryMSRating.registerAchievement(AchievementList.killWither, 50); // killWither
		RegistryMSRating.registerAchievement(AchievementList.fullBeacon, 50); // fullBeacon

		// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
		// Stats
		// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

		// MagicStatusRatingRegistry.registerStat(StatList.playerKillsStat, 0.01f);
		// MagicStatusRatingRegistry.registerStat(StatList.mobKillsStat, 0.01f);
		RegistryMSRating.registerStat(StatList.distanceWalkedStat, 0.000001f);
		RegistryMSRating.registerStat(StatList.distanceClimbedStat, 0.000001f);
		RegistryMSRating.registerStat(StatList.distanceSwumStat, 0.000001f);
		RegistryMSRating.registerStat(StatList.distanceFallenStat, 0.000001f);
		RegistryMSRating.registerStat(StatList.distanceFlownStat, 0.000001f);
		RegistryMSRating.registerStat(StatList.jumpStat, 0.000001f);
		RegistryMSRating.registerStat(MagicStatList.releasedMP, 0.001f);


	}


	private void registerMagicMaterial() {
		KMagicAPI.registerMagicMaterial(MagicMaterial.WOOD);
		KMagicAPI.registerMagicMaterial(MagicMaterial.STONE);
		KMagicAPI.registerMagicMaterial(MagicMaterial.IRON);
		KMagicAPI.registerMagicMaterial(MagicMaterial.GOLD);
		KMagicAPI.registerMagicMaterial(MagicMaterial.DIAMOND);
		KMagicAPI.registerMagicMaterial(MagicMaterial.MAGICSTONE_GRAY);
		KMagicAPI.registerMagicMaterial(MagicMaterial.MAGICSTONE_RED);
		KMagicAPI.registerMagicMaterial(MagicMaterial.MAGICSTONE_YELLOW);
		KMagicAPI.registerMagicMaterial(MagicMaterial.MAGICSTONE_GREEN);
		KMagicAPI.registerMagicMaterial(MagicMaterial.MAGICSTONE_BLUE);
		KMagicAPI.registerMagicMaterial(MagicMaterial.PLAYER);



	}




}
