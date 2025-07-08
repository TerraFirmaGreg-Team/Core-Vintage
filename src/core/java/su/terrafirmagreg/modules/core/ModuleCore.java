package su.terrafirmagreg.modules.core;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.command.api.ICommandRegistrar;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.manager.packet.api.IPacketRegistrar;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.base.BaseModule;
import su.terrafirmagreg.modules.core.event.EventHandlerGuiOpen;
import su.terrafirmagreg.modules.core.event.EventHandlerGuiScreen;
import su.terrafirmagreg.modules.core.event.EventHandlerOnConfigChanged;
import su.terrafirmagreg.modules.core.event.EventHandlerPortalSpawn;
import su.terrafirmagreg.modules.core.event.capabilities.EventHandlerCapabilitiesEntity;
import su.terrafirmagreg.modules.core.event.player.EventHandlerItemTooltip;
import su.terrafirmagreg.modules.core.event.player.EventHandlerPlayerChangedDimension;
import su.terrafirmagreg.modules.core.event.player.EventHandlerPlayerLoggedIn;
import su.terrafirmagreg.modules.core.event.player.EventHandlerPlayerLoggedOut;
import su.terrafirmagreg.modules.core.event.player.EventHandlerPlayerRespawn;
import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.core.init.CommandsCore;
import su.terrafirmagreg.modules.core.init.EffectsCore;
import su.terrafirmagreg.modules.core.init.EntitiesCore;
import su.terrafirmagreg.modules.core.init.FeaturesCore;
import su.terrafirmagreg.modules.core.init.FluidsCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.core.init.LootTablesCore;
import su.terrafirmagreg.modules.core.init.PacketsCore;
import su.terrafirmagreg.modules.core.init.PluginsCore;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@ModuleInfo(
  author = "Xikaro",
  version = "1.0.0",
  description = "Core TerraFirmaGreg content."
)
public class ModuleCore extends BaseModule {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleCore.class);


  public ModuleCore() {
    super("core");

    enableRegistry();
    enableNetwork();
    enableCommand();
    enableFeature();
    enablePlugin();
  }

  @Override
  public void onRegistryRegistrar(IRegistryRegistrar registrar) {
    registrar.group("wand");

    FluidsCore.onRegister(registrar);
    BlocksCore.onRegister(registrar);
    ItemsCore.onRegister(registrar);
    EntitiesCore.onRegister(registrar);
    EffectsCore.onRegister(registrar);
    LootTablesCore.onRegister(registrar);
  }

  @Override
  public void onCommandRegistrar(ICommandRegistrar registrar) {

    CommandsCore.onRegister(registrar);
  }

  @Override
  public void onFeatureRegistrar(IFeatureRegistrar registrar) {

    FeaturesCore.onRegister(registrar);
  }

  @Override
  public void onPluginRegistrar(IPluginRegistrar registrar) {

    PluginsCore.onRegister(registrar);
  }

  @Override
  public void onPacketRegistrar(IPacketRegistrar registrar) {

    PacketsCore.onRegister(registrar);
  }


  @Override
  public @NotNull List<Class<?>> getEventBusSubscribers() {
    return new ObjectArrayList<>() {{
      add(EventHandlerGuiOpen.class);
      add(EventHandlerGuiScreen.class);

      add(EventHandlerPlayerChangedDimension.class);
      add(EventHandlerPlayerLoggedIn.class);
      add(EventHandlerPlayerLoggedOut.class);
      add(EventHandlerPlayerRespawn.class);

      add(EventHandlerCapabilitiesEntity.class);

      add(EventHandlerPortalSpawn.class);

      add(EventHandlerItemTooltip.class);

      add(EventHandlerOnConfigChanged.class);
    }};


  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
