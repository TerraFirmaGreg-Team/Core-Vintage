package net.dries007.tfcflorae;

import su.terrafirmagreg.api.data.enums.Mods;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import net.dries007.tfc.objects.blocks.entity.EntitiesTFCF;
import net.dries007.tfc.util.HelpersTFCF;
import net.dries007.tfcflorae.client.ClientEvents;
import net.dries007.tfcflorae.client.GuiHandler;
import net.dries007.tfcflorae.proxy.CommonProxy;
import org.apache.logging.log4j.Logger;

@SuppressWarnings({"WeakerAccess", "unused"})
@Mod(modid = Mods.Names.TFCF, name = TFCFlorae.NAME, version = TFCFlorae.VERSION, dependencies = TFCFlorae.DEPENDENCIES)
public class TFCFlorae {

  public static final String NAME = "TFC Florae";
  public static final String VERSION = "@VERSION@";
  public static final String DEPENDENCIES = "required-after:tfc;"
                                            + "after:firmalife;"
                                            + "after:tfc_ph_compat;";

  @Mod.Instance
  public static TFCFlorae instance;
  public static Logger logger;
  public static boolean signedBuild = true;

  public static boolean FirmaLifeAdded = false;
  public static boolean TFCPHCompatAdded = false;

  @SidedProxy(serverSide = "net.dries007.tfcflorae.proxy.CommonProxy", clientSide = "net.dries007.tfcflorae.proxy.ClientProxy")
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
      if (Mod.getModId().equals("firmalife")) {FirmaLifeAdded = true;}
      if (Mod.getModId().equals("tfc_ph_compat")) {TFCPHCompatAdded = true;}
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
    proxy.init(event);
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit(event);
  }

  @Mod.EventHandler
  public void onLoadComplete(FMLLoadCompleteEvent event) {
    // This is the latest point that we can possibly stop creating non-decaying stacks on both server + client
    // It should be safe to use as we're only using it internally
  }
}
