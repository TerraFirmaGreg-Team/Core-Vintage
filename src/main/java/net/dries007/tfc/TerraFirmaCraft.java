package net.dries007.tfc;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.FoodHandler;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.metal.CapabilityMetalItem;
import net.dries007.tfc.api.capability.size.CapabilityItemSize;
import net.dries007.tfc.client.TFCKeybindings;
import net.dries007.tfc.client.gui.overlay.PlayerDataOverlay;
import net.dries007.tfc.command.*;
import net.dries007.tfc.common.CommonProxy;
import net.dries007.tfc.objects.LootTablesTFC;
import net.dries007.tfc.objects.advancements.TFCTriggers;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.types.DefaultRecipes;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.server.FMLServerHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dries007.tfc.TerraFirmaCraft.*;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
@Mod.EventBusSubscriber
@Mod(
        modid = MOD_ID,
        name = MOD_NAME,
        useMetadata = true,
        guiFactory = GUI_FACTORY,
        dependencies = DEPENDENCIES)
public final class TerraFirmaCraft {
    public static final String MOD_ID = "tfc";
    public static final String MOD_NAME = "TerraFirmaCraft";
    public static final String GUI_FACTORY = "net.dries007.tfc.client.TFCModGuiFactory";
    public static final String DEPENDENCIES = "required:forge@[14.23.5.2847,);after:jei@[4.14.2,);after:gregtech;after:top@(1.8.25,)";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final WorldTypeTFC WORLD_TYPE_TFC = new WorldTypeTFC();
    public static SimpleNetworkWrapper NETWORK = new SimpleNetworkWrapper(MOD_ID);

    @Mod.Instance
    private static TerraFirmaCraft INSTANCE = null;
    @SidedProxy(
            modId = MOD_ID,
            clientSide = "net.dries007.tfc.client.ClientProxy",
            serverSide = "net.dries007.tfc.common.CommonProxy")
    private static CommonProxy PROXY;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    public static CommonProxy getProxy() {
        return PROXY;
    }

    public static TerraFirmaCraft getInstance() {
        return INSTANCE;
    }

    private static int networkIdCounter = 0;

    /**
     * Используй это только на preInit фазе.
     * */
    public static <T extends IMessage> void registerNetwork(IMessageHandler<T, IMessage> handler, Class<T> packetLatexUpdateClass, Side side) {
        NETWORK.registerMessage(handler, packetLatexUpdateClass, networkIdCounter++, side);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Started PreInitialization Phase!");
        PROXY.onPreInit(event);
        LOGGER.info("Finished PreInitialization Phase!");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ItemsTFC.init();
        LootTablesTFC.init();
        CapabilityFood.init();
        TFCTriggers.init();

        PROXY.onInit(event);

        if (event.getSide().isClient()) {
            TFCKeybindings.init();
            // Enable overlay to render health, thirst and hunger bars, TFC style.
            // Also renders animal familiarity
            MinecraftForge.EVENT_BUS.register(PlayerDataOverlay.getInstance());
        } else {
            MinecraftServer server = FMLServerHandler.instance().getServer();
            if (server instanceof DedicatedServer) {
                PropertyManager settings = ((DedicatedServer) server).settings;
                if (ConfigTFC.General.OVERRIDES.forceTFCWorldType) {
                    // This is called before vanilla defaults it, meaning we intercept it's default with ours
                    // However, we can't actually set this due to fears of overriding the existing world
                    TerraFirmaCraft.LOGGER.info("Setting default level-type to `tfc_classic`");
                    settings.getStringProperty("level-type", "tfc_classic");
                }
            }
        }



        CapabilityItemSize.init();
        CapabilityItemHeat.init();
        CapabilityMetalItem.init();
        CapabilityForgeable.init();

        DefaultRecipes.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PROXY.onPostInit(event);
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        // This is the latest point that we can possibly stop creating non-decaying stacks on both server + client
        // It should be safe to use as we're only using it internally
        FoodHandler.setNonDecaying(false);
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandGetHeat());
        event.registerServerCommand(new CommandStripWorld());
        event.registerServerCommand(new CommandHeat());
        event.registerServerCommand(new CommandPlayerTFC());
        event.registerServerCommand(new CommandTimeTFC());
        event.registerServerCommand(new CommandDebugInfo());
        event.registerServerCommand(new CommandWorkChunk());
        event.registerServerCommand(new CommandGenTree());

        // Initialize calendar for the current server
        CalendarTFC.INSTANCE.init(event.getServer());
    }
}
