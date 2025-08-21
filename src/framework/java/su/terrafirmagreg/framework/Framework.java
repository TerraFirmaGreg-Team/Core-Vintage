package su.terrafirmagreg.framework;

import su.terrafirmagreg.api.client.GuiHandler;
import su.terrafirmagreg.api.util.AnnotationUtils;
import su.terrafirmagreg.framework.module.ModuleManager;
import su.terrafirmagreg.framework.module.api.IModuleManager;
import su.terrafirmagreg.framework.module.api.IModuleRegistrar;
import su.terrafirmagreg.framework.module.spi.StateEvent;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
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

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Getter;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;


@Getter
public abstract class Framework {

  public static String modId;
  public static String modName;

  @SuppressWarnings("rawtypes")
  private final Map<Class<? extends FMLStateEvent>, Consumer> wrapperMap;
  private final IModuleManager manager;


  protected Framework(String modId, String modName) {
    Framework.modId = Optional.ofNullable(modId).orElse("UnknownModId");
    Framework.modName = Optional.ofNullable(modName).orElse("UnknownModName");

    this.manager = ModuleManager.of(modId);
    this.wrapperMap = new Object2ObjectOpenHashMap<>();
    this.onModuleRegistrar(this.manager.getRegistrar());
    this.initializeEventWrappers();
  }

  public abstract void onModuleRegistrar(IModuleRegistrar registrar);


  private void initializeEventWrappers() {

    registerEventWrapper(FMLConstructionEvent.class, event -> {
      AnnotationUtils.setAsmData(event.getASMHarvestedData());
      FluidRegistry.enableUniversalBucket();
      GuiHandler.enableGui();
    });

    registerEventWrapper(FMLPreInitializationEvent.class, event -> {
      this.manager.onConstruction();
      MinecraftForge.EVENT_BUS.post(new StateEvent.PreInitialization());
    });

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

    wrapperMap.put(eventClass, wrapper);
  }


  public <E extends FMLStateEvent> void routeEvent(E event) {
    var eventClass = event.getClass();

    //noinspection unchecked
    Consumer<E> route = Optional.ofNullable(wrapperMap.get(eventClass))
      .orElseThrow(() -> new IllegalArgumentException("No route found for event: " + eventClass));

    route.accept(event);
  }


}
