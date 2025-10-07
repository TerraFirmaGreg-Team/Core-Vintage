package su.terrafirmagreg.modules.core;

import su.terrafirmagreg.framework.FrameworkLogger;
import su.terrafirmagreg.framework.manager.command.api.ICommandRegistrar;
import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.manager.packet.api.IPacketRegistrar;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.base.BaseModule;
import su.terrafirmagreg.modules.core.event.EventHandlerGuiOpen;
import su.terrafirmagreg.modules.core.event.EventHandlerGuiScreen;
import su.terrafirmagreg.modules.core.event.EventHandlerOnConfigChanged;
import su.terrafirmagreg.modules.core.event.EventHandlerPortalSpawn;
import su.terrafirmagreg.modules.core.event.block.EventHandlerBlock;
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

import org.jetbrains.annotations.NotNull;

@ModuleInfo(
  author = "Xikaro",
  version = "1.0.0",
  description = "Core TerraFirmaGreg content."
)
public class ModuleCore extends BaseModule {

  public static final FrameworkLogger LOGGER = FrameworkLogger.of(ModuleCore.class);


  public ModuleCore() {
    super(ModuleSettings.of()
      .registryKey("core")
      .addSubscription(
        EventHandlerBlock.class,
        EventHandlerGuiOpen.class,
        EventHandlerGuiScreen.class,
        EventHandlerPlayerChangedDimension.class,
        EventHandlerPlayerLoggedIn.class,
        EventHandlerPlayerLoggedOut.class,
        EventHandlerPlayerRespawn.class,
        EventHandlerPortalSpawn.class,
        EventHandlerItemTooltip.class,
        EventHandlerOnConfigChanged.class
      )
    );
  }

  @Override
  public void onRegistryRegistrar(IContentRegistrar registrar) {
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
  public @NotNull FrameworkLogger getLogger() {
    return LOGGER;
  }
}
