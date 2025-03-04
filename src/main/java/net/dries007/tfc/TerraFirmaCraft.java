package net.dries007.tfc;

import su.terrafirmagreg.modules.core.capabilities.food.CapabilityProviderFood;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraftforge.common.MinecraftForge;
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

import net.dries007.tfc.client.ClientEvents;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.client.TFCKeybindings;
import net.dries007.tfc.client.gui.overlay.PlayerDataOverlay;
import net.dries007.tfc.command.CommandDebugInfo;
import net.dries007.tfc.command.CommandFindVeins;
import net.dries007.tfc.command.CommandGenTree;
import net.dries007.tfc.command.CommandHeat;
import net.dries007.tfc.command.CommandPlayerTFC;
import net.dries007.tfc.command.CommandStripWorld;
import net.dries007.tfc.command.CommandTimeTFC;
import net.dries007.tfc.command.CommandWorkChunk;
import net.dries007.tfc.network.PacketCalendarUpdate;
import net.dries007.tfc.network.PacketCapabilityContainerUpdate;
import net.dries007.tfc.network.PacketChunkData;
import net.dries007.tfc.network.PacketCycleItemMode;
import net.dries007.tfc.network.PacketFoodStatsReplace;
import net.dries007.tfc.network.PacketFoodStatsUpdate;
import net.dries007.tfc.network.PacketGuiButton;
import net.dries007.tfc.network.PacketOpenCraftingGui;
import net.dries007.tfc.network.PacketPlaceBlockSpecial;
import net.dries007.tfc.network.PacketProspectResult;
import net.dries007.tfc.network.PacketSimpleMessage;
import net.dries007.tfc.network.PacketSpawnTFCParticle;
import net.dries007.tfc.network.PacketStackFood;
import net.dries007.tfc.network.PacketSwitchPlayerInventoryTab;
import net.dries007.tfc.objects.entity.EntitiesTFC;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.proxy.IProxy;
import net.dries007.tfc.util.fuel.FuelManager;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.chunkdata.CapabilityChunkData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.TFC;

@SuppressWarnings("FieldMayBeFinal")
@Mod.EventBusSubscriber(modid = TFC)
@Mod(modid = TFC, version = "2.0.0", name = TerraFirmaCraft.MOD_NAME, useMetadata = true, guiFactory = Constants.GUI_FACTORY, dependencies = "required:forge@[14.23.5.2816,);after:jei@[4.14.2,);after:crafttweaker@[4.1.11,);after:waila@(1.8.25,)")
public final class TerraFirmaCraft {

  public static final String MOD_NAME = "TerraFirmaCraft";

  @Mod.Instance
  private static TerraFirmaCraft INSTANCE;

  @SidedProxy(modId = TFC, clientSide = "net.dries007.tfc.proxy.ClientProxy", serverSide = "net.dries007.tfc.proxy.ServerProxy")
  private static IProxy PROXY = null;
  private final Logger log = LogManager.getLogger(TFC);
  private WorldTypeTFC worldTypeTFC;
  private SimpleNetworkWrapper network;

  public TerraFirmaCraft() {
    INSTANCE = this;

    FluidRegistry.enableUniversalBucket();
  }

  public static Logger getLog() {
    return INSTANCE.log;
  }

  public static IProxy getProxy() {
    return PROXY;
  }

  public static WorldTypeTFC getWorldType() {
    return INSTANCE.worldTypeTFC;
  }

  public static SimpleNetworkWrapper getNetwork() {
    return INSTANCE.network;
  }

  public static TerraFirmaCraft getInstance() {
    return INSTANCE;
  }

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    log.debug("If you can see this, debug logging is working :)");

    // No need to sync config here, forge magic

    NetworkRegistry.INSTANCE.registerGuiHandler(this, new TFCGuiHandler());
    network = NetworkRegistry.INSTANCE.newSimpleChannel(TFC);
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
    network.registerMessage(new PacketSpawnTFCParticle.Handler(), PacketSpawnTFCParticle.class, ++id, Side.CLIENT);
    network.registerMessage(new PacketSimpleMessage.Handler(), PacketSimpleMessage.class, ++id, Side.CLIENT);
    network.registerMessage(new PacketProspectResult.Handler(), PacketProspectResult.class, ++id, Side.CLIENT);

    EntitiesTFC.preInit();

    CapabilityChunkData.preInit();

    if (event.getSide().isClient()) {
      ClientEvents.preInit();
    }
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent event) {

    ItemsTFC.init();

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
          TerraFirmaCraft.getLog().info("Setting default level-type to `tfc_classic`");
          settings.getStringProperty("level-type", "tfc_classic");
        }
      }
    }

    worldTypeTFC = new WorldTypeTFC();

    FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "net.dries007.tfc.compat.waila.TOPPlugin");
  }

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    FuelManager.postInit();
  }

  @Mod.EventHandler
  public void onLoadComplete(FMLLoadCompleteEvent event) {
    // This is the latest point that we can possibly stop creating non-decaying stacks on both server + client
    // It should be safe to use as we're only using it internally
    CapabilityProviderFood.setNonDecaying(false);
  }

  @Mod.EventHandler
  public void onServerStarting(FMLServerStartingEvent event) {

    event.registerServerCommand(new CommandStripWorld());
    event.registerServerCommand(new CommandHeat());
    event.registerServerCommand(new CommandPlayerTFC());
    event.registerServerCommand(new CommandTimeTFC());
    event.registerServerCommand(new CommandFindVeins());
    event.registerServerCommand(new CommandDebugInfo());
    event.registerServerCommand(new CommandWorkChunk());
    event.registerServerCommand(new CommandGenTree());

    // Initialize calendar for the current server
    Calendar.INSTANCE.init(event.getServer());
  }
}
