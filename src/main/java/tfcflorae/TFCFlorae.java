package tfcflorae;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.Tags;
import tfcflorae.client.ClientEvents;
import tfcflorae.client.GuiHandler;
import tfcflorae.objects.blocks.entity.EntitiesTFCF;
import tfcflorae.proxy.CommonProxy;
import tfcflorae.util.CapabilityHeatHandler;
import tfcflorae.util.HelpersTFCF;
import tfcflorae.util.fuel.FuelsTFCF;

import static su.terrafirmagreg.api.lib.Constants.MODID_FL;
import static su.terrafirmagreg.api.lib.Constants.MODID_TFCF;

@SuppressWarnings({"WeakerAccess", "unused"})
@Mod(modid = MODID_TFCF, name = TFCFlorae.NAME, version = Tags.VERSION, dependencies = TFCFlorae.DEPENDENCIES)
public class TFCFlorae {
	public static final String NAME = "TFC Florae";
	public static final String DEPENDENCIES = "after:tfc;"
			+ "after:firmalife;"
			+ "after:tfcelementia;"
			+ "after:tfc_ph_compat;";

	@Mod.Instance
	public static TFCFlorae instance;
	public static Logger logger;
	public static boolean signedBuild = true;

	public static boolean FirmaLifeAdded = false;
	public static boolean TFCElementiaAdded = false;
	public static boolean TFCPHCompatAdded = false;

	@SidedProxy(serverSide = "tfcflorae.proxy.CommonProxy", clientSide = "tfcflorae.proxy.ClientProxy")
	public static CommonProxy proxy;

	public static Logger getLog() {
		return logger;
	}

	public static TFCFlorae getInstance() {
		return instance;
	}

	@EventHandler
	public void onFingerprintViolation(FMLFingerprintViolationEvent event) {
        /*if (!event.isDirectory())
        {
            signedBuild = false; // todo disabled for the time being
        }*/
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();

		for (ModContainer Mod : Loader.instance().getActiveModList()) {
			if (Mod.getModId().equals(MODID_FL))
				FirmaLifeAdded = true;
			if (Mod.getModId().equals("tfcelementia"))
				TFCElementiaAdded = true;
			if (Mod.getModId().equals("tfc_ph_compat"))
				TFCPHCompatAdded = true;
		}
        /*
        if (TFCFlorae.FirmaLifeAdded)
        {
            MinecraftForge.EVENT_BUS.register(JEIPluginFLCompat.class);
            MinecraftForge.EVENT_BUS.register(CastingCategoryFLCompat.class);
            MinecraftForge.EVENT_BUS.register(CastingRecipeWrapperKaoliniteFL.class);
            MinecraftForge.EVENT_BUS.register(UnmoldRecipeWrapperKaoliniteFL.class);
            MinecraftForge.EVENT_BUS.register(UnmoldMalletRecipe.class);
        }
        if (TFCFlorae.TFCElementiaAdded)
        {
            MinecraftForge.EVENT_BUS.register(ItemKaoliniteMoldTFCE.class);
            MinecraftForge.EVENT_BUS.register(ItemUnfiredKaoliniteMoldTFCE.class);
            MinecraftForge.EVENT_BUS.register(JEIPluginTFCECompat.class);
            MinecraftForge.EVENT_BUS.register(CastingRecipeKaoliniteTFCEWrapper.class);
            MinecraftForge.EVENT_BUS.register(UnmoldRecipeKaoliniteTFCEWrapper.class);
            MinecraftForge.EVENT_BUS.register(UnmoldRecipeKaolinite.class);
        }
        */

		proxy.preInit(event);
		EntitiesTFCF.preInit();

		if (event.getSide().isClient()) {
			ClientEvents.preInit();
		}

		HelpersTFCF.insertWhitelistFluids();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		CapabilityHeatHandler.init();
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		FuelsTFCF.postInit();
		proxy.postInit(event);
	}

	@Mod.EventHandler
	public void onLoadComplete(FMLLoadCompleteEvent event) {
		// This is the latest point that we can possibly stop creating non-decaying stacks on both server + client
		// It should be safe to use as we're only using it internally
	}
}
