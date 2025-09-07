package su.terrafirmagreg.framework.module;


import su.terrafirmagreg.framework.FrameworkLogger;
import su.terrafirmagreg.framework.manager.command.CommandManager;
import su.terrafirmagreg.framework.manager.content.ContentManager;
import su.terrafirmagreg.framework.manager.feature.FeatureManager;
import su.terrafirmagreg.framework.manager.packet.PacketManager;
import su.terrafirmagreg.framework.manager.plugin.PluginManager;
import su.terrafirmagreg.framework.module.api.IModuleEntry;
import su.terrafirmagreg.framework.module.api.IModuleManager;
import su.terrafirmagreg.framework.module.api.IModuleRegistrar;

import net.minecraftforge.common.MinecraftForge;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import lombok.Getter;

import java.util.Map;
import java.util.function.Consumer;

@Getter
public class ModuleManager implements IModuleManager {

  public static final FrameworkLogger LOGGER = FrameworkLogger.of(ModuleManager.class);

  private final String modId;
  private final Map<Class<?>, IModuleEntry> map;

  private final IModuleRegistrar registrar;


  private ModuleManager(String modId) {

    this.modId = modId;
    this.map = new Object2ObjectLinkedOpenHashMap<>();

    this.registrar = new ModuleRegistrar(this);

    MinecraftForge.EVENT_BUS.register(this);
  }


  public static synchronized IModuleManager of(String modId) {

    return MANAGER_MAP.computeIfAbsent(modId, ModuleManager::new);
  }


  @Override
  public void onConstruction() {
    this.fireEvent(module -> {
      var settings = module.getSettings();
      module.getLogger().debug("Construction start");

      if (settings.isSubscriptionEnabled()) {
        module.getLogger().debug("Registering event handlers");
        module.getEventBusSubscribers().forEach(MinecraftForge.EVENT_BUS::register);
      }

      if (settings.isPacketManagerEnabled()) {
        module.getLogger().debug("Construction packet");
        module.onPacketRegistrar(PacketManager.of(module).getRegistrar());
      }

      if (settings.isPluginManagerEnabled()) {
        module.getLogger().debug("Construction plugin");
        module.onPluginRegistrar(PluginManager.of(module).getRegistrar());
      }

      if (settings.isFeatureManagerEnabled()) {
        module.getLogger().debug("Construction feature");
        module.onFeatureRegistrar(FeatureManager.of(module).getRegistrar());
      }

      if (settings.isRegistryManagerEnabled()) {
        module.getLogger().debug("Construction registry");
        module.onRegistryRegistrar(ContentManager.of(module).getRegistrar());
      }

      if (settings.isCommandManagerEnabled()) {
        module.getLogger().debug("Construction command");
        module.onCommandRegistrar(CommandManager.of(module).getRegistrar());
      }

      module.getLogger().debug("Construction complete");
    });
  }

  @Override
  public FrameworkLogger getLogger() {
    return LOGGER;
  }

  // --------------------------------------------------------------------------
  // - Internal
  // --------------------------------------------------------------------------


  protected void fireEvent(Consumer<IModuleEntry> consumer) {

    this.map.values().forEach(consumer);
  }

}

