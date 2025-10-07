package su.terrafirmagreg.framework;

import su.terrafirmagreg.api.client.GuiHandler;
import su.terrafirmagreg.api.util.AnnotationUtils;
import su.terrafirmagreg.framework.module.ModuleManager;
import su.terrafirmagreg.framework.module.api.IModuleManager;
import su.terrafirmagreg.framework.module.api.IModuleRegistrar;
import su.terrafirmagreg.framework.module.spi.StateEvent;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLEvent;
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
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import com.google.common.collect.ImmutableMap;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.apache.logging.log4j.Level;

import lombok.Getter;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;


@Getter
public abstract class Framework {

  public static String modId;
  public static String modName;

  private static final FrameworkLogger LOGGER = FrameworkLogger.of(Framework.class);

  private final Map<Class<? extends FMLStateEvent>, Consumer<? super FMLStateEvent>> wrapperMap;
  private final IModuleManager manager;


  protected Framework(String modId, String modName) {
    Framework.modId = Optional.ofNullable(modId).orElse("UnknownModId");
    Framework.modName = Optional.ofNullable(modName).orElse("UnknownModName");

    this.manager = ModuleManager.of(modId);
    this.wrapperMap = new Object2ObjectOpenHashMap<>();
    this.onModuleRegistrar(this.manager.getRegistrar());
    this.manager.onConstruction();
    MinecraftForge.EVENT_BUS.register(this);

    this.initializeEventWrappers();
    this.registerEventBusListener();
    FluidRegistry.enableUniversalBucket();
    GuiHandler.enableGui();

    LOGGER.debug("Framework initialization complete for {} ({})", Framework.modId, Framework.modName);

  }

  public abstract void onModuleRegistrar(IModuleRegistrar registrar);

  @SuppressWarnings({"deprecation", "UnstableApiUsage"})
  private void registerEventBusListener() {
    try {
      Loader loader = Loader.instance();
      LoadController controller = ReflectionHelper.getPrivateValue(Loader.class, loader, "modController");

      ImmutableMap<String, EventBus> eventChannels = ReflectionHelper.getPrivateValue(LoadController.class, controller, "eventChannels");

      EventBus modEventBus = eventChannels.get(modId);
      if (modEventBus != null) {
        modEventBus.register(new Object() {
          @Subscribe
          public void handleEvent(FMLEvent event) {
            if (!(event instanceof FMLStateEvent stateEvent)) {
              return;
            }

            var eventClass = stateEvent.getClass();
            Consumer<? super FMLStateEvent> route = findRoute(eventClass);

            if (route == null) {
              LOGGER.log(Level.WARN, "No route found for event {}", eventClass.getName());
              return;
            }

            try {
              route.accept(stateEvent);
            } catch (Exception e) {
              LOGGER.error(e, "Error while routing event {}", eventClass.getName());
              return;
            }

            wrapperMap.remove(eventClass);
          }
        });
        LOGGER.debug("Registered framework event listener for mod {}", modId);
      }
    } catch (Exception e) {
      LOGGER.error(e, "Failed to register event listener for mod {}", modId);
    }
  }


  private void initializeEventWrappers() {

    registerEventWrapper(FMLConstructionEvent.class, event -> {
      AnnotationUtils.setAsmData(event.getASMHarvestedData());
    });

    registerEventWrapper(FMLPreInitializationEvent.class, event -> MinecraftForge.EVENT_BUS.post(new StateEvent.PreInitialization()));

    registerEventWrapper(FMLInitializationEvent.class, event -> MinecraftForge.EVENT_BUS.post(new StateEvent.Initialization()));

    registerEventWrapper(FMLPostInitializationEvent.class, event -> MinecraftForge.EVENT_BUS.post(new StateEvent.PostInitialization()));

    registerEventWrapper(FMLLoadCompleteEvent.class, event -> MinecraftForge.EVENT_BUS.post(new StateEvent.LoadComplete()));

    registerEventWrapper(FMLServerAboutToStartEvent.class, event -> MinecraftForge.EVENT_BUS.post(new StateEvent.ServerAboutToStart()));

    registerEventWrapper(FMLServerStartingEvent.class, event -> MinecraftForge.EVENT_BUS.post(new StateEvent.ServerStarting(event.getServer())));

    registerEventWrapper(FMLServerStartedEvent.class, event -> MinecraftForge.EVENT_BUS.post(new StateEvent.ServerStarted()));

    registerEventWrapper(FMLServerStoppingEvent.class, event -> MinecraftForge.EVENT_BUS.post(new StateEvent.ServerStopping()));

    registerEventWrapper(FMLServerStoppedEvent.class, event -> MinecraftForge.EVENT_BUS.post(new StateEvent.ServerStopped()));
  }


  private <T extends FMLStateEvent> void registerEventWrapper(Class<T> eventClass, Consumer<T> wrapper) {

    Consumer<? super FMLStateEvent> previous = wrapperMap.putIfAbsent(eventClass, event -> wrapper.accept(eventClass.cast(event)));
    if (previous != null) {
      LOGGER.log(Level.WARN, "Duplicate wrapper registration for {} was ignored", eventClass.getName());
    }
  }

  private Consumer<? super FMLStateEvent> findRoute(Class<? extends FMLStateEvent> eventClass) {
    Consumer<? super FMLStateEvent> route = wrapperMap.get(eventClass);
    if (route != null) {
      return route;
    }

    Class<?> superclass = eventClass.getSuperclass();
    while (superclass != null && FMLStateEvent.class.isAssignableFrom(superclass)) {
      @SuppressWarnings("unchecked")
      Class<? extends FMLStateEvent> stateSuperclass = (Class<? extends FMLStateEvent>) superclass;
      route = wrapperMap.get(stateSuperclass);
      if (route != null) {
        wrapperMap.put(eventClass, route);
        return route;
      }
      superclass = superclass.getSuperclass();
    }

    return null;
  }

}
