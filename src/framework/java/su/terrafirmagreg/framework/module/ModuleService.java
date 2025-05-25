package su.terrafirmagreg.framework.module;

import su.terrafirmagreg.api.library.EventStateWrapper;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.module.api.IModuleManager;
import su.terrafirmagreg.framework.module.api.IModuleService;

import net.minecraftforge.common.MinecraftForge;
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
import java.util.function.Consumer;


@Getter
public class ModuleService implements IModuleService {

  private final String modId;
  private final ModuleMap map;

  @SuppressWarnings("rawtypes")
  private final Map<Class<? extends FMLStateEvent>, EventStateWrapper> wrapperMap;


  public ModuleService(IModuleManager manager) {
    this.modId = manager.getModId();
    this.map = manager.getMap();

    this.wrapperMap = new Object2ObjectOpenHashMap<>();
    this.wrapperMap.put(FMLConstructionEvent.class, (EventStateWrapper<FMLConstructionEvent>) (event) -> {

      this.fireEvent(module -> {

        module.getLogger().debug("Registering event handlers");
        module.getEventBusSubscribers().forEach(MinecraftForge.EVENT_BUS::register);

        module.getLogger().debug("Construction start");
        module.onConstruction(event);
        module.getLogger().debug("Construction complete");
      });
    });

    this.wrapperMap.put(FMLPreInitializationEvent.class, (EventStateWrapper<FMLPreInitializationEvent>) (event) -> {

      this.fireEvent(module -> {

        Optional.ofNullable(module.getNetworkManager()).ifPresent(network -> {
          module.getLogger().debug("Registering network");
          module.onNetwork(network.getRegistrar());
        });

        Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
          module.getLogger().debug("Registering plugin");
          module.onPlugin(plugin.getRegistrar());
        });

        Optional.ofNullable(module.getFeatureManager()).ifPresent(feature -> {
          module.getLogger().debug("Registering feature");
          module.onFeature(feature.getRegistrar());
        });

        Optional.ofNullable(module.getRegistryManager()).ifPresent(registry -> {
          module.getLogger().debug("Registering registry");
          module.onRegistry(registry.getRegistrar());

          if (ModUtils.isClient()) {
            module.getLogger().debug("Client Registering registry");
            module.onRegistryClient(registry.getRegistrar());
          }
        });

        module.getLogger().debug("Pre-Init start");
        module.onPreInit(event);
        Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
          plugin.getService().onPreInit(event);
        });
        Optional.ofNullable(module.getFeatureManager()).ifPresent(feature -> {
          feature.getService().onPreInit(event);
        });
        module.getLogger().debug("Pre-Init complete");

        if (ModUtils.isClient()) {
          module.getLogger().debug("Client Pre-Init start");
          module.onClientPreInit(event);
          Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
            plugin.getService().onClientPreInit(event);
          });
          Optional.ofNullable(module.getFeatureManager()).ifPresent(feature -> {
            feature.getService().onClientPreInit(event);
          });
          module.getLogger().debug("Client Pre-Init complete");
        }
      });
    });

    this.wrapperMap.put(FMLInitializationEvent.class, (EventStateWrapper<FMLInitializationEvent>) (event) -> {
      this.fireEvent(module -> {
        module.getLogger().debug("Init start");
        module.onInit(event);
        Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
          plugin.getService().onInit(event);
        });
        Optional.ofNullable(module.getFeatureManager()).ifPresent(feature -> {
          feature.getService().onInit(event);
        });
        module.getLogger().debug("Init complete");

        if (ModUtils.isClient()) {
          module.getLogger().debug("Client Init start");
          module.onClientInit(event);
          Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
            plugin.getService().onClientInit(event);
          });
          Optional.ofNullable(module.getFeatureManager()).ifPresent(feature -> {
            feature.getService().onClientInit(event);
          });
          module.getLogger().debug("Client Init complete");
        }
      });
    });

    this.wrapperMap.put(FMLPostInitializationEvent.class, (EventStateWrapper<FMLPostInitializationEvent>) (event) -> {
      this.fireEvent(module -> {
        module.getLogger().debug("Post-Init start");
        module.onPostInit(event);
        Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
          plugin.getService().onPostInit(event);
        });
        Optional.ofNullable(module.getFeatureManager()).ifPresent(feature -> {
          feature.getService().onPostInit(event);
        });
        module.getLogger().debug("Post-Init complete");

        if (ModUtils.isClient()) {
          module.getLogger().debug("Client Post-Init start");
          module.onClientPostInit(event);
          Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
            plugin.getService().onClientPostInit(event);
          });
          Optional.ofNullable(module.getFeatureManager()).ifPresent(feature -> {
            feature.getService().onClientPostInit(event);
          });
          module.getLogger().debug("Client Post-Init complete");
        }
      });
    });

    this.wrapperMap.put(FMLLoadCompleteEvent.class, (EventStateWrapper<FMLLoadCompleteEvent>) (event) -> {
      this.fireEvent(module -> {
        module.getLogger().debug("Load-complete start");
        module.onLoadComplete(event);
        Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
          plugin.getService().onLoadComplete(event);
        });
        Optional.ofNullable(module.getFeatureManager()).ifPresent(feature -> {
          feature.getService().onLoadComplete(event);
        });
        module.getLogger().debug("Load-complete complete");
      });
    });

    this.wrapperMap.put(FMLServerAboutToStartEvent.class, (EventStateWrapper<FMLServerAboutToStartEvent>) (event) -> {
      this.fireEvent(module -> {
        module.getLogger().debug("Server-about-to-start start");
        module.onServerAboutToStart(event);
        Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
          plugin.getService().onServerAboutToStart(event);
        });
        Optional.ofNullable(module.getFeatureManager()).ifPresent(feature -> {
          feature.getService().onServerAboutToStart(event);
        });
        module.getLogger().debug("Server-about-to-start complete");
      });
    });

    this.wrapperMap.put(FMLServerStartingEvent.class, (EventStateWrapper<FMLServerStartingEvent>) (event) -> {
      this.fireEvent(module -> {
        module.getLogger().debug("Server-starting start");
        module.onServerStarting(event);
        Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
          plugin.getService().onServerStarting(event);
        });
        Optional.ofNullable(module.getFeatureManager()).ifPresent(feature -> {
          feature.getService().onServerStarting(event);
        });
        Optional.ofNullable(module.getCommandManager()).ifPresent(command -> {
          module.getLogger().debug("Registering command");
          module.onCommand(command.getRegistrar());
          command.getService().routeEvent(event);
        });
        module.getLogger().debug("Server-starting complete");
      });
    });

    this.wrapperMap.put(FMLServerStartedEvent.class, (EventStateWrapper<FMLServerStartedEvent>) (event) -> {
      this.fireEvent(module -> {
        module.getLogger().debug("Server-started start");
        module.onServerStarted(event);
        Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
          plugin.getService().onServerStarted(event);
        });
        Optional.ofNullable(module.getFeatureManager()).ifPresent(feature -> {
          feature.getService().onServerStarted(event);
        });
        module.getLogger().debug("Server-started complete");
      });
    });

    this.wrapperMap.put(FMLServerStoppingEvent.class, (EventStateWrapper<FMLServerStoppingEvent>) (event) -> {
      this.fireEvent(module -> {
        module.getLogger().debug("Server-stopping start");
        module.onServerStopping(event);
        Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
          plugin.getService().onServerStopping(event);
        });
        Optional.ofNullable(module.getFeatureManager()).ifPresent(feature -> {
          feature.getService().onServerStopping(event);
        });
        module.getLogger().debug("Server-stopping complete");
      });
    });

    this.wrapperMap.put(FMLServerStoppedEvent.class, (EventStateWrapper<FMLServerStoppedEvent>) (event) -> {
      this.fireEvent(module -> {
        module.getLogger().debug("Server-stopped start");
        module.onServerStopped(event);
        Optional.ofNullable(module.getPluginManager()).ifPresent(plugin -> {
          plugin.getService().onServerStopped(event);
        });
        Optional.ofNullable(module.getFeatureManager()).ifPresent(feature -> {
          feature.getService().onServerStopped(event);
        });
        module.getLogger().debug("Server-stopped complete");
      });
    });
  }

  // --------------------------------------------------------------------------
  // - Internal
  // --------------------------------------------------------------------------


  protected void fireEvent(Consumer<IModule> consumer) {

    this.map.values().forEach(wrapper -> consumer.accept(wrapper.getModule()));
  }


  @Override
  public <E extends FMLStateEvent> void routeEvent(E event) {
    var eventClass = event.getClass();
    //noinspection unchecked
    EventStateWrapper<E> route = Preconditions.checkNotNull(
      this.wrapperMap.get(eventClass), "No route found for event: %s", eventClass
    );

    route.route(event);
  }


}
