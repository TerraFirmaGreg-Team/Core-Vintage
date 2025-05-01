package su.terrafirmagreg;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.Framework;
import su.terrafirmagreg.modules.animal.ModuleAnimal;
import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.device.ModuleDevice;
import su.terrafirmagreg.modules.food.ModuleFood;
import su.terrafirmagreg.modules.integration.ModuleIntegration;
import su.terrafirmagreg.modules.rock.ModuleRock;
import su.terrafirmagreg.proxy.IProxy;

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

import static su.terrafirmagreg.Tags.CLIENT_PROXY;
import static su.terrafirmagreg.Tags.DEPENDENCIES;
import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;
import static su.terrafirmagreg.Tags.MOD_VERSION;
import static su.terrafirmagreg.Tags.SERVER_PROXY;

@SuppressWarnings("unused")
@Mod(modid = MOD_ID, name = MOD_NAME, version = MOD_VERSION, dependencies = DEPENDENCIES)
public class TerraFirmaGreg extends Framework {

  public static final LoggingHelper LOGGER = LoggingHelper.of();

  @SidedProxy(modId = MOD_ID, clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
  public static IProxy PROXY;

  @Mod.Instance(MOD_ID)
  public static TerraFirmaGreg INSTANCE;


  public TerraFirmaGreg() {
    super(MOD_ID);

    this.addModule(new ModuleCore());
    this.addModule(new ModuleRock());
    this.addModule(new ModuleDevice());
    this.addModule(new ModuleAnimal());
    this.addModule(new ModuleFood());
    this.addModule(new ModuleIntegration());
  }

  @EventHandler
  public void onConstruction(FMLConstructionEvent event) {

    this.configure(event);
    this.routeEvent(event);
  }

  @EventHandler
  public void onPreInit(FMLPreInitializationEvent event) {

    this.routeEvent(event);
  }

  @EventHandler
  public void onInit(FMLInitializationEvent event) {

    this.routeEvent(event);
  }

  @EventHandler
  public void onPostInit(FMLPostInitializationEvent event) {

    this.routeEvent(event);
  }

  @EventHandler
  public void onLoadComplete(FMLLoadCompleteEvent event) {

    this.routeEvent(event);
  }

  @EventHandler
  public void onServerAboutToStart(FMLServerAboutToStartEvent event) {

    this.routeEvent(event);
  }

  @EventHandler
  public void onServerStarting(FMLServerStartingEvent event) {

    this.routeEvent(event);
  }

  @EventHandler
  public void onServerStarted(FMLServerStartedEvent event) {

    this.routeEvent(event);
  }

  @EventHandler
  public void onServerStopping(FMLServerStoppingEvent event) {

    this.routeEvent(event);
  }

  @EventHandler
  public void onServerStopped(FMLServerStoppedEvent event) {

    this.routeEvent(event);
  }
}
