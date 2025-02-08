package su.terrafirmagreg;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.api.util.AnnotationUtils;
import su.terrafirmagreg.framework.module.ModuleManager;
import su.terrafirmagreg.framework.module.api.IModuleManager;
import su.terrafirmagreg.proxy.IProxy;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

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
  @SidedProxy(modId = MOD_ID, clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
  public static IProxy proxy;

  @Getter
  @Mod.Instance(MOD_ID)
  private static TerraFirmaGreg instance;

  private static IModuleManager moduleManager;

  public TerraFirmaGreg() {
    FluidRegistry.enableUniversalBucket();
  }

  @EventHandler
  public void onConstruction(FMLConstructionEvent event) {
    AnnotationUtils.configureAsmData(event);
    moduleManager = ModuleManager.of(MOD_ID);
    moduleManager.routeEvent(event);
  }

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {

    moduleManager.routeEvent(event);
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {

    moduleManager.routeEvent(event);
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {

    moduleManager.routeEvent(event);
  }

  @EventHandler
  public void loadComplete(FMLLoadCompleteEvent event) {

    moduleManager.routeEvent(event);
  }

  @EventHandler
  public void serverAboutToStart(FMLServerAboutToStartEvent event) {

    moduleManager.routeEvent(event);
  }

  @EventHandler
  public void serverStarting(FMLServerStartingEvent event) {

    moduleManager.routeEvent(event);
  }

  @EventHandler
  public void serverStarted(FMLServerStartedEvent event) {

    moduleManager.routeEvent(event);
  }

  @EventHandler
  public void serverStopping(FMLServerStoppingEvent event) {

    moduleManager.routeEvent(event);
  }

  @EventHandler
  public void serverStopped(FMLServerStoppedEvent event) {

    moduleManager.routeEvent(event);
  }
}
