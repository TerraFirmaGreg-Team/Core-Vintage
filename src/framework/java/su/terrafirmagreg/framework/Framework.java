package su.terrafirmagreg.framework;

import su.terrafirmagreg.api.client.GuiHandler;
import su.terrafirmagreg.api.library.EventStateWrapper;
import su.terrafirmagreg.framework.module.ModuleManager;
import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.module.api.IModuleManager;
import su.terrafirmagreg.framework.module.spi.EventState;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
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
import net.minecraftforge.fml.common.event.FMLStateEvent;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Getter;

import java.util.Map;
import java.util.Optional;


@Getter
public abstract class Framework {

  public static String modId;
  public static String modName;
  public static ASMDataTable asmData;

  @SuppressWarnings("rawtypes")
  private final Map<Class<? extends FMLStateEvent>, EventStateWrapper> wrapperMap;
  private final IModuleManager manager;


  protected Framework(String modId, String modName) {
    Framework.modId = Optional.ofNullable(modId).orElse("UnknownModId");
    Framework.modName = Optional.ofNullable(modName).orElse("UnknownModName");

    this.manager = ModuleManager.of(modId);
    this.wrapperMap = new Object2ObjectOpenHashMap<>();
    MinecraftForge.EVENT_BUS.register(this);
    initializeEventWrappers();
  }

  private void initializeEventWrappers() {
    registerEventWrapper(FMLConstructionEvent.class, event -> {
      MinecraftForge.EVENT_BUS.post(new EventState.Construction());
    });

    registerEventWrapper(FMLPreInitializationEvent.class, event -> {
      MinecraftForge.EVENT_BUS.post(new EventState.PreInitialization());
    });

    registerEventWrapper(FMLInitializationEvent.class, event -> {
      MinecraftForge.EVENT_BUS.post(new EventState.Initialization());
    });

    registerEventWrapper(FMLPostInitializationEvent.class, event -> {
      MinecraftForge.EVENT_BUS.post(new EventState.PostInitialization());
    });

    registerEventWrapper(FMLLoadCompleteEvent.class, event -> {
      MinecraftForge.EVENT_BUS.post(new EventState.LoadComplete());
    });

    registerEventWrapper(FMLServerAboutToStartEvent.class, event -> {
      MinecraftForge.EVENT_BUS.post(new EventState.ServerAboutToStart());
    });

    registerEventWrapper(FMLServerStartingEvent.class, event -> {
      MinecraftForge.EVENT_BUS.post(new EventState.ServerStarting(event.getServer()));
    });

    registerEventWrapper(FMLServerStartedEvent.class, event -> {
      MinecraftForge.EVENT_BUS.post(new EventState.ServerStarted());
    });

    registerEventWrapper(FMLServerStoppingEvent.class, event -> {
      MinecraftForge.EVENT_BUS.post(new EventState.ServerStopping());
    });

    registerEventWrapper(FMLServerStoppedEvent.class, event -> {
      MinecraftForge.EVENT_BUS.post(new EventState.ServerStopped());
    });
  }

  private <T extends FMLStateEvent> void registerEventWrapper(Class<T> eventClass, EventStateWrapper<T> wrapper) {
    wrapperMap.put(eventClass, wrapper);
  }

  protected void setup(FMLConstructionEvent event) {
    Framework.asmData = event.getASMHarvestedData();
    FluidRegistry.enableUniversalBucket();
    GuiHandler.enableGui();
  }


  protected <T extends IModule> void addModule(T module) {
    Preconditions.checkNotNull(module, "Module cannot be null");
    this.manager.getRegistrar().addModule(module);
  }

  public <E extends FMLStateEvent> void routeEvent(E event) {
    var eventClass = event.getClass();

    //noinspection unchecked
    EventStateWrapper<E> route = Optional.ofNullable(wrapperMap.get(eventClass))
      .orElseThrow(() -> new IllegalArgumentException("No route found for event: " + eventClass));

    route.route(event);
  }


}
