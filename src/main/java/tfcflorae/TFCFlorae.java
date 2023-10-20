package tfcflorae;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;
import tfcflorae.client.ClientEvents;
import tfcflorae.client.GuiHandler;
import tfcflorae.objects.LootTablesTFCF;
import tfcflorae.objects.entity.EntitiesTFCF;
import tfcflorae.proxy.CommonProxy;
import tfcflorae.util.CapabilityHeatHandler;
import tfcflorae.util.HelpersTFCF;
import tfcflorae.util.OreDictionaryHelper;
import tfcflorae.util.fuel.FuelsTFCF;

@SuppressWarnings({"WeakerAccess", "unused"})
@Mod(modid = TFCFlorae.MODID, name = TFCFlorae.NAME, version = TFCFlorae.VERSION, dependencies = TFCFlorae.DEPENDENCIES)
public class TFCFlorae {
    public static final String MODID = "tfcflorae";
    public static final String NAME = "TFC Florae";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDENCIES = "required-after:tfc@[1.7,);"
            + "after:firmalife;"
            + "after:tfcelementia;"
            + "after:tfc_ph_compat;";

    @Mod.Instance
    public static TFCFlorae instance;
    public static Logger logger;

    public static boolean FirmaLifeAdded = false;
    public static boolean TFCElementiaAdded = false;
    public static boolean TFCPHCompatAdded = false;

    @SidedProxy(serverSide = "tfcflorae.proxy.CommonProxy", clientSide = "tfcflorae.proxy.ClientProxy")
    public static CommonProxy proxy;
    private SimpleNetworkWrapper network;

    public static Logger getLog() {
        return logger;
    }

    public static CommonProxy getProxy() {
        return proxy;
    }

    public static TFCFlorae getInstance() {
        return instance;
    }

    public static SimpleNetworkWrapper getNetwork() {
        return instance.network;
    }


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //ClassAdder.addClasses(event.getModConfigurationDirectory());
        logger = event.getModLog();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        int id = 0;

        for (ModContainer Mod : Loader.instance().getActiveModList()) {
            if (Mod.getModId().equals("firmalife"))
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

        EntitiesTFCF.preInit();
        proxy.preInit(event);

        if (event.getSide().isClient()) {
            ClientEvents.preInit();
        }

        HelpersTFCF.insertWhitelistFluids();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        OreDictionaryHelper.init();
        LootTablesTFCF.init();
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
