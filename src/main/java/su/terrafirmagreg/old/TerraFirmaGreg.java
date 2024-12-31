package su.terrafirmagreg.old;

import su.terrafirmagreg.old.api.helper.LoggingHelper;
import su.terrafirmagreg.old.core.Recipes;
import su.terrafirmagreg.old.core.modules.ambiental.TFCAmbientalEventHandler;
import su.terrafirmagreg.old.core.modules.ambiental.TFCAmbientalGuiRenderer;
import su.terrafirmagreg.old.core.modules.ambiental.capability.TemperatureCapability;
import su.terrafirmagreg.old.core.modules.ambiental.capability.TemperaturePacket;
import su.terrafirmagreg.old.core.modules.gregtech.items.TFGModMetaItem;
import su.terrafirmagreg.old.core.modules.gregtech.items.tools.TFGToolItems;
import su.terrafirmagreg.old.core.modules.gregtech.machines.TFGTileEntities;
import su.terrafirmagreg.old.proxy.IProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.DumbStorage;

import lombok.Getter;

import static su.terrafirmagreg.Tags.CLIENT_PROXY;
import static su.terrafirmagreg.Tags.DEPENDENCIES;
import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;
import static su.terrafirmagreg.Tags.MOD_VERSION;
import static su.terrafirmagreg.Tags.SERVER_PROXY;

@SuppressWarnings("unused")
@Mod(modid = MOD_ID, name = MOD_NAME, version = MOD_VERSION, dependencies = DEPENDENCIES)
public class TerraFirmaGreg {

  public static final LoggingHelper LOGGER = LoggingHelper.of();


  @Getter
  @Mod.Instance(MOD_ID)
  private static TerraFirmaGreg instance;

  @Getter
  @SidedProxy(modId = MOD_ID, clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
  public static IProxy proxy;


  @EventHandler
  public void onConstruct(FMLConstructionEvent event) {

  }

  @EventHandler
  public void onPreInit(FMLPreInitializationEvent event) {
    TFGToolItems.init();
    TFGModMetaItem.init();
    TFGTileEntities.init();

    // Common Events
    MinecraftForge.EVENT_BUS.register(new TFCAmbientalEventHandler());

    // Client Events
    if (event.getSide() == Side.CLIENT) {
      MinecraftForge.EVENT_BUS.register(new TFCAmbientalGuiRenderer());
    }

    // Capability Registry
    CapabilityManager.INSTANCE.register(TemperatureCapability.class, new DumbStorage<>(), () -> null);

    TerraFirmaCraft.getNetwork().registerMessage(new TemperaturePacket.Handler(), TemperaturePacket.class, 100, Side.CLIENT);
  }

  @EventHandler
  public void onInit(FMLInitializationEvent event) {

  }

  @EventHandler
  public void onPostInit(FMLPostInitializationEvent event) {
    Recipes.register();
  }
}
