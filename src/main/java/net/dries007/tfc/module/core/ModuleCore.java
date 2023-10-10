package net.dries007.tfc.module.core;

import com.ferreusveritas.dynamictrees.event.BiomeSuitabilityEvent;
import net.dries007.tfc.api.capability.damage.CapabilityDamageResistance;
import net.dries007.tfc.api.capability.egg.CapabilityEgg;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.FoodHandler;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.module.core.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.metal.CapabilityMetalItem;
import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.module.core.api.capability.size.CapabilityItemSize;
import net.dries007.tfc.api.capability.worldtracker.CapabilityWorldTracker;
import net.dries007.tfc.client.TFCKeybindings;
import net.dries007.tfc.client.gui.overlay.PlayerDataOverlay;
import net.dries007.tfc.client.util.FluidSpriteCache;
import net.dries007.tfc.client.util.TFCGuiHandler;
import net.dries007.tfc.commands.*;
import net.dries007.tfc.compat.gregtech.items.tools.TFGToolItems;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.module.core.api.util.CreativeTabBase;
import net.dries007.tfc.module.core.init.*;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.json.JsonConfigRegistry;
import net.dries007.tfc.world.classic.chunkdata.CapabilityChunkData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.server.FMLServerHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.tfc.TerraFirmaCraft;
import su.terrafirmagreg.util.module.ModuleBase;
import su.terrafirmagreg.util.network.IPacketRegistry;
import su.terrafirmagreg.util.network.IPacketService;
import su.terrafirmagreg.util.network.tile.ITileDataService;
import su.terrafirmagreg.util.registry.Registry;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

public class ModuleCore extends ModuleBase {

    public static final CreativeTabs MISC_TAB = new CreativeTabBase("misc", "tfc:wand");
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + ModuleCore.class.getSimpleName());

    public static IPacketService PACKET_SERVICE;
    public static ITileDataService TILE_DATA_SERVICE;

    public ModuleCore() {
        super(0, MOD_ID);

        this.setRegistry(new Registry(MOD_ID, MISC_TAB));
        this.enableAutoRegistry();

        PACKET_SERVICE = this.enableNetwork();
        TILE_DATA_SERVICE = this.enableNetworkTileDataService(PACKET_SERVICE);

        MinecraftForge.EVENT_BUS.register(this);
    }


    // --------------------------------------------------------------------------
    // - Registration
    // --------------------------------------------------------------------------


    @SubscribeEvent
    public static void onNewRegistryEvent(RegistryEvent.NewRegistry event) {
        RegistryCore.createRegistries(event);
    }

    @SubscribeEvent
    public static void biomeHandler(BiomeSuitabilityEvent event) {
        event.setSuitability(1.0f); //doesn't change value, sets isHandled
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void textureStitched(TextureStitchEvent.Post event) {
        FluidSpriteCache.clear();
    }

    @Override
    public void onRegister(Registry registry) {
        BlocksCore.onRegister(registry);
        ItemsCore.onRegister(registry);
        EntitiesCore.onRegister(registry);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientRegister(Registry registry) {
        BlocksCore.onClientRegister(registry);
        ItemsCore.onClientRegister(registry);
    }

    // --------------------------------------------------------------------------
    // - FML Lifecycle
    // --------------------------------------------------------------------------

    @Override
    public void onNetworkRegister(IPacketRegistry registry) {
        PacketCore.register(registry);

    }

    @Override
    public void onPreInitializationEvent(FMLPreInitializationEvent event) {
        super.onPreInitializationEvent(event);

        //DrinkableHandler.preInit();

        TFGToolItems.preInit();

        JsonConfigRegistry.INSTANCE.preInit(event.getModConfigurationDirectory());

        // No need to sync config here, forge magic
        NetworkRegistry.INSTANCE.registerGuiHandler(TerraFirmaCraft.getInstance(), new TFCGuiHandler());

        CapabilityChunkData.preInit();
        CapabilityItemSize.preInit();
        CapabilityItemHeat.preInit();
        CapabilityForgeable.preInit();
        CapabilityFood.preInit();
        CapabilityEgg.preInit();
        CapabilityPlayerData.preInit();
        CapabilityDamageResistance.preInit();
        CapabilityMetalItem.preInit();
        CapabilityWorldTracker.preInit();


    }

    // --------------------------------------------------------------------------
    // - FML Lifecycle: Client
    // --------------------------------------------------------------------------

    @Override
    public void onInitializationEvent(FMLInitializationEvent event) {
        super.onInitializationEvent(event);

        //LootTablesTFC.init();
        CapabilityFood.init();

        setTFCWorldTypeAsDefault(event);

        CapabilityItemSize.init();
        CapabilityItemHeat.init();
        CapabilityMetalItem.init();
        CapabilityForgeable.init();

        //RecipeHandler.init();
    }

    @Override
    public void onPostInitializationEvent(FMLPostInitializationEvent event) {
        super.onPostInitializationEvent(event);

        ItemsCore.onPostInitialization();

        //JsonConfigRegistry.INSTANCE.postInit();
    }

    @Override
    public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {
        super.onLoadCompleteEvent(event);
        // Я сука несколько дней разбирался как эта хуйня работает, теперь рассказываю.
        // У нас есть статическая переменная в капабилити еды, отвечающая за то, будет ли еда портиться.
        // При создании еды и присваивании ей капабилити, создается еда, которая не умеет портиться,
        // но чтобы в игре она пропадала, в самом конце загрузки мы включаем порчу еды и при получении еды, мы О ЧУДО,
        // получаем еду которая портится, ведь переменная стоит на false.
        // Кстати говоря, почему это происходит именно на LoadComplete?
        // Потому что JEI загрузка плагинов срабатывает на PostInit,
        // поэтому, чтобы в JEI еда не портилась, мы делаем это на самом последнем моменте загрузки.
        FoodHandler.setNonDecaying(false);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientPreInitializationEvent(FMLPreInitializationEvent event) {
        super.onClientPreInitializationEvent(event);

    }


    // --------------------------------------------------------------------------
    // - Server
    // --------------------------------------------------------------------------

    @SideOnly(Side.CLIENT)
    public void onClientInitializationEvent(FMLInitializationEvent event) {
        super.onClientInitializationEvent(event);

        TFCKeybindings.onInit();
        // Enable overlay to render health, thirst and hunger bars, TFC style.
        // Also renders animal familiarity
        MinecraftForge.EVENT_BUS.register(PlayerDataOverlay.getInstance());
    }

    @SideOnly(Side.CLIENT)
    public void onClientPostInitializationEvent(FMLPostInitializationEvent event) {
        super.onClientPostInitializationEvent(event);
    }

    @Override
    public void onServerStartingEvent(FMLServerStartingEvent event) {
        super.onServerStartingEvent(event);

        event.registerServerCommand(new CommandGetHeat());
        event.registerServerCommand(new CommandStripWorld());
        event.registerServerCommand(new CommandHeat());
        event.registerServerCommand(new CommandPlayerTFC());
        event.registerServerCommand(new CommandTimeTFC());
        event.registerServerCommand(new CommandDebugInfo());
        event.registerServerCommand(new CommandWorkChunk());

        // Initialize calendar for the current server
        CalendarTFC.INSTANCE.init(event.getServer());
    }

    private void setTFCWorldTypeAsDefault(FMLInitializationEvent event) {
        if (event.getSide().isServer()) {
            MinecraftServer server = FMLServerHandler.instance().getServer();
            if (server instanceof DedicatedServer) {
                PropertyManager settings = ((DedicatedServer) server).settings;
                if (ConfigTFC.General.OVERRIDES.forceTFCWorldType) {
                    // This is called before vanilla defaults it, meaning we intercept it's default with ours
                    // However, we can't actually set this due to fears of overriding the existing world
                    ModuleCore.LOGGER.info("Setting default level-type to `tfc_classic`");
                    settings.getStringProperty("level-type", "tfc_classic");
                }
            }
        }
    }

}
