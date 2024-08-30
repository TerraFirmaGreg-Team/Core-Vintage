package net.dries007.tfc;

import su.terrafirmagreg.data.lib.LoggingHelper;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.server.FMLServerHandler;


import net.dries007.tfc.api.capability.chunkdata.CapabilityChunkData;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.FoodHandler;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.worldtracker.CapabilityWorldTracker;
import net.dries007.tfc.client.ClientEvents;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.client.TFCKeybindings;
import net.dries007.tfc.network.PacketCalendarUpdate;
import net.dries007.tfc.network.PacketCapabilityContainerUpdate;
import net.dries007.tfc.network.PacketChunkData;
import net.dries007.tfc.network.PacketCycleItemMode;
import net.dries007.tfc.network.PacketFoodStatsReplace;
import net.dries007.tfc.network.PacketFoodStatsUpdate;
import net.dries007.tfc.network.PacketGuiButton;
import net.dries007.tfc.network.PacketOpenCraftingGui;
import net.dries007.tfc.network.PacketPlaceBlockSpecial;
import net.dries007.tfc.network.PacketPlayerDataUpdate;
import net.dries007.tfc.network.PacketProspectResult;
import net.dries007.tfc.network.PacketSimpleMessage;
import net.dries007.tfc.network.PacketSpawnTFCParticle;
import net.dries007.tfc.network.PacketStackFood;
import net.dries007.tfc.network.PacketSwitchPlayerInventoryTab;
import net.dries007.tfc.objects.entity.EntitiesTFC;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.proxy.IProxy;
import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.fuel.FuelManager;

import static net.dries007.tfc.TerraFirmaCraft.GUI_FACTORY;
import static su.terrafirmagreg.data.Constants.MODID_TFC;
import static su.terrafirmagreg.data.Constants.MOD_VERSION;

@SuppressWarnings("FieldMayBeFinal")
@Mod.EventBusSubscriber
@Mod(modid = MODID_TFC,
     name = TerraFirmaCraft.MOD_NAME,
     version = MOD_VERSION,
     useMetadata = true,
     guiFactory = GUI_FACTORY,
     dependencies = "required:forge@[14.23.5.2816,);after:jei@[4.14.2,);after:crafttweaker@[4.1.11,);after:waila@(1.8.25,)")
public final class TerraFirmaCraft {

    public static final String MOD_NAME = "TerraFirmaCraft";
    public static final String GUI_FACTORY = "net.dries007.tfc.client.TFCModGuiFactory";

    @Mod.Instance(MODID_TFC)
    private static TerraFirmaCraft INSTANCE = null;

    @SidedProxy(modId = MODID_TFC, clientSide = "net.dries007.tfc.proxy.ClientProxy", serverSide = "net.dries007.tfc.proxy.ServerProxy")
    private static IProxy PROXY = null;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    private final LoggingHelper log = LoggingHelper.of(MODID_TFC);
    private SimpleNetworkWrapper network;

    public static LoggingHelper getLog() {
        return INSTANCE.log;
    }

    public static IProxy getProxy() {
        return PROXY;
    }

    public static SimpleNetworkWrapper getNetwork() {
        return INSTANCE.network;
    }

    public static TerraFirmaCraft getInstance() {
        return INSTANCE;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // No need to sync config here, forge magic

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new TFCGuiHandler());
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID_TFC);
        int id = 0;
        // Received on server
        network.registerMessage(new PacketGuiButton.Handler(), PacketGuiButton.class, ++id, Side.SERVER);
        network.registerMessage(new PacketPlaceBlockSpecial.Handler(), PacketPlaceBlockSpecial.class, ++id, Side.SERVER);
        network.registerMessage(new PacketSwitchPlayerInventoryTab.Handler(), PacketSwitchPlayerInventoryTab.class, ++id, Side.SERVER);
        network.registerMessage(new PacketOpenCraftingGui.Handler(), PacketOpenCraftingGui.class, ++id, Side.SERVER);
        network.registerMessage(new PacketCycleItemMode.Handler(), PacketCycleItemMode.class, ++id, Side.SERVER);
        network.registerMessage(new PacketStackFood.Handler(), PacketStackFood.class, ++id, Side.SERVER);

        // Received on client
        network.registerMessage(new PacketChunkData.Handler(), PacketChunkData.class, ++id, Side.CLIENT);
        network.registerMessage(new PacketCapabilityContainerUpdate.Handler(), PacketCapabilityContainerUpdate.class, ++id, Side.CLIENT);
        network.registerMessage(new PacketCalendarUpdate.Handler(), PacketCalendarUpdate.class, ++id, Side.CLIENT);
        network.registerMessage(new PacketFoodStatsUpdate.Handler(), PacketFoodStatsUpdate.class, ++id, Side.CLIENT);
        network.registerMessage(new PacketFoodStatsReplace.Handler(), PacketFoodStatsReplace.class, ++id, Side.CLIENT);
        network.registerMessage(new PacketPlayerDataUpdate.Handler(), PacketPlayerDataUpdate.class, ++id, Side.CLIENT);
        network.registerMessage(new PacketSpawnTFCParticle.Handler(), PacketSpawnTFCParticle.class, ++id, Side.CLIENT);
        network.registerMessage(new PacketSimpleMessage.Handler(), PacketSimpleMessage.class, ++id, Side.CLIENT);
        network.registerMessage(new PacketProspectResult.Handler(), PacketProspectResult.class, ++id, Side.CLIENT);

        EntitiesTFC.preInit();
        //JsonConfigRegistry.INSTANCE.preInit(event.getModConfigurationDirectory());

        CapabilityChunkData.preInit();
        CapabilityForgeable.preInit();
        CapabilityFood.preInit();
        CapabilityWorldTracker.preInit();

        if (event.getSide().isClient()) {
            ClientEvents.preInit();
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        ItemsTFC.init();

        if (event.getSide().isClient()) {
            TFCKeybindings.init();
        } else {
            MinecraftServer server = FMLServerHandler.instance().getServer();
            if (server instanceof DedicatedServer dedicatedServer) {
                PropertyManager settings = dedicatedServer.settings;
                if (ConfigTFC.General.OVERRIDES.forceTFCWorldType) {
                    // This is called before vanilla defaults it, meaning we intercept it's default with ours
                    // However, we can't actually set this due to fears of overriding the existing world
                    TerraFirmaCraft.getLog().info("Setting default level-type to `tfg:classic`");
                    settings.getStringProperty("level-type", "tfg:classic");
                }
            }
        }

        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "net.dries007.tfc.compat.waila.TOPPlugin");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        FuelManager.postInit();
        //JsonConfigRegistry.INSTANCE.postInit();
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        // This is the latest point that we can possibly stop creating non-decaying stacks on both server + client
        // It should be safe to use as we're only using it internally
        FoodHandler.setNonDecaying(false);
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {

        // Initialize calendar for the current server
        Calendar.INSTANCE.init(event.getServer());
    }
}
