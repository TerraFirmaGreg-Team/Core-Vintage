package su.terrafirmagreg.framework.module;

import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.module.api.IModuleManager;
import su.terrafirmagreg.framework.module.api.IModuleService;
import su.terrafirmagreg.framework.module.spi.EventState;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import lombok.Getter;

import java.util.Optional;
import java.util.function.Consumer;


@Getter
public class ModuleService implements IModuleService {

  private final String modId;
  private final ModuleMap map;

  public ModuleService(IModuleManager manager) {
    this.modId = manager.getModId();
    this.map = manager.getMap();

  }

  // ===== FML Lifecycle

  @SubscribeEvent
  public void onConstruction(EventState.Construction event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Construction start");

      module.getLogger().debug("Registering event handlers");
      module.getEventBusSubscribers().forEach(MinecraftForge.EVENT_BUS::register);

      Optional.ofNullable(module.getNetworkManager()).ifPresent(network -> {
        module.getLogger().debug("Construction packet");
        module.onPacketRegistrar(network.getRegistrar());
      });

      Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
        module.getLogger().debug("Construction plugin");
        module.onPluginRegistrar(plugin.getRegistrar());
      });

      Optional.ofNullable(module.getFeatureManager()).ifPresent(feature -> {
        module.getLogger().debug("Construction feature");
        module.onFeatureRegistrar(feature.getRegistrar());
      });

      Optional.ofNullable(module.getRegistryManager()).ifPresent(registry -> {
        module.getLogger().debug("Construction registry");
        module.onRegistryRegistrar(registry.getRegistrar());
      });
      module.getLogger().debug("Construction complete");
    });
  }

  @SubscribeEvent
  public void onPreInit(EventState.PreInitialization event) {
    this.fireEvent(module -> {

      module.getLogger().debug("Pre-Init start");
      Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
        plugin.getService().onPreInit();
      });
      module.getLogger().debug("Pre-Init complete");
    });
  }

  @SubscribeEvent
  public void onInit(EventState.Initialization event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Init start");
      Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
        plugin.getService().onInit();
      });
      module.getLogger().debug("Init complete");
    });
  }

  @SubscribeEvent
  public void onPostInit(EventState.PostInitialization event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Post-Init start");
      Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
        plugin.getService().onPostInit();
      });
      module.getLogger().debug("Post-Init complete");
    });
  }

  @SubscribeEvent
  public void onLoadComplete(EventState.LoadComplete event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Load-complete start");
      Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
        plugin.getService().onLoadComplete();
      });
      module.getLogger().debug("Load-complete complete");
    });
  }

  // ===== FML Lifecycle: Server
  @SubscribeEvent
  public void onServerAboutToStart(EventState.ServerAboutToStart event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Server-about-to-start start");
      Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
        plugin.getService().onServerAboutToStart();
      });
      module.getLogger().debug("Server-about-to-start complete");
    });
  }

  @SubscribeEvent
  public void onServerStarting(EventState.ServerStarting event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Server-starting start");
      Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
        plugin.getService().onServerStarting();
      });
      Optional.ofNullable(module.getCommandManager()).ifPresent(command -> {
        module.getLogger().debug("Registering command");
        module.onCommandRegistrar(command.getRegistrar());
        // command.getService().routeEvent(event);
      });
      module.getLogger().debug("Server-starting complete");
    });
  }

  @SubscribeEvent
  public void onServerStarted(EventState.ServerStarted event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Server-started start");
      Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
        plugin.getService().onServerStarted();
      });
      module.getLogger().debug("Server-started complete");
    });
  }

  @SubscribeEvent
  public void onServerStopping(EventState.ServerStopping event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Server-stopping start");
      Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
        plugin.getService().onServerStopping();
      });
      module.getLogger().debug("Server-stopping complete");
    });
  }

  @SubscribeEvent
  public void onServerStopped(EventState.ServerStopped event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Server-stopped start");
      Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
        plugin.getService().onServerStopped();
      });
      module.getLogger().debug("Server-stopped complete");
    });
  }

  // --------------------------------------------------------------------------
  // - Internal
  // --------------------------------------------------------------------------


  protected void fireEvent(Consumer<IModule> consumer) {

    this.map.values().forEach(wrapper -> consumer.accept(wrapper.getModule()));
  }


}
